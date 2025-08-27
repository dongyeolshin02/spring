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
   
   .file-list {
      display : flex;
      flex-direction : column;
      list-style-type: none;
      margin : 0;
      padding: 0;
      align-items: flex-start;
   }

.file-item	a {
	  text-decoration: none;
	  color : black;
	}
	
.file-item	a:hover{
	  color : #167edb;
	  font-weight: 600;
	}
	
.file-item	a:visited {
		color : black;
	}

</style>
</head>
<body>
  <%@ include file="../header/header.jsp" %>
  <main class="container">
     <header class="text-center">
        <h2>게시글 수정</h2>
     </header>
     <section class="write-form">
 
        <form id="frm" action="/board/update.do" method="POST" enctype="multipart/form-data">
           <input type="hidden" id="brdId" name="brdId" value="${vo.brdId}">
	     	<table class="table">
	     	   <tbody>
	     	   		<tr>
	     	   		   <th>
						 <label for="title" class="form-label pt-2">제목</label>
	                   </th>
	     	   		   <td>
	     	   		     <input type="text" id="title" name="title" class="form-control" value="${vo.title}" >
	     	   		   </td>
	     	   		</tr>
	     	   		<tr>
	     	   		   <th rowspan="2" style="vertical-align: middle;">
						 <label for="file" class="form-label pt-2">첨부파일</label>
	                   </th>
	     	   		   <td>
	     	   		     <input type="file" id="file" name="file" class="form-control">
	     	   		   </td>
	     	   		</tr>
	     	   		<tr>
	     	   		    <td>
		     	   		     <c:if test="${vo.files.size() ==0 }">없음</c:if>
		     	   		     <c:if test="${vo.files.size() > 0 }">
		     	   		       <ul class="file-list">
		     	   		         <c:forEach items="${vo.files}" var="item">
		     	   		         	<li class="file-item">
		     	   		         	  ${item.fileName}
		     	   		         	  &nbsp;&nbsp;<a href="javascript:void(0);" onclick="delFile(${item.bfId})">삭제</a>
		     	   		         	</li>
		     	   		         </c:forEach>
		     	   		        </ul>
		     	   		     </c:if>
	     	   		     </td>
	     	   		</tr>
	     	   		<tr>
	     	   		   <th>
						 <label for="contents" class="form-label pt-2">내용</label>
	                   </th>
	     	   		   <td>
	     	   		     <textarea id="contents" name="contents">${vo.contents}</textarea>
	     	   		    </td>
	     	   		</tr>
	     	   </tbody>
	     	</table>
     	</form>
     </section>
     <section class="text-center">
     	<button type="button" class="btn btn-primary me-2" onclick="updateBoard();">글수정</button>
     	<button type="button" class="btn btn-secondary">취소</button>
     </section>
  </main>
</body>
<script>


 function valiadted() {
	 
	 const title = document.querySelector('#title');
	 const contents = document.querySelector('#contents');
	 
	 if(title.value.trim().length === 0) {
		 alert('제목을 입력하십시오.');
		 title.focus();
		 return false;
	 }
	 
	 if(contents.value.trim().length === 0) {
		 alert('내용을 입력하십시오.');
		 contents.focus();
		 return false;
	 }
	 
	 const file = document.querySelector('#file');
	 //파일을 선택 했을 경우 
	 if(file.files.length > 0 ) {
		 const isConfirm = confirm('첨부파일을 추가히시면 기존파일이 삭제됩니다.\n진행하시겠습니까?');
		
		 if(isConfirm){
			 const maxSize = 50 * 1024 * 1024;
			 if(file.files[0].size > maxSize) {
				 alert('업로드 최대 용량은 50MB를 초과할수 없습니다.');
				 return false;
			 }
			 
		 }else {
			 return false; // 취소 시 진행 중지 
		 }
	 }
	 
	 return true;
 }
 
 const delFile = (bfId) =>{
	 const isConfirm = confirm('삭제하시면 수정을 취소해도 삭제됩니다.\n진행하시겠습니까?');
	 if(isConfirm){
		 
	 }
 }


  const updateBoard = () =>{
	  //유효성 통과 후  form 전송 
	  if(valiadted() ){
		  $('#frm').submit();
	  }
  }




















</script>
</html>