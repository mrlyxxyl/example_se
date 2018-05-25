package com.yx.test.fileOper;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.security.ExternalBlankSignatureContainer;
import com.itextpdf.text.pdf.security.ExternalSignatureContainer;
import com.itextpdf.text.pdf.security.MakeSignature;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * pdf 插入图片
 * User: LiWenC
 * Date: 18-5-21
 */
public class PdfImage {

    public static void main(String[] args) throws Exception {
        int estimatedSize = 50000;
        float lx = 10;
        float by = 10;
        float rx = 110;
        float ty = 110;
        int page = 1;
        String imagePath = "e:/test.png";
        String srcPath = "e:";
        String srcFileName = "test.pdf";
        String desPath = "e:";
        String desFileName = "des.pdf";
        String fieldName = "Signature-" + UUID.randomUUID().toString().replaceAll("-", "");
        boolean flag = addImage(srcPath, desPath, srcFileName, desFileName, imagePath, page, lx, by, rx, ty, fieldName, estimatedSize);
        System.out.println(flag + "--" + fieldName);
    }

    /**
     * 添加图片
     *
     * @param srcPath
     * @param desPath
     * @param imagePath
     * @param page
     * @param lx
     * @param by
     * @param rx
     * @param ty
     * @param fieldName
     * @param estimatedSize
     * @throws Exception
     */
    public static boolean addImage(String srcPath, String desPath, String srcFileName, String desFileName, String imagePath, int page, float lx, float by, float rx, float ty, String fieldName, int estimatedSize) throws Exception {
        PdfReader reader = null;
        PdfStamper stamper = null;
        FileOutputStream os = null;
        try {
            if (estimatedSize <= 8192) {
                estimatedSize = 8192;
            }
            File tmp = File.createTempFile("signTemp", ".pdf");
            reader = new PdfReader(srcPath + "/" + srcFileName);
            os = new FileOutputStream(desPath + "/" + desFileName);
            stamper = PdfStamper.createSignature(reader, os, '\000', tmp, true);
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            Rectangle rectangle = new Rectangle(lx, by, rx, ty);
            appearance.setVisibleSignature(rectangle, page, fieldName);
            Image image = Image.getInstance(imagePath);
            appearance.setImage(image);
            appearance.setLayer2Text("");
            appearance.setLayer4Text("");
            appearance.setReason("新月 SM2签章");
            appearance.setSignDate(Calendar.getInstance());
            appearance.setSignatureEvent(new PdfSignatureAppearance.SignatureEvent() {
                @Override
                public void getSignatureDictionary(PdfDictionary pdfDictionary) {
                    pdfDictionary.put(new PdfName("LK_SIGN_ALG"), new PdfString("3"));
                }
            });
            ExternalSignatureContainer blank = new ExternalBlankSignatureContainer(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
            MakeSignature.signExternalContainer(appearance, blank, estimatedSize);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stamper != null) {
                    stamper.close();
                }
                if (reader != null) {
                    reader.close();
                }

                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 解析页码
     *
     * @param page 1;3;5;6-9
     * @return
     */
    private static Set<Integer> parsePages(String page, int totalPage) {
        Set<Integer> result = new HashSet<Integer>();
        if (page == null || "".equals(page.trim())) {
            for (int i = 1; i <= totalPage; i++) {
                result.add(i);
            }
        } else {
            String[] pgs = page.split(";");
            for (String p : pgs) {
                if (p.indexOf("-") != -1) {
                    String[] point = p.split("-");
                    for (int i = Integer.parseInt(point[0]); i <= Integer.parseInt(point[1]); i++) {
                        if (i <= totalPage) {
                            result.add(i);
                        }
                    }
                } else if (Integer.parseInt(p) <= totalPage) {
                    result.add(Integer.parseInt(p));
                }
            }
        }
        return result;
    }
}
