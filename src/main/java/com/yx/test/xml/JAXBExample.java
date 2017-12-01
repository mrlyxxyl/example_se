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
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class JAXBExample {
    public static void main(String[] args) {
        try {
            File file = new File("D:\\person.xml");
            Hobbies hobbies = new Hobbies();
            List<Hobby> hobbyList = new ArrayList<Hobby>();
            Hobby hobby = new Hobby(1, "java");
            hobbyList.add(hobby);
            hobby = new Hobby(2, "php");
            hobbyList.add(hobby);
            hobby = new Hobby(3, "css");
            hobbyList.add(hobby);
            hobbies.setHobbies(hobbyList);
            Person person = new Person(1, "jack", 25, "371325198757586988", hobbies);

            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class, Hobby.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);//格式化输出
            marshaller.marshal(person, file);//写入文件
            marshaller.marshal(person, System.out);//打印到控制台

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            marshaller.marshal(person, bos);
            String xml = bos.toString();//输出到字符串中
            System.out.println(xml);

            //解析xml字符串到对象中
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Person person1 = (Person) unmarshaller.unmarshal(new StringReader(xml));
            System.out.println(person1);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}