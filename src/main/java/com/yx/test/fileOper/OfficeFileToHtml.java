package com.yx.test.fileOper;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * office文件转换为html文件
 */
public class OfficeFileToHtml {
    static int WORD_HTML = 8;
    static int EXCEL_HTML = 44;

    /**
     * WORD转HTML
     *
     * @param wordFile word全路径
     * @param htmlFile html全路径
     */
    public static void wordToHtml(String wordFile, String htmlFile) {
        ActiveXComponent app = new ActiveXComponent("Word.Application");
        try {
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[]{wordFile, new Variant(false), new Variant(true)}, new int[1]).toDispatch();
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[]{htmlFile, new Variant(WORD_HTML)}, new int[1]);
            Variant f = new Variant(false);
            Dispatch.call(doc, "Close", f);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            app.invoke("Quit", new Variant[]{});
        }
    }

    /**
     * EXCEL转HTML
     *
     * @param xlsFile
     * @param htmlFile
     */
    public static void excelToHtml(String xlsFile, String htmlFile) {
        ActiveXComponent app = new ActiveXComponent("Excel.Application");
        try {
            app.setProperty("Visible", new Variant(false));
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.invoke(excels, "Open", Dispatch.Method, new Object[]{xlsFile, new Variant(false), new Variant(true)}, new int[1]).toDispatch();
            Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[]{htmlFile, new Variant(EXCEL_HTML)}, new int[1]);
            Variant f = new Variant(false);
            Dispatch.call(excel, "Close", f);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            app.invoke("Quit", new Variant[]{});
        }
    }

    public static void main(String[] args) {
        excelToHtml("e:/test.xlsx", "e:/" + System.currentTimeMillis() + ".html");
    }
}