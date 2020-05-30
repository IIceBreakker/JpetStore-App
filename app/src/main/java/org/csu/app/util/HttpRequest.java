package org.csu.app.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  httpGet method accepts a url {@link String}, httpPost method accepts a url and data {@link String}
 *  and they both return a {@link HttpURLConnection}
 *
 *  @author IIceBreakker 575987720@qq.com
 */
public class HttpRequest {

    public static HttpURLConnection httpGet(String _url) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Charset", "UTF-8");
        return connection;
    }

    public static HttpURLConnection httpPost(String _url, String data) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Charset", "UTF-8");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        os.close();

        return connection;
    }

}
