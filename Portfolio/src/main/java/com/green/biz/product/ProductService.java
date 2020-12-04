package com.green.biz.product;

import java.util.List;

import com.green.biz.product.dto.ProductVO;
import com.green.biz.utils.Criteria;

public interface ProductService {

	ProductVO getProduct(ProductVO vo);

	List<ProductVO> getProductListByKind(ProductVO vo);

	List<ProductVO> getNewProductList();

	List<ProductVO> getBestProductList();

	public int countProductList(String name);
	
	public List<ProductVO> getListProduct(String name);
	
	public void insertProduct(ProductVO vo);
	
	public void updateProduct(ProductVO vo);
	
	public List<ProductVO> getListProductPaging(String name, Criteria cri);
	
	public List<SalesQuantity> getProductSales();
	
}