import java.io.File;

public class FileService {

    public static void listFilesForFolder(final File folder) {
        for(final File fileEntry : folder.listFiles()) {
            // If is folder, call method again
            if(fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            }
            else {
                System.out.println(fileEntry.getName());

                if(fileEntry.getName().endsWith(".txt")) {
                    // TODO: enten save pathen?, eller dataen?, eller prosseser med en gang?
                }
            }
        }
    }



}
