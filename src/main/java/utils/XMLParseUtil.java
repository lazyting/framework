package utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParseUtil {
    private static final String PREFIX_XML = "<xml>";
    private static final String SUFFIX_XML = "</xml>";
    private static final String PREFIX_CDATA = "<![CDATA[";
    private static final String SUFFIX_CDATA = "]]>";

    /**
     * 转化成xml, 单层无嵌套
     *
     * @param parm
     * @param isAddCDATA
     * @return
     */
    public static String mapToXml(Map<String, Object> parm, boolean isAddCDATA) {
        StringBuffer strbuff = new StringBuffer(PREFIX_XML);
        if (null != parm) {
            for (Map.Entry<String, Object> entry : parm.entrySet()) {
                strbuff.append("<").append(entry.getKey()).append(">");
                if (isAddCDATA) {
                    strbuff.append(PREFIX_CDATA);
                    if (null != entry.getValue()) {
                        strbuff.append(entry.getValue());
                    }
                    strbuff.append(SUFFIX_CDATA);
                } else {
                    if (null != entry.getValue()) {
                        strbuff.append(entry.getValue());
                    }
                }
                strbuff.append("</").append(entry.getKey()).append(">");
            }
            return strbuff.append(SUFFIX_XML).toString();
        }
        return null;
    }

    /**
     * xml转map
     *
     * @param xmlData
     * @return
     * @throws IOException
     */
    public static Map<String, Object> xml2Map(String xmlData) throws IOException {
        Map<String, Object> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
//            doc = reader.read(xmlData);
            doc = reader.read(new ByteArrayInputStream(xmlData.getBytes("UTF-8")));
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                if (e.getText().contains(PREFIX_CDATA)) {
                    map.put(e.getName(), e.getText().substring(8, e.getText().length() - 2));
                } else {
                    map.put(e.getName(), e.getText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
