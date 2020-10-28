package com.github.xmlparser.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsonparser.JsonParser;
import com.github.jsonparser.exception.JsonParsingException;
import com.github.xmlparser.XmlParser;
import com.github.xmlparser.exception.XmlParsingException;
import com.github.xmlparser.util.CommonUtil;
import com.github.xmlparser.util.TestConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author Ram Alapure
 * @version 1.0
 * @since 17/02/2020
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class XmlReaderTest {

    File xml, xsd;

    @BeforeAll
    public void setup() {
        xml = new File(new File(TestConstants.LOCATION).getAbsolutePath() + TestConstants.CHAR_FORWARD_SLASH
                + "SUBJECTVISITCRF.xml");
        xsd = new File(new File(TestConstants.LOCATION).getAbsolutePath() + TestConstants.CHAR_FORWARD_SLASH
                + "SiteVisit.xsd");
    }

    @Test
    void parseXML() throws XmlParsingException, JsonProcessingException, JsonParsingException, FileNotFoundException, UnsupportedEncodingException {
        Map map = XmlReader.parseXML(xml);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);
        System.out.println(json);

        //String json = "{\"SiebelMessage\":{\"ListOfSunClinicalSubjectVisit\":{\"ClinicalSubject\":[{\"SubjectNumber\":\"101001094\",\"SiteSourceId\":\"1-4JDOX28\",\"ListOfVisitPlan\":{\"VisitPlan\":[{\"Type\":\"Screening\",\"Version\":\"Version 1\",\"Due\":\"11/08/2019\",\"MinDueDate\":\"11/08/2019\",\"TemplateName\":\"IZA20420_Subject Visit Template\",\"Operation\":\"upsert\",\"Sequence\":\"14000\",\"Planned\":\"11/08/2019\",\"SubjectVisitSourceId\":\"1-4UABORO\",\"MaxDueDate\":\"11/08/2019\",\"Name\":\"UV 4\",\"CompletedDate\":\"12/05/2019\"},{\"Type\":\"Screening\",\"Version\":\"Version 1\",\"Due\":\"11/08/2019\",\"MinDueDate\":\"11/08/2019\",\"TemplateName\":\"IZA20420_Subject Visit Template\",\"Operation\":\"upsert\",\"Sequence\":\"15000\",\"Planned\":\"11/08/2019\",\"SubjectVisitSourceId\":\"1-4UABORT\",\"MaxDueDate\":\"11/08/2019\",\"Name\":\"UV 5\",\"CompletedDate\":\"12/09/2019\"}]},\"SubjectSourceId\":\"1-4UABOQ2\"},{\"SubjectNumber\":\"101001084\",\"SiteSourceId\":\"1-4JDOX28\",\"ListOfVisitPlan\":{\"VisitPlan\":[{\"Type\":\"Screening\",\"Version\":\"Version 1\",\"Due\":\"11/05/2019\",\"MinDueDate\":\"11/05/2019\",\"TemplateName\":\"IZA20420_Subject Visit Template\",\"Operation\":\"upsert\",\"Sequence\":\"13000\",\"Planned\":\"11/05/2019\",\"SubjectVisitSourceId\":\"1-4UABWBO\",\"MaxDueDate\":\"11/05/2019\",\"Name\":\"UV 3\",\"CompletedDate\":\"11/20/2019\"}]},\"SubjectSourceId\":\"1-4UABWA7\"}]},\"HeaderDataObject\":\"SUBJECTVISITCRF\",\"IntObjectName\":\"Sun Clinical Subject Visit\",\"xsi:schemaLocation\":\"http://www.siebel.com/xml/SUN%20Clinical%20Subject%20Visit SubjectVisit.xsd\",\"xmlns:xsi\":\"http://www.w3.org/2001/XMLSchema-instance\",\"xmlns\":\"http://www.siebel.com/xml/SUN%20Clinical%20Subject%20Visit\",\"IntObjectFormat\":\"Siebel Hierarchical\",\"HeaderDataEndDateTime\":\"01/13/2020 12:32:34 PM\",\"HeaderSource\":\"QUINTILES\",\"MessageType\":\"Integration Object\",\"HeaderDataStartDateTime\":\"01/10/2020 03:46:42 AM\",\"HeaderFileCreatedDateTime\":\"01/13/2020 12:32:34 PM\",\"MessageId\":\"0502\"}}\n";
        System.out.println(JsonParser.parse2Csv(json));
    }

    @Test
    public void parseXSD() throws Exception {
        Map map = XmlReader.parseXSD(xsd);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);
        System.out.println("JSON: " + json);
    }

}