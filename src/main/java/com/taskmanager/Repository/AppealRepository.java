package com.taskmanager.Repository;

import com.taskmanager.Model.Appeal;
import com.taskmanager.Model.Client;
import com.taskmanager.Model.Employee;
import com.taskmanager.Model.Times;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {
    List<Appeal> findByDateBetweenAndManagerAndDoneFalse(Date monday, Date now, Employee manager);
    List<Appeal> findByDateBetweenAndManagerAndDoneTrue(Date monday, Date now, Employee manager);
    List<Appeal> findByDateLessThanAndManagerAndDoneFalse(Date monday, Employee manager);
    List<Appeal> findByTaskdateAndEmployeeAndTasktimeAndDoneFalse(Date dt, Employee emp, Times time);
    List<Appeal> findByTaskdateAndEmployeeAndDoneTrue(Date dt, Employee emp);
    List<Appeal> findByDateBetweenAndManagerAndEmployeeNullAndDoneFalse(Date dt_start, Date dt_end, Employee manager);
    List<Appeal> findByDateBetweenAndManagerAndEmployeeNotNullAndDoneFalse(Date dt_start, Date dt_end, Employee manager);
    List<Appeal> findByDateBetweenAndManagerAndEmployeeNotNullAndDoneTrue(Date dt_start, Date dt_end, Employee manager);
    @Modifying
    @Transactional
    @Query(value="update Appeals set employee_id = ?1 where id = ?2", nativeQuery = true)
    int setEmployeeForAppeal(Long emp, Long id);

    @Modifying
    @Transactional
    @Query(value="update Appeals set is_done = true where id = ?1", nativeQuery = true)
    int setDoneForAppeal(Long id);

    @Modifying
    @Transactional
    @Query(value="update Appeals set employee_id = null where id = ?1", nativeQuery = true)
    int setNullEmployee(Long id);

    boolean existsAppealByClient(Client client);
}
