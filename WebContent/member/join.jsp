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
			title:'���̵� �ߺ�üũ',
			player:'iframe', //html���� include�� ���� ����
			width:380,
			height:200
		});
	});
	$('#postBtn').click(function(){
		Shadowbox.open({
			content:'../member/postfind.jsp',
			title:'�����ȣ �˻�',
			player:'iframe', //html���� include�� ���� ����
			width:460,
			height:350
			
		});
	});
});
</script>

<!-- main.jsp�� ${main_jsp}���� ���ȴ�. -->
</head>
<body>
 <div id="news_area">
	<div id="wrapper1">
		<form id=joinForm action=../member/join_ok.jsp name="joinForm">
			<!--404 db���� -->
			<p><!--required: �ʼ� not null  -->
				<label for=userid>���̵�</label> <input type=text name=id id=userid	readonly>
				<!--���ޱ�)java:name, jquery:id  -->
				<input type=button value=�ߺ�üũ id=idBtn required>
			</p>
			<p>
				<label for=userpwd>��й�ȣ</label> 
				<input type=password name=pwd	id=userpwd required>
				<!--���ޱ�)java:name, jquery:id  -->
				<input type=password name=pwd2 id=userpwd2 placeholder="���Է�" required> 
			</p>
			<p>
				<label for=username>�̸�</label> 
				<input type=text name=name	id=username required>
			</p>
			<p>
				<label for=usersex>����</label> 
				<input type=radio name=sex value=���� checked>���� 
				<input type=radio name=sex value=����>����
			</p>
			<p>
				<label for=userdate>�������</label> 
				<input type=date name=birthday	id=userdate required>
			</p>
			<p>
				<label for=useremail>�̸���</label> 
				<input type=text name=email	id=useremail>
			</p>
			<p>
				<label>�ּ�</label> 
				<span id=cellStyle> 
					<input type=text	id=userPost name=post readonly required>
					<input type=button	id=postBtn value=�����ȣ�˻�>
					<input type=text	id=userAddr1 name=addr1 readonly required>
					<input type=text	id=userAddr2 name=addr2 >
				</span>
			</p>
			<p>
				<label>��ȭ��ȣ</label> 
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
				<label for="userContent">�Ұ�</label>
				<span id=cellStyle> 
    			 <textarea id="userContent" name=content></textarea>
    			</span>
			</p>
			<p class=btnSubmit>
				<input type=submit id=btnSub value=ȸ������>
				<input type=button id=btnCan value=������� onclick="javascript:history.back()">
			</p>
		</form>
	</div>
</div>
</body>
</html>