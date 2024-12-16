import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileService {

    // Method to get all files in folder
    public static void listFilesForFolder(final File folder) throws Exception {
        Queue<String> queue = new LinkedList<>();

        for(final File fileEntry : folder.listFiles()) {
            // If is folder, call method again
            if(fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            }
            else {
                System.out.println(fileEntry.getName());

                if(fileEntry.getName().endsWith(".txt")) {
                    // TODO: enten save pathen?, eller dataen?, eller prosseser med en gang?
                    queue.add(fileEntry.getAbsolutePath());
                }
            }
        }

        StringBuilder builder = new StringBuilder();

        processTxtFiles(queue, queue.peek(), builder);
    }

    // Method to proccess all txt files form listFilesForFolder
    // TODO: kan a txtFile, kall den en gang, og if allTxtFiles.hasNext hvis dobbelt lenket liste eller .next osv, dvs rekursjon
    // TODO: metoden kan ta inn en stringbuilder som bygger hele? kanskje memory not efficent
    private static void processTxtFiles(Queue<String> queue, String path, StringBuilder builder) throws Exception {
        final File file = new File(path);
        Scanner s = new Scanner(file);

        // Scanner reads throught txt file
        while(s.hasNextLine()) {
            builder.append(s.nextLine());
        }

        s.close();

        // Removes txt file from queue, if empty stop recursion
        queue.remove();
        if(!queue.isEmpty()) {
            processTxtFiles(queue, queue.peek(), builder);
        }
    }





}
