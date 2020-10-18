package com.example.watcher;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class watcherService {

    final String api_key = "RGAPI-190c4cca-3af5-4478-916a-31bdf75bc8fa";
    final String url_active = "https://kr.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/";
    final String url_user = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";

    final String kakao_token ="oimNIC5ADoy-rlWAXEUFdvwuRut9ldB-7X6-dwo9c00AAAF1O11IEw";
    final String kakao_url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
//    List<String> user = new ArrayList<>("쿠우펑", "NoahWebster");

    public void request() throws Exception {
//        this.findId("이도류");
//        this.sendTalk();
        this.sendPost();
    }



    public void findId(String name) {
        BufferedReader br = null;

        try {

            URL url = new URL(url_user+ name + "?api_key=" + api_key);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            int responseCode = urlconnection.getResponseCode();

//            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"euc-kr"));
            String result = "";
            String line;
            while((line = br.readLine()) != null) {
                result = result + line;
            }

            JsonParser jsonParser = new JsonParser();



            System.out.println(result);
            JsonObject k = (JsonObject) jsonParser.parse(result);
            String id = k.get("id").getAsString();
            this.getActive(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void getActive(String id) {
        BufferedReader br = null;

        try {

            URL url = new URL(url_active + id);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            urlconnection.setRequestProperty("X-Riot-Token", "RGAPI-190c4cca-3af5-4478-916a-31bdf75bc8fa");
            int responseCode = urlconnection.getResponseCode();
            urlconnection.disconnect();
            if(responseCode == 200){
                this.sendTalk();
            }
//            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
//
//
//
//            String result = "";
//            String line;
//            while((line = br.readLine()) != null) {
//                result = result + line;
//            }
//            JsonParser jsonParser = new JsonParser();
//            System.out.println(result);
//
//            JsonObject k = (JsonObject) jsonParser.parse(result);
//            String code = k.get("status_code").getAsString();
//            return code;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void sendTalk(){
        BufferedReader br = null;
        BufferedWriter bw = null;
        String body ="{   \"object_type\" : \"text\",   \"text\" : \"민준이 게임시작했씁니다\",   \"link\" : {     \"web_url\" : \"https://mrkevinna.github.io\",      \"mobile_web_url\" : \"https://mrkevinna.github.io\"   },   \"button_title\" : \"Check it out!\" }";
        String Bearer = "Bearer ";

        try {

            URL url = new URL(kakao_url);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();

            httpcon.setRequestMethod("POST");
            httpcon.setRequestProperty("Content-Type", "Application/x-www-form-urlencode");
            httpcon.setRequestProperty("Authorization", "Bearer oimNIC5ADoy-rlWAXEUFdvwuRut9ldB-7X6-dwo9c00AAAF1O11IEw");
            httpcon.setConnectTimeout(1000);
            httpcon.setReadTimeout(1000);
            httpcon.setDoInput(true);
            httpcon.setDefaultUseCaches(false);

            //파라메터가 없으면 지워주면 된다.
//            bw = new BufferedWriter(new OutputStreamWriter(httpcon.getOutputStream(), "UTF-8"));
//            bw.write("template_object="+ body);

            br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

            String result = "";
            String line;
            while((line = br.readLine()) != null) {
                result = result + line;
            }

            JsonParser jsonParser = new JsonParser();



            System.out.println(result);
            JsonObject k = (JsonObject) jsonParser.parse(result);
            String id = k.get("id").getAsString();
            this.getActive(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // HTTP POST request
    public void sendPost() throws Exception
    {


        String parameters = "template_object={   \"object_type\" : \"text\",   \"text\" : \"fff\",   \"link\" : {     \"web_url\" : \"https://mrkevinna.github.io\",      \"mobile_web_url\" : \"https://mrkevinna.github.io\"   },   \"button_title\" : \"Check it out!\" }";
        URL url = new URL(kakao_url);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + kakao_token);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("charset", "UTF-8");
        // HTTP POST 메소드 설정
        con.setDoOutput(true);
        // POST 파라미터 전달을 위한 설정
        // Send post request
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) { response.append(inputLine); } in.close();
        // print result
        System.out.println("HTTP 응답 코드 : " + responseCode);
        System.out.println("HTTP body : " + response.toString()); }

}
