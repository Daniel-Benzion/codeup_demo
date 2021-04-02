package com.codeup.codeup_demo.repo;

import com.codeup.codeup_demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	List<User> findByIdEquals(long id);
}
