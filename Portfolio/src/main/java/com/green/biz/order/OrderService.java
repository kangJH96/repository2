package com.green.biz.order;

import java.util.List;

public interface OrderService {

	int selectMaxOseq();

	int insertOrder(OrderVO vo);

	void insertOrderDetail(OrderVO vo);

	List<OrderVO> listOrderById(OrderVO vo);

	List<Integer> selectSeqOrdering(OrderVO vo);

	// ��ü �ֹ� ���� ��ȸ
	public List<OrderVO> listOrder(String key);
			
	// �ֹ�ó�� �Ϸ� ����
	public void updateOrder(int odseq); 
}