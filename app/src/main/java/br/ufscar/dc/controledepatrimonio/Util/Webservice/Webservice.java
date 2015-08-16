package br.ufscar.dc.controledepatrimonio.Util.Webservice;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Webservice {
    private URL urlJSOn;
    private String url;
    private HttpURLConnection con = null;
    private final String URL_AUTENTICAR = "http://192.168.0.10:8080/Patrimonio/j_spring_security_check";
    private final String USER_AGENT = "Mozilla/5.0";
    private List<String> cookies;
    private HttpURLConnection conn;

    public Webservice(String url) {
        this.url = url;
        autenticar();
    }

    public String getJSON() {
        try {
            this.urlJSOn = new URL(url);
            con = (HttpURLConnection) this.urlJSOn.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Content-length", "0");
            con.setUseCaches(false);
            con.setAllowUserInteraction(false);
            con.connect();
            int status = con.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
            }
        } catch (ProtocolException ex) {
            Log.d("ProtocolException", ex.getMessage());
        } catch (IOException ex) {
            Log.d("IOException", ex.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    Log.d("Exception", ex.getMessage());
                }
            }
        }
        return null;
    }

    private void autenticar() {
        CookieHandler.setDefault(new CookieManager());

        try {
            String page = getPageContent(URL_AUTENTICAR);
            String postParams = getFormParams(page, "membro", "membro");

            sendPost(URL_AUTENTICAR, postParams);

            String result = getPageContent(url);

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendPost(String url, String postParams) throws Exception {
        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();

        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", "accounts.google.com");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", "https://accounts.google.com/ServiceLoginAuth");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        conn.setDoOutput(true);
        conn.setDoInput(true);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    }

    private String getPageContent(String url) throws Exception {
        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("GET");

        conn.setUseCaches(false);

        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        int responseCode = conn.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private String getFormParams(String html, String username, String password) throws UnsupportedEncodingException {
        Document doc = Jsoup.parse(html);

        Element loginform = doc.getElementById("loginForm");
        Elements inputElements = loginform.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("j_username"))
                value = username;
            else if (key.equals("j_password"))
                value = password;
            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return result.toString();
    }

}
