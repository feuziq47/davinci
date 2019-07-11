package tts;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import javazoom.jl.player.Player;

public class Clovatts implements Runnable{
	private String clientId = "q7fry28vkn";//애플리케이션 클라이언트 아이디값";
	private String clientSecret = "GO7dAoV7g2Vzy9kDGXVoi2lsPRgi4vP7wasj2RPG";//애플리케이션 클라이언트 시크릿값";
	String meant;
	public Clovatts(String meant) {
		this.meant=meant;
	}
	public void run() {
		try {
	        String text = URLEncoder.encode(meant, "UTF-8"); // 13자
	        String apiURL = "https://naveropenapi.apigw.ntruss.com/voice/v1/tts";
	        URL url = new URL(apiURL);
	        HttpURLConnection con = (HttpURLConnection)url.openConnection();
	        con.setRequestMethod("POST");
	        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
	        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
	        // post request
	        String postParams = "speaker=mijin&speed=0&text=" + text;
	        con.setDoOutput(true);
	        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	        wr.writeBytes(postParams);
	        wr.flush();
	        wr.close();
	        int responseCode = con.getResponseCode();
	        BufferedReader br;
	        if(responseCode==200) { // 정상 호
	            InputStream is = con.getInputStream();		//데이터 스트림으로 변환된 값 버퍼에 저장
	            Readtext p=new Readtext(is);				//javazoom 플레이어로 데이터스트림 재생
	            p.play();
	            is.close();
	        } else {  // 에러 발생
	            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));	
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            System.out.println(response.toString());
	        }
	    } catch (Exception e) {
	        System.out.println(e);
	    }
		
	}
		
}

