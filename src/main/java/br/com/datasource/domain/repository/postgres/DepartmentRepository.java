package br.com.datasource.domain.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.datasource.domain.model.postgres.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
