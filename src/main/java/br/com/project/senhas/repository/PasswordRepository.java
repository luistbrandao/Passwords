package br.com.project.senhas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.project.senhas.model.Password;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
	
//	@Query(value = "select * from password where url")
	public Password findByUrl(String url);

}
