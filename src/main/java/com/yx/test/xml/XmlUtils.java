package com.yx.test.xml;

/**
 * Hobby: NMY
 * Date: 16-8-29
 */

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XmlUtils {

    static JAXBContext jaxbContext;
    static Marshaller marshaller;
    static Unmarshaller unmarshaller;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(Person.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);//格式化输出
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出XML字符串
     *
     * @param object
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    public static String toXml(Object object) throws JAXBException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        marshaller.marshal(object, bos);
        return bos.toString();
    }

    /**
     * 输出XML字符串
     *
     * @param clazz
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String xml, Class<T> clazz) throws JAXBException {
        return (T) unmarshaller.unmarshal(new StringReader(xml));
    }

    public static void main(String[] args) {
        try {
            Hobbies hobbies = new Hobbies();
            List<Hobby> hobbyList = new ArrayList<Hobby>();
            Hobby hobby = new Hobby(1, "java");
            hobbyList.add(hobby);
            hobby = new Hobby(2, "php");
            hobbyList.add(hobby);
            hobby = new Hobby(3, "css");
            hobbyList.add(hobby);
            hobbies.setHobbies(hobbyList);
            String str = "test";
            Person person = new Person(1, str, 25, "371325198757586988", hobbies);

            String xml = XmlUtils.toXml(person);
            System.out.println(xml);

            Person personXml1 = XmlUtils.toBean(xml, Person.class);
            System.out.println(personXml1);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}