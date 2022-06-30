package com.taskmanager.Repository;

import com.taskmanager.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value="select count(a.id)\n" +
            "from public.employees e\n" +
            "left join public.appeals a\n" +
            "on e.id=a.employee_id\n" +
            "where post_id != 1\n" +
            "group by e.id order by e.id", nativeQuery = true)
    List<Long> employeesTasks();
    @Query(value="select id, first_name, last_name, up_name, post_id, phone, email\n" +
    "from public.employees\n" +
    "where post_id != 1\n" +
    "order by id", nativeQuery = true)
    List<Employee> empsNotManagers();
}
