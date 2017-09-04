<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="rm" class="com.sist.movie.model.ReserveModel"/>
<%
    rm.reserveTime(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../reserve/table.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	//document.getElementByClassName("time_span")
	$('.time_span').css("cursor","pointer");

	$('.time_span').hover(function(){
		$(this).css("color","green");
		},function(){
			$(this).css("color","white");
	});

	$('.time_span').click(function(){
		var time = $(this).attr("rtime");
		$('#reserve_time').text("상영시간 : "+time);
		$('#restime').val(time);
		$.ajax({
			type:'post',
			url:'../reserve/num_info.jsp',
			success:function(res){
				$('#num_info').html(res);
				    
			}
		});
	});
});
</script>
</head>
<body>
  <table id="table_content" width=350>
  
   <tr height="40"><!-- varStatus="s" ==> int s=0;s++ -->
    <c:forEach var="time" items="${list }" varStatus="s">
    <c:if test="${s.index<5 }">
     <td align=center>
     <span style="color:white" class="time_span" rtime=${time }>${time }</span>
     </td>
     </c:if>
    </c:forEach>
   </tr>
   <tr height="40">
   <c:forEach var="time" items="${list }" varStatus="s">
    <c:if test="${s.index>4 }">
     <td align=center>
     <span style="color:white" class="time_span" rtime=${time }>${time }</span>
     </td>
     </c:if>
    </c:forEach>
   </tr>
   </table>
</body>
</html>