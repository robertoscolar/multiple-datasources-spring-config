package br.com.datasource.domain.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.datasource.domain.model.mysql.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
