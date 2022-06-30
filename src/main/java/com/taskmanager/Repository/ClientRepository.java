package com.taskmanager.Repository;

import com.taskmanager.Model.Client;
import com.taskmanager.Model.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
