<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
    *{
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }

        .nav-container{
            display: flex;
            background-color: #1a1a1a;
            height: 80px;
            line-height: 20px;
            padding: 1rem 2rem;
            justify-content: space-between;

        }

        .nav-menu{
            display: flex;
            list-style-type: none;
            gap: 20px;

        }

        .nav-link{
            color: white;
            text-decoration: none;
            padding: 1rem 1.5rem;
            border-radius: 5px;
        }
        .nav-link:hover{
            background-color: #eaeaea;
        }

        .nav-link.active{
            background-color: #f52424;
        }
        
</style>
</head>
<body>
 <!-- 전역변수하나 만들어놓은 것 이라고 생각하세요  -->
  <c:set var="isLogin" value="${not empty sessionScope.userInfo}" scope="session"/>
  <c:set var="user" value="${sessionScope.userInfo != null ? sessionScope.userInfo : null}" scope="session"/>
	<header>
	    <nav>
	        <div class="nav-container">
	            <ul class="nav-menu">
	                <li class="nav-item">
	                    <a href="/board/list.do" class="nav-link">게시판1</a>
	                </li>
	                <li class="nav-item">
	                    <a href="/board/list2.do" class="nav-link">게시판2</a>
	                </li>
	                 <li class="nav-item">
	                    <a href="/user/list.do"  class="nav-link">사용자리스트</a>
	                </li>
	            </ul>
	            <ul class="nav-menu">
	                <li class="nav-item">
	                   <c:if test="${!isLogin }">
	                      <a href="/user/login.do"  data-page="register"class="nav-link">로그인</a>
	                    </c:if>
	                     <c:if test="${isLogin }">
	                         <p style="color:white"> ${user.userName} 님 환영합니다</p>
	                         <span style="color:white;"><a href="/logout.do">로그아웃 </a></span> 
	                     </c:if>
	                </li>
	            </ul>
	        </div>
	    </nav>
	</header>
</body>
<script>

    function move(site){
        const menuLinks = document.querySelectorAll('.nav-link');

        menuLinks.forEach( link => {
            link.classList.remove('active');
            id = link.getAttribute('data-page');
            if(site === id) {
                link.classList.add('active');
            }
        });
    }
</script>
</html>