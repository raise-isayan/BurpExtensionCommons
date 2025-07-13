package extension.helpers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author isayan
 */
public class XMLUtil {

    public static Document parseXml(String xmlElementString) throws SAXException {
        Document doc = null;
        try {
            DocumentBuilder dom = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = dom.parse(new InputSource(new StringReader(xmlElementString)));
        } catch (IOException ex) {
            throw new SAXException(ex);
        } catch (ParserConfigurationException ex) {
            throw new SAXException(ex);
        }
        return doc;
    }

    public static String toXPath(Node node) {
        if (node == null) return null;
        switch (node.getNodeType()) {
            case Node.DOCUMENT_NODE:
                return "";
            case Node.ATTRIBUTE_NODE:
                Attr attr = (Attr) node;
                String parentPath = toXPath(attr.getOwnerElement());
                return parentPath + "/@" + attr.getName();
            case Node.ELEMENT_NODE:
                Node parent = node.getParentNode();
                String parentXPath = toXPath(parent);
                String nodeName = node.getNodeName();
                int index = getElementIndex(node);
                String currentPath = "/" + nodeName + (index > 1 ? "[" + index + "]" : "");
                return parentXPath + currentPath;
            case Node.TEXT_NODE:
//                // テキストノードのパスが必要な場合、ここに追加
//                return toXPath(node.getParentNode()) + "/text()";
                return "";
            default:
                // その他のノード型はここで適宜対応
                return null;
        }
    }

    private static int getElementIndex(Node node) {
        Node parent = node.getParentNode();
        if (parent == null) return 1;

        NodeList siblings = parent.getChildNodes();
        int index = 0;
        int count = 0;

        for (int i = 0; i < siblings.getLength(); i++) {
            Node sibling = siblings.item(i);
            if (sibling.getNodeType() == Node.ELEMENT_NODE &&
                sibling.getNodeName().equals(node.getNodeName())) {
                count++;
                if (sibling == node) {
                    index = count;
                }
            }
        }
        return index == 0 ? 1 : index;
    }
}
