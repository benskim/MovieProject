package com.sist.movie.model;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.member.dao.MemberDAO;
import com.sist.member.dao.MemberVO;
import com.sist.member.dao.ZipcodeVO;

/*
 *idcheck 처리하는 곳 
 *idcheck_result 처리하는 곳
 */
public class MemberModel {
	public void memberJoin(HttpServletRequest request) {
		request.setAttribute("main_jsp", "../member/join.jsp");
	}

	public void memberIdCheck(HttpServletRequest request) {
		String id = request.getParameter("id");
		MemberDAO dao = new MemberDAO();
		int count = dao.memberIdcheck(id);
		request.setAttribute("count", count);
	}
	
	public void postFindData(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");//euc-kr하면 안된다.ajax는
			String dong = request.getParameter("dong");
			MemberDAO dao =new MemberDAO();
			int count =dao.postFindCount(dong);
			request.setAttribute("count", count);
			//db있는 경우만 list : jsp전송
			if(count>0) {
				List<ZipcodeVO> list = dao.postFindData(dong);
				request.setAttribute("list", list);
			}		
		} catch (Exception e) {
			// COMPILE EX
			/* Exception은 처리가능한 예러 vs Error 처리불가능한 오류
			 *  자바가상머신이 예외처리여부 검색한다-컴파일중 or 인터프리터중(runtime)
			 *  						javac	  java
			 *  1) 컴파일 예외 :	
			 *  2) 실행 예외 : 
			 */
			
			
			System.out.println(e.getMessage());
		}
	}
	
	public void memberJoinOk(MemberVO vo, HttpServletResponse response) {
		MemberDAO dao = new MemberDAO();
		dao.memberInsert(vo);
		try {
			response.sendRedirect("../main/movie_main.jsp");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	//오버로딩 : 새로 생성
	//public void memberJoinOk(HttpServletRequest request, HttpServletResponse response) {}
	 public void memberLogin(HttpServletRequest request)
	  {
		 String id=request.getParameter("id");
		 String pwd=request.getParameter("pwd");
		 //DB연동 
		 MemberDAO dao=new MemberDAO();
		 MemberVO vo=dao.isLogin(id, pwd);
		 if(vo.getMsg().equals("OK"))
		 {
			 HttpSession session=request.getSession();
			 // session,cookie => request
			 session.setAttribute("id", vo.getId());
			 session.setAttribute("name", vo.getName());
			 session.setAttribute("admin", vo.getAdmin());
		 }
		 request.setAttribute("res", vo.getMsg());
	  }
}
