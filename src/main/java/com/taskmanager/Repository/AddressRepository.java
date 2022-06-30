package com.taskmanager.Repository;

import com.taskmanager.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsAddressByCityAndStreetAndBuild(String city, String street, String build);

}
