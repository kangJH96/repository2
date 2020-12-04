package com.green.biz.product.impl;

import java.util.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.product.SalesQuantity;
import com.green.biz.product.dto.ProductVO;
import com.green.biz.utils.Criteria;

@Repository
public class ProductDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public ProductVO getProduct(ProductVO vo) {
		
		return mybatis.selectOne("ProductDAO.getProduct", vo);
	}
	
	public List<ProductVO> getProductListByKind(ProductVO vo) {
	
		return mybatis.selectList("ProductDAO.getProductListByKind", vo);
	}
	
	public List<ProductVO> getNewProductList() {
	
		return mybatis.selectList("ProductDAO.getNewProductList");
	}
	
	public List<ProductVO> getBestProductList() {
	
		return mybatis.selectList("ProductDAO.getBestProductList");
	}
	
	public int countProductList(String name) {
		
		return mybatis.selectOne("ProductDAO.countProductList", name);
	}
	
	public List<ProductVO> getListProduct(String name) {
		
		return mybatis.selectList("ProductDAO.listProduct", name);
	}
	
	// 페이지별 상품 목록 조회
	public List<ProductVO> getListProductPaging(String name, Criteria cri) {
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("criteria", cri);
		
		return mybatis.selectList("ProductDAO.listWithPaging", map);
	}
	
	public void insertProduct(ProductVO vo) {
		
		mybatis.insert("ProductDAO.insertProduct", vo);
	}
	
	public void updateProduct(ProductVO vo) {
		
		mybatis.update("ProductDAO.updateProduct", vo);
	}
	
	// 상품 판매 실적 조회
	public List<SalesQuantity> getProductSales() {
		
		return mybatis.selectList("ProductDAO.getProductSales");
	}
}





































