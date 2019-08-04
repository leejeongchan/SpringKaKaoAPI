<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>카카오로그인 테스트</title>
</head>
<body>
    <c:if test="${userId eq null}">
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=699e22769b9f3789d4764b8d68226a98&redirect_uri=http://localhost:8080/login&response_type=code">
            <img src="../../resources/img/kakaologin.png">
        </a>
       	<script>
       		var message = "${message}";
       		var flag = "${flag}";
       		
       		if(flag==1){
       			alert(message);
       		}
       	</script>
    </c:if>
    
    <c:if test="${userId ne null}">
        <h1>로그인 성공입니다</h1>
        <h1>사용자 이메일:${email}</h1>
        <h1>사용자 닉네임:${nickname}</h1>
        <input type="button" value="로그아웃" onclick="location.href='/logout'">
    </c:if>
</body>
</html>
