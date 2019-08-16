<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<style>
:root {
  --input-padding-x: 1.5rem;
  --input-padding-y: .75rem;
}

body {
  background: #007bff;
  background: linear-gradient(to right, #0062E6, #33AEFF);
}

.card-signin {
  border: 0;
  border-radius: 1rem;
  box-shadow: 0 0.5rem 1rem 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-signin .card-title {
  margin-bottom: 2rem;
  font-weight: 300;
  font-size: 1.5rem;
}

.card-signin .card-img-left {
  width: 45%;
  /* Link to your background image using in the property below! */
  background: scroll center url('https://source.unsplash.com/WEQbe2jBg40/414x512');
  background-size: cover;
}

.card-signin .card-body {
  padding: 2rem;
}

.form-signin {
  width: 100%;
}

.form-signin .btn {
  font-size: 80%;
  border-radius: 5rem;
  letter-spacing: .1rem;
  font-weight: bold;
  padding: 1rem;
  transition: all 0.2s;
}

.form-label-group {
  position: relative;
  margin-bottom: 1rem;
}

.form-label-group input {
  height: auto;
  border-radius: 2rem;
}

.form-label-group>input,
.form-label-group>label {
  padding: var(--input-padding-y) var(--input-padding-x);
}

.form-label-group>label {
  position: absolute;
  top: 0;
  left: 0;
  display: block;
  width: 100%;
  margin-bottom: 0;
  /* Override default `<label>` margin */
  line-height: 1.5;
  color: #495057;
  border: 1px solid transparent;
  border-radius: .25rem;
  transition: all .1s ease-in-out;
}

.form-label-group input::-webkit-input-placeholder {
  color: transparent;
}

.form-label-group input:-ms-input-placeholder {
  color: transparent;
}

.form-label-group input::-ms-input-placeholder {
  color: transparent;
}

.form-label-group input::-moz-placeholder {
  color: transparent;
}

.form-label-group input::placeholder {
  color: transparent;
}

.form-label-group input:not(:placeholder-shown) {
  padding-top: calc(var(--input-padding-y) + var(--input-padding-y) * (2 / 3));
  padding-bottom: calc(var(--input-padding-y) / 3);
}

.form-label-group input:not(:placeholder-shown)~label {
  padding-top: calc(var(--input-padding-y) / 3);
  padding-bottom: calc(var(--input-padding-y) / 3);
  font-size: 12px;
  color: #777;
}

.btn-google {
  color: white;
  background-color: #ea4335;
}

.btn-facebook {
  color: white;
  background-color: #3b5998;
}
</style>
<link rel="stylesheet" href="../../resources/css/bootstrap.min.css">
<script src="../../resources/js/bootstrap.bundle.min.js"></script>
<script src="../../resources/js/jquery-3.4.1.js"></script>
</head>
<body>
 <div class="container">
    <div class="row">
      <div class="col-lg-10 col-xl-9 mx-auto">
        <div class="card card-signin flex-row my-5">
          <div class="card-img-left d-none d-md-flex">
             <!-- Background image for card set in CSS! -->
          </div>
          <div class="card-body">
            <h5 class="card-title text-center">Register</h5>
            <form class="form-signin" id='regForm' method="post" action="/user/register">
              <div class="form-label-group" id="divInputId">
                <input type="text" id="userId" name="userId" class="form-control" placeholder="User Id" required autofocus>
                <label for="inputUserame">UserId</label>
                <input type="button" class="btn btn-default" style="width: 30%;" value="중복확인" onclick="duplicationId();" />
              </div>

              <div class="form-label-group">
                <input type="email" id="inputEmail" name="userEmail" class="form-control" placeholder="Email address" >
                <label for="inputEmail">Email address</label>
              </div>
              
              <hr>

              <div class="form-label-group">
                <input type="password" id="inputPassword" name="userPw" class="form-control" placeholder="Password" >
                <label for="inputPassword">Password</label>
              </div>
              
              <div class="form-label-group">
                <input type="password" id="inputConfirmPassword" class="form-control" placeholder="Password" >
                <label for="inputConfirmPassword">Confirm password</label>
              </div>

              <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Register</button>
              <a class="d-block text-center mt-2 small" href="/user/login">Sign In</a>
              <hr class="my-4">
              <button class="btn btn-lg btn-google btn-block text-uppercase" id='' type="submit"><i class="fab fa-google mr-2"></i> Sign up with Google</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <script type="text/javascript">
  
	//아이디 중복 체크 AJAX
	var isCheckId = 0;
	function duplicationId () {
		var inputId = $("#userId").val();
		$.ajax({
			async: false,
			type: "post",
			url: "/user/idcheck",
			contentType: "application/json; charset=UTF-8",
			data: inputId,
			success: function (data) {
				console.log("data?: "+data);
				if(data == "S") {
					alert("사용가능한 아이디입니다.");
					
					$("#divInputId").addClass("has-success")
					$("#divInputId").removeClass("has-error")
					
					$("#userName").focus();
					isCheckId = 1;
				} else {
					alert("아이디가 존재합니다. 다른 아이디를 입력해주세요.");
					
					$("#divInputId").addClass("has-error")
					$("#divInputId").removeClass("has-success")
					
					$("#userId").focus();
				}
			},
			error: function(req, status, errThrown) {
				alert("network error occur");
			}
		});
	}
  
  
  var regForm = $("#regForm");
	$("#regBtn").on("click",function(e){
		e.preventDefault();
		var userid = $("#userId").val();
	    var userpwd = $("#userPw").val();
	    var inputPwdCfm = $("#inputConfirmPassword").val();
	    var email = $("#userEmail").val();
	    
	    if(userid.length == 0){
	        alert("아이디를 입력해 주세요"); 
	        $("#userId").focus();
	        return false;
	    }
	    
	    if(isCheckId == 0 ){
	    	alert("아이디 중복 체크 해주세요.");
	    	$("#userId").focus();
	    	return false;
	    }
	    
	    
	    
	    if(email.length == 0){
	        alert("이메일을 입력해주세요");
	        $("#userEmail").focus();
	        return false;
	    }
	    
	   
	    if(userpwd.length == 0){
	        alert("비밀번호를 입력해 주세요"); 
	        $("#userPw").focus();
	        return false;
	    }
	 
	    if(userpwd != inputPwdCfm){
	        alert("비밀번호가 서로 다릅니다. 비밀번호를 확인해 주세요."); 
	        $("#userPw2").focus();
	        return false; 
	    }
	 
	   
	    if(confirm("회원가입을 하시겠습니까?")){
	        
	        regForm.submit();
	    }
	});
  
  </script>
</body>
</html>