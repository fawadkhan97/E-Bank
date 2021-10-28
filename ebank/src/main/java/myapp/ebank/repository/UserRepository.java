package myapp.ebank.repository;


import myapp.ebank.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByUserNameAndPassword(String userName, String Password);

	Optional<Users> findByIdAndEmailTokenAndSmsToken(Long id, int emailToken, int smsToken);

	List<Users> findAllByisActive(boolean status);

}