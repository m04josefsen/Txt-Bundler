import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class FileService {

    private static Logger logger;
    private static Queue<String> txtQueue;

    public static void traverseDirectory(File directory) {
        logger = Logger.getLogger("FileService");

        txtQueue = new LinkedList<>();

        addTxtToQueue(directory);

        StringBuilder builder = new StringBuilder();

        processTxtFiles(builder);
    }

    // Method to get all files in folder
    private static void addTxtToQueue(final File folder) {
        logger = Logger.getLogger("FileService");

        for(final File fileEntry : folder.listFiles()) {
            // If is folder, call method again
            if(fileEntry.isDirectory()) {
                addTxtToQueue(fileEntry);
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
    private static void processTxtFiles(StringBuilder builder) {
        final File file = new File(txtQueue.peek());

        // System.out.println("Proccessing file: " + file.getName());

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
        if(!txtQueue.isEmpty()) {
            processTxtFiles(builder);
        }

        if(txtQueue.isEmpty()) {
            // System.out.println(builder.toString());
            createTxtFile(builder);
        }
    }

    // Creates txt file based on StringBuilder
    private static void createTxtFile(StringBuilder txtContent) {
        try {
            FileWriter file = new FileWriter("navn" + ".txt");
            file.write(txtContent.toString());
            file.close();
        }
        catch (Exception e) {
            logger.severe("Error while creating file: " + e.getMessage());
        }
    }
}
