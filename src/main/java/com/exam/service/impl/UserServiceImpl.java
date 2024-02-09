package com.exam.service.impl;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	//creating user
	@Override
	public User createUser(User user, Set<UserRole> userRoles) {
		User local = this.userRepository.findByUserName(user.getUserName());

		if (local != null) {
			System.out.println("User is already present");
			try {
				throw new Exception("User already Present");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			//user create
			for(UserRole ur :userRoles) 
			{
				roleRepository.save(ur.getRole());
			}
			user.getUserRole().addAll(userRoles);
			local = this.userRepository.save(user);
		}
		
		return local;
	}

	//getting user by username
	@Override
	public User getUser(String username) {
		return this.userRepository.findByUserName(username);
	}

	@Override
	public void deleteUser(Long userId) {
	   this.userRepository.deleteById(userId);
		
	}

	@Override
	public User getUser(Long userId) {
		return this.userRepository.getById(userId);
	}


}
