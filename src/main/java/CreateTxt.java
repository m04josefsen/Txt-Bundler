import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class CreateTxt {
    private Logger logger;
    private Queue<String> txtQueue;
    private FileWriter newTxt;

    public FileWriter createTxt(boolean includeTableOfContents, Queue<String> txtQueue, String fileName) {
        logger = Logger.getLogger("CreateTxt");
        this.txtQueue = txtQueue;

        // Creating file and adding title
        try {
            newTxt = new FileWriter(fileName + ".txt");
            // Heading / Title
            newTxt.append(fileName + "\n\n");
        }
        catch (Exception e) {
            logger.severe("Error while creating file: " + e.getMessage());
        }

        if(includeTableOfContents) {
            addTableOfContents();
        }

        processTxtFiles();

        // Closes txt file after creating
        try {
            newTxt.close();
        }
        catch (Exception e) {
            logger.severe("Error while closing file: " + e.getMessage());
        }

        return newTxt;
    }

    // Adds the Table of Contents to file
    private void addTableOfContents() {
        Queue<String> tempQueue = new LinkedList<>();
        StringBuilder builder = new StringBuilder();

        while(!txtQueue.isEmpty()) {
            tempQueue.add(txtQueue.remove());
        }

        // Adds files back to original queue and to table of contents
        while(!tempQueue.isEmpty()) {
            builder.append("    - " + tempQueue.peek() + "\n");

            txtQueue.add(tempQueue.remove());
        }

        writeTxtFile(builder);
    }

    // Method to proccess all txt files form listFilesForFolder
    private void processTxtFiles() {
        final File file = new File(txtQueue.peek());

        // System.out.println("Proccessing file: " + file.getName());

        StringBuilder builder = new StringBuilder();
        // Adds heading to each file part
        builder.append("\n--- " + txtQueue.peek() + " ---\n");

        // Scanner reads throught txt file
        try {
            Scanner s = new Scanner(file);

            // TODO: maks antall karakterer per linje her
            while(s.hasNextLine()) {
                builder.append(s.nextLine() + "\n");
            }

            s.close();
        }
        catch (Exception e) {
            logger.severe("Error while processing file: " + e.getMessage());
        }

        // Removes txt file from queue, if empty stop recursion
        writeTxtFile(builder);
        txtQueue.remove();

        if(!txtQueue.isEmpty()) {
            processTxtFiles();
        }
    }

    // Writes to txt file based on StringBuilder
    private void writeTxtFile(StringBuilder txtContent) {
        try {
            newTxt.append(txtContent.toString() + "\n");
            // Seperator
            newTxt.append("\n-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-\n");
        }
        catch (Exception e) {
            logger.severe("Error while writing to file: " + e.getMessage());
        }
    }
}