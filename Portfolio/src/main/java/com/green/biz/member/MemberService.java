package com.green.biz.member;

import java.util.List;

public interface MemberService {

	// id�� �����ϴ��� Ȯ��
	MemberVO findMember(String id);

	// id�� pwd�� �̿��� �α���
	MemberVO getMember(MemberVO vo);

	void insertMember(MemberVO vo);
	
	public List<AddressVO> selectAddressByDong(AddressVO vo);
	
	public MemberVO getMemberByNameAndEmail(MemberVO vo);
	
	public MemberVO findPassword(MemberVO vo);
	
	public void changePassword(MemberVO vo);
	
	public List<MemberVO> listMember(String key);
}