package com.green.biz.admin;

public interface AdminService {

	int workerCheck(String id, String pwd);

	WorkerVO getEmployee(String id);

}