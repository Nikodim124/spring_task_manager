package com.taskmanager.Repository;

import com.taskmanager.Model.Times;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends JpaRepository<Times, Long> {
}
