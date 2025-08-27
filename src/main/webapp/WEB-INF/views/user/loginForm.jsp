<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="/static/common/common.jsp" %>
    
    <style>
        *{
            padding: 0;
            margin: 0;
        }

        body{
            background-color: #b4b3b3;
        }
        
        .container{
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .contents{
            width: 400px;
            height: 300px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 1px 1px 4px 4px #e9e9e9;
        }

        .login-form{
            display: flex;
            height :100%;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .ip-txt{
            width: 250px;
            height: 34px;
            border: none;
            border-bottom: 2px solid black;
            outline: none;
        }

        .mb-4 {
            margin-bottom: 1.5rem;
        }

        .ip-txt:focus{
            border-bottom-color: #007bff;
        }

        .title{
            text-align: center;
            margin-bottom: 40px;
        }

        .btn{
               width: 70px;
               height: 35px;
               background-color: white;
               border: none;
               border-radius: 5px;
               color: white;
               font-weight: 500;
               font-size: 16px;
        }

        
        .btn-primary{
               background-color: #007bff;
        }

          .btn-success{
               background-color: #03a51e;
        }
        
        

    </style>
</head>
<body>
    <main class="container">
        <section class="contents">         
             <div class="login-form">
              <header class="title">
                 <h2> Login</h2>
             </header>
                <div class="mb-4">
                    <input type="text" class="ip-txt" id="userId" placeholder="ID">
                </div>
                 <div  class="mb-4">
                    <input type="password" class="ip-txt" id="passwd" placeholder="PASSWORD">
                </div>
                <div>
                    <button class="btn btn-primary" onclick="login();">로그인</button>
                     <button class="btn btn-success" onclick="login();">회원 가입</button>
                </div>
             </div>
        </section>
    </main>
</body>
<script>

    function validated() {
        const userId =  document.querySelector('#userId');
        const passwd = document.querySelector('#passwd');

        var regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()])[a-zA-Z\d!@#$%^&*()]{8,12}$/;

        if(userId.value.trim().length === 0) {
            alert('아이디를 입력하십시오.');
            userId.focus();
            return false;
        }

         if(passwd.value.trim().length === 0) {
            alert('패스워드를 입력하십시오.');
            passwd.focus();
            return false;
        }

       /*  if(!regex.test(passwd.value)){
            alert('패스워드는 최소 8자리 이상이며, 대소문자와 특수기호를 포함해야합니다.');
            return false;
        } */

        return true;
    }


    const login = () =>{

        if( validated() ) {
            //ajax 를 통해서 비동기로 서버에 로그인 확인
            $.ajax({
            	url : '/user/login.do',
            	type :'post',
            	dataType : 'json',
            	data : {
            		userId : $('#userId').val(),
            		passwd : $('#passwd').val()
            	}
            }).done(function(res){
            	
            	if(res.resultCode === 200) {
            		location.href = "/board/list2.do";
            	}else {
            		alert('아이디 나 패스워드를 확인해주십시오.');
            		return false;
            	}
            	
            }).fail(function(xhr, status, error){
            	alert("로그인 에러!!");
            })
            
        }

    }

</script>

</html>