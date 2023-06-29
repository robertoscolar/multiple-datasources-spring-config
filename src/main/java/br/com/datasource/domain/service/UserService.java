package br.com.datasource.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.datasource.domain.model.mysql.User;
import br.com.datasource.domain.repository.mysql.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
}
