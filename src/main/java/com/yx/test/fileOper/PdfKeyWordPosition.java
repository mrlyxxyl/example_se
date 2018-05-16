package com.yx.test.fileOper;

import com.itextpdf.awt.geom.Rectangle2D.Float;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.io.IOException;

/**
 * 获取pdf文件中关键字的坐标信息
 */
public class PdfKeyWordPosition {
    public static void main(String[] args) {
        try {
            getKeyWordPosition("e:/test.pdf", "1526460707995");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getKeyWordPosition(String filePath, final String keyWord) throws IOException {

        PdfReader pdfReader = new PdfReader(filePath);
        int pageNum = pdfReader.getNumberOfPages();
        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);

        for (int i = 1; i <= pageNum; i++) {
            final int finalI = i;
            pdfReaderContentParser.processContent(i, new RenderListener() {
                @Override
                public void renderText(TextRenderInfo textRenderInfo) {
                    String text = textRenderInfo.getText();
                    if (null != text && text.contains(keyWord)) {
                        Float rect = textRenderInfo.getBaseline().getBoundingRectange();
                        System.out.println("x:" + rect.x);
                        System.out.println("y:" + rect.y);
                        System.out.println("width:" + rect.width);
                        System.out.println("height:" + rect.height);
                        System.out.println("page:" + finalI);
                    }
                }

                @Override
                public void renderImage(ImageRenderInfo arg0) {
                }

                @Override
                public void endTextBlock() {
                }

                @Override
                public void beginTextBlock() {
                }
            });
        }
    }
}