<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*, com.weblab.booking.entity.*" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공연 예약 프로그램-예약변경하기</title>
<link rel="stylesheet" href="/resources/css/main.css">
<style>
td {
	width: 30px;
	text-align: center;
}
tr {
	height: 10px;
}
</style>
<script>
	function checkAndSubmit() {
		const pw = document.getElementById("passwd").value;
		const pw2 = document.getElementById("passwd2").value;
		if (pw === "" || pw2 === "" || pw !==  pw2) {
			alert("예약 확인 비밀번호가 입력되지 않았거나 다르게 입력되었습니다!\n다시 입력하여 주세요.");
		}
		else {
			document.getElementById("update_form").submit();
		}
	};	
</script>
</head>
<body>
	<header>
		<h1>신라 티켓 링크</h1>	
	</header>
	<div class="main">
	    <div class="detail_content_head">
	        <h2>예약 변경</h2>
	    </div>
	    <form id="update_form" action="/update_proc" method="POST">
	    <div class="detail_content_body">
	    	<div>
	    		<div class="detail_content_title">1. 좌석 선택</div>
	    		<div class="detail_content_field" style="height: auto;">
	    			<table align="center">
	    				<tr>
	    					<td></td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td><td>10</td>
	    				</tr>
						<c:set var="rows" value="${fn:split('A,B,C,D,E,F,G,H,I,J',',')}" />
						<c:forEach var="i" begin="0" end="9">
							<tr><td>${rows[i]}</td>
							<c:forEach var="j" begin="0" end="9">
								<td><input type="checkbox" value="${rows[i]}${j+1}" name="seats" 
										${(seats[i*10+j] == 0) ? "" : ((seats[i*10+j] == 1) ? " checked disabled " : " checked ")} /></td>
 							</c:forEach>
							<tr>
							<c:if test="${i == 1 || i == 4}">
								<tr><td colspan=11></td></tr>
							</c:if>
						</c:forEach>
	    			</table>
	    		</div>
	    	</div>
	    	<div>
		    	<div  class="detail_content_title">2. 예약 정보 입력</div>
		    	<div  class="detail_content_field">
		    		<div>
		    			<label>예약자 이름   :</label>
		    			<input type="text" name="name" value=${rsv.name} required />
		    		</div>
		    		<div>
		    			<label>예약자 연락처 :</label>
		    			<input type="text" name="phone" value=${rsv.phone} required />
		    		</div>
		    	</div>
	    	</div>
	    	<div>
	    		<div class="detail_content_title">3. 예약 비밀번호 입력</div>
	    		<div class="detail_content_field">
	    			<div>
		    			<label>예약 확인 비밀번호   :</label>
			    		<input type="password" id="passwd" name="passwd" value=${rsv.passwd} required />
	    			</div>
	    			<div>
		    			<label>예약 비밀번호 재입력 :</label>
			    		<input type="password" id="passwd2" name="passwd2" value=${rsv.passwd} required />
	    			</div>
	    		</div>
	    	</div>
	    	<input type="hidden" name="seq" value="${rsv.rsvSeq}" />
	    </div>
	    <div class="menubar">
	    	<div class="button" onclick="checkAndSubmit();"><strong>변경</strong></div>
	    	<div class="button" onclick="history.go(-1);"><strong>취소</strong></div>
	    </div>
	    </form>
	</div>	
	<footer>
		<h4>공연 예약 샘플 프로그램</h4>
	</footer>
</body>
</html>