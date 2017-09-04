<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="com.sist.movie.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model" class="com.sist.change.MovieController"/>
<%
//servlet과 jsp는 자동으로 request를 주고 받을수 있따.     
model.controller(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR" />
<title>Company Name</title>
<link rel="stylesheet" type="text/css" href="style/style_sheet.css" media="screen" />
<link rel="stylesheet" href="css/nivo-slider.css" media="screen">
<link rel="stylesheet" href="css/futurico-theme.css" media="screen">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script>

	$(function(){
		$('#logBtn').click(function(){
			var id=$('#id').val();
			var pwd=$('#pwd').val();
			if(id.trim()=="")
			{
				$('#id').focus();
				return;
			}
			if(pwd.trim()=="")
			{
				$('#pwd').focus();
				return;
			}
			$('#log_frm').submit();
		});
	});
</script>
	<!-- <style type="text/css">body{background:url(img/bg.png) repeat;width:960px;margin:0px auto}.footer{margin-top:100px;text-align:center;color:#666;font:bold 14px Arial}.footer a{color:#999;text-decoration:none}#wrapper{padding: 50px 0 0 325px;}</style> -->

</head>

<body>

<div id="wrapper"><!--####  wrapper  ###-->

		<div id="top_strip"></div><!--top strip end-->


        <div id="header"><!--header--> 
        
       		         
       	  		<h1><a href="#s">SIST Movie Center</a></h1>
                
                <h6>강북쌍용교육센터 A강의장</h6>
                <c:if test="${sessionScope.id==null }">
                 
	                <div style="color:white;float: right;">
	                 <form method=post action="../member/login.jsp" id="log_frm">
			          ID:<input type=text name=id size=13 id="id">
			          &nbsp;
			          Password:<input type=password name=pwd size=13 id="pwd">
			          &nbsp;
			          <input type=button value="로그인" id="logBtn">
			         </form>
			        </div>
			     </c:if>
			     <c:if test="${sessionScope.id!=null }">
	                <div style="color:white;float: right;">
	                 <form method="post" action="../member/logout.jsp" id="logout_frm">
			          ${sessionScope.name }(${sessionScope.admin==1?"관리자":"일반사용자" })님 로그인중입니다...
			          <input type=submit value="로그아웃">
			         </form>
			        </div>
			     </c:if>
        </div>
        
        <!--header end-->

    
    		      <div id="menu-top"><!--menu top-->
    
                        <div id="top_nav"><!--top nav--> 
                            
                                <ul>
                                   <li><a href="movie_main.jsp">Home</a></li>
                                   <%-- movie_list(0),movie_detail(1) --%>
                                   <c:if test="${sessionScope.id==null }"> 
                                    <li><a href="movie_main.jsp?mode=2">회원가입</a></li>
                                   </c:if>
                                   <c:if test="${sessionScope.id!=null }"> 
                                    <li><a href="#">회원수정</a></li>
                                   </c:if>
                                   <li><a href="#s">자유게시판</a></li>  <!-- 댓글형 게시판 -->
                                   <c:if test="${sessionScope.id!=null }"> 
                                    <li><a href="movie_main.jsp?mode=3">예매센터</a></li>
                                   </c:if>
                                   <li><a href="#s">상영영화통계</a></li>
                                   <li><a href="#s">추천영화</a></li>
                                   <c:if test="${sessionScope.admin==0 && sessionScope.id!=null }">
                                    <li><a href="movie_main.jsp?mode=4">마이페이지</a></li>
                                   </c:if>
                                   <c:if test="${sessionScope.admin==1 && sessionScope.id!=null }">
                                   <li><a href="movie_main.jsp?mode=5">예매현황</a></li>
                                   </c:if>

                                 </ul>
                                 
                           </div><!--top nav end--> 
                           
                           
                           <div id="search_area"><!--search box--> 
                           
                                  <form action="" method="get" id="form_search">
                                  
                                        <input name="input" type="text" id="search_box"/>
                                        
                                        <input name="" type="image" id="search_btn"  value=""/>
                                  
                                  
                                  </form>
                          
                           </div><!--search box end--> 
                           
            
				</div><!--menu top end-->   
        
        
        <div id="banner"><!--banner-->
        
        		 <img src="images/header1.jpg" alt="" width="940" height="280" />
        
  		</div><!--banner end-->
  		
  		<!--content //movieModel을 통해 호출됨.  -->
         <jsp:include page="${main_jsp }"></jsp:include>
       <!--다음페이지 /이전페이지  -->
        <div id="footer"><!--footer-->
        
        				<div id="footer_nav">
                
                                <ul>
                                    
                                   <li><a href="#s">Home</a></li>
                                   <li><a href="#s">회원가입</a></li>
                                   <li><a href="#s">자유게시판</a></li>
                                   <li><a href="#s">예매센터</a></li>
                                   <li><a href="#s">상영영화통계</a></li>
                                   <li><a href="#s">추천영화</a></li>
                                 </ul>
                
                		</div>
                        
                        
                         <div id="copyright">
                        
                         SIST Movie Center                       
                         </div>
        
       			 </div><!--footer end-->

<div class="clr"></div>
</div><!--####  wrapper  ###-->

</body>
</html>
