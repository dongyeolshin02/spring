<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 리스트</title>
<link rel ="stylesheet" href="/webjars/bootstrap/5.3.1/css/bootstrap.min.css"/>
<style>

.container{
    display : flex;
    flex-direction: column;
    align-items: center;
    margin-top: 10vh;
    height:80vh;
    
 }

  .contents{
  	 width: 80%;
  }

.data-list{
    width : 100%;
    height : 600px;
    margin-top: 30px;
    overflow: auto;
}

</style>
</head>
<body>
  <%@ include file="../header/header.jsp" %>
   <main class="container">
     <section class="contents">
        <header class="text-center">
             <h2>게시판 리스트 </h2>
        </header>
        <section class="data-list">
           <table class='table'>
              <colgroup>
              	<col style="width:10%"/>
              	<col style="width:40%"/>
                <col style="width:10%"/>
              	<col style="width:10%"/>
              	<col style="width:10%"/>
              	<col style="width:20%"/>
              </colgroup>
              <thead class="table-dark text-center">
                <tr>
                  <th>번호</th>
                  <th>제목</th>
                  <th>글쓴이</th>
                  <th>조회 수</th>
                  <th>추천 수</th>
                  <th>수정일자</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${data.dataList}" var="item">
                   <tr>
                     <td>${item.brdId}</td>
                     <td>${item.title}</td>
                     <td>${item.writer}</td>
                     <td>${item.readCount}</td>
                     <td>${item.likeCount}</td>
                     <td>${item.modifiedDate}</td>
                </c:forEach>
              </tbody>
           </table>
        </section>
        <section class="page">
	        <nav aria-label="Page navigation example">
			  <ul class="pagination justify-content-center">
			     ${data.pageHTML}
			  </ul>
			</nav>
        </section>
     </section>
   </main>
</body>
<script src="/webjars/jquery/3.7.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/5.3.1/js/bootstrap.min.js"></script>
<script>

   function movePage(pageNum) {
	   //페이지 이동
	   location.href ='/board/list.do?currentPage=' + pageNum;
   }

</script>










</html>