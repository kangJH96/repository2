package com.green.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.order.CartService;
import com.green.biz.order.CartVO;

@Service("cartService")
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDAO cartDao;
	
	@Override
	public void insertCart(CartVO vo) {
		
		cartDao.insertCart(vo);
	}

	@Override
	public List<CartVO> listCart(CartVO vo) {
		
		return cartDao.listCart(vo);
	}

	@Override
	public void deleteCart(int cseq) {
		
		cartDao.deleteCart(cseq);
	}

	@Override
	public void updateCart(CartVO vo) {
		
		cartDao.updateCart(vo);
	}

}
