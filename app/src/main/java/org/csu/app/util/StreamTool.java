package org.csu.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class StreamTool {

    public static String getJsonString(HttpURLConnection connection) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int statusCode = connection.getResponseCode();
        if (statusCode == 200) {
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String result;
            while ((result = bufferedReader.readLine()) != null) {
                stringBuilder.append(result);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            connection.disconnect();

            return stringBuilder.toString();
        }
        return stringBuilder.toString();
    }
}
