package com.sist.movie.model;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.member.dao.MemberDAO;
import com.sist.member.dao.MemberVO;
import com.sist.member.dao.ZipcodeVO;

/*
 *idcheck ó���ϴ� �� 
 *idcheck_result ó���ϴ� ��
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
			request.setCharacterEncoding("UTF-8");//euc-kr�ϸ� �ȵȴ�.ajax��
			String dong = request.getParameter("dong");
			MemberDAO dao =new MemberDAO();
			int count =dao.postFindCount(dong);
			request.setAttribute("count", count);
			//db�ִ� ��츸 list : jsp����
			if(count>0) {
				List<ZipcodeVO> list = dao.postFindData(dong);
				request.setAttribute("list", list);
			}		
		} catch (Exception e) {
			// COMPILE EX
			/* Exception�� ó�������� ���� vs Error ó���Ұ����� ����
			 *  �ڹٰ���ӽ��� ����ó������ �˻��Ѵ�-�������� or ������������(runtime)
			 *  						javac	  java
			 *  1) ������ ���� :	
			 *  2) ���� ���� : 
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
	//�����ε� : ���� ����
	//public void memberJoinOk(HttpServletRequest request, HttpServletResponse response) {}
	 public void memberLogin(HttpServletRequest request)
	  {
		 String id=request.getParameter("id");
		 String pwd=request.getParameter("pwd");
		 //DB���� 
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
