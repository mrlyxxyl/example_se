package com.yx.test.fileOper;

import com.yx.bean.Person;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportExcelList {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        String srcFilePath = ExportExcelList.class.getClassLoader().getResource("persons.xls").getPath();
        String filePath = "d:/test.xls";
        XLSTransformer transformer = new XLSTransformer();
        Map<String, Object> beanParams = new HashMap<String, Object>();
        List<Person> list = new ArrayList<Person>();
        list.add(new Person(1, "张三", 25));
        list.add(new Person(2, "李四", 30));
        list.add(new Person(3, "王五", 35));
        list.add(new Person(4, "赵六", 40));
        beanParams.put("list", list);
        transformer.transformXLS(srcFilePath, beanParams, filePath);
    }
}
