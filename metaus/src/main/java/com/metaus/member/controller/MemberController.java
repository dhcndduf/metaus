package com.metaus.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metaus.member.model.KakaoService;
import com.metaus.member.model.KakaoVO;
import com.metaus.member.model.MemberService;
import com.metaus.member.model.MemberVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private final MemberService memberService;
	private final KakaoService kakaoService;
	
	@GetMapping("/register")
	public void register_get() {
		logger.info("회원가입 뷰");
	}
	
	@PostMapping("/memberRegister")
	public String memregister_post(@ModelAttribute MemberVO vo, Model model) {
		logger.info("일반회원 회원가입 처리, 파라미터 vo={}", vo);
		
		int cnt=memberService.insertMember(vo);
		logger.info("회원가입 결과, cnt={}", cnt);

		String msg="회원가입 실패", url="/member/register";
		if(cnt>0) {
			msg="회원가입되었습니다.";
			url="/";
		}

		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		return "/common/message";
		
	}
	
	@RequestMapping("/socialMerge")
	public String socialMerge(@RequestParam String memId, @RequestParam String socialEmail,
							@RequestParam String socialName, @RequestParam String socialType, Model model) {
		logger.info("socialMerge 파라미터 memId={}, socialEmail={}, socialName={}, socialType={}", memId,socialEmail,socialName,socialType);


		model.addAttribute("memId", memId);
		model.addAttribute("socialEmail", socialEmail);
		model.addAttribute("socialName", socialName);
		model.addAttribute("socialType", socialType);

		return "/member/socialMerge";
	}
	@RequestMapping("/kakaoMerge")
	public String kakaoMerge(@RequestParam String memId, @RequestParam String socialEmail,
			@RequestParam String socialName, @RequestParam String socialType,@RequestParam String memPw, Model model,HttpServletRequest request) {
		logger.info("kakaoMerge 파라미터 memId={}, socialEmail={}, socialName={}, socialType={}, memPw={}", memId,socialEmail,socialName,socialType,memPw);
		
		int result=memberService.checkLogin(memId, memPw);
		logger.info("로그인 처리 결과 result={}", result);
		model.addAttribute("memId", memId);
		model.addAttribute("socialEmail", socialEmail);
		model.addAttribute("socialName", socialName);
		model.addAttribute("socialType", socialType);
		
		String msg="로그인 처리 실패", url="/member/socialMerge";
		if(result==MemberService.LOGIN_OK) {
			//회원정보 조회
			MemberVO memVo=memberService.selectByUserid(memId);
			logger.info("로그인 처리-회원정보 조회결과 memVo={}", memVo);
			
			KakaoVO kakaoVo = new KakaoVO();
			kakaoVo.setMemNo(memVo.getMemNo());
			kakaoVo.setKakaoEmail(socialEmail);
			kakaoVo.setKakaoName(socialName);
			kakaoService.insertMember(kakaoVo);
			
			
			//[1] session에 저장

			HttpSession session=request.getSession();
			session.setAttribute("isLogin", "kakao");
			session.setAttribute("memNo", memVo.getMemNo());
			session.setAttribute("memId", memVo.getMemId());
			session.setAttribute("memName", memVo.getMemName());
			
			msg=memVo.getMemName() +"님 로그인되었습니다.";
			url="/";
		}else if(result==MemberService.DISAGREE_PWD) {
			msg="비밀번호가 일치하지 않습니다.";
			return "/member/socialMerge";
		}else if(result==MemberService.NONE_USERID) {
			msg="해당 아이디가 존재하지 않습니다.";		
			return "/member/socialMerge";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/common/message";
	}

}
