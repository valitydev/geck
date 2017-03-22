package com.rbkmoney.geck.serializer.kit.xml;

import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.kit.StructType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.dom.DOMResult;
import java.io.IOException;
import java.sql.Struct;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by inalarsanukaev on 16.03.17.
 */
public class XMLProcessor implements StructProcessor<DOMResult> {
    @Override
    public <R> R process(DOMResult value, StructHandler<R> handler) throws IOException {
        Node document = value.getNode().getFirstChild();
        String nodeName = document.getNodeName();
        if (!nodeName.equals(XMLConstants.ROOT)) {
            throw new BadFormatException("Wrong root element name. Expected '"+XMLConstants.ROOT+"', actual '"+nodeName+"'");
        }
        processNode((Element) document, handler, false);
        return handler.getResult();
    }

    protected void processNode(Element node, StructHandler handler, boolean printName) throws IOException {
        if (node != null){
            String nodeName = node.getNodeName();
            if (printName) {
                handler.name(nodeName);
            }
            StructType type = StructType.valueOfKey(node.getAttribute(XMLConstants.ATTRIBUTE_TYPE));
            if (type == null) {
                throw new BadFormatException("Attribute 'type' must not be null. Node name: "+nodeName);
            }
            NodeList childNodes;
            switch (type) {
                case STRING:
                    handler.value(node.getTextContent());
                    break;
                case BOOL:
                    handler.value(Boolean.valueOf(node.getTextContent()));
                    break;
                case BYTEARRAY:
                    handler.value(Base64.getDecoder().decode(node.getTextContent()));
                    break;
                case DOUBLE:
                    handler.value(Double.valueOf(node.getTextContent()));
                    break;
                case LONG:
                    handler.value(Long.valueOf(node.getTextContent()));
                    break;
                case NULL:
                    handler.nullValue();
                    break;
                case STRUCT:
                    childNodes = node.getChildNodes();
                    handler.beginStruct(childNodes == null ? 0 : childNodes.getLength());
                    processChildNodes(childNodes, handler, true);
                    handler.endStruct();
                    break;
                case LIST:
                    childNodes = node.getChildNodes();
                    handler.beginList(childNodes == null ? 0 : childNodes.getLength());
                    processChildNodes(childNodes, handler, false);
                    handler.endList();
                    break;
                case SET:
                    childNodes = node.getChildNodes();
                    handler.beginSet(childNodes == null ? 0 : childNodes.getLength());
                    processChildNodes(childNodes, handler, false);
                    handler.endSet();
                    break;
                case MAP:
                    childNodes = node.getChildNodes();
                    handler.beginMap(childNodes== null ? 0 : childNodes.getLength());
                    processChildNodes(childNodes, handler, false);
                    handler.endMap();
                    break;
                case MAP_ENTRY :
                    handler.beginKey();
                    processNode((Element) node.getFirstChild(), handler, false);
                    handler.endKey();
                    handler.beginValue();
                    processNode((Element) node.getLastChild(), handler, false);
                    handler.endValue();
                    break;
                default:
                    new BadFormatException("Unknown type of node: "+type+". Must be on of them : "+ Arrays.toString(StructType.values()));
            }
        }
    }
    private void processChildNodes(NodeList nodeList, StructHandler handler, boolean printName) throws IOException {
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node item = nodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                processNode((Element) item, handler, printName);
            } else {
                throw new BadFormatException("Wrong type of node. Expected - "+Node.ELEMENT_NODE+", actual - "+item.getNodeType());
            }
        }
    }
}
