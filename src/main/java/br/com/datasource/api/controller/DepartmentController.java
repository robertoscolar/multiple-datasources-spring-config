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

import br.com.datasource.domain.model.postgres.Department;
import br.com.datasource.domain.service.DepartmentService;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;

	@GetMapping	
	public ResponseEntity<List<Department>> findAll() {
		List<Department> departments = departmentService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(departments);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Department> findById(@PathVariable Long id) {
		Department department = departmentService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(department);
	}
	
	@PostMapping
	public ResponseEntity<Department> save(@RequestBody Department department) {
		departmentService.save(department);
		return ResponseEntity.status(HttpStatus.CREATED).body(department);
	}
}
