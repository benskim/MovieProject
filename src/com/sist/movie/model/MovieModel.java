package com.sist.movie.model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import com.sist.movie.dao.*;


public class MovieModel {
//목록출력  
  public void movieListData(HttpServletRequest request) {
	  String page=request.getParameter("page");
	  if(page==null)
		  page="1";
	  int curpage=Integer.parseInt(page);
	  MovieDAO dao=new MovieDAO();
	  List<MovieVO> mList=dao.movieListData(curpage);
	  int totalpage=dao.movieTotalPage();
	  // jsp 데이터 전송 
	  request.setAttribute("mList", mList);
	  request.setAttribute("curpage", curpage);
	  request.setAttribute("totalpage", totalpage);
	  request.setAttribute("main_jsp", "default.jsp");//default.jsp파일명을 넣으면 알아서 찾아주나?
  }
  /*
   * @model1 과 model2의 차이점
   * 모델1: JSP끼리 연결
   * 모델2: MVC (재사용을 위한 분리작업) 
   * - 기능여러개(MODEL) ,조립(CONTROL), 출력(VIEW)
   * - 모델에 따른 출력파일(JSP)를 미리 제작된 조립기에 매뉴얼(XML)을 만들어 넘긴다
   * - 그럼 매뉴얼대로 모델을 조립해주는 조립기가 SPRING이다.
   * - 모델이 부품(기능처리)이고, 콘트롤러가 조립기다
   *  일반클래스는 부품만들기다. 기능처리가 모델이다. 
   * 
   * MVC
   * 
   * 
   */

//상세출력 - detail.jsp <%%>에 있어야 하는 걸 여기로 가져왔따. 그래서 requeset값도 받아야 한다.
  public void movieDetailData(HttpServletRequest request) {
	  String mno=request.getParameter("mno");
	  MovieDAO dao = new MovieDAO();
	  MovieVO vo = dao.movieDetailData(Integer.parseInt(mno));
	  //java -> java : 매개변수(call by ref:클래스형, call by val:리터럴형)
	  //jsp -> java : get, post방식
	  //java에서 -> jsp로 값을 전송하는 방법  : request.setAttribute . 값을 첨부해서 보내겠다
	  MovieManager mm = new MovieManager();
	  mm.jsonParse(vo.getTitle());
	  List<MovieFeelVO> list=mm.recommandMovie();
	  request.setAttribute("list", list);
	  request.setAttribute("vo", vo);	  
	  request.setAttribute("main_jsp", "movie_detail.jsp");
  }
  
}




