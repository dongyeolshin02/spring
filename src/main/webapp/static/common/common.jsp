<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel ="stylesheet" href="/webjars/bootstrap/5.3.1/css/bootstrap.min.css"/>

<script src="/webjars/jquery/3.7.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/5.3.1/js/bootstrap.min.js"></script>
</head>
<script>

	function  makeTD(className,  contents){
		   const td = $('<td></td>');
		   td.addClass(className);
		   td.append(contents);
		   
		   return td;
	}


</script>
</html>