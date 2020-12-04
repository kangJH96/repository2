package com.green.biz.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.admin.AdminService;
import com.green.biz.admin.WorkerVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO adminDao;
	
	// 관리자 아이디의 존재여부 확인
	// 입력 매개변수(id:화면에서 입력한 관리자 id, pwd:화면에서 입력한 관리자 암호)
	@Override
	public int workerCheck(String id, String pwd) {
		
		int result = -1;
		
		// 테이블에서 조회한 관리자 암호
		String pwd_in_db = adminDao.workerCheck(id);
		
		if (pwd_in_db == null) {
			result = -1;
		} else {	// id를 조건으로 테이블에서 값이 조회됨
			if (pwd.equals(pwd_in_db)) {	// 화면에서 입력한 암호와 테이블의 암호가 일치
				result = 1;	// 정상적인 로그인
			} else {
				result = 0;	// 암호가 불일치
			}
		}
		return result;
	}

	// 관리자 정보 조회
	@Override
	public WorkerVO getEmployee(String id) {
		
		return adminDao.getEmployee(id);
	}

}
