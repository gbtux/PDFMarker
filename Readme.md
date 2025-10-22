# PDFMarker

## Description
PDFMarker est un logiciel d'apposition de filigrane sur des PDF

## Developper

mvn clean package

## Usage

### Normal en travers de la page
```
java -jar App.jar doc.pdf out.pdf ""URGENT"" 30 45
```

### Gros en Diagonale:
```
java -jar App.jar doc.pdf out.pdf ""URGENT"" 96 45
```

### Petit en Bas de Page (Horizontal),
```
java -jar App.jar doc.pdf out.pdf ""ID: 123456"" 14 0
```

### Très Grand en Vertical :
```
java -jar App.jar doc.pdf out.pdf ""BROUILLON"" 150 90
```