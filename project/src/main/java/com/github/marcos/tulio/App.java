package com.github.marcos.tulio;

public class App{
    public static void main(String[] args) {
        System.out.println("\n *** Zlib tools for PES 2018 by Marcos Santos ***\n");

        if (args == null || args.length != 2){
            System.err.println("Usage: " +
                "\n [-d] [file_path] -> decompress file" + 
                "\n [-c] [file_path] -> compress file" +

                "\n\n [-db] [file_path] -> decompress file reading sizes in big endian format" +
                "\n [-cb] [file_path] -> compress file and save sizes in big endian format" + 

                "\n\n [-cdds] [file_path] -> compress file and save dds default header (PS3)"
            );
            System.exit(0);
        }

        switch(args[0].toLowerCase()){
            /** default descompress */
            case "-d":
                if (ZlibTools.descompress(args[1], false)) 
                    System.out.println("Descompressed successfully!");
                else 
                    System.out.println("Error descompressing!");
                break;

            /** default compress and alter header */
            case "-cdds":
                ZlibTools.header = new byte[]{(byte) 0xFF, 0x10, 0x01, 0x57, 0x45, 0x53, 0x59, 0x53};
                System.out.println("Header modded!");

            /** default compress */
            case "-c":
                if (ZlibTools.compress(args[1], false))
                    System.out.println("Compressed successfully!");
                else
                    System.out.println("Error compressing!");
                break;

            /** descompress big endian */
            case "-de":
                if (ZlibTools.descompress(args[1], true))
                    System.out.println("Descompressed with big endian successfully!");
                else
                    System.out.println("Error descompressing with big endian!");
                break;

            /** compress big endian */
            case "-ce":
                if (ZlibTools.compress(args[1], true))
                    System.out.println("Compressed with big endian successfully!");
                else
                    System.out.println("Error compressing with big endian!");
        }
    }
}
