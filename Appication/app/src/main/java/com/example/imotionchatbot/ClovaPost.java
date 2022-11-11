package com.example.imotionchatbot;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClovaPost {

    private final String url = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";

    public ClovaPost() {
    }

    public int sendPost(String params)
    {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "gv6bxin5su");
            connection.setRequestProperty("X-NCP-APIGW-API-KEY", "xB5jO0EAJrbBFxkwzMvAGl62dBMwdtBFeWVcNf69");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setReadTimeout(1000);
            connection.setConnectTimeout(1500);

            // Send Data
            byte[] outputInBytes = params.getBytes(StandardCharsets.UTF_8);
            OutputStream os = connection.getOutputStream();
            os.write(outputInBytes);
            os.close();

            // Recv Data
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = br.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            br.close();

            String res = response.toString();
            res = res.trim();
            Log.i("Telechips", res);

            JSONObject jsonObject = new JSONObject(res);
            String sentiment = jsonObject.getJSONObject("document").getString("sentiment");

            if (sentiment.compareTo("positive") == 0)
            {
                return 1;
            }
            else if (sentiment.compareTo("negative") == 0)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
