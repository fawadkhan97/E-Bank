package myapp.ebank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myapp.ebank.model.Users;

@Repository

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserNameAndPassword(String userName, String Password);

    Optional<Users> findByIdAndSmsToken(Long id, int smsToken);

    List<Users> findAllByisActive(boolean status);

}
