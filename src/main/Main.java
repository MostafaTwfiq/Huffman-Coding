package main;


import Huffman.*;

import java.io.File;
import java.nio.file.Files;

public class Main {

    private static void printErrorAndExit() {
        System.out.println("Invalid input.");
        System.exit(-1);
    }

    public static void main (String[] args) {
        try {

            if (args.length != 3 && args.length != 2) {
                printErrorAndExit();
            }

            if (args[0].equals("c")) {
                long startTime = System.currentTimeMillis();

                long beforeCompSize = Files.size((new File(args[1])).toPath());

                FileCompressor compressor = new FileCompressor(args[1], Integer.parseInt(args[2]));
                String compressedFilePath = compressor.compressFile();
                System.out.println(compressedFilePath);
                long afterCompSize = Files.size((new File(compressedFilePath)).toPath());

                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println("Compression ratio: " + (beforeCompSize / (afterCompSize * 1.0)));
                System.out.println("Time: " + elapsedTime / 1000.0 + " s");

            } else if (args[0].equals("d")) {
                long startTime = System.currentTimeMillis();

                FileDecompressor decompressor = new FileDecompressor(args[1]);
                decompressor.decompressFile();

                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println("Time: " + elapsedTime / 1000.0 + " s");

            } else {
                printErrorAndExit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
