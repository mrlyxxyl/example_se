import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * post提交大文本处理方式
 */
public class HttpPostBigText {

    private static final String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";
    private static final String ENCODING = "UTF-8";

    public static void main(String[] args) throws Exception {
        sendHttpPostRequest("http://www.baidu.com?table_name=person&code=12345", "hello world");//普通参数直接拼在url后
    }

    public static String sendHttpPostRequest(String serverUrl, String data) throws Exception {
        URL url = new URL(serverUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Charset", ENCODING);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);
        contentBody.append("\r\n").append("Content-Disposition: form-data; name=\"").append("data\"").append("\r\n\r\n").append(data).append("\r\n").append("--").append(BOUNDARY);
        String boundaryMessage = contentBody.toString();
        OutputStream out = connection.getOutputStream();
        out.write(boundaryMessage.getBytes(ENCODING));
        out.write((BOUNDARY + "--\r\n").getBytes(ENCODING));
        String endBoundary = "\r\n--" + BOUNDARY + "--\r\n";
        out.write(endBoundary.getBytes(ENCODING));
        out.flush();
        out.close();
        String strLine;
        String strResponse = "";
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
        while ((strLine = reader.readLine()) != null) {
            strResponse += strLine;
        }
        return strResponse;
    }
}