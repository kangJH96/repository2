package com.green.biz.member;

import java.util.List;

public interface MemberService {

	// id가 존재하는지 확인
	MemberVO findMember(String id);

	// id와 pwd를 이용한 로그인
	MemberVO getMember(MemberVO vo);

	void insertMember(MemberVO vo);
	
	public List<AddressVO> selectAddressByDong(AddressVO vo);
	
	public MemberVO getMemberByNameAndEmail(MemberVO vo);
	
	public MemberVO findPassword(MemberVO vo);
	
	public void changePassword(MemberVO vo);
	
	public List<MemberVO> listMember(String key);
}