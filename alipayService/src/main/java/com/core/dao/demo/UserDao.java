package com.core.dao.demo;



import java.util.HashMap;
import java.util.List;

import com.core.model.demo.UserDomain;


public interface UserDao {


    List<UserDomain> selectUsers();

	void add(HashMap<String, String> map);
	void add1(HashMap<String, String> map);
}