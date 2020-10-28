package com.github.xmlparser;

import com.github.jsonparser.JsonParser;
import com.github.jsonparser.exception.JsonParsingException;
import com.github.xmlparser.exception.XmlParsingException;
import com.github.xmlparser.reader.XmlReader;
import com.github.xmlparser.util.ErrorUtil;
import com.github.xmlparser.util.ValidationUtil;

import java.io.File;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * The class parse XML document to csv. The default separator is "_" and delimiter is ",".
 * The XmlParser supports output in 3 different formats:
 * 1. String
 * 2. File
 * 3. Writer
 *
 * @author Ram Alapure
 * @version 1.0
 * @since 17/02/2020
 */
public class XmlParser {

    public static final Logger log = Logger.getLogger(XmlParser.class.getName());

    private XmlParser() {
    }

    /**
     * This method process the xml input file and returns a csv string.
     *
     * @param xml - The input xml file
     * @return Returns a csv string
     * @throws XmlParsingException
     */
    public static String xml2Csv(File xml) throws XmlParsingException {
        ValidationUtil.rejectNull(xml, "XML");
        log.info(String.format("Received request to parse xml file: %s to csv", xml.getName()));
        try {
            return JsonParser.parse2Csv(XmlReader.parseXml2Json(xml));
        } catch (JsonParsingException e) {
            ErrorUtil.fileParsingException(e.getMessage());
        }
        return null;
    }

    /**
     * This method process the xml input file and writes to csv file path provided.
     *
     * @param xml         - The input xml file
     * @param csvFilePath - The csv file path with name to write output
     * @throws XmlParsingException
     */
    public static void xml2Csv(File xml, String csvFilePath) throws XmlParsingException {
        ValidationUtil.rejectNull(xml, "XML");
        ValidationUtil.rejectNull(csvFilePath, "CSV file path");
        log.info(String.format("Received request to parse xml file: %s and write to csv file: %s", xml.getName(), csvFilePath));
        try {
            JsonParser.parse2Csv(XmlReader.parseXml2Json(xml), csvFilePath);
        } catch (JsonParsingException e) {
            ErrorUtil.fileParsingException(e.getMessage());
        }
    }

    /**
     * This method process the xml input file and writes csv output to a writer.
     *
     * @param xml    - The input xml file
     * @param writer - The writer object to write the csv e.g. StringWriter, FileWriter, etc.
     * @throws XmlParsingException
     */
    public static void xml2Csv(File xml, Writer writer) throws XmlParsingException {
        ValidationUtil.rejectNull(xml, "XML");
        ValidationUtil.rejectNull(writer, "writer");
        log.info(String.format("Received request to parse xml file: %s and write to a writer", xml.getName()));
        try {
            JsonParser.parse2Csv(XmlReader.parseXml2Json(xml), writer);
        } catch (JsonParsingException e) {
            ErrorUtil.fileParsingException(e.getMessage());
        }
    }

    /**
     * This method process the xsd input file and returns a csv string.
     *
     * @param xsd - The input xsd file
     * @return Returns a csv string
     * @throws XmlParsingException
     */
    public static String xsd2Csv(File xsd) throws XmlParsingException {
        ValidationUtil.rejectNull(xsd, "XSD");
        log.info(String.format("Received request to parse xsd file: %s to csv", xsd.getName()));
        try {
            //directly write the JSON document to CSV
            return JsonParser.parseXsd2Csv(XmlReader.parseXsd2Json(xsd));
        } catch (JsonParsingException e) {
            ErrorUtil.fileParsingException(e.getMessage());
        }
        return null;
    }

    /**
     * This method process the xsd input file and writes to csv file path provided.
     *
     * @param xsd         - The input xsd file
     * @param csvFilePath - The csv file path with name to write output
     * @throws XmlParsingException
     */
    public static void xsd2Csv(File xsd, String csvFilePath) throws XmlParsingException {
        ValidationUtil.rejectNull(xsd, "XSD");
        ValidationUtil.rejectNull(csvFilePath, "CSV file path");
        log.info(String.format("Received request to parse xsd file: %s and write to csv file: %s", xsd.getName(), csvFilePath));
        try {
            //directly write the JSON document to CSV
            JsonParser.parseXsd2Csv(XmlReader.parseXsd2Json(xsd), csvFilePath);
        } catch (JsonParsingException e) {
            ErrorUtil.fileParsingException(e.getMessage());
        }
    }

    /**
     * This method process the xsd input file and writes csv output to a writer.
     *
     * @param xsd    - The input xsd file
     * @param writer - The writer object to write the csv e.g. StringWriter, FileWriter, etc.
     * @throws XmlParsingException
     */
    public static void xsd2Csv(File xsd, Writer writer) throws XmlParsingException {
        ValidationUtil.rejectNull(xsd, "XSD");
        ValidationUtil.rejectNull(writer, "writer");
        log.info(String.format("Received request to parse xsd file: %s and write to a writer", xsd.getName()));
        try {
            JsonParser.parseXsd2Csv(XmlReader.parseXsd2Json(xsd), writer);
        } catch (JsonParsingException e) {
            ErrorUtil.fileParsingException(e.getMessage());
        }
    }

    /**
     * This method process the xml and xsd input files and returns a csv string.
     *
     * @param xml - The input xml file
     * @param xsd - The input xsd file
     * @return Returns a csv string
     * @throws XmlParsingException
     */
    public static String xmlAndXsd2Csv(File xml, File xsd) throws XmlParsingException {
        try {
            //directly write the JSON document to CSV
            return JsonParser.parse2CsvWithXsd(XmlReader.parseXml2Json(xml), XmlReader.parseXsd2Json(xsd));
        } catch (JsonParsingException e) {
            ErrorUtil.fileParsingException(e.getMessage());
        }
        return null;
    }

    /**
     * This method process the xml and xsd input files and writes to csv file path provided.
     *
     * @param xml         - The input xml file
     * @param xsd         - The input xsd file
     * @param csvFilePath - The csv file path with name to write output
     * @throws XmlParsingException
     */
    public static void xmlAndXsd2Csv(File xml, File xsd, String csvFilePath) throws XmlParsingException {
        try {
            //directly write the JSON document to CSV
            JsonParser.parse2CsvWithXsd(XmlReader.parseXml2Json(xml), XmlReader.parseXsd2Json(xsd), csvFilePath);
        } catch (JsonParsingException e) {
            ErrorUtil.fileParsingException(e.getMessage());
        }
    }

    /**
     * This method process the xml and xsd input file and writes csv output to a writer.
     *
     * @param xml    - The input xml file
     * @param xsd    - The input xsd file
     * @param writer - The writer object to write the csv e.g. StringWriter, FileWriter, etc.
     * @throws XmlParsingException
     */
    public static void xmlAndXsd2Csv(File xml, File xsd, Writer writer) throws XmlParsingException {
        try {
            //directly write the JSON document to CSV
            JsonParser.parse2CsvWithXsd(XmlReader.parseXml2Json(xml), XmlReader.parseXsd2Json(xsd), writer);
        } catch (JsonParsingException e) {
            ErrorUtil.fileParsingException(e.getMessage());
        }
    }
}
