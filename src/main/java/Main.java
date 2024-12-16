import java.io.File;

public class Main {

    /*
        TODO:
        - Add option to read all subdirectories or just main
        - Add option to create a pdf or txt file bundled
            - Option to add headings and table of contents
     */

    public static void main(String[] args) {
        // TODO: temp folder for testing purposes
        final File folder = new File("/Users/mjosefsen/Developer/Java/Dats2300 - Oppgaver");

        FileService.listFilesForFolder(folder);
    }

}
