package br.com.datasource.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.datasource.domain.model.mysql.User;
import br.com.datasource.domain.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping	
	public ResponseEntity<List<User>> findAll() {
		List<User> users = userService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User department = userService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(department);
	}
	
	@PostMapping
	public ResponseEntity<User> save(@RequestBody User department) {
		userService.save(department);
		return ResponseEntity.status(HttpStatus.CREATED).body(department);
	}
}
