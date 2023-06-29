package br.com.datasource.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.datasource.domain.model.postgres.Department;
import br.com.datasource.domain.repository.postgres.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	public List<Department> findAll() {
		return departmentRepository.findAll();
	}
	
	public Department findById(Long id) {
		return departmentRepository.findById(id).get();
	}
	
	public Department save(Department department) {
		return departmentRepository.save(department);
	}
}
