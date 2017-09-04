<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style/table.css">
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {

        var data = google.visualization.arrayToDataTable([
          ['감성', '빈도'],
          <c:forEach var ="vo" items="${list}">
          ['<c:out value="${vo.feel}"/>', <c:out value="${vo.count}"/>],
          </c:forEach>
          ]);

        var options = {
          title: '영화 감성분석',
          is3D:true
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }
    </script>

<!--  PIE CHART end-->

</head>
<body>
	<center>
		<table border=0 width=900>
			<tr>
				<td width=450>
					<table id="table_content" width=450>

						<tr>
							<td width=45% align=center rowspan="6"><img
								src="${vo.poster }" width="202" height="300"></td>
							<th width=55% align=center colspan="2">${vo.title }</th>
						</tr>

						<tr>
							<td width=20% align=right>감독</td>
							<td width=35% align=left>${vo.director }</td>
						</tr>
						<tr>
							<td width=20% align=right>출연</td>
							<td width=35% align=left>${vo.actor }</td>
						</tr>
						<tr>
							<td width=20% align=right>장르</td>
							<td width=35% align=left>${vo.genre }</td>
						</tr>
						<tr>
							<td width=20% align=right>등급</td>
							<td width=35% align=left>${vo.grade }</td>
						</tr>
						<tr>
							<td width=20% align=right>상영일</td>
							<td width=35% align=left>${vo.regdate }</td>
						</tr>
					</table>
					<table id="table_content" width=450>
						<tr>
							<td align=left valign="top" height=200>${vo.story }</td>
						</tr>
					</table>
				</td>
				<td width=450>
				<div id="piechart" style="width: 450x; height: 500px; background: #906e49"></div>
				</td>
			</tr>
			<tr>
				<td colspan=2 align=right> <a href=movie_main.jsp>목록</a>
				</td>
			</tr>
		</table>

	</center>
</body>
</html>







