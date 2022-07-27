# 의뢰 목록 주요 기능
- 1. 페이징 처리


***

# 1. 페이징 처리
![리스트]![의뢰목록페이지](https://user-images.githubusercontent.com/107232180/181196959-6051c444-ce8a-4ce5-a42a-18238228c833.png)
   

- 의뢰 지원한 기업만 노출
- 매퍼 sql문   
  -페이징 처리 상수를 MypageVO에 파라미터로 담는다   

```html
<select id="selectAll" parameterType="int" resultType="MypageVO">
	select * from
		(
			select rownum as RNUM, R.*
			from
				(	
		
		select A.recpre_no,A.mem_no,A.recpre_date,A.recpre_content, B.rec_no,B.rec_title,B.rec_content,B.rec_content2,B.rec_content3,
		B.rec_web,B.rec_twitter,B.rec_pay,B.rec_regdate,B.rec_readcount,
		C.com_no,C.com_id,C.com_pw,C.com_name,C.com_preview,C.com_pic,C.com_ceo,C.com_add,C.com_lati,C.com_longi,C.com_rrn,
		C.com_reccnt,C.com_tel,C.com_joindate,C.com_outdate,
		(select count(*) from fp_recpre D where B.rec_no = D.rec_no) rec_count  
		from fp_recpre A join fp_rec B 
		on A.rec_no = B.rec_no 
		join fp_com C 
		on B.com_no = C.com_no 
		where A.mem_no = #{memNo} and B.rec_no not in (select rec_no from fp_contact)
		order by A.rec_no desc
		)R
		)
		where  
		RNUM>#{firstRecordIndex} 
		<![CDATA[	
		and RNUM<=#{firstRecordIndex} + #{recordCountPerPage} ]]>
 	</select>
 	
 	<select id="getTotalRecord" resultType="int" parameterType="int">
 			
		select count(*) from fp_recpre  where mem_no = #{memNo} and rec_no not in (select rec_no from fp_contact)
	
	</select>
```

- 뷰페이지에서 페이지 이동 시 form   

```javascript		
 <form name="frmPage" method="post"
		action="<c:url value='/commission/commissionList'/>">
					<input type="hidden" name="currentPage">
				</form>
    
    //페이지 버튼 클릭시 현재 페이지를 담아 유지
		<input type="hidden" name="currentPage">
</form>
    
```
- 컨트롤러   

```java
	@RequestMapping("/commissionList")
	public String commissionList(@ModelAttribute MypageVO searchVo,HttpSession session,Model model) {
		logger.info("의뢰 목록 페이지");

		int memNo = (int)session.getAttribute("memNo");
		logger.info("memNo={}",memNo);
		
		
		
		
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(ConstUtil.BLOCKSIZE);
		pagingInfo.setBlockSize(ConstUtil.BLOCKSIZE);
		pagingInfo.setRecordCountPerPage(ConstUtil.COMMISSION_RECORD_COUNT);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		logger.info("t ={}",pagingInfo.getCurrentPage());
		
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		logger.info("t2={}",pagingInfo.getFirstRecordIndex());
		logger.info("t3={}",searchVo.getFirstRecordIndex());
		searchVo.setRecordCountPerPage(ConstUtil.COMMISSION_RECORD_COUNT);
		searchVo.setMemNo(memNo);
		
		List<MypageVO> list=mypageService.selectAll(searchVo);
		logger.info("list={}",list);
		int totalRecord = mypageService.getTotalRecord(memNo);
		pagingInfo.setTotalRecord(totalRecord);
		
		logger.info("목록 조회-레코드 개수, totalRecord={}", totalRecord);
		logger.info("목록 조회-pagingInfo, pagingInfo.getFirstPage={}", pagingInfo.getFirstPage());
		logger.info("목록 조회-pagingInfo, pagingInfo.getLastPage={}", pagingInfo.getLastPage());
		
		model.addAttribute("searchVo",searchVo);
		model.addAttribute("pagingInfo", pagingInfo);
		model.addAttribute("list",list);
		
		return "/commission/commissionList";
	}
```


***



