/*     */
package com.yx.test.fileOper;
/*     */ 
/*     */

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */ public class UndoPdfSignature
/*     */ {
    /*  38 */   private static Log log = LogFactory.getLog(UndoPdfSignature.class);

    /*     */
/*     */
    public static UndoSignResult undoSign(String sourcePath, String destPath, String fieldName)
/*     */ {
/*  44 */
        UndoSignResult result = new UndoSignResult();
/*  45 */
        if (isEmpty(sourcePath)) {
/*  46 */
            result.setErrMsg("源文件路径加文件名为空");
/*  47 */
            result.setErrNo(1001);
/*  48 */
            return result;
/*     */
        }
/*  50 */
        if (isEmpty(destPath)) {
/*  51 */
            result.setErrMsg("目标文件路径加文件名为空");
/*  52 */
            result.setErrNo(1001);
/*  53 */
            return result;
/*     */
        }
/*  55 */
        if (isEmpty(fieldName)) {
/*  56 */
            result.setErrMsg("字段名为空");
/*  57 */
            result.setErrNo(1001);
/*  58 */
            return result;
/*     */
        }
/*     */ 
/*  61 */
        PdfReader reader = null;
/*  62 */
        FileOutputStream os = null;
/*  63 */
        InputStream ip = null;
/*  64 */
        PdfStamper stamper = null;
/*     */
        try {
/*  66 */
            reader = new PdfReader(sourcePath);
/*  67 */
            os = new FileOutputStream(destPath);
/*     */ 
/*  69 */
            stamper = new PdfStamper(reader, os, '\000', true);
/*     */ 
/*  71 */
            AcroFields fields = reader.getAcroFields();
/*  72 */
            ArrayList names = fields.getSignatureNames();
/*     */
            UndoSignResult localUndoSignResult1;
/*  73 */
            if (names.size() <= 0) {
/*  74 */
                result.setErrMsg("源文件没有签章");
/*  75 */
                result.setErrNo(1001);
/*  76 */
                localUndoSignResult1 = result;
/*     */ 
/* 109 */
                if (stamper != null)
/*     */ try {
/* 111 */
                    stamper.close();
/*     */
                }
/*     */ catch (IOException e2) {
/* 114 */
                    e2.printStackTrace();
/*     */
                }
/* 116 */
                if (reader != null)
/* 117 */ reader.close();
/* 118 */
                if (os != null)
/*     */ try {
/* 120 */
                    os.close();
/*     */
                }
/*     */ catch (IOException e1) {
/* 123 */
                    e1.printStackTrace();
/*     */
                }
/* 125 */
                if (ip != null)
/*     */ try {
/* 127 */
                    ip.close();
/*     */
                }
/*     */ catch (IOException e) {
/* 130 */
                    e.printStackTrace();
/*     */
                }
/*  76 */
                return localUndoSignResult1;
/*     */
            }
/*  78 */
            if (fields.getField(fieldName) == null) {
/*  79 */
                result.setErrMsg("PDF里不存在这个字段");
/*  80 */
                result.setErrNo(1001);
/*  81 */
                localUndoSignResult1 = result;
/*     */ 
/* 109 */
                if (stamper != null)
/*     */ try {
/* 111 */
                    stamper.close();
/*     */
                }
/*     */ catch (IOException e2) {
/* 114 */
                    e2.printStackTrace();
/*     */
                }
/* 116 */
                if (reader != null)
/* 117 */ reader.close();
/* 118 */
                if (os != null)
/*     */ try {
/* 120 */
                    os.close();
/*     */
                }
/*     */ catch (IOException e1) {
/* 123 */
                    e1.printStackTrace();
/*     */
                }
/* 125 */
                if (ip != null)
/*     */ try {
/* 127 */
                    ip.close();
/*     */
                }
/*     */ catch (IOException e) {
/* 130 */
                    e.printStackTrace();
/*     */
                }
/*  81 */
                return localUndoSignResult1;
/*     */
            }
/*     */ 
/*  90 */
            if (clearSignatureField(fieldName, stamper))
/*     */ {
/*  92 */
                result.setErrMsg("success");
/*  93 */
                result.setErrNo(0);
/*  94 */
                localUndoSignResult1 = result;
/*     */ 
/* 109 */
                if (stamper != null)
/*     */ try {
/* 111 */
                    stamper.close();
/*     */
                }
/*     */ catch (IOException e2) {
/* 114 */
                    e2.printStackTrace();
/*     */
                }
/* 116 */
                if (reader != null)
/* 117 */ reader.close();
/* 118 */
                if (os != null)
/*     */ try {
/* 120 */
                    os.close();
/*     */
                }
/*     */ catch (IOException e1) {
/* 123 */
                    e1.printStackTrace();
/*     */
                }
/* 125 */
                if (ip != null)
/*     */ try {
/* 127 */
                    ip.close();
/*     */
                }
/*     */ catch (IOException e) {
/* 130 */
                    e.printStackTrace();
/*     */
                }
/*  94 */
                return localUndoSignResult1;
/*     */
            }
/*     */ 
/*     */
        }
/*     */ catch (Exception e)
/*     */ {
/* 104 */
            e.printStackTrace();
/* 105 */
            log.error(e.getMessage());
/*     */
        }
/*     */ finally
/*     */ {
/* 109 */
            if (stamper != null)
/*     */ try {
/* 111 */
                stamper.close();
/*     */
            }
/*     */ catch (DocumentException e2) {
/* 114 */
                e2.printStackTrace();
/*     */
            } catch (IOException e) {
                e.printStackTrace();
            }
/* 116 */
            if (reader != null)
/* 117 */ reader.close();
/* 118 */
            if (os != null)
/*     */ try {
/* 120 */
                os.close();
/*     */
            }
/*     */ catch (IOException e1) {
/* 123 */
                e1.printStackTrace();
/*     */
            }
/* 125 */
            if (ip != null)
/*     */ try {
/* 127 */
                ip.close();
/*     */
            }
/*     */ catch (IOException e) {
/* 130 */
                e.printStackTrace();
/*     */
            }
/*     */
        }
/* 109 */
        if (stamper != null)
/*     */ try {
/* 111 */
            stamper.close();
/*     */
        }
/*     */ catch (IOException e2) {
/* 114 */
            e2.printStackTrace();
/*     */
        } catch (DocumentException e) {
            e.printStackTrace();
        }
/* 116 */
        if (reader != null)
/* 117 */ reader.close();
/* 118 */
        if (os != null)
/*     */ try {
/* 120 */
            os.close();
/*     */
        }
/*     */ catch (IOException e1) {
/* 123 */
            e1.printStackTrace();
/*     */
        }
/* 125 */
        if (ip != null) {
/*     */
            try {
/* 127 */
                ip.close();
/*     */
            }
/*     */ catch (IOException e) {
/* 130 */
                e.printStackTrace();
/*     */
            }
/*     */
        }
/* 133 */
        result.setErrMsg("撤消失败，系统异常");
/* 134 */
        result.setErrNo(1000);
/* 135 */
        return result;
/*     */
    }

    /*     */
    private static boolean clearSignatureField(String fieldName, PdfStamper stamper) {
/* 138 */
        stamper.getAcroFields().clearSignatureField(fieldName);
/*     */ 
/* 140 */
        return stamper.getAcroFields().removeField(fieldName);
/*     */
    }

    /*     */
/*     */
    public static UndoSignResult undoSign(String sourcePath, String destPath)
/*     */ {
/* 145 */
        UndoSignResult result = new UndoSignResult();
/*     */ 
/* 147 */
        if (isEmpty(sourcePath)) {
/* 148 */
            result.setErrMsg("源文件路径加文件名为空");
/* 149 */
            result.setErrNo(1001);
/* 150 */
            return result;
/*     */
        }
/* 152 */
        if (isEmpty(destPath)) {
/* 153 */
            result.setErrMsg("目标文件路径加文件名为空");
/* 154 */
            result.setErrNo(1001);
/* 155 */
            return result;
/*     */
        }
/* 157 */
        PdfStamper stamper = null;
/*     */ 
/* 159 */
        PdfReader reader = null;
/* 160 */
        FileOutputStream os = null;
/* 161 */
        InputStream ip = null;
/*     */
        try {
/* 163 */
            reader = new PdfReader(sourcePath);
/* 164 */
            AcroFields fields = reader.getAcroFields();
/* 165 */
            stamper = new PdfStamper(reader, os, '\000', true);
/*     */ 
/* 167 */
            ArrayList names = fields.getSignatureNames();
/*     */
            UndoSignResult localUndoSignResult1;
/* 170 */
            if (names.size() <= 0) {
/* 171 */
                result.setErrMsg("源文件没有签章");
/* 172 */
                result.setErrNo(1001);
/* 173 */
                localUndoSignResult1 = result;
/*     */
                return localUndoSignResult1;
/*     */
            }
/* 175 */
            String lastFieldName = (String) names.get(names.size() - 1);
/* 176 */
            if (clearSignatureField(lastFieldName, stamper))
/*     */ {
/* 178 */
                result.setErrMsg("success");
/* 179 */
                result.setErrNo(0);
/* 180 */
                localUndoSignResult1 = result;
/*     */
                return localUndoSignResult1;
/*     */
            }
/*     */
        }
/*     */ catch (DocumentException e) {
/* 184 */
            e.printStackTrace();
/* 185 */
            log.error(e.getMessage());
/*     */
        }
/*     */ catch (IOException e) {
            e.printStackTrace();
        } finally
/*     */ {
/* 189 */
            if (stamper != null)
/*     */ try {
/* 191 */
                stamper.close();
/*     */
            }
/*     */ catch (DocumentException e2) {
/* 194 */
                e2.printStackTrace();
/*     */
            }
/*     */ catch (IOException e2) {
/* 197 */
                e2.printStackTrace();
/*     */
            }
/* 199 */
            if (reader != null)
/* 200 */ reader.close();
/* 201 */
            if (os != null)
/*     */ try {
/* 203 */
                os.close();
/*     */
            }
/*     */ catch (IOException e1) {
/* 206 */
                e1.printStackTrace();
/*     */
            }
/* 208 */
            if (ip != null)
/*     */ try {
/* 210 */
                ip.close();
/*     */
            }
/*     */ catch (IOException e) {
/* 213 */
                e.printStackTrace();
/*     */
            }
/*     */
        }
/* 189 */
        if (stamper != null)
/*     */ try {
/* 191 */
            stamper.close();
/*     */
        }
/*     */ catch (DocumentException e2) {
/* 194 */
            e2.printStackTrace();
/*     */
        }
/*     */ catch (IOException e2) {
/* 197 */
            e2.printStackTrace();
/*     */
        }
/* 199 */
        if (reader != null)
/* 200 */ reader.close();
/* 201 */
        if (os != null)
/*     */ try {
/* 203 */
            os.close();
/*     */
        }
/*     */ catch (IOException e1) {
/* 206 */
            e1.printStackTrace();
/*     */
        }
/* 208 */
        if (ip != null) {
/*     */
            try {
/* 210 */
                ip.close();
/*     */
            }
/*     */ catch (IOException e) {
/* 213 */
                e.printStackTrace();
/*     */
            }
/*     */
        }
/* 216 */
        result.setErrMsg("撤消失败，系统异常");
/* 217 */
        result.setErrNo(1000);
/* 218 */
        return result;
/*     */
    }

    /*     */
/*     */
    private static boolean isEmpty(String string)
/*     */ {
/* 223 */
        return (string == null) || (string.trim().equals(""));
/*     */
    }
/*     */
}

/* Location:           C:\Users\NMY\Desktop\linksign-seal-1.0.1\
 * Qualified Name:     cn.linksign.pdf.UndoPdfSignature
 * JD-Core Version:    0.6.0
 */