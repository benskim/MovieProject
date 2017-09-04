<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../member/style.css">
<link rel="stylesheet" type="text/css" href="../member/shadow/css/shadowbox.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript" src="../member/shadow/js/shadowbox.js"></script>
<script type="text/javascript">
Shadowbox.init({
	players:["iframe"]
});
$(function(){
	$('#idBtn').click(function(){
		Shadowbox.open({
			content:'../member/idcheck.jsp',
			title:'아이디 중복체크',
			player:'iframe', //html에서 include와 같은 형식
			width:380,
			height:200
		});
	});
	$('#postBtn').click(function(){
		Shadowbox.open({
			content:'../member/postfind.jsp',
			title:'우편번호 검색',
			player:'iframe', //html에서 include와 같은 형식
			width:460,
			height:350
			
		});
	});
});
</script>

<!-- main.jsp의 ${main_jsp}에서 사용된다. -->
</head>
<body>
 <div id="news_area">
	<div id="wrapper1">
		<form id=joinForm action=../member/join_ok.jsp name="joinForm">
			<!--404 db연동 -->
			<p><!--required: 필수 not null  -->
				<label for=userid>아이디</label> <input type=text name=id id=userid	readonly>
				<!--값받기)java:name, jquery:id  -->
				<input type=button value=중복체크 id=idBtn required>
			</p>
			<p>
				<label for=userpwd>비밀번호</label> 
				<input type=password name=pwd	id=userpwd required>
				<!--값받기)java:name, jquery:id  -->
				<input type=password name=pwd2 id=userpwd2 placeholder="재입력" required> 
			</p>
			<p>
				<label for=username>이름</label> 
				<input type=text name=name	id=username required>
			</p>
			<p>
				<label for=usersex>성별</label> 
				<input type=radio name=sex value=남자 checked>남자 
				<input type=radio name=sex value=여자>여자
			</p>
			<p>
				<label for=userdate>생년월일</label> 
				<input type=date name=birthday	id=userdate required>
			</p>
			<p>
				<label for=useremail>이메일</label> 
				<input type=text name=email	id=useremail>
			</p>
			<p>
				<label>주소</label> 
				<span id=cellStyle> 
					<input type=text	id=userPost name=post readonly required>
					<input type=button	id=postBtn value=우편번호검색>
					<input type=text	id=userAddr1 name=addr1 readonly required>
					<input type=text	id=userAddr2 name=addr2 >
				</span>
			</p>
			<p>
				<label>전화번호</label> 
				<span id=cellStyle> 
					<select id=userPhone name=tel>
						<option>010</option>
						<option>011</option>
						<option>017</option>
					</select>
					-<input type=text	id=userPhone1 name=tel1 >
					-<input type=text	id=userPhone2 name=tel2 >
				</span>
			</p>
			<p>
				<label for="userContent">소개</label>
				<span id=cellStyle> 
    			 <textarea id="userContent" name=content></textarea>
    			</span>
			</p>
			<p class=btnSubmit>
				<input type=submit id=btnSub value=회원가입>
				<input type=button id=btnCan value=가입취소 onclick="javascript:history.back()">
			</p>
		</form>
	</div>
</div>
</body>
</html>