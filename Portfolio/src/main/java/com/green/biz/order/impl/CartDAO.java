package com.green.biz.order.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.order.CartVO;

@Repository
public class CartDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 장바구니에 담기
	public void insertCart(CartVO vo) {
		
		mybatis.insert("CartDAO.insertCart", vo);
	}
	
	// 장바구니 목록
	public List<CartVO> listCart(CartVO vo) {
		
		return mybatis.selectList("CartDAO.listCart", vo);
	}
	
	// 장바구니 삭제
	public void deleteCart(int cseq) {
		
		mybatis.delete("CartDAO.deleteCart", cseq);
	}
	
	// 장바구니 내용의 주문 처리
	public void updateCart(CartVO vo) {
		
		mybatis.update("CartDAO.updateCart", vo);
	}
}
