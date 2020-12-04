package com.green.biz.order;

import java.util.List;

public interface OrderService {

	int selectMaxOseq();

	int insertOrder(OrderVO vo);

	void insertOrderDetail(OrderVO vo);

	List<OrderVO> listOrderById(OrderVO vo);

	List<Integer> selectSeqOrdering(OrderVO vo);

	// 전체 주문 내역 조회
	public List<OrderVO> listOrder(String key);
			
	// 주문처리 완료 수정
	public void updateOrder(int odseq); 
}