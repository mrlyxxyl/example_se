package com.yx.test.fileOper;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExportDocTest {

    public static void main(String[] args) {
        InputStream is;
        FileOutputStream fos;
        try {
            String destFile = "f:\\test.doc";
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "hello");
            map.put("sex", "男");
            map.put("idCard", "200010");
            is = ExportDocTest.class.getClassLoader().getResourceAsStream("test.doc");
            HWPFDocument doc = new HWPFDocument(is);
            Range bodyRange = doc.getRange();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                bodyRange.replaceText("${" + entry.getKey() + "}",
                        entry.getValue());
            }
            fos = new FileOutputStream(destFile);
            doc.write(fos);
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}