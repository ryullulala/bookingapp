<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공연 예약 프로그램</title>
	<style>
		/* styles for JQuery modal dialog */
		label, input { display:block; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
	</style>
	<link rel="stylesheet" href="/resources/css/main.css">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$( function() {
			var confirmDialog, confirmForm;
			var name = $( "#name" ), phone = $( "#phone" ),
					allFields = $( [] ).add( name ).add( phone );
			function checkLength( o, min, max ) {
				console
				if ( o.val().length > max || o.val().length < min ) {
					o.addClass( "ui-state-error" );
					return false;
				} else {
					return true;
				}
			}

			confirmDialog = $( "#confirm-dialog-form" ).dialog({
				autoOpen: false,
				height: 320,
				width: 350,
				modal: true,
				buttons: {
					"확인": function() {
						confirmForm.trigger("submit");
					},
					"취소": function() {
						confirmDialog.dialog( "close" );
					}
				},
				close: function() {
//			confirmForm[ 0 ].reset();
					allFields.removeClass( "ui-state-error" );
				}
			});

			confirmForm = confirmDialog.find( "form" ).on( "submit", function( event ) {
				var valid = true;
				allFields.removeClass( "ui-state-error" );

				valid = valid && checkLength( name, 3, 20 );
				valid = valid && checkLength( phone, 7, 13 );

				if ( valid ) {
					confirmDialog.dialog( "close" );
				}
				return valid;
			});

			$( "#confirm-rsv" ).on( "click", function() {
				confirmDialog.dialog( "open" );
			});
		} );
	</script>
</head>
<body>
	<header>
		<h1>신라 티켓 링크</h1>	
		<div style="align-self: flex-end; padding-right: 15px;"><a><img class="icon" src="/resources/images/admin.png" alt="admin-login" /></a></div>
	</header>
	<div class="main">
	    <div class="detail_content_head">
	        <h2>공연정보</h2>
	    </div>
	    <div class="detail_content_body">
	        <div class="tiket_poster">
	            <p style="text-align:center"><span style="font-size:20px"><strong>2021 자양강장재~즈 시리즈</strong></span></p>
				<p style="text-align:center"><span style="font-size:16px">7월은 피아니스트 임정원트리오, 퀄텟 과 함께 </span></p>
				<p style="text-align:center"><span style="font-size:16px">'Thus we are' 'My favourite songs' 라는 주제로 연주합니다.</span></p>
				<p style="text-align:center">&nbsp;</p>				
				<p style="text-align:center">
					<span><img alt="" src="/resources/images/concert-01-front.jpg" style="height:564px; width:400px"></span>
					<span><img alt="" src="/resources/images/concert-01-back.jpg" style="height:564px; width:400px"></span>
				</p>				
	        </div>
	    </div>
	    <div class="menubar">
	    	<div class="button" onclick="location.href='/register';"><strong>예약하기</strong></div>
	    	<div class="button" id="confirm-rsv"><strong>예약확인</strong></div>
	    </div>
		<div id="confirm-dialog-form" title="예약 확인">
			<form action="/confirm" method="POST">
				<fieldset>
					<label for="name">이름:</label>
					<input type="text" name="name" id="name" placeholder="예약 시에 등록한 이름" class="text ui-widget-content ui-corner-all">
					<label for="phone">연락처:</label>
					<input type="text" name="phone" id="phone" placeholder="예약 시에 등록한 연락처" class="text ui-widget-content ui-corner-all">

					<!-- Allow form submission with keyboard without duplicating the dialog button -->
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
				</fieldset>
			</form>
		</div>

	</div>	
	<footer>
		<h4>공연 예약 샘플 프로그램</h4>
	</footer>
</body>
</html>