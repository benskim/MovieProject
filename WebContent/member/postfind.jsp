<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <jsp:useBean id="model" class="com.sist.movie.model.MemberModel"/>
    <% model.postFindData(request);%> <!-- ${list} �� ����!! -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="table.css">
<style type="text/css">
td, div{
  color:white;
}
</style><!--jquery �ι����� �浹����  -->
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$('#findBtn').click(function(){
		var dong=$('#dong').val();
			/*�� ��������~jsoup�� ����
			val() : input, select, textarea
			text() : td, span, a - �±׻���
			html() : td, span, a - �±�����
			attr() : �±� ���� �Ӽ� ���: <a href="aa"> attr('href')
			
			==============setter==============
			val("id")
			$('td').text("bbb")
			$('td').html("id")
			attr("�Ӽ���","��")
			append("<tr><td></td></tr>").after() : ������ �ٿ���
			append("<tr><td></td></tr>").before() : ���� �ٿ���
			*/
		if(dong.trim()==""){
			$('#print').html("<center>��/��/���� �Է��Ͻÿ�</center>");
			$('#dong').focus();
			
			return;
		}
			$.ajax({
				type:'POST',
				url: 'postfind_result.jsp',
				data: {"dong":dong},
				success:function(response){
					$('#print').html(response);
				}
			});
		//������ ������ (���Ʒ���������:trim) ���ڸ� ���� ���, ���ڷ� �ν��Ѵ�. count==0
	});
});
</script>
</head>
<body>
   <center>
    <table id="table_content" width=420>
     <tr>
      <td align=center>
      	�Է�:&nbsp;
      	<input type=text name=dong size=13 id="dong">
        <input type=button value="�˻�" id="findBtn">
      </td>
     </tr>
    </table>
    <div id="print"></div>
   </center>
</body>
</html>













