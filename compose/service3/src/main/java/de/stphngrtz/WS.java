package de.stphngrtz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public final class WS {

    private WS() {
    }

    public static String get(String url) throws IOException {
        HttpURLConnection connService1 = (HttpURLConnection) new URL(url).openConnection();
        connService1.setRequestMethod("GET");

        int responseCode = connService1.getResponseCode();
        if (responseCode != 200) {
            connService1.disconnect();
            throw new IllegalStateException("[" + new Date() + "] got response code " + responseCode + " from " + url);
        }

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connService1.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        connService1.disconnect();
        return sb.toString();
    }
}
