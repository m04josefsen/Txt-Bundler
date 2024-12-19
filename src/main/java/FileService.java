import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class FileService {

    private static Logger logger;
    private static Queue<String> txtQueue;
    private static FileWriter newTxt;

    public static void traverseDirectory(File directory) {
        logger = Logger.getLogger("FileService");
        txtQueue = new LinkedList<>();

        boolean checkSubdirectories = checkSubdirectories();

        addTxtToQueue(directory, checkSubdirectories);

        // Initializes new txt file
        try {
            newTxt = new FileWriter("navn" + ".txt");
        }
        catch (Exception e) {
            logger.severe("Error while creating file: " + e.getMessage());
        }

        processTxtFiles();

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
                    System.out.println(fileEntry.getName());
                    txtQueue.add(fileEntry.getAbsolutePath());
                }
            }
        }
    }

    // Method to proccess all txt files form listFilesForFolder
    private static void processTxtFiles() {
        final File file = new File(txtQueue.peek());

        // System.out.println("Proccessing file: " + file.getName());

        StringBuilder builder = new StringBuilder();

        // Scanner reads throught txt file
        try {
            Scanner s = new Scanner(file);

            while(s.hasNextLine()) {
                builder.append(s.nextLine());
                builder.append(System.lineSeparator());
            }

            s.close();
        }
        catch (Exception e) {
            logger.severe("Error while processing file: " + e.getMessage());
        }

        // Removes txt file from queue, if empty stop recursion
        txtQueue.remove();
        writeTxtFile(builder);

        if(!txtQueue.isEmpty()) {
            processTxtFiles();
        }
    }

    // Writes to txt file based on StringBuilder
    private static void writeTxtFile(StringBuilder txtContent) {
        try {
            newTxt.write(txtContent.toString());
        }
        catch (Exception e) {
            logger.severe("Error while writing to file: " + e.getMessage());
        }
    }

    private static boolean checkSubdirectories() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to check all subdirectories for txt files? (y/n)");

            String input = scanner.nextLine();

            if(input.equals("y")) {
                return true;
            }
            else if (input.equals("n")) {
                return false;
            }
            else {
                // TODO: noe annen check her som looper tilbake
                return false;
            }
        }
        catch (Exception e) {
            // TODO: noe mer check for input ting her
            logger.severe(e.getMessage());
        }

        return false;
    }
}
