package com.github.xmlparser.model;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ram Alapure
 * @version 1.0
 * @since 17/02/2020
 */
public class XmlNodeList implements NodeList {

    List<Node> nodes = new ArrayList<>();
    int length = 0;

    public XmlNodeList() {
    }

    public void addNode(Node node) {
        nodes.add(node);
        length++;
    }

    @Override
    public Node item(int index) {
        try {
            return nodes.get(index);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int getLength() {
        return length;
    }
}
