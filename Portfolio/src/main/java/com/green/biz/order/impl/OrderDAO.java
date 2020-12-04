package com.green.biz.order.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.order.OrderVO;

@Repository
public class OrderDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int selectMaxOseq() {
		
		return mybatis.selectOne("OrderDAO.selectMaxOseq");
	}
	
	public void insertOrder(OrderVO vo) {
		
		mybatis.insert("OrderDAO.insertOrder", vo);
	}
	
	public void insertOrderDetail(OrderVO vo) {
		
		mybatis.insert("OrderDAO.insertOrderDetail", vo);	
	}
	
	public List<OrderVO> listOrderById(OrderVO vo) {
		
		return mybatis.selectList("OrderDAO.listOrderById", vo);
	}
	
	public List<Integer> selectSeqOrdering(OrderVO vo) {
		
		return mybatis.selectList("OrderDAO.selectSeqOrdering", vo);
	}
	
	// 전체 주문 내역 조회
	public List<OrderVO> listOrder(String key) {
		
		return mybatis.selectList("OrderDAO.listOrder", key);
	}
	
	// 주문처리 완료 수정
	public void updateOrder(int odseq) {
		
		mybatis.update("OrderDAO.updateOrder", odseq);
	}
}






































