package com.cvn.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvn.user.entity.Child;
import com.cvn.user.entity.Parent;

public interface ChildRepository extends JpaRepository<Child, Long> {
	List<Child> findByMyParent(Parent parent);
}
