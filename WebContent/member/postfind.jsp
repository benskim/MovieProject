<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <jsp:useBean id="model" class="com.sist.movie.model.MemberModel"/>
    <% model.postFindData(request);%> <!-- ${list} 랑 같다!! -->
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
</style><!--jquery 두번들어가면 충돌생김  -->
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$('#findBtn').click(function(){
		var dong=$('#dong').val();
			/*값 가져오기~jsoup과 같음
			val() : input, select, textarea
			text() : td, span, a - 태그생략
			html() : td, span, a - 태그포함
			attr() : 태그 안의 속성 모두: <a href="aa"> attr('href')
			
			==============setter==============
			val("id")
			$('td').text("bbb")
			$('td').html("id")
			attr("속성명","값")
			append("<tr><td></td></tr>").after() : 밑으로 붙여라
			append("<tr><td></td></tr>").before() : 위로 붙여라
			*/
		if(dong.trim()==""){
			$('#print').html("<center>동/읍/면을 입력하시오</center>");
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
		//공백이 없으면 (위아래공백제거:trim) 숫자만 있을 경우, 숫자로 인식한다. count==0
	});
});
</script>
</head>
<body>
   <center>
    <table id="table_content" width=420>
     <tr>
      <td align=center>
      	입력:&nbsp;
      	<input type=text name=dong size=13 id="dong">
        <input type=button value="검색" id="findBtn">
      </td>
     </tr>
    </table>
    <div id="print"></div>
   </center>
</body>
</html>













