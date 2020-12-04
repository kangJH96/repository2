package com.green.biz.order;

import java.sql.Timestamp;

import lombok.Data;

// 장바구니 정보
@Data
public class CartVO {
	
	private int cseq;			// 카트 일련번호
	private String id;			// 사용자 아이디
	private int pseq;			// 상품 일련번호
	private String mname;		// 회원명
	private String pname;		// 상품명
	private int quantity;		// 수량
	private int price2;			// 단가
	private Timestamp indate;	// 등록일자
}
