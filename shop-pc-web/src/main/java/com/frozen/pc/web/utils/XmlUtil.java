package com.frozen.pc.web.utils;

import com.frozen.pc.web.bean.TextMessage;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-13 20:25
 **/
@Slf4j
public class XmlUtil {
    /**
     * 扩展xstream使其支持CDATA
     */
    private static XStream xstream = new XStream();

    /**
     * 解析微信发来的请求（XML）
     *
     * @param xml
     * @return Map<String, String>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(String xml) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();

        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(xml);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String messageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }
    /**
     * <description> xml转实体对象 </description>
     * @param xml :
     * @param clazz :
     * @return : T
     * @author : lw
     * @date : 2020/4/14 21:52
     */
    public static <T> T xmlToBean(String xml,Class<T> clazz){
        T t = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            log.error("xml转换异常",e);
        }
        return t;
    }

    /**
     * <description> java对象转xml </description>
     * @param obj :
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/14 21:52
     */
    public static String beanToXml(Object obj){
        StringWriter writer = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            //Marshaller.JAXB_FRAGMENT:是否省略xml头信息,true省略，false不省略
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            //Marshaller.JAXB_FORMATTED_OUTPUT:决定是否在转换成xml时同时进行格式化（即按标签自动换行，否则即是一行的xml）
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Marshaller.JAXB_ENCODING:xml的编码方式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            writer = new StringWriter();
            marshaller.marshal(obj, writer);
            return writer.toString();
        } catch (JAXBException e) {
            log.info(e.getMessage());
            return "";
        }
    }
}

