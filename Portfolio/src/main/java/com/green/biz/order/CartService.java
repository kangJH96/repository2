package com.green.biz.order;

import java.util.List;

public interface CartService {

	// ��ٱ��Ͽ� ���
	void insertCart(CartVO vo);

	// ��ٱ��� ���
	List<CartVO> listCart(CartVO vo);

	// ��ٱ��� ����
	void deleteCart(int cseq);

	// ��ٱ��� ������ �ֹ� ó��
	void updateCart(CartVO vo);

}