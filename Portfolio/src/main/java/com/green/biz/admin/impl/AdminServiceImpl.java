package com.green.biz.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.admin.AdminService;
import com.green.biz.admin.WorkerVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO adminDao;
	
	// ������ ���̵��� ���翩�� Ȯ��
	// �Է� �Ű�����(id:ȭ�鿡�� �Է��� ������ id, pwd:ȭ�鿡�� �Է��� ������ ��ȣ)
	@Override
	public int workerCheck(String id, String pwd) {
		
		int result = -1;
		
		// ���̺��� ��ȸ�� ������ ��ȣ
		String pwd_in_db = adminDao.workerCheck(id);
		
		if (pwd_in_db == null) {
			result = -1;
		} else {	// id�� �������� ���̺��� ���� ��ȸ��
			if (pwd.equals(pwd_in_db)) {	// ȭ�鿡�� �Է��� ��ȣ�� ���̺��� ��ȣ�� ��ġ
				result = 1;	// �������� �α���
			} else {
				result = 0;	// ��ȣ�� ����ġ
			}
		}
		return result;
	}

	// ������ ���� ��ȸ
	@Override
	public WorkerVO getEmployee(String id) {
		
		return adminDao.getEmployee(id);
	}

}
