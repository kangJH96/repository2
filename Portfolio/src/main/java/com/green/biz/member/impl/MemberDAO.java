package com.green.biz.member.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.member.AddressVO;
import com.green.biz.member.MemberVO;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// id�� �����ϴ��� Ȯ��
	public MemberVO findMember(String id) {
		
		return mybatis.selectOne("MemberDAO.findMember", id);
	}
	
	// idȭ pwd�� �̿��� �α���
	public MemberVO getMember(MemberVO vo) {
		
		return mybatis.selectOne("MemberDAO.getMember", vo);
	}
	
	// 
	public void insertMember(MemberVO vo) {
		
		mybatis.insert("MemberDAO.insertMember", vo);
	}
	
	// �� �̸��� �������� �ּ� �˻�
	public List<AddressVO> selectAddressByDong(AddressVO vo) {
		
		return mybatis.selectList("MemberDAO.selectAddressByDong", vo);
	}
	
	// ����ڸ�� �̸��Ϸ� ����� ��ȸ
	public MemberVO getMemberByNameAndEmail(MemberVO vo) {
		
		return mybatis.selectOne("MemberDAO.getMemberByNameAndEmail", vo);
	}
	
	// ���̵�, ����ڸ�, �̸��Ϸ� ��й�ȣ ã��
	public MemberVO findPassword(MemberVO vo) {
		
		return mybatis.selectOne("MemberDAO.findPassword", vo);
	}
	
	// ����� ��к�ȣ ����
	public void changePassword(MemberVO vo) {
		
		mybatis.update("MemberDAO.changePassword", vo);
	}
	
	// ��ü ȸ�� ��ȸ
	public List<MemberVO> listMember(String key) {
		
		return mybatis.selectList("MemberDAO.listMember", key);
	}
}





































