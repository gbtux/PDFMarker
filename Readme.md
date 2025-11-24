# PDFMarker

## Description
PDFMarker est un logiciel d'apposition de filigrane sur des PDF

## Developper

mvn clean package

## Usage

### Liste des arguments

1. inputFilePath : path du fichier PDF d'entrée (sans filigrane)
2. outputFilePath: path du fichier PDF de sortie (avec filigrane ajouté)
3. text: Texte du filigrane à aposer (entre double guillemets)
4. fontSize: taille de la police
5. rotationAngle: angle de rotation
6. transparence: par défaut à 0.2 (valeur doit être comprise entre 0.0 et 1.0)

### Normal en travers de la page
```
java -jar PDFMarker-1.0.jar doc.pdf out.pdf ""URGENT"" 30 45
```

### Gros en Diagonale:
```
java -jar PDFMarker-1.0.jar doc.pdf out.pdf ""URGENT"" 96 45
```
même chose mais avec une transparence personnalisée:
```
java -jar PDFMarker-1.0.jar doc.pdf out.pdf ""URGENT"" 96 45 0.4
```

### Petit en Bas de Page (Horizontal),
```
java -jar PDFMarker-1.0.jar doc.pdf out.pdf ""ID: 123456"" 14 0
```

### Très Grand en Vertical :
```
java -jar PDFMarker-1.0.jar doc.pdf out.pdf ""BROUILLON"" 150 90
```