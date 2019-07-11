package tts;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import javazoom.jl.player.Player;

public class Clovatts implements Runnable{
	private String clientId = "q7fry28vkn";//���ø����̼� Ŭ���̾�Ʈ ���̵�";
	private String clientSecret = "GO7dAoV7g2Vzy9kDGXVoi2lsPRgi4vP7wasj2RPG";//���ø����̼� Ŭ���̾�Ʈ ��ũ����";
	String meant;
	public Clovatts(String meant) {
		this.meant=meant;
	}
	public void run() {
		try {
	        String text = URLEncoder.encode(meant, "UTF-8"); // 13��
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
	        if(responseCode==200) { // ���� ȣ
	            InputStream is = con.getInputStream();		//������ ��Ʈ������ ��ȯ�� �� ���ۿ� ����
	            Readtext p=new Readtext(is);				//javazoom �÷��̾�� �����ͽ�Ʈ�� ���
	            p.play();
	            is.close();
	        } else {  // ���� �߻�
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

