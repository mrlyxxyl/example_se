package com.yx.test.fileOper;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * pdf 插入图片
 * User: LiWenC
 * Date: 18-5-21
 */
public class PdfImage {
    public static void main(String[] args) throws Exception {
        addImage();
    }

    public static void addImage() throws Exception {
        String templatePath = "e:/test.pdf";//模板文件路径
        String targetPath = "e:/target.pdf";//生成的文件路径
        String fieldName = "field";//域名
        String imagePath = "e:/test.png";//图片路径
        InputStream input = new FileInputStream(new File(templatePath));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetPath));
        AcroFields form = stamper.getAcroFields();
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();
        Image image = Image.getInstance(imagePath);//读图片
        PdfContentByte under = stamper.getOverContent(pageNo);
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        image.setAbsolutePosition(x, y);
        under.addImage(image);
        stamper.close();
        reader.close();
    }
}