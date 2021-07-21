<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*, com.weblab.booking.entity.*" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공연 예약 프로그램-예약확인</title>
<link rel="stylesheet" href="/resources/css/main.css">
<style>
td, th {
	text-align: center;
}

/* styles for JQuery modal dialog */ 
label, input { display:block; }
input.text { margin-bottom:12px; width:95%; padding: .4em; }
fieldset { padding:0; border:0; margin-top:25px; }
</style>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
  var dialog, form;
  
  $( function() {
    var passwd = $( "#passwd" ), allFields = $( [] ).add( passwd );

    function checkLength( o, min, max ) {
    	console.log(o.val().length);
		if ( o.val().length > max || o.val().length < min ) {
		  o.addClass( "ui-state-error" );
		  return false;
		} else {
		  return true;
		}
    }
    
	dialog = $( "#ud-dialog-form" ).dialog({
		autoOpen: false,
		height: 280,
		width: 350,	
		modal: true,
		buttons: {
		   "확인": function() {
			   form.trigger("submit");
			},
		   "취소": function() {
			   dialog.dialog( "close" );
		  }
		},
		close: function() {
	        allFields.removeClass( "ui-state-error" );
		}
	});
	
 	form = dialog.find( "form" ).on( "submit", function( event ) {
		var valid = true;
		allFields.removeClass( "ui-state-error" );
		
		valid = valid && checkLength( passwd, 4, 30 );
		
		if ( valid ) {
			dialog.dialog( "close" );
		}
		return valid;
	});
 	
  } );
  
  function doDelete( seq ) {
	$("#rsv-seq").val(seq);
	form.attr('action', '/delete');
	dialog.dialog('option', 'title', '예약 취소').dialog( "open" );
  }
  
  function doUpdate( seq ) {
	$("#rsv-seq").val(seq);
	form.attr('action', '/update');
	dialog.dialog('option', 'title', '예약 변경').dialog( "open" );
  }
</script>
</head>
<body>
	<header>
		<h1>신라 티켓 링크</h1>	
	</header>
	<div class="main">
	    <div class="detail_content_head">
	        <h2>예약 확인</h2>
	    </div>
	    <div class="detail_content_body">
	    	<div>
	    		<div class="detail_content_title"><h3>${name}님 예약 현황</h3></div>
	    		<div class="detail_content_field" style="height: auto;">
<%--	    		
<%
				List<Reservation> rsvList = (List<Reservation>)request.getAttribute("rsv_list");
				if (rsvList == null || rsvList.size() == 0) {
%>
					<p>예약 내용이 존재하지 않습니다!.</p>					
<%					
				}
				else {
%>	    		
 --%>
 				<c:if test="${rsv_list == null || rsv_list.size() == 0}">
					<p>예약 내용이 존재하지 않습니다!.</p>					
 				</c:if>
 				<c:if test="${rsv_list != null && rsv_list.size() > 0}">
 					<div style="display: flex; justify-content: center; margin-top: 20px;">
	    			<table border="1">
	    				<tr><th>예약코드</th><th>이름</th><th>연락처</th><th>예약날짜</th><th>좌석 번호</th><th></th></tr>
<%--	    				
<%
					for (Reservation rsv : rsvList) {
						out.print("<tr>");
						out.print("<td>" + rsv.getRsvSeq() + "</td>");
						out.print("<td>" + rsv.getName() + "</td>");
						out.print("<td>" + rsv.getPhone() + "</td>");
						out.print("<td>" + rsv.getRsvDate() + "</td>");
						out.print("<td>" + Arrays.toString(rsv.getSeatNumbers()) + "</td>");
						out.print("<td>");
						out.print("<input type='button' id='update-rsv' value='예약 변경' style='margin: 3px;' onclick='doUpdate("+rsv.getRsvSeq()+")'/>");
						out.print("<input type='button' id='delete-rsv' value='예약 취소' style='margin: 3px;' onclick='doDelete("+rsv.getRsvSeq()+")'/>");
						out.print("</td>");
						out.print("<tr>");
					}
%>
--%>	    		
					<c:forEach var="rsv" items="${rsv_list}">
						<tr>
						<td>${rsv.rsvSeq}</td>
						<td>${rsv.name}</td>
						<td>${rsv.phone}</td>
						<td><fmt:formatDate value="${rsv.rsvDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
						<td>${Arrays.toString(rsv.seatNumbers)}</td>
						<td>
							<input type='button' id='update-rsv' value='예약 변경' style='margin: 3px;' onclick='doUpdate(${rsv.rsvSeq})'/>
							<input type='button' id='delete-rsv' value='예약 취소' style='margin: 3px;' onclick='doDelete(${rsv.rsvSeq})'/>
						</td>
						<tr>
					</c:forEach>
	    			</table>
	    			</div>
 				</c:if>
<%-- 				
<%
				}
%>	    			
 --%>
 	    		</div>
	    		<div class="detail_content_message">${message}</div>
	    	</div>
	    	
			<div id="ud-dialog-form" title="제목">
			  <form method="POST">
			    <p>예약할 때에 등록한 비밀번호를 입력하여주세요.</p>
			    <input type="hidden" id="rsv-seq" name="seq" />
			    <fieldset>
			      <label for="name">예약 비밀번호:</label>
			      <input type="password" name="passwd" id="passwd" class="text ui-widget-content ui-corner-all">
			 
			      <!-- Allow form submission with keyboard without duplicating the dialog button -->
			      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
			    </fieldset>
			  </form>
			</div>
		
   		</div>	    
	    <div class="menubar">
	    	<div class="button" onclick="location.href='/index';"><strong>확인</strong></div>
	    </div>
	</div>	
	<footer>
		<h4>공연 예약 샘플 프로그램</h4>
	</footer>
</body>
</html>