<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <jsp:useBean id="rm" class="com.sist.movie.model.ReserveModel"/>
   <%
   rm.reserveTheaterData(request);
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
	$('.tnoTr').click(function(){
		var no=$(this).attr("data-no"); //Ŭ���� ����� this��� �Ѵ�
		var tname=$('#ss'+no).text();
		$('#reserve_theater').text("����� : "+tname);
		$('#theater').val(tname);//value=""�� ä����
		$.ajax({
			type:'post',
			url:'../reserve/date_info.jsp',
			data:{"tno":no},
			success:function(res){/*res: date_info.jsp�������� �����´� */
				$('#date_info').html(res);/* reserve_main������ id�� date_info�� �� */
			}
		});
	});
});
</script>
</head>
<body>
	<center>
		<table id=table_content width=150>
			<c:forEach var="vo" items="${list }">
				<tr class="dataTr tnoTr" height=35 data-no="${vo.tno }"><!--�Ӽ��̸� ��������� �ȴ�  -->
					<td>
					<span style="color:white;" id="ss${vo.tno }">${vo.tname }(${vo.loc })</span>
					</td>
					
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
</html>