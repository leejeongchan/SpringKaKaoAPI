package com.api.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@Service
public class KaKaoAPI {
	//Post로 토큰 얻어오기
	/**
	 * 
	 * @param authorize_code
	 * @return access_token
	 * curl -v -X POST https://kauth.kakao.com/login/token \
		 -d 'grant_type=authorization_code' \
		 -d 'client_id={app_key}' \
		 -d 'redirect_uri={redirect_uri}' \
		 -d 'code={authorize_code}'
	 * Post로 전송해서 여기서 토큰을 받아옴.
	 * HTTP/1.1 200 OK
		Content-Type: application/json;charset=UTF-8
		{
		    "access_token":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
		    "token_type":"bearer",
		    "refresh_token":"yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
		    "expires_in":43199,
		    "scope":"Basic_Profile"
		}
	 * 
	 */
	public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        
        try {
            URL url = new URL(reqURL);
            // url기반으로 열어주기 
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=699e22769b9f3789d4764b8d68226a98");
            sb.append("&redirect_uri=http://localhost:8080/login");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();
            
            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return access_Token;
    }
	/**
	 * 
	 * @param access_Token
	 * @return 유저정보
	 * POST https://kapi.kakao.com/v2/user/me
	 * 헤더에 Authorization: Bearer: access token 추가하여 요청
	 * GET/POST /v2/user/me HTTP/1.1
Host: kapi.kakao.com
Authorization: Bearer {access_token}
Content-type: application/x-www-form-urlencoded;charset=utf-8
	 * 응답으로 Json 객체 받아옴.
	 * HTTP/1.1 200 OK
{
  "id":123456789,
  "properties":{
     "nickname":"홍길동",
     "thumbnail_image":"http://xxx.kakao.co.kr/.../aaa.jpg",
     "profile_image":"http://xxx.kakao.co.kr/.../bbb.jpg",
     "custom_field1":"23",
     "custom_field2":"여"
     ...
  },
  "kakao_account": { 
    "email_needs_agreement":false, 
    "is_email_valid": true,   
    "is_email_verified": true,   
    "email": "xxxxxxx@xxxxx.com"
    "age_range_needs_agreement":false,
    "age_range":"20~29",
    "birthday_needs_agreement":false,
    "birthday":"1130",
    "gender_needs_agreement":false,
    "gender":"female"
  }
}
	 */
	public HashMap<String, Object> getUserInfo (String access_Token) {
	    
	    //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
	    HashMap<String, Object> userInfo = new HashMap<>();
	    String reqURL = "https://kapi.kakao.com/v2/user/me";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        
	        //    요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	        
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        
	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	        
	        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        String email = kakao_account.getAsJsonObject().get("email").getAsString();
	        
	        userInfo.put("nickname", nickname);
	        userInfo.put("email", email);
	        
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	    return userInfo;
	}
	/**
	 * 로그아웃 
	 * @param access_Token
	 * https://kapi.kakao.com/v1/user/logout POST로 토큰을 담아서 요청하면된다.
	 * 응답으로 로그아웃 된 ㅅ ㅏ용자 id가 부여된다.
	 */
	public void kakaoLogout(String access_Token) {
	    String reqURL = "https://kapi.kakao.com/v1/user/logout";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	        
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String result = "";
	        String line = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println(result);
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}


}
