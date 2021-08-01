package com.github.marcos.tulio;

public class App{
    public static void main(String[] args) {
        System.out.println("\n *** Zlib tools for PES 2018 by Marcos Santos ***\n");

        if (args == null || args.length != 2){
            System.err.println("Usage: " +
                "\n [-d] [file_path] -> decompress file" + 
                "\n [-c] [file_path] -> compress file" +

                "\n\n [-db] [file_path] -> decompress file reading sizes in big endian format" +
                "\n [-cb] [file_path] -> compress file and save sizes in big endian format"
            );
            System.exit(0);
        }

        if (args[0].equalsIgnoreCase("-d")){
            if (ZlibTools.descompress(args[1], false))
                System.out.println("Descompressed successfully!");
            else
                System.out.println("Error descompressing!");

        } else if (args[0].equalsIgnoreCase("-c")){
            if (ZlibTools.compress(args[1], false))
                System.out.println("Compressed successfully!");
            else
                System.out.println("Error compressing!");

        } else if (args[0].equalsIgnoreCase("-de")){
            if (ZlibTools.descompress(args[1], true))
                System.out.println("Descompressed with big endian successfully!");
            else
                System.out.println("Error descompressing with big endian!");

        } else if (args[0].equalsIgnoreCase("-ce")){
            if (ZlibTools.compress(args[1], true))
                System.out.println("Compressed with big endian successfully!");
            else
                System.out.println("Error compressing with big endian!");
        }
    }
}
