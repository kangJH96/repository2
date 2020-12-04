package com.green.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.green.biz.member.AddressVO;
import com.green.biz.member.MemberService;
import com.green.biz.member.MemberVO;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	// �α��� ȭ�� ǥ��
	@RequestMapping(value="/login_form", method=RequestMethod.GET)
	public String loginView() {
		
		return "member/login";
	}
	
	// �Ϲ� ����� �α��� ó��
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginAction(MemberVO vo, Model model) {
		
		MemberVO loginUser = memberService.getMember(vo);
		
		if (loginUser != null) {	// ����ڰ� ����
			model.addAttribute("loginUser", loginUser);	// request ������ ��������� ����
		
			return "redirect:/index";
		} else {
			return "member/login_fail";
		}
	}
	
	// �α׾ƿ� ó��
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutAction(SessionStatus status) {
		
		status.setComplete();
		
		return "redirect:login_form";
	}
	
	// ��� ȭ�� ǥ��
	@RequestMapping(value="/contract", method=RequestMethod.GET)
	public String contractView() {
		
		return "member/contract";
	}
	// ȸ������ ȭ�� ǥ��
	@RequestMapping(value="/join_form", method=RequestMethod.POST)
	public String joinView() {
		
		return "member/join";
	}
	
	// �α��� ȭ���� Join us ����޴� ó��
	@RequestMapping(value="/join_form", method=RequestMethod.GET)
	public String joinForm() {
		
		return "member/contract";
	}
	
	// ���̵� �ߺ�üũ ȭ��ǥ��
	@RequestMapping(value="/id_check_form", method=RequestMethod.GET)
	public String idCheckView(@RequestParam(value="id", defaultValue="", required=false) String id, Model model) {
		
		model.addAttribute("id", id);
		
		return "member/idcheck";
	}
	// �ߺ��� ���̵����� member���̺��� ��ȸ�Ͽ� Ȯ��
	@RequestMapping(value="/id_check_form", method=RequestMethod.POST)
	public String idCheckAction(@RequestParam(value="id", defaultValue="", required=false) String id, Model model) {
		
		MemberVO user = memberService.findMember(id);
		
		if (user != null) {	// ����ڰ� �̹� ����
			model.addAttribute("message", 1);
		} else {
			model.addAttribute("message", -1);
		}
		
		model.addAttribute("id", id);
		
		return "member/idcheck";
	}
	// ȸ������ ó��
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinAction(@RequestParam(value="addr1") String addr1,
							 @RequestParam(value="addr2") String addr2, MemberVO vo) {
		
		vo.setAddress(addr1 + " " + addr2);
		System.out.println("����� ����=" + vo);
		
		memberService.insertMember(vo);
		
		return "member/login";
	}
	// �ּ� ã�� ȭ�� ǥ��
	@RequestMapping(value="find_zip_num", method=RequestMethod.GET)
	public String findZipNumView() {
		
		return "member/findZipNum";
	}
	// �� �̸����� �ּҸ� ��ȸ�Ͽ� �ּ� ã�� ȭ�鿡 ǥ��
	@RequestMapping(value="find_zip_num", method=RequestMethod.POST)
	public String findZipNumAction(AddressVO vo, Model model) {
		
		List<AddressVO> addrList = memberService.selectAddressByDong(vo);
		
		model.addAttribute("addressList", addrList);
		
		return "member/findZipNum";
	}
	
	// ���̵�, ��й�ȣ ã�� ȭ�� ǥ��
	@RequestMapping(value="find_id_form", method=RequestMethod.GET)
	public String findIdView() {
		
		return "member/findIdAndPassword";
	}
	
	// ���̵� ã�� ó��
	@RequestMapping(value="find_id", method=RequestMethod.GET)
	public String findIdAction(MemberVO vo, Model model) {
		
		MemberVO member = memberService.getMemberByNameAndEmail(vo);
		
		if (member!= null) {	// �����Ͱ� �����ϴ� ���
			model.addAttribute("message", "1");
			model.addAttribute("id", member.getId());
		} else {
			model.addAttribute("message", "-1");
		}
		return "member/findResult";
	}
	
	// ��й�ȣ ã��
	@RequestMapping(value="find_password", method=RequestMethod.GET)
	public String findPassword(MemberVO vo, Model model) {
		
		MemberVO member = memberService.findPassword(vo);
		
		if (member!= null) {	// �����Ͱ� �����ϴ� ���
			model.addAttribute("message", "1");
			model.addAttribute("id", member.getId());
		} else {
			model.addAttribute("message", "-1");
		}
		return "member/findPwdResult";
	}
	
	// ��й�ȣ ����
	@RequestMapping(value="change_password", method=RequestMethod.POST)
	public String changePassword(MemberVO vo) {
		
		System.out.println("��ȣ�� ���������� ����Ǿ����ϴ�!");
		
		memberService.changePassword(vo);
		
		return "member/close";
	}
}





































