package org.devops;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.Loader;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class PDFMarker {

    public static void addTextWatermark(
            String inputPath,
            String outputPath,
            String watermarkText,
            float fontSize,
            int rotationAngle,
            float transparency
    ) throws IOException {

        // 1. CHARGEMENT DU DOCUMENT
        try (PDDocument document = Loader.loadPDF(new File(inputPath))) {

            // 2. Définir le style du filigrane (couleur et transparence)
            PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
            gs.setNonStrokingAlphaConstant(transparency); //0.2f --> 20% de transparence (valeur entre 0.0 et 1.0)

            // Paramètres du texte
            PDType1Font font = new PDType1Font(FontName.HELVETICA_BOLD);
            //float fontSize = 72.0f;
            //int rotationAngle = 45; // 45 degrés pour une diagonale

            // 3. Traiter chaque page
            for (PDPage page : document.getPages()) {

                // Récupérer les dimensions de la page
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();

                // Calculer la position centrale
                float textWidth = (font.getStringWidth(watermarkText) / 1000) * fontSize;
                float x = pageWidth / 2;
                float y = pageHeight / 2;

                // 4. Créer le flux de contenu pour la page
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {

                    // Appliquer la transparence
                    contentStream.setGraphicsStateParameters(gs);

                    // Définir la couleur du texte (gris clair)
                    contentStream.setNonStrokingColor(Color.LIGHT_GRAY);

                    // Début de la transformation (rotation/positionnement)
                    contentStream.beginText();

                    // Définir la police et la taille
                    contentStream.setFont(font, fontSize);

                    // Calculer la matrice de transformation pour le centrage et la rotation
                    // Cette matrice déplace le point d'origine du texte au centre de la page,
                    // le fait pivoter, puis le compense pour le centrage du texte lui-même.
                    Matrix matrix = Matrix.getRotateInstance(Math.toRadians(rotationAngle), x, y);

                    // Appliquer la matrice de transformation
                    contentStream.setTextMatrix(matrix);

                    // Positionner le texte de manière à ce qu'il soit centré après rotation
                    contentStream.newLineAtOffset(-textWidth / 2, 0);

                    // Afficher le filigrane (variable)
                    contentStream.showText(watermarkText);

                    contentStream.endText();
                }
            }

            // 5. Sauvegarder le nouveau document
            document.save(new File(outputPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    
    public static void main(String[] args) throws IOException {
        if (args.length != 6) {
            System.err.println("Usage: java -jar PDFMarker.jar <inputPath> <outputPath> \"<watermarkText>\" <fontSize> <rotationAngle> <transparency>");
            System.exit(1);
            return;
        }

        // 2. Attribution des arguments
        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String variableText = args[2];

        float fontSize;
        int rotationAngle;
        float transparency = 0.2f;

        try {
            // Conversion en types numériques
            fontSize = Float.parseFloat(args[3]);
            rotationAngle = Integer.parseInt(args[4]);
            transparency = Float.parseFloat(args[5]);

        } catch (NumberFormatException e) {
            System.err.println("❌ Erreur: La taille de police, l'angle et la transparence doivent être des nombres.");
            System.exit(1);
            return;
        }

        try {
            addTextWatermark(inputFilePath, outputFilePath, variableText, fontSize, rotationAngle, transparency);
            System.out.println("✅ Filigrane '" + variableText + "' ajouté avec succès.");
            System.out.println("Fichier de sortie : " + outputFilePath);
        } catch (IOException e) {
            System.err.println("❌ Erreur lors du traitement du PDF : " + e.getMessage());
            System.exit(2);
        }
    }
}