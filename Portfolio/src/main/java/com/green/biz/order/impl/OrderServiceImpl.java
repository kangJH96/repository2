package com.green.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.order.CartService;
import com.green.biz.order.CartVO;
import com.green.biz.order.OrderService;
import com.green.biz.order.OrderVO;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private CartService cartService;
	
	@Override
	public int selectMaxOseq() {
		
		return orderDao.selectMaxOseq();
	}

	@Override
	public int insertOrder(OrderVO vo) {
		// 주문번호 생성
		int oseq = selectMaxOseq();
		
		// 주문번호를 vo객체에 설정
		vo.setOseq(oseq);
		
		// 주문테이블에 주문을번호를 저장
		orderDao.insertOrder(vo);
		
		// 장바구니에서 주문 상세목록을 조회
		CartVO cartVO = new CartVO();
		cartVO.setId(vo.getId());
		List<CartVO> listCart = cartService.listCart(cartVO);
		
		// 장바구니의 목록을 주문상세 테이블에 저장
		for (CartVO item : listCart) {
			OrderVO order = new OrderVO();
			
			order.setOseq(oseq);
			order.setPseq(item.getPseq());
			order.setQuantity(item.getQuantity());
			
			insertOrderDetail(order);
			
			// cart테이블에 주문처리 결과를 수정
			cartService.updateCart(item);
		}
		return oseq;
	}

	@Override
	public void insertOrderDetail(OrderVO vo) {
		
		orderDao.insertOrderDetail(vo);
	}

	@Override
	public List<OrderVO> listOrderById(OrderVO vo) {
		
		return orderDao.listOrderById(vo);
	}

	@Override
	public List<Integer> selectSeqOrdering(OrderVO vo) {
		
		return orderDao.selectSeqOrdering(vo);
	}

	@Override
	public List<OrderVO> listOrder(String key) {
		
		return orderDao.listOrder(key);
	}

	@Override
	public void updateOrder(int odseq) {
		
		orderDao.updateOrder(odseq);
	}

}






































