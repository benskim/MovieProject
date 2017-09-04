<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
   <%
   request.setCharacterEncoding("EUC-KR");
   %>
   <jsp:useBean id="model" class="com.sist.movie.model.MemberModel"/>
   <jsp:useBean id="vo" class="com.sist.member.dao.MemberVO">
   	<jsp:setProperty name="vo" property="*"/>
   </jsp:useBean>
<%model.memberJoinOk(vo, response); %>