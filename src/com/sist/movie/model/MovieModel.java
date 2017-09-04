package com.sist.movie.model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import com.sist.movie.dao.*;


public class MovieModel {
//������  
  public void movieListData(HttpServletRequest request) {
	  String page=request.getParameter("page");
	  if(page==null)
		  page="1";
	  int curpage=Integer.parseInt(page);
	  MovieDAO dao=new MovieDAO();
	  List<MovieVO> mList=dao.movieListData(curpage);
	  int totalpage=dao.movieTotalPage();
	  // jsp ������ ���� 
	  request.setAttribute("mList", mList);
	  request.setAttribute("curpage", curpage);
	  request.setAttribute("totalpage", totalpage);
	  request.setAttribute("main_jsp", "default.jsp");//default.jsp���ϸ��� ������ �˾Ƽ� ã���ֳ�?
  }
  /*
   * @model1 �� model2�� ������
   * ��1: JSP���� ����
   * ��2: MVC (������ ���� �и��۾�) 
   * - ��ɿ�����(MODEL) ,����(CONTROL), ���(VIEW)
   * - �𵨿� ���� �������(JSP)�� �̸� ���۵� �����⿡ �Ŵ���(XML)�� ����� �ѱ��
   * - �׷� �Ŵ����� ���� �������ִ� �����Ⱑ SPRING�̴�.
   * - ���� ��ǰ(���ó��)�̰�, ��Ʈ�ѷ��� �������
   *  �Ϲ�Ŭ������ ��ǰ������. ���ó���� ���̴�. 
   * 
   * MVC
   * 
   * 
   */

//����� - detail.jsp <%%>�� �־�� �ϴ� �� ����� �����Ե�. �׷��� requeset���� �޾ƾ� �Ѵ�.
  public void movieDetailData(HttpServletRequest request) {
	  String mno=request.getParameter("mno");
	  MovieDAO dao = new MovieDAO();
	  MovieVO vo = dao.movieDetailData(Integer.parseInt(mno));
	  //java -> java : �Ű�����(call by ref:Ŭ������, call by val:���ͷ���)
	  //jsp -> java : get, post���
	  //java���� -> jsp�� ���� �����ϴ� ���  : request.setAttribute . ���� ÷���ؼ� �����ڴ�
	  MovieManager mm = new MovieManager();
	  mm.jsonParse(vo.getTitle());
	  List<MovieFeelVO> list=mm.recommandMovie();
	  request.setAttribute("list", list);
	  request.setAttribute("vo", vo);	  
	  request.setAttribute("main_jsp", "movie_detail.jsp");
  }
  
}




