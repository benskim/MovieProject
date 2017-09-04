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
<!-- url 주소가 바뀌면 request가 바뀐다: a.jsp -> b.jsp이면 바뀜. a.jsp->a.jsp는 request안바뀜
jsp하나가 jsp.class로 자바 클래스파일 생성한다. 따라서 url바뀌면 jsp바뀌고 클래스생성하는 new를 하고 있는거다.

1) request 초기화: sendRedriect, a, form

2) request 덮어쓰기 :forward 

3) request 유지 : include
내부클래스 : 멤버클래스
내부에 들어가면 하나의 request를 두개의 클래스가 공유할 수 있다.

 -->
<body>
  <div id="news_area"><!--news area-->
        
        	<!--### news area box start ###-->
          <%--
                 for(MovieVO vo:mList)
           --%>	
          <c:forEach var="vo" items="${mList }"> <!--mList는 어디서왔나?  -->
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
             <a href="movie_main.jsp?page=${curpage>1?curpage-1:curpage }">이전</a>&nbsp;
             <a href="movie_main.jsp?page=${curpage<totalpage?curpage+1:curpage }">다음</a>&nbsp;&nbsp;
             ${curpage } page / ${totalpage } pages
            </div>
            
            <div class="clr"></div>
                           
        </div><!--news area end-->
        
        
        <div id="content_wrapper"><!-- content wrapper-->
                    
                   
        </div><!-- content wrapper end -->
        
        
</body>
</html>