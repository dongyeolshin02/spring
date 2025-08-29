<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/common.jsp" %>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
   .container{
     display: flex;
     flex-direction: column;
     height : 90vh;
     margin-top :  10vh;
     align-items: center;
   }
   
   .write-form{
      width:600px;
      margin-top :20px;
      
   }
   
   #contents {
      resize: none;
      width:500px;
      height:200px;
      border-radius: 7px;
   }
   
   .table th {
     text-align: right;
   }
   
   .table td {
     vertical-align: middle;
   }
   
   .file-list {
      display : flex;
      flex-direction : column;
      list-style-type: none;
      margin : 0;
      padding: 0;
      align-items: flex-start;
   }

.file-list a {
  text-decoration: none;
  color : black;
}

.file-list a:hover{
  color : #167edb;
  font-weight: 600;
}

.file-list  a:visited {
	color : black;
}

</style>
</head>
<body>
 <%@ include file="../header/header.jsp" %>
  <main class="container">
     <header class="text-center">
        <h2>게시글 쓰기</h2>
     </header>
     <section class="write-form">
        <form id="frm">
            <input type="hidden" id="currentPage" name="currentPage" value="${currentPage}">
            <input type="hidden" id="brdId" name="brdId" value="${vo.brdId}">
	     	<table class="table">
	     	   <colgroup>
	     	     <col style="width:15%"/>
	     	     <col style="width:35%"/>
	     	     <col style="width:15%"/>
	     	     <col style="width:35%"/>
	     	   </colgroup>
	     	   <tbody>
	     	   		<tr>
	     	   		   <th>
						 <label class="form-label pt-2">제목</label>
	                   </th>
	     	   		   <td colspan="3">
	     	   		     ${vo.title}
	     	   		    </td>
	     	   		</tr>
	     	   		<tr>
	     	   		   <th>
						 <label class="form-label pt-2">글쓴이</label>
	                   </th>
	     	   		   <td colspan="3">
	     	   		     ${vo.writer}
	     	   		    </td>
	     	   		</tr>
	     	   		<tr>
	     	   		   <th>
						 <label class="form-label pt-2">조회수</label>
	                   </th>
	     	   		   <td>
	     	   		     ${vo.readCount}
	     	   		    </td>
	     	   		    <th>
	     	   		      <label class="form-label pt-2">좋아요</label>
	     	   		    </th>
	     	   		     <td>
	     	   		        <span id="likeCount">${vo.likeCount}</span>
	     	   		    </td>
	     	   		</tr>
	     	   		<tr>
	     	   		   <th>
						 <label class="form-label pt-2">첨부파일</label>
	                   </th>
	     	   		    <td colspan="3">
		     	   		     <c:if test="${vo.files.size() ==0 }">없음</c:if>
		     	   		     <c:if test="${vo.files.size() > 0 }">
		     	   		       <ul class="file-list">
		     	   		         <c:forEach items="${vo.files}" var="item">
		     	   		         	<li class="file-item">
		     	   		         	  <a class="file-link" href="/board/down.do?bfId=${item.bfId}">${item.fileName}</a>
		     	   		         	</li>
		     	   		         </c:forEach>
		     	   		        </ul>
		     	   		     </c:if>
	     	   		     </td>
	     	   		</tr>
	     	   		<tr>
	     	   		   <th>
						 <label class="form-label pt-2">내용</label>
	                   </th>
	     	   		    <td colspan="3">
	     	   		       ${vo.contents}
	     	   		    </td>
	     	   		</tr>
	     	   </tbody>
	     	</table>
     	</form>
     </section>
     <section class="text-center">
       <c:if test="${isLogin}">
            <button type="button" class="btn btn-warning me-2" onclick="updateLike();">좋아요</button>
            <c:if test="${user.userId eq vo.writer }">
     		  <button type="button" class="btn btn-primary me-2" onclick="writeBoard();">글 수정</button>	
     		  <button type="button" class="btn btn-danger  me-2" onclick="deleteBoard();">글 삭제</button>
     		</c:if>
     	</c:if>
     	<button type="button" class="btn btn-secondary" onclick="goList();">목록</button>
     </section>
  </main>
</body>
<script>

  const goList = () =>{
	  const currentPage = $('#currentPage').val();
	  location.href ="/board/list2.do?currentPage=" + currentPage;
  }
  
  
  const updateLike = ()=>{
	  
	  const like = $('#likeCount');
	  console.log(like.text())
	  const newCount = Number( like.text() ) + 1;
	  
	  like.text(newCount);
	  
	  
	  const dataParam = {
			  likeCount : newCount,
			  brdId : $('#brdId').val()
		    };
	  
	  //ajax 로 db 업데이트  > ajax 쓸때 json 문서 전송 방법 예제 
	  $.ajax({
		  url :'/board/like.do',
		  type :'patch',
		  dataType : 'json',
		  contentType : 'application/json', //기본은 application/x-www-form-urlencoded
		  data : JSON.stringify(dataParam)   // 자바스크립트 객체를 json 형식으로 변환 
	  }).done(function(res) {
		  //오류메시지가 있을때만 출력 
		  if(res.msg.length > 0) {
			  alert(res.msg);
		  }
	  }).fail(function(xhr, status, error) {
		  if(xhr.status === 500) {
			  const res = JSON.parse(xhr.responseText);
			  alert(res.msg[0]);
		  }
	  });	  
  }
  
  
  const writeBoard = () =>{
	  location.href = '/board/write/view.do?brdId=' + $('#brdId').val();
  }
  
  
  const deleteBoard = () =>{
	  const isConfirm = confirm('정말 삭제하시겠습니까?');
	  
	  
	  if(isConfirm) {
		  location.href = '/board/delete.do?brdId=' + $('#brdId').val();
	  }
  }
  
  

</script>
</html>











