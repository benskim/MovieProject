<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model" class="com.sist.movie.model.MemberModel"/>
<%
    model.postFindData(request);//${list}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="table.css">
<style type="text/css">
td,div{
  color:white;
}
</style>
<script type="text/javascript">
function ok(zip,addr){
	//join.jsp = parent
	parent.joinForm.post.value=zip;
	parent.joinForm.addr1.value=addr;
	parent.Shadowbox.close();
}
</script>
</head>
<body>
  <center>
   <c:if test="${count==0 }">
    <table id="table_content" width=420>
     <tr>
      <td align=center>
                검색된 결과가 없습니다
      </td>
     </tr>
    </table>
   </c:if>
   <c:if test="${count!=0 }">
     <table id="table_content" width=420>
     <tr>
      <th width=20%>우편번호</th>
      <th width=80%>주소</th>
     </tr>
     <c:forEach var="vo" items="${list }">
      <tr class="dataTr">
       <td width=20% align=center>${vo.zipcode }</td>
       <td width=80% align=left>
       <a href="javascript:ok('${vo.zipcode }','${vo.address }')" class=post>${vo.address }</a>
       <!--  css때문에 class=post줌 -->
       </td>
      </tr>
     </c:forEach>
    </table>
   </c:if>
  </center>
</body>
</html>





