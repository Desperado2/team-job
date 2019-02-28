package com.desperado.teamjob.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {

    private static BufferedReader br;

    public static String doGet(String urlPath)throws Exception{
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection","Keep-Alive");
            connection.setRequestProperty("Charset","UTF-8");

            connection.connect();

        int resultCode = connection.getResponseCode();
        StringBuffer sb = new StringBuffer();
        if(HttpURLConnection.HTTP_OK == resultCode){
            String readLine = new String();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            while ((readLine = br.readLine())!=null){
                sb.append(readLine);
            }
            br.close();
        }
        return sb.toString();
    }
}
