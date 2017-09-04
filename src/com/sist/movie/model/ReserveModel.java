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
		String mno=request.getParameter("mno");//movie_info�� theater_info���� �Ѱ��� ��
		
		String tnos= dao.reserveTheater_noData(Integer.parseInt(mno));
		String[] tno_array=tnos.split(",");
		List<theaterinfoVO> list = new ArrayList<theaterinfoVO>();
		
		for(String s: tno_array) {
			theaterinfoVO vo = dao.reserveTheaterInfoData(Integer.parseInt(s));
			list.add(vo);
		}
		//���� request�ȿ� list�� �߰��Ȱ��̴�. �ʱ�ȭ�� �ƴϴ�...��, mno�� ������ �ֵ�.
		request.setAttribute("list", list);	
	}
		
	public void reserveDate(HttpServletRequest request) {
		Date date = new Date();//���ó�¥?
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		String today = sdf.format(date);
		//  System.out.println("���ó�¥:"+today);
		
		StringTokenizer st = new StringTokenizer(today,"-");
		String strYear = st.nextToken();
		String strMonth = st.nextToken();
		String strDay = st.nextToken();
		
		int year = Integer.parseInt(strYear);
		int month = Integer.parseInt(strMonth);
		int day = Integer.parseInt(strDay);
		
		 // ��� ==> �޼ҵ� ==> Ŭ���� ==> �������̽� ==> ����ó�� 
		  // ���̺귯�� ==> �� / ������  / ����
		  // �� => �÷��� , �����ͺ��̽�
		  // ������ => ���Խ�
		  // ���� => ������ , ��Ʈ��ũ
		
		int total = (year-1)*365 + (year-1)/4 - (year-1)/100 + (year-1)/400;
		//���� �������� �� day��� : 0����� 2016�����
		//���� ���� : + (year-1)/4 - (year-1)/100 + (year-1)/400
		
		int[] DayOfMonths = { 31,28, 31,30,31,30,31, 31,30,31,30,31};
		if((year%4==0)&&(year%100!=0)||(year%400==0)) {//+�̸� ==, -�̸� !=: ���⿡ �ɸ��� �����̴�
			DayOfMonths[1]=29; //������ �� 2���� �ϼ�
		}else {
			DayOfMonths[1]=28; //����ƴ� �� 2���� �ϼ�
		}
		
		//�̿��� ��¥�� ��
		for(int i=0;i<month-1;i++) {//lastDay�� ���ؼ� month-1�Ѵ�
			total+=DayOfMonths[i];
		}//8�� 30�ϱ��� ���Ѵ�
		total++;//9��1���� �����.
		
		//����- ���� 0�� 1�� 1���� �Ͽ����̴�.
		int firstdayOfWeek = total%7;//9�� 1���� ������ ã�´�.0�̸� �Ͽ����̴�.
		
		String[] strWeek= {"��","��","ȭ","��","��","��","��"};
		
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("week", firstdayOfWeek);
		request.setAttribute("strWeek", strWeek);
		request.setAttribute("DayOfMonths", DayOfMonths[month-1]);
		
		//������
		String tno = request.getParameter("tno");
		MovieDAO dao = new MovieDAO();
		String data = dao.reserveDate(Integer.parseInt(tno));
		int[] rday=new int[DayOfMonths[month-1]];//0�� 29~31���� �迭
		//System.out.println(data);
		String[] tmp = data.split(","); // "12,14,6,7"
		//System.out.println(tmp);
		for(String s:tmp) {//�迭�� 11,13,5,6��° �ڸ��� 1������ ���డ���� ��
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
		List<String> list = new ArrayList<String>();//list�ȿ� string!
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
