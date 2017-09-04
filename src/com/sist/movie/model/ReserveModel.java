package com.sist.movie.model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.member.dao.ReserveVO;
import com.sist.movie.dao.*;

public class ReserveModel {
	public void reserveMain(HttpServletRequest request){
		request.setAttribute("main_jsp", "../reserve/reserve_main.jsp");
	}
	public void reserveMovieData(HttpServletRequest request) {
		MovieDAO dao = new MovieDAO();
		List<MovieinfoVO> list = dao.reserveMovieListData();
		request.setAttribute("list", list);
	}
	public void reserveTheaterData(HttpServletRequest request) {
		MovieDAO dao = new MovieDAO();
		String mno=request.getParameter("mno");//movie_info가 theater_info에서 넘겨준 값
		
		String tnos= dao.reserveTheater_noData(Integer.parseInt(mno));
		String[] tno_array=tnos.split(",");
		List<theaterinfoVO> list = new ArrayList<theaterinfoVO>();
		
		for(String s: tno_array) {
			theaterinfoVO vo = dao.reserveTheaterInfoData(Integer.parseInt(s));
			list.add(vo);
		}
		//기존 request안에 list가 추가된것이다. 초기화가 아니다...즉, mno도 받을수 있따.
		request.setAttribute("list", list);	
	}
		
	public void reserveDate(HttpServletRequest request) {
		Date date = new Date();//오늘날짜?
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		String today = sdf.format(date);
		//  System.out.println("오늘날짜:"+today);
		
		StringTokenizer st = new StringTokenizer(today,"-");
		String strYear = st.nextToken();
		String strMonth = st.nextToken();
		String strDay = st.nextToken();
		
		int year = Integer.parseInt(strYear);
		int month = Integer.parseInt(strMonth);
		int day = Integer.parseInt(strDay);
		
		 // 제어문 ==> 메소드 ==> 클래스 ==> 인터페이스 ==> 예외처리 
		  // 라이브러리 ==> 웹 / 빅데이터  / 게임
		  // 웹 => 컬렉션 , 데이터베이스
		  // 빅데이터 => 정규식
		  // 게임 => 쓰레드 , 네트워크
		
		int total = (year-1)*365 + (year-1)/4 - (year-1)/100 + (year-1)/400;
		//서기 연도기준 총 day계산 : 0년부터 2016년까지
		//윤년 조정 : + (year-1)/4 - (year-1)/100 + (year-1)/400
		
		int[] DayOfMonths = { 31,28, 31,30,31,30,31, 31,30,31,30,31};
		if((year%4==0)&&(year%100!=0)||(year%400==0)) {//+이면 ==, -이면 !=: 윤년에 걸리는 조건이다
			DayOfMonths[1]=29; //윤년일 때 2월의 일수
		}else {
			DayOfMonths[1]=28; //윤년아닐 때 2월의 일수
		}
		
		//이월의 날짜수 합
		for(int i=0;i<month-1;i++) {//lastDay를 위해서 month-1한다
			total+=DayOfMonths[i];
		}//8월 30일까지 합한다
		total++;//9월1일을 만든다.
		
		//요일- 서기 0년 1월 1일이 일요일이다.
		int firstdayOfWeek = total%7;//9월 1일의 요일을 찾는다.0이면 일요일이다.
		
		String[] strWeek= {"일","월","화","수","목","금","토"};
		
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("week", firstdayOfWeek);
		request.setAttribute("strWeek", strWeek);
		request.setAttribute("DayOfMonths", DayOfMonths[month-1]);
		
		//예약일
		String tno = request.getParameter("tno");
		MovieDAO dao = new MovieDAO();
		String data = dao.reserveDate(Integer.parseInt(tno));
		int[] rday=new int[DayOfMonths[month-1]];//0이 29~31개인 배열
		//System.out.println(data);
		String[] tmp = data.split(","); // "12,14,6,7"
		//System.out.println(tmp);
		for(String s:tmp) {//배열의 11,13,5,6번째 자리에 1넣으면 예약가능일 들어감
			//int rday[] = {0,1,0,1,0,0,0,0,0,0,1,0,0,0,0,1}
			rday[Integer.parseInt(s)-1]=1;
		}
		request.setAttribute("rday", rday);
	}
	public void reserveTime(HttpServletRequest request) {
		String dno=request.getParameter("dno");//date_no
		MovieDAO dao = new MovieDAO();
		String time_no=dao.reserveTime(Integer.parseInt(dno));
		String[] temp=time_no.split(",");
		List<String> list = new ArrayList<String>();//list안에 string!
		for(String s:temp) {
			String t =dao.reserveTimeData(Integer.parseInt(s));
			list.add(t);
		}
		request.setAttribute("list", list);
	}
	public void reserve_ok(ReserveVO vo,HttpServletResponse response){
		MovieDAO dao=new MovieDAO();
		  dao.reserveInsert(vo);
		  // main==> include (mypage)
		  try
		  {
		     response.sendRedirect("../main/movie_main.jsp?mode=4");
		  }catch(Exception ex) {}
	}
	public void reserve_mypage(HttpServletRequest request) {
		MovieDAO dao=new MovieDAO();
		HttpSession session=request.getSession();
		String id =(String)session.getAttribute("id");
		List<ReserveVO> list=dao.reserveMypage(id);
		request.setAttribute("list", list);
		request.setAttribute("main_jsp", "../reserve/mypage.jsp");
	}
	public void reserve_adminPage(HttpServletRequest request) {
		MovieDAO dao=new MovieDAO();
		List<ReserveVO> list=dao.reserveAdminPage();
		request.setAttribute("list", list);
		request.setAttribute("main_jsp", "../reserve/adminpage.jsp");
	}
	public void reserve_admin_ok(HttpServletRequest request, HttpServletResponse response) {
		String no = request.getParameter("no");
		MovieDAO dao=new MovieDAO();
		dao.reserveOk(Integer.parseInt(no));
		try {
			
		response.sendRedirect("../main/movie_main.jsp?mode=5");
	  }catch(Exception ex) {
		  
	  }
	
	}
	}
