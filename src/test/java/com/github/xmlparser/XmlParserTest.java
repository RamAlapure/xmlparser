package com.github.xmlparser;

import com.github.xmlparser.exception.XmlParsingException;
import com.github.xmlparser.util.CommonUtil;
import com.github.xmlparser.util.TestConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;

/**
 * @author Ram Alapure
 * @version 1.0
 * @since 17/02/2020
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class XmlParserTest {

    File xml, xsd;

    @BeforeAll
    public void setup() {
        xml = new File(new File(TestConstants.LOCATION).getAbsolutePath() + TestConstants.CHAR_FORWARD_SLASH
                + "SITEVISIT.xml");
        xsd = new File(new File(TestConstants.LOCATION).getAbsolutePath() + TestConstants.CHAR_FORWARD_SLASH
                + "SiteVisit.xsd");
    }

    @Test
    void parseXMLtoCSV() throws XmlParsingException {
        System.out.println(XmlParser.xml2Csv(xml));
    }

    @Test
    public void parseXSD() throws Exception {
        System.out.println(XmlParser.xsd2Csv(xsd));
    }

    @Test
    void parseXSDtoCSV() throws XmlParsingException {
        String csvFilePath = new File(TestConstants.LOCATION).getAbsolutePath() + TestConstants.CHAR_FORWARD_SLASH
                + CommonUtil.getFileNamePrefix(xsd) + ".csv";
        XmlParser.xsd2Csv(xsd, csvFilePath);
    }

    @Test
    void xmlAndXsd2Csv() throws XmlParsingException {
        System.out.println(XmlParser.xmlAndXsd2Csv(xml, xsd));
    }

    @Test
    void xmlAndXsd2CsvFile() throws XmlParsingException {
        String csvFilePath = new File(TestConstants.LOCATION).getAbsolutePath() + TestConstants.CHAR_FORWARD_SLASH
                + CommonUtil.getFileNamePrefix(xsd) + ".csv";
        XmlParser.xmlAndXsd2Csv(xml, xsd, csvFilePath);
    }
}