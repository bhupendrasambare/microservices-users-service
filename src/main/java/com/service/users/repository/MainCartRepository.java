package com.service.users.repository;

import com.service.users.model.MainCart;
import com.service.users.model.embaded.UserProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainCartRepository extends JpaRepository<MainCart, UserProductId> {
    @Query("Select u from MainCart u where u.id.userId = ?1")
    List<MainCart> findByIdUserId(Long userId);
}