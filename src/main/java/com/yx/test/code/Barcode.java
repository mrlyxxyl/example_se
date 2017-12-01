package com.yx.test.code;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 生成条形码
 */
public class Barcode {

    public static void main(String[] args) {
        try {
            Code39Bean bean = new Code39Bean();
            final int dpi = 150;
            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
            bean.setWideFactor(3);
            bean.doQuietZone(false);
            File outputFile = new File("E:\\barcode.png");
            OutputStream out = new FileOutputStream(outputFile);
            try {
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
                bean.generateBarcode(canvas, "A123456");//内容
                canvas.finish();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}