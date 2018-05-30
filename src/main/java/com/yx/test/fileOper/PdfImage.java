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
import java.util.UUID;

/**
 * pdf 插入图片
 * User: LiWenC
 * Date: 18-5-21
 */
public class PdfImage {

    public static void main(String[] args) throws Exception {
//        sign();
        unSign("e:", "des.pdf", System.currentTimeMillis() + ".pdf", "Signature-d16a2afb748646fdac45510f717c2f18");
    }

    public static void sign() throws Exception {
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
     * 撤销签名
     *
     * @param filePath    文件路径
     * @param fileName    文件名称
     * @param desFileName 临时文件名称
     * @param fieldName   域名称
     * @return
     */
    public static boolean unSign(String filePath, String fileName, String desFileName, String fieldName) {
        String signedFile = filePath + "/" + fileName;
        String desFile = filePath + "/" + desFileName;
        UndoSignResult result = UndoPdfSignature.undoSign(signedFile, desFile, fieldName);
        return result.getErrNo() == 0;
    }
}
