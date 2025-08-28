<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 리스트</title>
<%@ include file="/static/common/common.jsp" %>
<style>

.container{
    display : flex;
    flex-direction: column;
    align-items: center;
    margin-top: 10vh;
    height:70vh;
    
 }

  .contents{
  	 width: 80%;
  }

.data-list{
    width : 100%;
    height : 500px;
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
        <section class="text-end">
        	<button type="button" class="btn btn-primary" onclick="goWrite();">글쓰기</button>
        </section>
        <section class="data-list">
           <input type="hidden" id="currentPage" name="currentPage" value="${currentPage}">
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
              <tbody id="boardBody">
              </tbody>
           </table>
        </section>
        <section class="page">
	        <nav aria-label="Page navigation example">
			  <ul class="pagination justify-content-center"  id="page">
			     
			  </ul>
			</nav>
        </section>
     </section>
   </main>
</body>
<script>
   function movePage(pageNum) {
	   //페이지 번호 업데이트 
	   $('#currentPage').val(pageNum);
	   getBoardData();
   }
   
   //서버에 데이터 요청 
   function getBoardData(){
	   //비동기로 서버에 데이터 요청
	   $.ajax(
			  {
				  
		 		url : '/board/list/data.do',  //요청 경로 
		 		type : 'get',        //전송 방식
		 		dataType : 'json',   //돌려받을 데이터 타입,
		 		data : {
		 			currentPage : $('#currentPage').val()
		 		}
 	   		}
	   ).done(function(res){
		   
		   drawTable(res);
		   
	   }).fail(function(xhr, status, error){
		  alert(error); 
	   });
   }
   
   
   function  drawTable(data) {
	   
	   const tbody = $('#boardBody');
	   
	   //비워
	   tbody.empty();
	   
	   //테이블 만들기 
	   $.each(data.dataList, function(index, obj){
		  const tr = $('<tr></tr>'); 
		  const link = $('<a></a>');
		  link.attr('href', 'javascript:void(0);');
		  //jquery에서 객체에 이벤트 바인딩 하는 방법 
		  link.on('click', ()=> goDteail(obj.brdId) );
		  
		  tr.append(makeTD('', obj.brdId));
		  tr.append(makeTD('', link.append(obj.title)) );
		  tr.append(makeTD('', obj.writer));
		  tr.append(makeTD('', obj.readCount));
		  tr.append(makeTD('', obj.likeCount));
		  tr.append(makeTD('', obj.modifiedDate));
		  
		  tbody.append(tr);
	   });
	   
	   const page = $('#page');
	   page.empty();
	   page.append(data.pageHTML);
   }
   
   

   
   const goWrite = () => {
	   location.href = '/board/form.do';
   }
   
   //시작함수
   $(document).ready(function() {
	
	   getBoardData();
	   
   });
   
   
   
   const goDteail = (brdId) =>{
	   const currentPage = $('#currentPage').val();
	   location.href = '/board/detail.do?brdId=' + brdId +"&currentPage=" + currentPage;
   }

</script>










</html>