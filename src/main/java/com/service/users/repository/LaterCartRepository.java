package com.service.users.repository;

import com.service.users.model.LaterCart;
import com.service.users.model.embaded.UserProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaterCartRepository extends JpaRepository<LaterCart, UserProductId> {
    @Query("Select u from LaterCart u where u.id.userId = ?1")
    List<LaterCart> findByIdUserId(Long userId);
}