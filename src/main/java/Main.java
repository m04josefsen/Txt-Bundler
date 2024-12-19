import java.io.File;

public class Main {

    /*
        TODO:
        - Add option to read all subdirectories or just main
        - Add option to create a pdf or txt file bundled
            - Option to add headings and table of contents

        // TODO: hvis innholdsfortegnelse burde det kanskje bli lagt til før i builderen, og heading må igjen etter det.
        // innholdsfortegnelse kan bli lagt til på slutt ved å sette sammen to string builders, men må lagre filnavn i Queue, for ritkig rekkefølge.

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
