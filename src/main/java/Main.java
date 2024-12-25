import java.io.File;

public class Main {
    /*
        TODO:

        - If txt, table of contents can include line numbers
        - If pdf, normal table of contents

        - Error if no txt files in (sub)directory(ies)
     */

    public static void main(String[] args) {
        // TODO: temp folder for testing purposes
        final File folder = new File("/Users/mjosefsen/Developer/Java/Dats2300 - Oppgaver");

        FileService.traverseDirectory(folder);
    }
}
