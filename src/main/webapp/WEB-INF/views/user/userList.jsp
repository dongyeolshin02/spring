<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 리스트</title>
<%@ include file="/static/common/common.jsp" %>
<link rel="stylesheet" href="/static/css/userList.css"/>

</head>
<body>
   <%@ include file="../header/header.jsp" %>
   <main class="container">
      <section class="contents">
        <header class="text-center">
             <h2>사용자 리스트 </h2>
        </header>
         <section class="sch">
           <div>
              <select class="form-select" id="schType" name="schType">
                 <option value="userId" selected>아이디</option>
                 <option value="userName">이름</option>
              </select>
           </div>
           <div class="sch-input">
             <input type="text" 
                id="schText" name="schText" 
                class="form-control" onkeydown="searchEnter(event);">      
           </div>
           <button type="button"
                class="btn btn-success ms-2" onclick="searchEvt();">검색</button>
           <div class="sch-btn">
              <button type="button" class="btn btn-primary ms-2" onclick="addUserEvt();">등록</button>
           </div>
         </section>
         <section class="data-list">
              <input type="hidden" id="currentPage" name="currentPage" value="${data.currentPage}">
              <table class='table'>
                <colgroup>
	              	<col style="width:20%"/>
	              	<col style="width:20%"/>
	                <col style="width:20%"/>
	              	<col style="width:20%"/>
	              	<col style="width:20%"/>
                </colgroup>
                <thead class="table-dark text-center">
	                <tr>
	                  <th>아이디</th>
	                  <th>이름</th>
	                  <th>생년월일</th>
	                  <th>전화번호</th>
	                  <th>이메일</th>
	                </tr>
                </thead>
                <tbody id="tbody">
                 <c:forEach items="${data.dataList}"  var="item">
                 	<tr>
                 	  <td>${item.userId}</td>
                 	  <td>${item.userName}</td>
                 	  <td>${item.birth}</td>
                 	  <td>${item.phone}</td>
                 	  <td>${item.email}</td>
                 	</tr>
                 </c:forEach>
                
                </tbody>
              </table>
         </section>
         <section class="page">
         	 <nav aria-label="Page navigation example">
			  <ul class="pagination justify-content-center" id="page">
			    ${data.pageHTML} 
			  </ul>
			</nav>
         </section>
      </section>
   </main>
</body>
<script>


  const    addUserEvt = () =>{
	  location.href ="/user/join.do";
  }

  const searchEvt =() =>{
	  const type =  document.querySelector('#schType').value;
	  const schText = document.querySelector('#schText').value;
	  
	  if(schText.trim().length === 0) {
		  alert("검색할 내용을 작성해주십시오.");
		  return false;
	  }
	  searchUser(type, schText);
  }
  
  function searchUser(type, schText) {

	  $.ajax({
		  url : '/user/search.do',
		  type : 'get',
		  dataType : 'json',
		  data :{type, schText} //key 이름과 value 변수 이름이 같을 때 축약  es6
	  }).done(function(res) {
		
		  drawTable(res);
	 
	  }).fail(function(xhr, status, error){
		  alert('검색실패');
	  });   
  }
  
  
  function drawTable(res) {
	  
	  const tbody = $('#tbody');
	  tbody.empty(); //날리기 
	  
	  //테이블 만들기
	  $.each(res.dataList, function(index, obj){		  
		  const tr = $('<tr></tr>'); 
		  const link = $('<a></a>');
		  link.attr('href', 'javascript:void(0);');
		  //jquery에서 객체에 이벤트 바인딩 하는 방법 
		  link.on('click', ()=> goDteail(obj.userId) );
		  
		  tr.append(makeTD('', obj.userId));
		  tr.append(makeTD('', link.append(obj.userName)) );
		  tr.append(makeTD('', obj.birth));
		  tr.append(makeTD('', obj.phone));
		  tr.append(makeTD('', obj.email));
		  
		  tbody.append(tr);  
	  });
	  
	  const page = $('#page');
	  page.empty();
	  page.append(res.pageHTML);
	  //검색 후 첫페이지 지정 
	  $('#currentPage').val(res.currentPage);
	  
  }
  
  const movePage = (pageNum) =>{
	  $('#currentPage').val(pageNum);
	  location.href = '/user/list.do?currentPage=' + pageNum;
  }
  
  const searchEnter = (e) =>{
	  //엔터키 누르면 검색 실행 
	  if(e.keyCode === 13){
		  searchEvt();
	  }
  }
  
  
</script>
</html>






