package utils.httpConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpRequestBodyReader {


    public static String readInputStream(HttpURLConnection connection) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);
            bufferedReader.close();
            return stringBuilder.toString();
    }

    public static String readErrorStream(HttpURLConnection connection) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);
            bufferedReader.close();
            return stringBuilder.toString();

    }
}
