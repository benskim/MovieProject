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
		var img=$('#m'+no).attr("src");/* no = ������ ������ var no */
		var title=$('#s'+no).text(); 
		$('#reserve_img').attr("src",img); /* ���� ���������� ������ ����jsp�� ��� �����Ͱ�������? jquery�� Ư¡! */
		$('#reserve_title').text(title); /* ���� ����ϰ� �ʹٸ� �̰�ó�� ������ ���� ������� ��밡���ϴ� */
		$('#title').val(title);//reserve_main�� hidden#title�� �ִ´�

		/*ajax : �ش��ڸ� server���� ������ �ٽ� server���� �޴´�*/
		$.ajax({
			/*������  theater_info.jsp?mno=1(no) �� json������� �������� ajax��*/
			type: 'post',
			url: '../reserve/theater_info.jsp',/* include: movie_main <- reserve_main <- movie_info */
			data: {"mno":no},
			/*�ޱ�  : server���� ������� �Ű������� �˾Ƽ� �־���, idcheck.jsp����*/
			success:function(recvData){
				$('#theater_info').html(recvData);/*�ݵ�� html��� �Ѵ�  */
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






