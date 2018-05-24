package com.yx.test.fileOper;

import com.itextpdf.text.Image;
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
        addImage("e:/test.pdf", "e:/" + System.currentTimeMillis() + ".pdf", "e:/test.png", 1, 10, 10, 100, 50);
    }

    /**
     * pdf中添加图片
     *
     * @param srcPath   原文件路径
     * @param desPath   生成文件路径
     * @param imagePath 图片路径
     * @param page      页码
     * @param x         x坐标
     * @param y         y坐标
     * @param w         图片宽
     * @param h         图片高
     * @throws Exception
     */
    public static void addImage(String srcPath, String desPath, String imagePath, int page, float x, float y, float w, float h) throws Exception {
        InputStream input = new FileInputStream(new File(srcPath));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(desPath));
        AcroFields form = stamper.getAcroFields();
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
        Image image = Image.getInstance(imagePath);
        PdfContentByte under = stamper.getOverContent(page);
        image.scaleToFit(w, h);
        image.setAbsolutePosition(x, y);
        under.addImage(image);
        stamper.close();
        reader.close();
    }
}