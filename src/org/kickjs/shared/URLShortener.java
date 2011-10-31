package org.kickjs.shared;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class URLShortener {
    public static String URL_SHORTENER_API_KEY = getProperty("URL_SHORTENER_API_KEY");

    private static String getProperty(String name){
        // Read properties file.
        Properties properties = new Properties();
        try {
            InputStream is = URLShortener.class.getResourceAsStream("ServerConstants.properties");
            properties.load(is);
            return properties.getProperty(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getShortURL(String url) throws Exception{
        URL urlObject = new URL("https://www.googleapis.com/urlshortener/v1/url?key="+ URL_SHORTENER_API_KEY);
        HttpURLConnection urlConnection = (HttpURLConnection )urlObject.openConnection();
        JSONObject obj = new JSONObject();
        obj.put("longUrl",url);
        String jsonString = obj.toString();


        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type",
                "application/json");

        urlConnection.setUseCaches (false);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);

        //Send request
        DataOutputStream wr = new DataOutputStream (
                urlConnection.getOutputStream ());
        wr.write(jsonString.getBytes("UTF-8"));
        wr.flush ();
        wr.close ();

        //Get Response
        InputStream is = urlConnection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder response = new StringBuilder();
        while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        JSONObject result = new JSONObject (response.toString());
        if (result.has("id")){
            return result.getString("id");
        }
        else {
            throw new Exception(response.toString());
        }
    }

    public static void main(String[] args) {
        try {
            String shortUrl = getShortURL("http://google.com/?2=4&s=test");
            System.out.println(shortUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
