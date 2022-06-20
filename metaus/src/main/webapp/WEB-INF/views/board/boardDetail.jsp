<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../inc/header.jsp"%>
<!-- =============== Start of Page Header 1 Section =============== -->
<section class="page-header" style="margin-top: 150px;">
	<div class="container">

		<!-- Start of Page Title -->
		<div class="row">
			<div class="col-md-12">
				<h2>게시판 글 상세보기</h2>
			</div>
		</div>
		<!-- End of Page Title -->

		<!-- Start of Breadcrumb -->
		<div class="row">
			<div class="col-md-12">
				<ul class="breadcrumb">
					<li><a href="<c:url value='/'/>">home</a></li>
					<li class="active">커뮤니티</li>
					<li class="active"><c:if test="${param.btypeNo==3 }">QNA 게시판</c:if>

						<c:if test="${param.btypeNo==8 }">자유 게시판</c:if> <c:if
							test="${param.btypeNo==5 }">질문 게시판</c:if> <c:if
							test="${param.btypeNo==6 }">공유/정보 게시판</c:if> <c:if
							test="${param.btypeNo==7 }">개인의뢰 게시판</c:if></li>
				</ul>
			</div>
		</div>
		<!-- End of Breadcrumb -->

	</div>
</section>
<!-- =============== End of Page Header 1 Section =============== -->





<!-- ===== Start of Main Wrapper Section ===== -->
<section class="ptb80" id="post-job">
	<div class="container" style="border: solid 3px lightgray; padding: 20px;">

		<!-- Start of Post Resume Form -->
		<form action="<c:url value='/board/boardWrite'/>"
			class="post-job-resume mt50" style="margin-top: 0px;" method="post">

			<!-- Start of Resume Details -->
			<div class="row">
				<div class="col-md-12">


					<input type="hidden" name="btypeNo" id="btypeNo"
						value="${param.btypeNo }">
					<!-- Form Group -->
					<div class="col-md-12 blog-thumbnail"
						style="text-align: center; border: solid 3px lightgray; padding: 20px;
						margin-bottom: 10px;">
						<c:if test="${!empty AtcVo.bfileFilename }">
							<img alt=""
								src="<c:url value='/img_upload/${AtcVo.bfileFilename }'/>">
						</c:if>
					</div>
					
					<div class="form-group" style="float: left; width: 50%;height: 150px;">
						<h4>글 제목</h4>
						<h4>${vo.boardTitle }</h4>
					</div>
					<div class="form-group" style="float: right; margin-left: 300px; margin-bottom: 0px;">
						<p>작성일 <fmt:formatDate value="${vo.boardRegdate }" pattern="yyyy-MM-dd"/> </p>
					</div>
					<div class="form-group" style="float: right;">
						<p>조회수 ${vo.boardReadcount }</p>
					</div>
					
					<!-- Form Group -->
					<div class="form-group" style="float: left; margin-right: 500px;">
						<h4>글 내용</h4>
						<h4>${vo.boardContent }</h4>
					</div>

					<div class="form-group" style="float: left; margin-right: 500px;">
						<p>파일 이름 : ${AtcVo.bfileOriginname }</p>
					</div>
				</div>
			</div>
			<!-- End of Resume Details -->

		</form>
		<!-- End of Post Resume Form -->

	</div>
</section>
<!-- ===== End of Main Wrapper Section ===== -->


<%@ include file="../inc/footer.jsp"%>