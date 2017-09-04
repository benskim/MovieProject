<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../reserve/table.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
var num1=0;
var num2=0;/*attr('value')  */
var data1=null;
var data2=null;


$(function(){
	$('.numBtn').css("cursor","pointer");
	$('.numBtn').hover(function(){
		$(this).css("color","green");
		},function(){
			$(this).css("color","black");
	});
	/*select -> change  */
	$('.numBtn1').click(function(){
		num1 = $(this).val();
		//alert(num1+"");
		if(num1!=0){
			data1="성인: "+num1+" ";
		    alert(data1);
		}
	});
	$('.numBtn2').click(function(){/* 이름.val */
		num2 = $(this).val();
		if(num2!=0){
			data2="학생: "+num2;
			alert(data2);
		}
	});
	$('#numBtns').click(function(){
		var data=data1+data2;
		alert(data);
		$('#reserve_num').text("인원 : "+data);
		var price = num1*10000+num2*8000;
		$('#reserve_cost').text("금액 : "+price);
		$('#okTr').show();		
		$('#inwon').val(data);
		$('#price').val(price);
	});
	
	
	
});
</script>
</head>
<body>
<center>
	<table width=300 style="color:white">
		<tr>
		 <td align=center width=30>성인 </td>
		 <td align=right>
	<c:forEach var="i" begin="1" end="5">
			<input type="button" class="numBtn1" id="numBtn1" value="${i }">
	 		<%-- <button class="numBtn1" num="${i }" value="${i }" id="numbtn1">${i }</button> --%>
		 	<!-- <input type="button" class="numBtn1" id="numBtn1" value=""> -->
    </c:forEach>
          <span>명</span>
		 		 
		 </td>
		<!--  <td rowspan=2>
		 	<input type="submit" class="Btn" value="확인">
		 </td> -->
		 <td rowspan=2>
		 	<input type="button"  id="numBtns" value="확인">
		 </td>
		</tr>
		<tr>
		 <td align=center width =30 rowspan=2>아동 </td>
		 <td align=right>		 	
	<c:forEach var="i" begin="1" end="5">
		 	<input type="button" class="numBtn2" id="numBtn2" value="${i }">
    </c:forEach>
    	  <span>명</span>
		 </td>
		</tr>
	</table>
</center>
</body>
</html>