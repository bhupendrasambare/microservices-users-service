/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :1:51 am
 * Project:user service
 **/
package com.service.users.repository;

import com.service.users.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long> {
    @Query("Select u from City u order by u.state.country.name, u.state.name, u.name")
    List<City> findAllByName();

    @Query("Select u from City u where u.state.id = ?1")
    List<City> findByStateId(Long id);
}
