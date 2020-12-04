package com.green.biz.member;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberVO {
	
	private String id;
	private String pwd;
	private String name;
	private String email;
	private String zip_num;
	private String address;
	private String phone;
	private String useyn;
	private Timestamp regdate;
}
