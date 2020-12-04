package com.green.view.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.biz.member.MemberVO;
import com.green.biz.order.CartService;
import com.green.biz.order.CartVO;
import com.green.biz.order.OrderService;
import com.green.biz.order.OrderVO;

@Controller
public class MypageController {
	
	static final String INCOMPLETE_ORDER = "1";
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/cart_insert", method=RequestMethod.POST)
	public String cartInsert(CartVO vo, HttpSession session) {
		
		// 사용자가 로그인 되어 있는지 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());	// 사용자 ID를 카트정보에 설정
			cartService.insertCart(vo);
		
			return "redirect:cart_list";	// Controller로 "cart_list" 요청하는 문자열 반환
		}
	}
	
	@RequestMapping(value="/cart_list")
	public String cartList(CartVO vo, HttpSession session, Model model) {
		
		// 사용자가 로그인 되어 있는지 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			List<CartVO> cartList = cartService.listCart(vo);
			
			int totalPrice = 0;
			for (CartVO cartVO : cartList) {
				totalPrice += cartVO.getQuantity() * cartVO.getPrice2();
			}
		
			model.addAttribute("cartList", cartList);
			model.addAttribute("totalPrice", totalPrice);
		
			return "mypage/cartList";
		}
	}
	@RequestMapping(value="/cart_delete")
	public String cartDelete(@RequestParam(value="cseq") int[] cseq) {
		
		for (int i=0; i < cseq.length; i++) {
			cartService.deleteCart(cseq[i]);
		}
		
		return "redirect:cart_list";
	}
	
	@RequestMapping(value="/order_insert")
	public String orderInsert(OrderVO order, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			order.setId(loginUser.getId());
			
			int oseq = orderService.insertOrder(order);
			model.addAttribute("oseq", oseq);
			
			return "redirect:order_list";
		}
	}
	
	@RequestMapping(value="/order_list")
	public String orderList(HttpSession session, OrderVO vo, Model model) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			//vo.setOseq(vo.getOseq());
			vo.setResult("1");
			
			List<OrderVO> orderList = orderService.listOrderById(vo);
			
			int totalPrice = 0;
			for (OrderVO item : orderList) {
				totalPrice += (item.getQuantity() * item.getPrice2());
			}
			
			model.addAttribute("orderList", orderList);
			model.addAttribute("totalPrice", totalPrice);
			
			return "mypage/orderList";
		}
	}
	@RequestMapping(value="/mypage")
	public String myPageView(OrderVO orderVo, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			
			orderVo.setId(loginUser.getId());
			orderVo.setResult("1");
			
			List<Integer> oseqList = orderService.selectSeqOrdering(orderVo);
			
			// 사용자의 전체 주문 목록을 저장할 변수 생성
			List<OrderVO> orderList = new ArrayList<OrderVO>();
			
			for (int oseq : oseqList) {
				OrderVO order = new OrderVO();
				
				order.setId(loginUser.getId());
				order.setOseq(oseq);
				order.setResult(INCOMPLETE_ORDER);
				
				// 하나의 주문번호에 대한 주문내역 조회
				List<OrderVO> orderBySeq = orderService.listOrderById(order);
				
				OrderVO vo = new OrderVO();		// 각 주문별 주문내역을 요액해 저장하는 변수
				vo.setIndate(orderBySeq.get(0).getIndate());
				vo.setOseq(orderBySeq.get(0).getOseq());
				if (orderBySeq.size() > 1) {
				vo.setPname(orderBySeq.get(0).getPname() + " 외" + (orderBySeq.size() -1) + "건");
				} else {
					vo.setPname(orderBySeq.get(0).getPname());
				}
				int priceSum = 0;
				for (int i=0; i < orderBySeq.size(); i++) {
					priceSum += (orderBySeq.get(i).getQuantity() * orderBySeq.get(i).getPrice2());
				}
				vo.setPrice2(priceSum);
				
				orderList.add(vo);
			}
			model.addAttribute("orderList", orderList);
			model.addAttribute("title", "진행중인 주문 내역");
			
			return "mypage/mypage";
		}
	}
	
	@RequestMapping(value="order_detail")
	public String orderDetailView(OrderVO vo, Model model, HttpSession session) {
		
		// 사용자 로그인 확인
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			// oseq는 별도로 설정하지 않아도 됨(화면에서 입력되어짐)
			vo.setId(loginUser.getId());
			vo.setResult("");	// 주문 처리 중인 내역
			
			List<OrderVO> orderList = orderService.listOrderById(vo);
			
			// 주문자 정보를 조립
			OrderVO orderDetail = new OrderVO();
			
			orderDetail.setIndate(orderList.get(0).getIndate());
			orderDetail.setOseq(orderList.get(0).getOseq());
			orderDetail.setMname(orderList.get(0).getMname());
			orderDetail.setResult(orderList.get(0).getResult());
			
			int totalPrice = 0;
			for (int i=0; i < orderList.size(); i++) {
				totalPrice += (orderList.get(i).getPrice2() * orderList.get(i).getQuantity());
			}
			
			model.addAttribute("orderDetail", orderDetail);
			model.addAttribute("totalPrice", totalPrice);
			model.addAttribute("orderList", orderList);
			
			return "mypage/orderDetail";
		}
	}
	
	@RequestMapping(value="order_all")
	public String orderAll(Model model, HttpSession session) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			OrderVO orderVo = new OrderVO();
			orderVo.setId(loginUser.getId());
			orderVo.setResult("");
			
			List<Integer> oseqList = orderService.selectSeqOrdering(orderVo);
			
			// 주문내역 요약 목록 저장용 객체
			List<OrderVO> orderList = new ArrayList<OrderVO>();
			
			for (int oseq : oseqList) {
				OrderVO vo = new OrderVO();
				
				vo.setId(loginUser.getId());
				vo.setOseq(oseq);
				vo.setResult("");
				
				List<OrderVO> orderListing = orderService.listOrderById(vo);
				
				// 각 주문별 요약내용 처리
				OrderVO item = orderListing.get(0);
				item.setPname(item.getPname() + " 외" + (orderListing.size()-1) + "건");
				
				int totalPrice = 0;
				
				for (int i=0; i <orderListing.size(); i++) {
					totalPrice += (orderListing.get(i).getPrice2() * orderListing.get(i).getQuantity());
				}
				item.setPrice2(totalPrice);
				
				// 주문 요약 내용을 리스트에 저장
				orderList.add(item);
			}
			model.addAttribute("title", "총 주문 내역");
			model.addAttribute("orderList", orderList);
			
			return "mypage/mypage";
		}
	}
}







































