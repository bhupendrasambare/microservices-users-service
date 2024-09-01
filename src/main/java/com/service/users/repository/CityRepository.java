/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :1:51 am
 * Project:user service
 **/
package com.service.users.repository;

import com.service.users.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
