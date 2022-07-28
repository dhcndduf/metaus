# 관리자페이지 - 주요 기능
-관리자 등록,수정
-아이디 찾기
-비밀번호 변경
-회원 조회
![관리자등록](https://user-images.githubusercontent.com/107232180/181466862-6b0e2fb5-c126-4b4e-a184-4b957d5193e8.png)
![정보수정](https://user-images.githubusercontent.com/107232180/181466900-94b48d81-d8c5-48f0-9295-d96e70bc8c61.png)
![아이디 찾기](https://user-images.githubusercontent.com/107232180/181461140-8c05bb6c-780d-4870-8d0e-35352449cdac.png)
![비밀번호변경](https://user-images.githubusercontent.com/107232180/181461222-78c4ba08-41e9-4383-a4cc-42706c67bda8.png)
![회원조회](https://user-images.githubusercontent.com/107232180/181466960-e846922b-3f58-44c5-b32f-34dd8dc36076.png)



***

## 1. 아이디 찾기,비밀번호 변경
-ajex이용

```java
	@ResponseBody
	@RequestMapping("/findId")
	public String findId(String managerName, String managerTel) {

		ManagerVO vo = new ManagerVO();
		vo.setManagerName(managerName);
		vo.setManagerTel(managerTel);
		logger.info("관리자 아이디 찾기 vo={}",vo);
		String result = "";
		String managerId = managerService.findId(vo);
		if(managerId!=null && !managerId.isEmpty()) {
			result=managerId;
		
			logger.info("아이디 확인 결과, result={}", result);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findPw")
	public int findPw(String managerName, String managerTel, String managerId) {

		ManagerVO vo = new ManagerVO();
		vo.setManagerName(managerName);
		vo.setManagerTel(managerTel);
		vo.setManagerId(managerId);
		logger.info("관리자 비밀번호 찾기 vo={}",vo);
		int result = 0;
		String managerPw = managerService.findPw(vo);
		if(managerPw!=null && !managerPw.isEmpty()) {
			result=1;
		
			logger.info("비밀번호 확인 결과, result={}", result);
		}
		
		return result;
	}
	
	@PostMapping("/updatePw")
	public String updatePw(@ModelAttribute ManagerVO vo, Model model) {
		logger.info("관리자 비밀번호 변경 처리, 파라미터 vo={}", vo);
		
		int cnt=managerService.updatePw(vo);
		logger.info("비밀번호 변경 결과, cnt={}", cnt);
		
		model.addAttribute("managerId",vo.getManagerId());
		String msg="비밀번호 변경 실패", url="/admin/member/managerPwReset";
		if(cnt>0) {
			msg="비밀번호 변경 성공";
			url="/admin/login/adminLogin";
		}

		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		return "/common/message";
		
	}
```

```javascript

<script type="text/javascript">
$(function(){
	$('#managerIdFind').click(function() {
		if ($.trim($('#managerName').val()).length < 1) {
			alert("이름을 입력해주세요");
			$('#managerName').focus();
			event.preventDefault();

		} else if ($.trim($('#managerTel').val()).length < 1) {
			alert("전화번호를 입력해주세요");
			$('#managerTel').focus();
			event.preventDefault();
			
		} else{
			var name = $('#managerName').val();
			var tel = $('#managerTel').val();
			
			$.ajax({
				url: "<c:url value='/admin/member/findId'/>",
				type:"GET",
				data:{
					managerName:name,
					managerTel:tel
				},
				async:false,
				success:function(data){
					if(data==""){
						alert('사용자의 정보와 일치하는 아이디가 없습니다.');
						
					}else{
						alert("회원님의 아이디는 "+data+" 입니다.");
						$('#managerPwId').val(data);
						$('#managerPwId').focus();
					}
				},
				error:function(){
					alert('ajax 오류');
				}
			});
		}
	});
	
	$('#menagerPwFind').click(function() {
		if($.trim($('#managerPwId').val()).length < 1){
			alert("아이디를 입력해주세요");
			$('#managerPwId').focus();
			event.preventDefault();
			
		} else if ($.trim($('#managerPwName').val()).length < 1) {
			alert("이름을 입력해주세요");
			$('#managerPwName').focus();
			event.preventDefault();

		} else if ($.trim($('#managerPwTel').val()).length < 1) {
			alert("전화번호를 입력해주세요");
			$('#managerPwTel').focus();
			event.preventDefault();		

		} else{
			var name = $('#managerPwName').val();
			var tel = $('#managerPwTel').val();
			var id = $('#managerPwId').val();
			
			$.ajax({
				url: "<c:url value='/admin/member/findPw'/>",
				type:"GET",
				data:{
					managerName:name,
					managerTel:tel,
					managerId:id
				},
				async:false,
				success:function(data){
					if(data==0){
						alert('사용자의 정보와 일치하는 계정이 없습니다.');
						
					}else if(data==1){
						document.querySelector('#maPwFindFrm').submit();					
					}
				},
				error:function(){
					alert('ajax 오류');
				}
			});
		}
	});
	
	function validate_tel(tel) {
		var pattern = new RegExp(/^[0-9]*$/g);
		return pattern.test(tel);
	}
}); 

</script>

```


```view

<body class="bg-gradient-primary">

    <div class="container">

        <!-- Outer Row -->
        <div class="row justify-content-center">

            <div class="col-xl-12 col-lg-12 col-md-8">

                <div class="card o-hidden border-0 shadow-lg my-5" >
                    <div class="card-body p-0">
                    
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5">	                                
	                                    <div class="form-group">
	                                	<label style="color: black;">이름</label>                                    
	                                        <input type="text" class="form-control form-control-user" id="managerName" name="managerName"
	                                            placeholder="Name"> 
	                                </div>
	                                <div class="form-group">
	                                	<label style="color: black;">전화번호</label>                                    
	                                        <input type="text" class="form-control form-control-user" id="managerTel"
	                                            placeholder="Tel"> 
	                                </div>
	                                	<button type="button" class="btn btn-primary btn-user btn-block" id="managerIdFind">아이디 찾기</button>                             
	                                <hr>
                           	
		                            <form method="post" action="<c:url value='/admin/member/managerPwReset'/>" id="maPwFindFrm"> 
		                                
		                                <div class="form-group">
		                                	<label style="color: black;">아이디</label>                                    
		                                        <input type="text" class="form-control form-control-user" name="managerId" id="managerPwId"
		                                            placeholder="Id"> 
		                                </div>
		                                <div class="form-group">
		                                	<label style="color: black;">이름</label>                                    
		                                        <input type="text" class="form-control form-control-user" id="managerPwName"
		                                            placeholder="Name"> 
		                                </div>
		                                <div class="form-group">
		                                	<label style="color: black;">전화번호</label>                                    
		                                        <input type="text" class="form-control form-control-user" id="managerPwTel"
		                                            placeholder="Tel"> 
		                                </div>                                
										<button type="button" class="btn btn-primary btn-user btn-block" id="menagerPwFind">비밀번호 찾기</button>                               
		                           	</form>
               
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>

    <!-- Bootstrap core JavaScript-->
    
    <script src="<c:url value='/admin/vendor/jquery/jquery.min.js'/>"></script>
    <script src="<c:url value='/admin/vendor/bootstrap/js/bootstrap.bundle.min.js'/>"></script>

    <!-- Core plugin JavaScript-->
    <script src="<c:url value='/admin/vendor/jquery-easing/jquery.easing.min.js'/>"></script>

    <!-- Custom scripts for all pages-->
    <script src="<c:url value='/admin/js/sb-admin-2.min.js'/>"></script>

</body>

```


```
