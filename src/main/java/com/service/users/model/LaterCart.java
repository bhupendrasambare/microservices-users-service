/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :1:11 am
 * Project:user service
 **/
package com.service.users.model;

import com.service.users.model.embaded.UserProductId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "later_cart")
public class LaterCart {
    @EmbeddedId
    private UserProductId id;

    private Long quantity;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

}
