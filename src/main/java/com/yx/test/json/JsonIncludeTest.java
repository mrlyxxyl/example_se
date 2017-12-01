package com.yx.test.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yx.bean.Animal;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

/**
 * User: NMY
 * Date: 16-8-29
 * bean的属性值不为null则生成json，否则不会生成
 */
public class JsonIncludeTest {

    @Test
    public void doTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        StringWriter sw;
        String json;
        try {
            Animal animal = new Animal();
            //代码块:1,设置name和sex属性
            {
                animal.setName("dog");
                animal.setSex(true);
            }
            //代码块:2,只设置name属性
            {
                animal.setName("dog");
            }
            //代码块:3,什么也不设置
            {
            }
            sw = new StringWriter();
            JsonGenerator generator = mapper.getFactory().createGenerator(sw);
            generator.writeObject(animal);
            json = sw.toString();
            System.out.println(json);
            generator.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}