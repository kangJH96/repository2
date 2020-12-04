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
	
	// id가 존재하는지 확인
	public MemberVO findMember(String id) {
		
		return mybatis.selectOne("MemberDAO.findMember", id);
	}
	
	// id화 pwd를 이용한 로그인
	public MemberVO getMember(MemberVO vo) {
		
		return mybatis.selectOne("MemberDAO.getMember", vo);
	}
	
	// 
	public void insertMember(MemberVO vo) {
		
		mybatis.insert("MemberDAO.insertMember", vo);
	}
	
	// 동 이름을 조건으로 주소 검색
	public List<AddressVO> selectAddressByDong(AddressVO vo) {
		
		return mybatis.selectList("MemberDAO.selectAddressByDong", vo);
	}
	
	// 사용자명과 이메일로 사용자 조회
	public MemberVO getMemberByNameAndEmail(MemberVO vo) {
		
		return mybatis.selectOne("MemberDAO.getMemberByNameAndEmail", vo);
	}
	
	// 아이디, 사용자명, 이메일로 비밀번호 찾기
	public MemberVO findPassword(MemberVO vo) {
		
		return mybatis.selectOne("MemberDAO.findPassword", vo);
	}
	
	// 사용자 비밀변호 변경
	public void changePassword(MemberVO vo) {
		
		mybatis.update("MemberDAO.changePassword", vo);
	}
	
	// 전체 회원 조회
	public List<MemberVO> listMember(String key) {
		
		return mybatis.selectList("MemberDAO.listMember", key);
	}
}





































