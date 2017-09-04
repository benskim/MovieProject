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
 영화명 : <%=request.getParameter("title") %><br/>
 극장명: <%=request.getParameter("theater") %><br/>
 예매일: <%=request.getParameter("resday") %><br/>
 상영시간: <%=request.getParameter("restime") %><br/>
 인원: <%=request.getParameter("inwon") %><br/>
 가격: <%=request.getParameter("price") %><br/> 
 --%>
</body>
</html>