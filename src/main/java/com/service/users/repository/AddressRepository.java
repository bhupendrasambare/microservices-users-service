package com.service.users.repository;

import com.service.users.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    @Query("Select u from Address u where u.userId = ?1")
    List<Address> findByUserId(Long id);
}
