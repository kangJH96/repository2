package com.green.biz.order;

import java.sql.Timestamp;

import lombok.Data;

// ��ٱ��� ����
@Data
public class CartVO {
	
	private int cseq;			// īƮ �Ϸù�ȣ
	private String id;			// ����� ���̵�
	private int pseq;			// ��ǰ �Ϸù�ȣ
	private String mname;		// ȸ����
	private String pname;		// ��ǰ��
	private int quantity;		// ����
	private int price2;			// �ܰ�
	private Timestamp indate;	// �������
}
