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
		
		// ����ڰ� �α��� �Ǿ� �ִ��� Ȯ��
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			vo.setId(loginUser.getId());	// ����� ID�� īƮ������ ����
			cartService.insertCart(vo);
		
			return "redirect:cart_list";	// Controller�� "cart_list" ��û�ϴ� ���ڿ� ��ȯ
		}
	}
	
	@RequestMapping(value="/cart_list")
	public String cartList(CartVO vo, HttpSession session, Model model) {
		
		// ����ڰ� �α��� �Ǿ� �ִ��� Ȯ��
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
			
			// ������� ��ü �ֹ� ����� ������ ���� ����
			List<OrderVO> orderList = new ArrayList<OrderVO>();
			
			for (int oseq : oseqList) {
				OrderVO order = new OrderVO();
				
				order.setId(loginUser.getId());
				order.setOseq(oseq);
				order.setResult(INCOMPLETE_ORDER);
				
				// �ϳ��� �ֹ���ȣ�� ���� �ֹ����� ��ȸ
				List<OrderVO> orderBySeq = orderService.listOrderById(order);
				
				OrderVO vo = new OrderVO();		// �� �ֹ��� �ֹ������� ����� �����ϴ� ����
				vo.setIndate(orderBySeq.get(0).getIndate());
				vo.setOseq(orderBySeq.get(0).getOseq());
				if (orderBySeq.size() > 1) {
				vo.setPname(orderBySeq.get(0).getPname() + " ��" + (orderBySeq.size() -1) + "��");
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
			model.addAttribute("title", "�������� �ֹ� ����");
			
			return "mypage/mypage";
		}
	}
	
	@RequestMapping(value="order_detail")
	public String orderDetailView(OrderVO vo, Model model, HttpSession session) {
		
		// ����� �α��� Ȯ��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "member/login";
		} else {
			// oseq�� ������ �������� �ʾƵ� ��(ȭ�鿡�� �ԷµǾ���)
			vo.setId(loginUser.getId());
			vo.setResult("");	// �ֹ� ó�� ���� ����
			
			List<OrderVO> orderList = orderService.listOrderById(vo);
			
			// �ֹ��� ������ ����
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
			
			// �ֹ����� ��� ��� ����� ��ü
			List<OrderVO> orderList = new ArrayList<OrderVO>();
			
			for (int oseq : oseqList) {
				OrderVO vo = new OrderVO();
				
				vo.setId(loginUser.getId());
				vo.setOseq(oseq);
				vo.setResult("");
				
				List<OrderVO> orderListing = orderService.listOrderById(vo);
				
				// �� �ֹ��� ��೻�� ó��
				OrderVO item = orderListing.get(0);
				item.setPname(item.getPname() + " ��" + (orderListing.size()-1) + "��");
				
				int totalPrice = 0;
				
				for (int i=0; i <orderListing.size(); i++) {
					totalPrice += (orderListing.get(i).getPrice2() * orderListing.get(i).getQuantity());
				}
				item.setPrice2(totalPrice);
				
				// �ֹ� ��� ������ ����Ʈ�� ����
				orderList.add(item);
			}
			model.addAttribute("title", "�� �ֹ� ����");
			model.addAttribute("orderList", orderList);
			
			return "mypage/mypage";
		}
	}
}







































