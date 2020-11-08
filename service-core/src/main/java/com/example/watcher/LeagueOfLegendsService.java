package com.example.watcher;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class LeagueOfLegendsService {

    @Autowired
    public KakaoService kakaoService;


    final String api_key = "RGAPI-190c4cca-3af5-4478-916a-31bdf75bc8fa";
    final String url_active = "https://kr.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/";
    final String url_user = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";


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
                kakaoService.sendTalk();
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

}
