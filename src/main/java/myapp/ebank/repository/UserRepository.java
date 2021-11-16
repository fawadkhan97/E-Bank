package myapp.ebank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myapp.ebank.model.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String userName);

    Optional<Users> findByIdAndToken(Long id, int token);

    Optional<Users> findByIdAndIsActive(Long id,Boolean isActive);

    List<Users> findAllByIsActiveOrderByCreatedDateDesc(boolean status);


}
