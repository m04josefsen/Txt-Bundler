import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CreatePdf {

    public static void createPdf(String txtFilePath, String fileName) {
        String pdfFilePath = "/Users/mjosefsen/Developer/Java/TxtBundler/" + fileName + ".pdf";
        String fontPath = "/Users/mjosefsen/Developer/Java/TxtBundler/src/main/resources/fonts/Noto-sans.ttf";

        try (PDDocument document = new PDDocument()) {
            PDType0Font font = PDType0Font.load(document, new File(fontPath));

            // Variables for text processing
            BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
            String line;
            int lineSpacing = 15;
            int margin = 50;
            int yPosition = 750;

            PDPage page = new PDPage();
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);

            while ((line = reader.readLine()) != null) {
                // Preprocess the line
                line = line.replaceAll("[\\uF000-\\uF8FF]", "?"); // Replace private-use characters
                line = line.replace("\t", "    "); // Replace tabs with spaces
                line = line.replaceAll("\\p{Cntrl}", ""); // Remove other control characters

                if (yPosition <= margin) { // Check if we need a new page
                    contentStream.endText();
                    contentStream.close();
                    document.addPage(page);

                    page = new PDPage();
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(font, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, 750);
                    yPosition = 750;
                }

                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -lineSpacing);
                yPosition -= lineSpacing;
            }

            // Finalize the current page and save the document
            contentStream.endText();
            contentStream.close();
            document.addPage(page);
            reader.close();

            document.save(pdfFilePath);
            System.out.println("PDF created successfully at: " + pdfFilePath);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating PDF: " + e.getMessage());
        }
    }
}