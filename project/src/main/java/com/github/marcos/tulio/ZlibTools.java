package com.github.marcos.tulio;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.GZIPException;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;

/**
 *
 * @author MrCapybara
 */
public class ZlibTools {

    private static final String DESC_NAME_END = "_descompressed";
    private static final String COMP_NAME_END = "_compressed";

    private static final byte[] HEADER = {0x02, 0x10, (byte) 0x81, 0x57, 0x45, 0x53, 0x59, 0x53};

    public static boolean descompress(String path, boolean isBig) {
        try {
            //Create a vector with all bytes of the file
            byte[] c = Files.readAllBytes(new File(path).toPath());

            byte[] cLenght;
            byte[] dLenght;

            if (isBig){
                cLenght = new byte[]{ c[8], c[9], c[10], c[11] };
                dLenght = new byte[]{ c[12], c[13], c[14], c[15] };

            } else {
                cLenght = new byte[]{ c[11], c[10], c[9], c[8] };
                dLenght = new byte[]{ c[15], c[14], c[13], c[12] };
            }

            
            
            //Reads the size of files
            int compressLenght = ByteBuffer.wrap(cLenght).getInt();
            int descomprLenght = ByteBuffer.wrap(dLenght).getInt();

            //Remove header
            c = Arrays.copyOfRange(c, 16, c.length);

            //Create output vector
            byte[] descompress = new byte[descomprLenght];

            //Descompress file
            Inflater inflater = new Inflater();
            inflater.setInput(c);
            inflater.setOutput(descompress);

            while (inflater.total_out < descomprLenght && inflater.total_in < compressLenght) {
                inflater.avail_in = inflater.avail_out = 1;
                if (inflater.inflate(JZlib.Z_NO_FLUSH) == JZlib.Z_STREAM_END) {
                    break;
                }
            }

            inflater.end();
            //End descompress file

            FileOutputStream fos;
            //Save descompress file in a new file
            fos = new FileOutputStream(path.concat(DESC_NAME_END));
            fos.write(descompress);
            fos.close();

            return true;
        } catch (GZIPException e) {
            return false;
        } catch (IOException | java.lang.ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    public static boolean compress(String path, boolean isBig) {
        try {
            //Create a vector with all bytes of the file
            byte[] descompress = Files.readAllBytes(new File(path).toPath());

            //Create a vector that will receive output bytes
            byte[] compress = new byte[descompress.length];

            //Compress file
            Deflater deflater = new Deflater(JZlib.Z_BEST_COMPRESSION);
            deflater.params(JZlib.Z_BEST_COMPRESSION, JZlib.Z_DEFAULT_STRATEGY);

            deflater.setInput(descompress);
            deflater.setOutput(compress);

            while (deflater.total_in != descompress.length && deflater.total_out < compress.length) {
                deflater.avail_in = deflater.avail_out = 1; // force small buffers
                deflater.deflate(JZlib.Z_NO_FLUSH);
            }

            while (true) {
                deflater.avail_out = 1;
                if (deflater.deflate(JZlib.Z_FINISH) == JZlib.Z_STREAM_END) {
                    break;
                }
            }
            deflater.end();
            //End compress file

            //Remove clear bytes
            int offset;
            for (offset = (compress.length - 1); offset > 0; offset--) {
                if (compress[offset] != 0x00) {
                    offset++;
                    break;
                }
            }

            //Save bytes in a new file
            OutputStream stream = new FileOutputStream(path.concat(COMP_NAME_END));
            DataOutputStream out = new DataOutputStream(stream);

            byte[] dLenght =  ByteBuffer.allocate(4).putInt(descompress.length).array();
            byte[] cLenght =  ByteBuffer.allocate(4).putInt(offset).array();

            //Save header
            out.write(HEADER);

            //Save compress and descompress size
            if (isBig){
                //Save compress size
                for (int i = 0; i < cLenght.length; i--) out.write(cLenght[i]);
                //Save descompress size            
                for (int i = 0; i < dLenght.length; i--) out.write(dLenght[i]);

            } else {
                //Save compress size
                for (int i = cLenght.length - 1; i >= 0; i--) out.write(cLenght[i]);
                //Save descompress size            
                for (int i = dLenght.length - 1; i >= 0; i--) out.write(dLenght[i]);
            }

            //Save compressed file
            out.write(compress, 0, offset);

            out.flush();
            out.close();
            stream.flush();
            stream.close();
            //End save file

            return true;
        } catch (GZIPException e) {
            return false;
        } catch (IOException | java.lang.ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

}
