<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean id="rm" class="com.sist.movie.model.ReserveModel"/>
<%
request.setCharacterEncoding("EUC-KR");
%>
<jsp:useBean id="vo" class="com.sist.member.dao.ReserveVO">
<jsp:setProperty name="vo" property="*"/>
</jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
   String id=(String)session.getAttribute("id");
   vo.setId(id);
   rm.reserve_ok(vo, response);
%>

<%-- 
<% 
request.setCharacterEncoding("EUC-KR");
%>
 ��ȭ�� : <%=request.getParameter("title") %><br/>
 �����: <%=request.getParameter("theater") %><br/>
 ������: <%=request.getParameter("resday") %><br/>
 �󿵽ð�: <%=request.getParameter("restime") %><br/>
 �ο�: <%=request.getParameter("inwon") %><br/>
 ����: <%=request.getParameter("price") %><br/> 
 --%>
</body>
</html>