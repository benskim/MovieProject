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
	private String[] feel = {"»ç¶û","·Î¸Ç½º","¸Å·Â","Áñ°Å¿ò","½º¸±",
			"¼Ò¸§","±äÀå","°øÆ÷","À¯¸Ó","¿ôÀ½","°³±×","¿ìÁ¤","½½ÇÄ","´«¹°",
			"Çàº¹","ÀüÀ²","°æÀÌ","¿ì¿ï","Àý¸Á","½Åºñ",
			"¿©¿î","Èñ¸Á","±ä¹Ú","°¨µ¿","°¨¼º","ÈÞ¸Ó´ÏÁò",
			"ÀÚ±Ø","Àç¹Ì","¾×¼Ç","¹ÝÀü","ºñ±Ø","¹Ì½ºÅ×¸®",
			"ÆÇÅ¸Áö","²Þ","¼³·½","Èï¹Ì","Ç³°æ","ÀÏ»ó",
			"¼ø¼ö","Èú¸µ","´«¹°","±×¸®¿ò","È£·¯","Ãæ°Ý","ÀÜÈ¤",
			"¾ÖÁ¤","¸ðÇè","½º¸±","´À¿Í¸£","´ÙÅ¥","ÄÚ¹Ìµð","Ãæ°Ý"
	};
	public String naverFind(String title)
    {
    	String json="";
    	String clientId = "xzCkjdHVMmHBQA5GsaNk";//¾ÖÇÃ¸®ÄÉÀÌ¼Ç Å¬¶óÀÌ¾ðÆ® ¾ÆÀÌµð°ª";
        String clientSecret = "xpoZsdLvAt";//¾ÖÇÃ¸®ÄÉÀÌ¼Ç Å¬¶óÀÌ¾ðÆ® ½ÃÅ©¸´°ª";
        try {
            String text = URLEncoder.encode(title, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?display=100&query="+ text; // json °á°ú
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml °á°ú
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // Á¤»ó È£Ãâ
                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            } else {  // ¿¡·¯ ¹ß»ý
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
    		System.out.println("ÀúÀå ¿Ï·á");
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
    			// compile => ÆÐÅÏÀ» Àû¿ë (¼³Á¤)
    		}
    		// ÆÄÀÏ ÀÐ±â => FileReader(ÆÄÀÏÀÐ±â) , FileWriter(ÆÄÀÏ¾²±â)
    		/*
    		 *   1. ÀÚ¹Ù IO 
    		 *   1) ¹ÙÀÌÆ® ½ºÆ®¸² : 8bit => ÆÄÀÏ Àü¼Û , ³×Æ®¿öÅ© Àü¼Û 
    		 *      InputStream / OutputStream 
    		 *   2) ¹®ÀÚ ½ºÆ®¸² : 16bit => ÇÑ±Û ÆÄÀÏ ÀÐ±â 
    		 *      Reader / Writer
    		 *   3) -1 : EOF
    		 *   4) Buffered
    		 */
    		/*
    		 *   ¸ÀÀÖ()
    		 *   ¸ÀÀÖ[°¡-ÆR]+  [0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}
    		 *                 211 238 142 20
    		 *   Â¥[°¡-ÆR]+
    		 *   
    		 *   ¸ÀÀÖÁö´Â ¾Ê°í
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
    		 *    pattern => ´Ü¾î(¿µÈ­Á¦¸ñ)
    		 *    matcher => line
    		 *    
    		 *    find()
    		 *    
    		 *    (([0-9]{1,3}).([0-9]{1,3}).([0-9]{1,3}).([0-9]{1,3}))
    		 *    211.238.142
    		 *    [0-9] 0~9
    		 *    [0-9]{1,3} => 0~999
    		 *    
    		 *    ^[A-Za-z0-9°¡-ÆR]
    		 *    0@naver.com
    		 *    aaaaa@naver.com
    		 *    abcsks@naver.com
    		 *    ¸ÀÀÖ[°¡-ÆR]* ¸ÀÀÖ´Â ¸ÀÀÖ°í ¸ÀÀÖ°Ô....
    		 *    + 1rodltkd
    		 *    *
    		 *    °¡·Î¼ö 
    		 */
    		//System.out.println(data);
    		String[] datas=data.split("\n");
    		for(String line:datas)
    		{
    			for(int j=0;j<m.length;j++)
    			{
    				m[j]=p[j].matcher(line);// Mathcer»ý¼º
    				// ´ñ±Û¿¡ ¿µÈ­Á¦¸ñÀ» Æ÷ÇÔ
    				while(m[j].find())// ºñ±³ÆÐÅÏ (Æ÷ÇÔ)
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
