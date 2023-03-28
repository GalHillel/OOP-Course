package EX2_1;

import java.io.*;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EX2_1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long startTime, endTime;
        int numberOfFiles;
        int maxNumberOfLines;

        System.out.println("Enter number of files to be created: ");
        numberOfFiles = scanner.nextInt();
        System.out.println("Enter maximum number of lines in each file: ");
        maxNumberOfLines = scanner.nextInt();

        System.out.println("Creating files...");
        String[] fileNames = createTextFiles(numberOfFiles, 2407, maxNumberOfLines);
        System.out.println("Checking " + numberOfFiles + " files with " + maxNumberOfLines + " maximum lines each:");

        // Test getNumOfLines method
        System.out.println("Testing getNumOfLines method:");
        startTime = System.currentTimeMillis();
        System.out.println("Num of lines in all files: " + getNumOfLines(fileNames));
        endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        System.out.println();

        // Test getNumOfLinesThreads method
        System.out.println("Testing getNumOfLinesThreads method:");
        startTime = System.currentTimeMillis();
        try {
            System.out.println("Num of lines in all files using threads: " + getNumOfLinesThreads(fileNames));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        System.out.println();

        // Test getNumOfLinesThreadPool method
        System.out.println("Testing getNumOfLinesThreadPool method:");
        startTime = System.currentTimeMillis();
        try {
            System.out.println("Num of lines in all files using threadPool: " + getNumOfLinesThreadPool(fileNames));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        System.out.println();

        deleteAllFiles(fileNames);

        scanner.close();
    }

    /**
     * The createTextFiles method creates n text files, each containing the string
     * "HELLO WORLD".
     *
     * @param n     Number of text files to create.
     * @param seed  Seed for a random number generator used to create the text
     *              files.
     * @param bound The upper bound (exclusive). Must be positive.
     * @return Array of strings, each element of which is the contents of one of the
     *         text files.
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] fileNames = new String[n];
        Random random = new Random(seed);

        for (int i = 0; i < n; i++) {
            String fileName = "file" + i + 1 + ".txt";
            fileNames[i] = fileName;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                int numLines = random.nextInt(bound) + 1;
                for (int j = 0; j < numLines; j++) {
                    bw.write("Hello World");
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileNames;
    }

    /**
     * The getNumOfLines method takes in an array of file names and counts the
     * number of lines in each file,
     * by reading the file line by line until the end of the file is reached.
     *
     * @param fileNames The file paths.
     * @return Total number of lines in all the files.
     */
    public static int getNumOfLines(String[] fileNames) {
        int numLines = 0;
        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while (reader.readLine() != null) {
                    numLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numLines;
    }

    /**
     * The getNumOfLinesThreads method works similarly to getNumOfLines,
     * but it uses multiple threads to count the lines in the files.
     * The method creates a LineCounter thread for each file, starts the threads,
     * and then waits for them to finish.
     * The LineCounter class extends the Thread class and overrides the run method
     * to count the lines in a single file.
     *
     * @param fileNames The file paths.
     * @return Total number of lines in all the files.
     */
    public static int getNumOfLinesThreads(String[] fileNames) throws InterruptedException {
        LineCounter[] counters = new LineCounter[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            counters[i] = new LineCounter(fileNames[i]);
            counters[i].start();
        }
        int numLines = 0;
        for (LineCounter counter : counters) {
            counter.join();
            numLines += counter.getNumLines();
        }
        return numLines;
    }

    static class LineCounter extends Thread {
        private final String fileName;
        private int numLines;

        public LineCounter(String fileName) {
            this.fileName = fileName;
        }

        public int getNumLines() {
            return numLines;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while (reader.readLine() != null) {
                    numLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The getNumOfLinesThreadPool method also uses multiple threads to count the
     * lines in the files,
     * but it uses a thread pool and the Callable interface to do so.
     * The method creates a LineCounterPool object for each file and submits it to
     * an executor service (a thread pool).
     * The LineCounterPool class implements the Callable interface and has a call
     * method that counts the lines in a single file.
     * The method then waits for the results of the Callable tasks and returns the
     * total number of lines in all the files.
     *
     * @param fileNames The file paths.
     * @return Total number of lines in all the files.
     */
    public static int getNumOfLinesThreadPool(String[] fileNames)
            throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(fileNames.length);
        List<Future<Integer>> futures = new ArrayList<>();
        for (String fileName : fileNames) {
            futures.add(executor.submit(new LineCounterPool(fileName)));
        }
        int numLines = 0;
        for (Future<Integer> future : futures) {
            numLines += future.get();
        }
        executor.shutdown();
        return numLines;
    }

    static class LineCounterPool implements Callable<Integer> {
        private final String fileName;

        public LineCounterPool(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public Integer call() {
            int numLines = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while (reader.readLine() != null) {
                    numLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return numLines;
        }
    }

    /**
     * This function delete all files
     * For the main
     */
    public static void deleteAllFiles(String[] filesNames) {
        for (String fileName : filesNames) {
            File file = new File(fileName);
            file.delete();
        }
    }
}
