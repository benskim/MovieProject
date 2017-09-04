<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="rm" class="com.sist.movie.model.ReserveModel"/>
<%
    rm.reserveMovieData(request);
   /*
      MovieDAO dao=new MovieDAO();
	  List<MovieInfoVO> list=dao.reserveMovieListData();
	  request.setAttribute("list", list);
   */
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
	$('.dataTr').click(function(){
		var no=$(this).attr("data-no");/* this = '.dataTr' */
		var img=$('#m'+no).attr("src");/* no = 위에서 정의한 var no */
		var title=$('#s'+no).text(); 
		$('#reserve_img').attr("src",img); /* 같은 웹페이지에 있으면 여기jsp에 없어도 데이터공유가능? jquery의 특징! */
		$('#reserve_title').text(title); /* 따라서 사용하고 싶다면 이것처럼 상대방이 값을 보내줘야 사용가능하다 */
		$('#title').val(title);//reserve_main의 hidden#title에 넣는다

		/*ajax : 해당자리 server에서 보내고 다시 server에서 받는다*/
		$.ajax({
			/*보내기  theater_info.jsp?mno=1(no) 을 json방식으로 보낸것이 ajax다*/
			type: 'post',
			url: '../reserve/theater_info.jsp',/* include: movie_main <- reserve_main <- movie_info */
			data: {"mno":no},
			/*받기  : server에서 결과값을 매개변수로 알아서 넣어줌, idcheck.jsp참고*/
			success:function(recvData){
				$('#theater_info').html(recvData);/*반드시 html써야 한다  */
			}
		});
	})
});
</script>
</head>
<body>
  <table id="table_content" width=150>
    <c:forEach var="vo" items="${list }">
      <tr class="dataTr" data-no="${vo.mno }">
       <td>
        <img src="${vo.poster }" width=35 height=40 id="m${vo.mno }">
       </td>
       <td><span style="font-size:6pt;color:#ff0000" id="s${vo.mno }">${vo.title }</span></td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>






