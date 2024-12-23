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

        // Constants for page layout
        int margin = 50;
        int fontSize = 12;
        int lineSpacing = 15;
        int pageWidth = 595; // A4 width in points (72 points per inch)
        int writableWidth = pageWidth - 2 * margin; // Account for margins

        try (PDDocument document = new PDDocument()) {
            // Load custom font
            PDType0Font font = PDType0Font.load(document, new File(fontPath));

            BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
            String line;
            int yPosition = 750;

            PDPage page = new PDPage();
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, fontSize);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);

            while ((line = reader.readLine()) != null) {
                // Remove or replace non-printable characters (like tabs)
                line = line.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", ""); // Remove control characters except newline and tab
                line = line.replaceAll("\t", "    "); // Replace tabs with spaces

                // Remove unsupported characters
                line = removeUnsupportedCharacters(line, font);

                // Wrap text if it exceeds writable width
                for (String wrappedLine : wrapText(line, writableWidth, font, fontSize)) {
                    if (yPosition <= margin) { // Start a new page if necessary
                        contentStream.endText();
                        contentStream.close();
                        document.addPage(page);

                        page = new PDPage();
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(font, fontSize);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin, 750);
                        yPosition = 750;
                    }

                    contentStream.showText(wrappedLine);
                    contentStream.newLineAtOffset(0, -lineSpacing);
                    yPosition -= lineSpacing;
                }
            }

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

    private static String removeUnsupportedCharacters(String text, PDType0Font font) throws IOException {
        StringBuilder sanitizedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            try {
                // Check if the character can be encoded by the font
                if (font.getStringWidth(String.valueOf(c)) / 1000 * 12 <= 595) {
                    sanitizedText.append(c);
                }
            } catch (IllegalArgumentException e) {
                // Skip unsupported characters
                continue;
            }
        }
        return sanitizedText.toString();
    }

    private static String[] wrapText(String text, int maxWidth, PDType0Font font, int fontSize) throws IOException {
        StringBuilder wrappedLine = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();
        String[] words = text.split(" ");

        for (String word : words) {
            String potentialLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            if (font.getStringWidth(potentialLine) / 1000 * fontSize > maxWidth) {
                wrappedLine.append(currentLine).append("\n");
                currentLine = new StringBuilder(word);
            } else {
                currentLine.append((currentLine.length() == 0 ? "" : " ") + word);
            }
        }

        if (currentLine.length() > 0) {
            wrappedLine.append(currentLine);
        }

        return wrappedLine.toString().split("\n");
    }
}