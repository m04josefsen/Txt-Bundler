import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.logging.Logger;

public class FileService {

    // Using an ArrayList would be more effective, but fun to use other data structures
    private static Queue<String> txtQueue;
    private static Logger logger;
    private static FileWriter newTxt;

    public static void traverseDirectory() {
        logger = Logger.getLogger("FileService");
        txtQueue = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        String filePath = filePath(scanner);
        File file = new File(filePath);

        boolean checkSubdirectories = checkSubdirectories(scanner);
        boolean includeTableOfContents = includeTableOfContents(scanner);
        boolean createPdf = createPdf(scanner);
        String fileName = fileName(scanner);
        scanner.close();

        addTxtToQueue(file, checkSubdirectories);

        if(txtQueue.isEmpty()) {
            System.out.println("No files found in the directory");
            return;
        }

        CreateTxt createTxt = new CreateTxt();

        // Initializes new txt file
        try {
            newTxt = createTxt.createTxt(includeTableOfContents, txtQueue, fileName);
        }
        catch (Exception e) {
            logger.severe("Error while creating file: " + e.getMessage());
        }

        if(createPdf) {
            // TODO: temp emd absolute path her
            String path = "/Users/mjosefsen/Developer/Java/TxtBundler/" + fileName + ".txt";

            CreatePdf.createPdf(path, fileName);
        }

        // Closes txt file after creating
        try {
            newTxt.close();
        }
        catch (Exception e) {
            logger.severe("Error while closing file: " + e.getMessage());
        }
    }

    // Method to get all files in folder
    private static void addTxtToQueue(final File folder, boolean checkSubdirectories) {
        for(final File fileEntry : folder.listFiles()) {
            // If is folder, call method again
            if(fileEntry.isDirectory() && checkSubdirectories) {
                addTxtToQueue(fileEntry, true);
            }
            else {
                // System.out.println(fileEntry.getName());

                if(fileEntry.getName().endsWith(".txt")) {
                    // System.out.println(fileEntry.getName());
                    txtQueue.add(fileEntry.getAbsolutePath());
                }
            }
        }
    }

    // Gets the filepath for directory to check for TXTs
    private static String filePath(Scanner scanner) {
        System.out.println("Please paste the absolute path of the directory containing the .txt files:");
        return scanner.nextLine();
    }

    // Checks users input if they want to check all subdirectories
    private static boolean checkSubdirectories(Scanner scanner) {
        while (true) {
            System.out.println("Do you want to check all subdirectories for txt files (y/n)");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    // Checks users input if they want Table of Contents
    private static boolean includeTableOfContents(Scanner scanner) {
        while (true) {
            System.out.println("Do you want to add Table of Contents (y/n)");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    // Checks users input if they want Table of Contents
    private static boolean createPdf(Scanner scanner) {
        while (true) {
            System.out.println("Do you want to also create a PDF document (y/n)");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    // Checks user input for filename
    private static String fileName(Scanner scanner) {
        System.out.println("What do you want to name the file? (do not include .filetype)");
        String input = scanner.nextLine().toLowerCase();

        return input;
    }

    private static void test() {
        File f = new File("test.txt");
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(Path.of(f.getAbsolutePath()), "creationTime");
        }
        catch (Exception e) {
            System.out.println("lol");
        }
    }
}