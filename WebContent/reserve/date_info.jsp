<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="rm" class="com.sist.movie.model.ReserveModel"/>
<%
    rm.reserveDate(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../reserve/table.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
/*
 *   $('.rday').css({"":��,"":��});
 *   $('.rday').css("",��).css("",��)
 */

$(function(){
	$('.rday').css("cursor","pointer");
	
	$('.rday').hover(function(){
		$(this).css("color","green");
		},function(){
			$(this).css("color","white");
	});

	$('.rday').click(function(){
		var year=$(this).attr("data-year");
		var month=$(this).attr("data-month");
		var day=$(this).text();
		var data=year+"-"+month+"-"+day;
		$('#reserve_date').text("������ : "+data);
	    $('#resday').val(data);
		//�ð��б�
		$.ajax({
			type:'post',
			url:'../reserve/time_info.jsp',
			data:{"dno":day},
			success:function(res){
				$('#time_info').html(res);
			}
		});
	});

});
</script>
</head>
<%--
   figcaption figure wbr dt dd addb menu 
 --%>
<body>
  <table id="table_content" width=280><!-- ����for���� ����for�� ���Է¿� ������ -->
   <caption>${year }�� ${month }�� ${day }��</caption><!--table����:caption, �̹�������:figcaption ����:figure -->
   <tr height="40"><!-- �޷¸����� ���� �ֱ� -->
    <%--
       for(int i=0;i<=6;i++)
    --%>
    <c:forEach var="i" begin="0" end="6" step="1">
     <th>${strWeek[i] }</th> <%--���ϳֱ� --%>
    </c:forEach>
   </tr>
   <c:forEach var="i" begin="1" end="${DayOfMonths }">  <%--�޸��� �ϼ��� �ִ´�.--%>
    <c:if test="${i==1 }">
     <tr height="40">
     <%--
           for(int j=0;j<2;j++)
           for(int j=1;j<=2;j++)
      --%>
     <c:forEach var="j" begin="1" end="${week }"> <%--ù���۵Ǵ� 1�� ����ֱ�--%>
       <td align=center>&nbsp;</td>
     </c:forEach>
    </c:if>
      <td align=center>
        <c:if test="${rday[i]==1 }">
         <span style="color:white" class="rday" data-year=${year } data-month=${month }>${i }</span>
        </c:if>
        <c:if test="${rday[i]!=1 }">
         <span style="color:gray">${i }</span>
        </c:if> 
       </td>

       <c:set var="week" value="${week+1 }"/>
       <%--
         week++
         if(week>6) 
         {
           week=0
         }
        --%>
       <c:if test="${week>6 }">
         <c:set var="week" value="0"/>
         </tr>
         <tr height="40">
       </c:if>
   </c:forEach>
   </tr>
  </table>
</body>
</html>













