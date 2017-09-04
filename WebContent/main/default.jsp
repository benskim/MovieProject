<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<style type="text/css">
a:hover {
	text-decoration: underline;
	color:white;
}
a{
    text-decoration: none;
	color:black;
}
</style>
</head>
<!-- url �ּҰ� �ٲ�� request�� �ٲ��: a.jsp -> b.jsp�̸� �ٲ�. a.jsp->a.jsp�� request�ȹٲ�
jsp�ϳ��� jsp.class�� �ڹ� Ŭ�������� �����Ѵ�. ���� url�ٲ�� jsp�ٲ�� Ŭ���������ϴ� new�� �ϰ� �ִ°Ŵ�.

1) request �ʱ�ȭ: sendRedriect, a, form

2) request ����� :forward 

3) request ���� : include
����Ŭ���� : ���Ŭ����
���ο� ���� �ϳ��� request�� �ΰ��� Ŭ������ ������ �� �ִ�.

 -->
<body>
  <div id="news_area"><!--news area-->
        
        	<!--### news area box start ###-->
          <%--
                 for(MovieVO vo:mList)
           --%>	
          <c:forEach var="vo" items="${mList }"> <!--mList�� ��𼭿Գ�?  -->
            <div class="news_area1">
                <h2>${vo.title }</h2>
                <h3>${vo.director }</h3>
                <a href="movie_main.jsp?mode=1&mno=${vo.mno }">
                <img src="${vo.poster }" width=270 height=130 /></a>    
            </div>
           </c:forEach>  
           
             <!--### news area box start ###-->
            <div style="height:15px"></div>
            <div style="margin-left:730px;color:black;font-size: 12px">
             <a href="movie_main.jsp?page=${curpage>1?curpage-1:curpage }">����</a>&nbsp;
             <a href="movie_main.jsp?page=${curpage<totalpage?curpage+1:curpage }">����</a>&nbsp;&nbsp;
             ${curpage } page / ${totalpage } pages
            </div>
            
            <div class="clr"></div>
                           
        </div><!--news area end-->
        
        
        <div id="content_wrapper"><!-- content wrapper-->
                    
                   
        </div><!-- content wrapper end -->
        
        
</body>
</html>