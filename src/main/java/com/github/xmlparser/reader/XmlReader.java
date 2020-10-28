package com.github.xmlparser.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xmlparser.exception.XmlParsingException;
import com.github.xmlparser.model.XmlNodeList;
import com.github.xmlparser.util.ErrorUtil;
import com.github.xmlparser.util.ExceptionConstants;
import com.sun.xml.xsom.*;
import com.sun.xml.xsom.parser.XSOMParser;
import jlibs.xml.xsd.XSParser;
import org.apache.xerces.xs.XSConstants;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSNamedMap;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * The class process xml, xsd document and provides output in json, {@link Map} format.
 *
 * @author Ram Alapure
 * @version 1.0
 * @since 17/02/2020
 */
public class XmlReader {

    public static final Logger log = Logger.getLogger(XmlReader.class.getName());

    private XmlReader() {
    }

    /**
     * This method can be used to parse xml and xsd file to {@link Map}.
     *
     * @param xml - The input xml file
     * @param xsd - The input xsd file
     * @return Returns map with parsed data.
     * @throws XmlParsingException
     */
    public static Map parseXML(File xml, File xsd) throws XmlParsingException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Document document = null;
        try {
            factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(xsd)}));
            DocumentBuilder builder = factory.newDocumentBuilder();
            //builder.setErrorHandler(new SimpleErrorHandler());
            document = builder.parse(xml);
        } catch (ParserConfigurationException | SAXException e) {
            ErrorUtil.fileParsingException(ExceptionConstants.STR_PARSING_EXCEPTION, e);
        } catch (IOException e) {
            ErrorUtil.fileParsingException(ExceptionConstants.STR_IO_EXCEPTION, e);
        }
        return parseDocument(document);
    }

    /**
     * This method can be used to parse xml file to json string.
     *
     * @param xml - The input xml file
     * @return Returns json string of parsed xml data.
     * @throws XmlParsingException
     */
    public static String parseXml2Json(File xml) throws XmlParsingException {
        log.info(String.format("Parsing xml: %s file to json", xml.getName()));
        Map map = parseXML(xml);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            ErrorUtil.fileParsingException(ExceptionConstants.STR_PARSING_EXCEPTION, e);
        }
        return null;
    }

    /**
     * This method can be used to parse xsd file to json string.
     *
     * @param xsd - The input xsd file
     * @return
     * @throws XmlParsingException
     */
    public static String parseXsd2Json(File xsd) throws XmlParsingException {
        log.info(String.format("Parsing xsd: %s file to json", xsd.getName()));
        Map map = parseXSD(xsd);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            ErrorUtil.fileParsingException(ExceptionConstants.STR_PARSING_EXCEPTION, e);
        }
        return null;
    }

    /**
     * This method can be used to parse xml file to {@link Map}.
     *
     * @param xml - The input xml file
     * @return Returns map of parsed xml data.
     * @throws XmlParsingException
     */
    public static Map parseXML(File xml) throws XmlParsingException {
        Document document = null;
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = dBuilder.parse(xml);
        } catch (ParserConfigurationException | SAXException e) {
            ErrorUtil.fileParsingException(ExceptionConstants.STR_PARSING_EXCEPTION, e);
        } catch (IOException e) {
            ErrorUtil.fileParsingException(ExceptionConstants.STR_IO_EXCEPTION, e);
        }
        return parseDocument(document);
    }

    /**
     * This method can be used to parse xml file to {@link Map}.
     *
     * @param xsd - The input xsd file
     * @return Returns map of parsed xsd data.
     * @throws XmlParsingException
     */
    public static Map parseXSD(File xsd) throws XmlParsingException {
        HashMap properties = new HashMap();
        XSModel xsModel = new XSParser().parse(xsd.getAbsolutePath());
        XSNamedMap map = xsModel.getComponents(XSConstants.ELEMENT_DECLARATION);
        XSOMParser parser = new XSOMParser(SAXParserFactory.newInstance());
        try {
            parser.parse(xsd);
            XSSchemaSet schemaSet = parser.getResult();
            // global namespace
            XSSchema schema = schemaSet.getSchema(map.item(0).getNamespace());
            Map<String, XSElementDecl> elementDecls = schema.getElementDecls();
            // the main entity of this file is in the Elements
            // there should only be one!
            if (elementDecls.size() != 1) {
                throw new XmlParsingException("Should be only element type per file.");
            }
            for (Map.Entry<String, XSElementDecl> element : elementDecls.entrySet()) {
                XSElementDecl value = element.getValue();
                processElementDecl(value, properties);
            }
        } catch (SAXException e) {
            ErrorUtil.fileParsingException(ExceptionConstants.STR_PARSING_EXCEPTION, e);
        } catch (IOException e) {
            ErrorUtil.fileParsingException(ExceptionConstants.STR_IO_EXCEPTION, e);
        }
        return properties;
    }

    /**
     * This method can be used to parse {@link Document} to {@link Map}.
     *
     * @param doc - The input document object
     * @return Returns map from parsed document data.
     * @throws XmlParsingException
     */
    private static Map parseDocument(Document doc) {
        HashMap result = new HashMap();
        doc.getDocumentElement().normalize();
        NodeList resultNode = doc.getChildNodes();
        XmlNodeList tempNodeList = new XmlNodeList();
        String emptyNodeName = null, emptyNodeValue = null;
        for (int index = 0; index < resultNode.getLength(); index++) {
            Node tempNode = resultNode.item(index);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                tempNodeList.addNode(tempNode);
            }
            emptyNodeName = tempNode.getNodeName();
            emptyNodeValue = tempNode.getNodeValue();
        }
        if (tempNodeList.getLength() == 0 && emptyNodeName != null && emptyNodeValue != null) {
            result.put(emptyNodeName, emptyNodeValue);
            return result;
        }
        parseXMLNode(tempNodeList, result);
        return result;
    }

    private static void parseXMLNode(NodeList nList, HashMap result) {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.hasChildNodes() && nNode.getFirstChild() != null
                    && (nNode.getFirstChild().getNextSibling() != null || nNode.getFirstChild().hasChildNodes())) {
                NodeList childNodes = nNode.getChildNodes();
                XmlNodeList tempNodeList = new XmlNodeList();
                for (int index = 0; index < childNodes.getLength(); index++) {
                    Node tempNode = childNodes.item(index);
                    if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                        tempNodeList.addNode(tempNode);
                    }
                }
                HashMap dataHashMap = new HashMap();
                if (result.containsKey(nNode.getNodeName()) && result.get(nNode.getNodeName()) instanceof List) {
                    List mapExisting = (List) result.get(nNode.getNodeName());
                    mapExisting.add(dataHashMap);
                } else if (result.containsKey(nNode.getNodeName())) {
                    List counterList = new ArrayList();
                    counterList.add(result.get(nNode.getNodeName()));
                    counterList.add(dataHashMap);
                    result.put(nNode.getNodeName(), counterList);
                } else {
                    result.put(nNode.getNodeName(), dataHashMap);
                }
                iterateAttributes(nNode, dataHashMap);
                parseXMLNode(tempNodeList, dataHashMap);
            } else if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.hasChildNodes() && nNode.getFirstChild() != null
                    && nNode.getFirstChild().getNextSibling() == null) {
                putValue(result, nNode);
            } else if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                putValue(result, nNode);
            }
        }
    }

    private static void iterateAttributes(Node nNode, HashMap dataHashMap) {
        if (nNode.getAttributes().getLength() > 0) {
            addAttribute(nNode, dataHashMap);
        }
    }

    private static void addAttribute(Node nNode, HashMap dataHashMap) {
        IntStream.range(0, nNode.getAttributes().getLength()).forEach(attributeCounter -> dataHashMap.put(
                nNode.getAttributes().item(attributeCounter).getNodeName(),
                nNode.getAttributes().item(attributeCounter).getNodeValue()
        ));
    }

    private static void putValue(HashMap result, Node nNode) {
        Object nodeValue = null;
        if (nNode.getFirstChild() != null) {
            nodeValue = nNode.getFirstChild().getNodeValue();
            if (nodeValue != null) {
                nodeValue = nodeValue.toString().trim();
            }
        }
        HashMap nodeMap = new HashMap();
        nodeMap.put("<<value>>", nodeValue);
        Object putNode = nodeValue;
        if (nNode.getAttributes().getLength() > 0) {
            addAttribute(nNode, nodeMap);
            putNode = nodeMap;
        }
        if (result.containsKey(nNode.getNodeName()) && result.get(nNode.getNodeName()) instanceof List) {
            List mapExisting = (List) result.get(nNode.getNodeName());
            mapExisting.add(putNode);
        } else if (result.containsKey(nNode.getNodeName())) {
            List counterList = new ArrayList();
            counterList.add(result.get(nNode.getNodeName()));
            counterList.add(putNode);
            result.put(nNode.getNodeName(), counterList);
        } else {
            result.put(nNode.getNodeName(), putNode);
        }
    }

    private static void processElementDecl(XSElementDecl element, HashMap parent) {
        XSType type = element.getType();
        if (type.isSimpleType()) {
            parent.put(element.getName(), element.getName());
        } else if (type.isComplexType()) {
            HashMap child = new HashMap();
            // Extract attributes
            extractAttributes(type, child);
            // Extract child elements
            XSContentType xsContentType = type.asComplexType().getContentType();
            parent.put(element.getName(), child);
            XSParticle particle = xsContentType.asParticle();
            if (particle != null) {
                XSTerm term = particle.getTerm();
                printXSTerm(term, child);
            }
        }
    }

    private static void extractAttributes(XSType type, HashMap child) {
        Collection<? extends XSAttributeUse> attributeUses = type.asComplexType().getAttributeUses();
        attributeUses.stream()
                .map(XSAttributeUse::getDecl)
                .forEach(attribute -> child.put(attribute.getName(), attribute.getName()));
    }

    private static void printXSTerm(XSTerm term, HashMap child) {
        if (term.isModelGroup()) {
            XSModelGroup xsModelGroup = term.asModelGroup();
            XSParticle[] particles = xsModelGroup.getChildren();
            for (XSParticle p : particles) {
                XSTerm pterm = p.getTerm();
                printXSTerm(pterm, child);
            }
        } else if (term.isElementDecl()) {
            processElementDecl(term.asElementDecl(), child);
        } else if (term.isWildcard()) {
            System.out.println("Wildcard: " + term.asElementDecl().getName());
        }
    }
}