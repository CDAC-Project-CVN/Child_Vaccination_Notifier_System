package com.cvn.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvn.user.entity.Parent;
import com.cvn.user.entity.User;

public interface ParentRepository extends JpaRepository<Parent, Long> {

	Optional<Parent> findByMyUser(User user);

}
