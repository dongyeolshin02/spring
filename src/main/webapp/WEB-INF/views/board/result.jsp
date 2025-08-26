<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/static/common/common.jsp" %>
</head>
<body>
 <input type="hidden" id="msg" value="${msg}">
</body>
<script>
$(document).ready(function() {
	const msg = $('#msg').val();
	alert(msg);
	location.href = '/board/list2.do';
});

</script>
</html>