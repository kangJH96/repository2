package com.green.biz.order;

import java.util.List;

public interface CartService {

	// 장바구니에 담기
	void insertCart(CartVO vo);

	// 장바구니 목록
	List<CartVO> listCart(CartVO vo);

	// 장바구니 삭제
	void deleteCart(int cseq);

	// 장바구니 내용의 주문 처리
	void updateCart(CartVO vo);

}