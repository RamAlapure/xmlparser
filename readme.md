The library perform operation on xml and returns csv data in multiple output formats:
1. String
2. File
3. Writer

How you can use it:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.ramalapure/xml-parser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.ramalapure/xml-parser)

**Maven**
```
<dependency>
  <groupId>com.github.ramalapure</groupId>
  <artifactId>xml-parser</artifactId>
  <version>1.0</version>
</dependency>
```

**Gradle**
```
implementation 'com.github.ramalapure:xml-parser:1.0'
```

**Note:** The following default separator **"_"** and delimiter **","** are used while creating CSV.

To parse xml input file to csv, you can do it by following ways:

1. Output as String
   ```
   XmlParser.xml2Csv(XML_FILE);
   ```

2. Output as Writer
   ```
   XmlParser.xml2Csv(XML_FILE, WRITER_OBJECT); //e.g. StringWriter, FileWriter, etc.
   ```

3. Output as File
   ```
   XmlParser.xml2Csv(XML_FILE, CSV_FILE_PATH); // CSV_FILE_PATH e.g. test.csv or D://somefolder/test.csv or /home/user/downloads/test.csv
   ```

To parse xsd input file to csv, you can do it by following ways:

1. Output as String
   ```
   XmlParser.xsd2Csv(XSD_FILE);
   ```

2. Output as Writer
   ```
   XmlParser.xsd2Csv(XSD_FILE, WRITER_OBJECT); //e.g. StringWriter, FileWriter, etc.
   ```

3. Output as File
   ```
   XmlParser.xsd2Csv(XSD_FILE, CSV_FILE_PATH); // CSV_FILE_PATH e.g. test.csv or D://somefolder/test.csv or /home/user/downloads/test.csv
   ```

To parse xml, xsd input file to csv, you can do it by following ways:

1. Output as String
   ```
   XmlParser.xmlAndXsd2Csv(XML_FILE, XSD_FILE);
   ```

2. Output as Writer
   ```
   XmlParser.xmlAndXsd2Csv(XML_FILE, XSD_FILE, WRITER_OBJECT); //e.g. StringWriter, FileWriter, etc.
   ```

3. Output as File
   ```
   XmlParser.xmlAndXsd2Csv(XML_FILE, XSD_FILE, CSV_FILE_PATH); // CSV_FILE_PATH e.g. test.csv or D://somefolder/test.csv or /home/user/downloads/test.csv
   ```