<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>사용자가입</title>
    <%@ include file="/static/common/common.jsp" %>
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        body{
            background-color: #f5f3f3;
        }

        .join-box {
            width: 100%;
            max-width: 600px;
            background-color:  #ffff;
        }

    </style>
</head>
<body>
   <main class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card shadow p-4 join-box">
            <header>
                <h2 class="mt-4 text-center">회원가입</h2>
            </header>
            <div>
            <form autocomplete="off" id="myfrm">
                <div class="mb-3">
                    <label for="userId" class="form-label">아이디</label>
                    <input type="text" class="form-control" id="userId" name="userId" required>
                </div>
                <div class="mb-3">
                    <label for="userName" class="form-label">이름</label>
                    <input type="text" class="form-control" id="userName" name="userName" required>
                </div>
                <div class="mb-3">
                    <label for="passwd" class="form-label">패스워드</label>
                    <input type="password" class="form-control" id="passwd" name="passwd" required>
                </div>
                 <div class="mb-3">
                    <label for="passwdConfirm" class="form-label">패스워드 확인</label>
                    <input type="password" class="form-control" id="passwdConfirm" name="passwdConfirm" required>
                </div>
                 <div class="mb-3">
                    <label for="birth" class="form-label">생년월일</label>
                    <input type="text" class="form-control" id="birth" name="birth" required>
                </div>
                     <div class="mb-3">
                    <label for="phone" class="form-label">모바일</label>
                    <input type="text" class="form-control" id="phone" name="phone" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">이메일</label>
                    <input type="text" class="form-control" id="email" name="email" required>
                </div>           
            </form>
            <div class="text-center">
                <button type="button" id="joinBtn" class="btn btn-primary me-2">가입</button>
                <button type="button" id="cancelBtn" class="btn btn-secondary">취소</button>
            </div>
        </div>
      </div>
   </main>
</body>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" ></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>


  const searchAddr = ()=>{
    new daum.Postcode(
        {
            oncomplete :function(data){
                let fullAddr = '';
                let extarAddr = '';

                //도로명 주소 선택 여부
                if(data.userSelectedType === 'R') {
                    fullAddr = data.roadAddress;
                }else {
                      fullAddr = data.jibunAddress;
                }

                //법정동    
                if(data.bname.trim().length > 0 ||  /[동|로|가]$/g.test(data.bname)) {
                    extarAddr += data.bname;
                }

                //아파트 또는 빌딩 명
                if(data.buildingName.trim().length > 0){
                    extarAddr +=  (extarAddr.length > 0 ? ', ' : '') +  data.buildingName;
                }

                extarAddr =  extarAddr.length > 0 ?  ` ( ${extarAddr} )` : extarAddr;
                fullAddr += extarAddr;

               // $('#addr').val(fullAddr);
               document.querySelector('#addr').value =  fullAddr;
            }
        }
    ).open();

  }


  function validated() {
    // 미 입력 시 노출 될 메시지
    // key 는 input 창의 id와 동일하게 한다. 
    const eventMessages = {
        userId : '아이디를 입력하십시오',
        userName : '이름을 입력하십시오.',
        passwd : '패스워드를 입력하십시오',
        passwdConfirm : '패스워드 확인을 입력하십시오',
        birth : '생년월일을 입력하십시오',
        phone :'주민번호를 입력하십시오',
        email : '메일을 입력하십시오'
    }  


    //addrDetail 빼고 모든 input 엘리먼트 가져오기 
    const inputArr = $('input[type="text"]:not(#addrDetail), input[type="password"]');

    //인증용 변수
    let isConfirm = true;
    //메시지 담을 변수 
    let alertMessage = '';

    $.each(inputArr, (index, eleObj)=>{
        // 콜백에서 사용되는 객체는 자바스크립트 객체임을!!
        const id = eleObj.id;
        const value = eleObj.value.trim();
        //입력 값이 없을 경우 
        if(value.length === 0) {
            isConfirm = false;
            alertMessage =  eventMessages[id];
            $('#'+id).focus(); // 입력란에 포커스 주기 
            return false; //break;
        }
    });

    //입력 값이 없을 경우
    if(!isConfirm) {
        alert(alertMessage);
        return isConfirm;
    }

    //유효성 통과
    return isConfirm;

  }

  const goJoin = (e)=>{
    if(validated()) {
    	
    	const param = {
    	        userId :   $('#userId').val(),
    	        userName : $('#userName').val(),
    	        passwd :   $('#passwd').val(),
    	        birth :    $('#birth').val(),
    	        phone :    $('#phone').val(),
    	        email :    $('#email').val()
    	    }  
    	
        
    	 const fd = new FormData(document.getElementById("myfrm")); // file 없어도 OK
    	 
    	console.log(fd);
    	
    	$.ajax({
    		url :'/user/add.do',
    		type : 'post',
    		dataType :'json',
    		data : fd,
    	    processData: false,
    	      contentType: false,
    	}).done(function(res){
    		
    		if(res.resultCode === 200) {
    			alert('신규 사용자가 등록되었습니다.');
    		}else {
    			alert('신규사용자 등록이 실패했습니다');
    		}
    		
    		location.href ="/user/list.do";
    		
    	}).fail(function(xhr, status, error) {
		  
    		if(xhr.status === 500) {
    			const res = JSON.parse(xhr.responseText);
    			alert(res.msg[0]);
	  	}
	 	});
    	
    } 
  }
  

  function init(){
    // 콜백함수를 부여할 때 매개변수를 전달해야할 경우가 있음.
    // 그럴경우  콜백자리에 바로 함수(매개변수); 라고 적으면 안됨
    // Ex)  joinBtn.addEventListener('click', go(data));
    // go(); 라고 하면 실행의 의미를 가짐 
    // 이럴 경우 콜백함수를 만들고 그 안에서 실행할 함수와 매개변수 처리해야함 
    // ex) joinBtn.addEventListener('click', function(){  goJoin();  });
    const joinBtn = document.querySelector('#joinBtn');
    joinBtn.addEventListener('click', (e) => goJoin(e));
  }


  //jquery 시작함수 
  $(document).ready(function(){
    init();
  });

</script>
</html>