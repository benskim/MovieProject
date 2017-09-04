<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../reserve/table.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
	$(function() {
		$('#okBtn').css("cursor","pointer");
		$.ajax({
			type : 'post',
			url : '../reserve/movie_info.jsp',
			success : function(res) {
				$('#movie_info').html(res);
			}
		});
		$('#okBtn').click(function(){
			$('#rfrm').submit();
			/* 		var title=$('#reserve_title').text();
			var theater=$('#reserve_theater').text();
			var resday=$('#reserve_date').text();
			var restime=$('#reserve_time').text();
			var inwon=$('#reserve_num').text();
			var price=$('#reserve_cost').text(); */
			
		});
	});
</script>
</head>
<body>
	<center>
		<div id="news_area">
			<table id="table_content" width=800 height=400>
				<tr>
					<td width=150 height=280 valign="top">
						<table id="table_content" width=150>
							<tr>
								<th>영화정보</th>
							</tr>
						</table>
						<div style="overflow-y: auto;" id="movie_info" width=150
							height=280></div>
					</td>
					<td width=150 height=280 valign="top">
						<table id="table_content" width=150>
							<tr>
								<th valign="top">극장정보</th>
							</tr>
						</table>
						<div style="overflow-y: auto;" id="theater_info" width=150
							height=280></div>
					</td>
					<td width=300 height=280 valign="top">
						<table id="table_content" width=300>
							<tr>
								<th valign="top">날짜정보</th>
							</tr>
						</table>
						<div style="overflow-y: auto;" id="date_info" width=150
							height=280 ></div>
					</td>
					<td width=200 height=400 rowspan="2" valign="top">
						<table id="table_content" width=200>
							<tr>
								<th valign="top">예매정보</th>
							</tr>
						</table>
						<table id="table_content" width=200>
							<tr>
								<td align=center><img src="" width=160 height=200
									id="reserve_img"></td>
							</tr>
							<tr>
								<td align=center><span id="reserve_title" style="color:white;"></span></td>
							</tr>
							<tr>
								<td align=left><span id="reserve_theater" style="color:white;">극장명: </span></td>
							</tr>
							<tr>
								<td align=left><span id="reserve_date" style="color:white;">예매일: </span></td>
							</tr>
							<tr>
								<td align=left><span id="reserve_time" style="color:white;">상영시간: </span></td>
							</tr>
							<tr>
								<td align=left><span id="reserve_num" style="color:white;">인원: </span></td>
							</tr>
							<tr>
								<td align=left><span id="reserve_cost" style="color:white;">총액: </span></td>
							</tr>
						
							<tr id="okTr" style="display:none">
							 <td align=center>
							 <form method="post"  action="../reserve/reserve_ok.jsp" id="rfrm">
							 <img alt="d" src="../reserve/ok.png" id="okBtn">
         					   <input type="hidden" name="title" value="" id="title">
         					   <input type="hidden" name="theater" value="" id="theater">
        					   <input type="hidden" name="resday" value="" id="resday">
       					       <input type="hidden" name="restime" value="" id="restime">
        					   <input type="hidden" name="inwon" value="" id="inwon">
       					       <input type="hidden" name="price" value="" id="price">
        					  </form>
							 </td>
							</tr>
						
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" height=120 valign="top">
						<table id="table_content" width=350>
							<tr>
								<th valign="top">시간정보</th>
							</tr>
						</table>
						<div style="overflow-y: auto;" id="time_info" width=150	height=280 ></div>
					</td>
					<td width=250 height=120 valign="top">
						<table id="table_content" width=300>
							<tr>
								<th valign="top">인원정보</th>
							</tr>
						</table>
						<div style="overflow-y: auto;" id="num_info" width=150 height=280 ></div>
					</td>
				</tr>
			</table>
		</div>
	</center>
</body>
</html>