package com.core.service.demo.impl;

import com.core.dao.demo.UserDao;
import com.core.model.demo.UserDomain;
import com.core.service.demo.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by WJ on 2018/5/3
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    public List<UserDomain> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> list=userDao.selectUsers();
       
        return list;
    }


	@Override
	 @Transactional
	public void add() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", "2");
		map.put("user_name", "2");
		map.put("password", "2");
		map.put("phone", "2");
	    userDao.add(map);
	    map.put("user_id", "3");
		map.put("user_name", "3");
		map.put("password", "3");
		map.put("phone", "3");
	    userDao.add(map);
	    
	    
	   
	}
}
