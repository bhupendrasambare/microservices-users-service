package com.service.users.repository;

import com.service.users.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {
    @Query("Select u from State u where u.country.id = ?1")
    List<State> findAllByCountry(Long countryId);

    @Query("Select u from State u order by u.country.name, u.name")
    List<State> findAllByName();
}
