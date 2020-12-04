package com.green.view.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.biz.member.MemberVO;
import com.green.biz.qna.QnaService;
import com.green.biz.qna.QnaVO;

@Controller
public class QnaController {

	@Autowired
	private QnaService qnaService;
	
	@RequestMapping(value="qna_list")
	public String qnaList(HttpSession session, Model model) {
		
		// 사용자 로그인 확인
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			List<QnaVO> qnaList = qnaService.listQna(loginUser.getId());
			
			model.addAttribute("qnaList", qnaList);
		
			return "qna/qnaList";
		}
	}
	
	@RequestMapping(value="qna_view") 
	public String QnaView(@RequestParam(value="qseq") int qseq, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			QnaVO qnaVo = qnaService.getQna(qseq);
			
			model.addAttribute("qnaVo", qnaVo);
			
			return "qna/qnaView";
		}
	}
	
	@RequestMapping(value="qna_write_form")
	public String qnaWriteView(HttpSession session) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			
			return "qna/qnaWrite";
		}
	}
	
	@RequestMapping(value="qna_write")
	public String qnaWrite(QnaVO vo, HttpSession session) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			
			qnaService.insertQna(vo);
			
			return "redirect:qna_list";
		}
	}
}































































