package com.sist.movie.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MovieManager {
	private String[] feel = {"사랑","로맨스","매력","즐거움","스릴",
			"소름","긴장","공포","유머","웃음","개그","우정","슬픔","눈물",
			"행복","전율","경이","우울","절망","신비",
			"여운","희망","긴박","감동","감성","휴머니즘",
			"자극","재미","액션","반전","비극","미스테리",
			"판타지","꿈","설렘","흥미","풍경","일상",
			"순수","힐링","눈물","그리움","호러","충격","잔혹",
			"애정","모험","스릴","느와르","다큐","코미디","충격"
	};
	public String naverFind(String title)
    {
    	String json="";
    	String clientId = "xzCkjdHVMmHBQA5GsaNk";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "xpoZsdLvAt";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(title, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?display=100&query="+ text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //System.out.println(response.toString());
            json=response.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return json;
    }
	public void jsonParse(String rData)
    {
    	try
    	{
    		String json=naverFind(rData);
    		//System.out.println(json);
    		JSONParser jp=new JSONParser();
    		JSONObject obj=(JSONObject)jp.parse(json);
    		JSONArray arr=(JSONArray)obj.get("items");
    		//System.out.println(arr.toJSONString());
    		String data="";
    		for(int i=0;i<arr.size();i++)
    		{
    			JSONObject j=(JSONObject)arr.get(i);
    			String desc=(String)j.get("description");
    			//System.out.println(desc);
    			data+=desc+"\n";
    		}
    		data=data.replaceAll("[A-Za-z0-9]", "");
    		data=data.replace("<", "");
    		data=data.replace(">", "");
    		data=data.replace("/", "");
    		data=data.replace("&", "");
    		data=data.replace(";", "");
    		data=data.replace("#", "");
    		//System.out.println(data);
    		FileWriter fw=new FileWriter("c:\\download\\naver.txt");
    		fw.write(data);
    		fw.close();
    		System.out.println("저장 완료");
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    }
	public List<MovieFeelVO> recommandMovie()
    {
    	List<MovieFeelVO> list=new ArrayList<MovieFeelVO>();
    	try
    	{
    		
    		Pattern[] p=new Pattern[feel.length];
    		for(int i=0;i<p.length;i++)
    		{
    			p[i]=Pattern.compile(feel[i]);
    			// compile => 패턴을 적용 (설정)
    		}
    		// 파일 읽기 => FileReader(파일읽기) , FileWriter(파일쓰기)
    		/*
    		 *   1. 자바 IO 
    		 *   1) 바이트 스트림 : 8bit => 파일 전송 , 네트워크 전송 
    		 *      InputStream / OutputStream 
    		 *   2) 문자 스트림 : 16bit => 한글 파일 읽기 
    		 *      Reader / Writer
    		 *   3) -1 : EOF
    		 *   4) Buffered
    		 */
    		/*
    		 *   맛있()
    		 *   맛있[가-힣]+  [0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}
    		 *                 211 238 142 20
    		 *   짜[가-힣]+
    		 *   
    		 *   맛있지는 않고
    		 */
    		int[] count=new int[feel.length];
    		Matcher[] m=new Matcher[feel.length];
    		FileReader fr=new FileReader("c:\\download\\naver.txt");
    		String data="";
    		int i=0;
    		while((i=fr.read())!=-1)
    		{
    			data+=String.valueOf((char)i);
    		}
    		/*
    		 *    name LIKE '%n%'
    		 *              ====== Pattern
    		 *    =====
    		 *     mather
    		 *    ==> find()
    		 *    
    		 *    pattern => 단어(영화제목)
    		 *    matcher => line
    		 *    
    		 *    find()
    		 *    
    		 *    (([0-9]{1,3}).([0-9]{1,3}).([0-9]{1,3}).([0-9]{1,3}))
    		 *    211.238.142
    		 *    [0-9] 0~9
    		 *    [0-9]{1,3} => 0~999
    		 *    
    		 *    ^[A-Za-z0-9가-힣]
    		 *    0@naver.com
    		 *    aaaaa@naver.com
    		 *    abcsks@naver.com
    		 *    맛있[가-힣]* 맛있는 맛있고 맛있게....
    		 *    + 1rodltkd
    		 *    *
    		 *    가로수 
    		 */
    		//System.out.println(data);
    		String[] datas=data.split("\n");
    		for(String line:datas)
    		{
    			for(int j=0;j<m.length;j++)
    			{
    				m[j]=p[j].matcher(line);// Mathcer생성
    				// 댓글에 영화제목을 포함
    				while(m[j].find())// 비교패턴 (포함)
    				{
    					//System.out.println(m[j].group());
    					count[j]++;
    				}
    			}
    		}
    		
    		for(int k=0;k<feel.length;k++)
    		{
    			if(count[k]>1)
    			{
    				MovieFeelVO vo=new MovieFeelVO();
    				vo.setFeel(feel[k]);
    				vo.setCount(count[k]);
    				list.add(vo);
    				
    			}
    		}
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    	return list;
    }

	
}
