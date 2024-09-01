/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :1:11 am
 * Project:user service
 **/
package com.service.users.model;

import com.service.users.model.embaded.UserProductId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "main_cart")
public class MainCart {
    @EmbeddedId
    private UserProductId id;

    private Long quantity;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

}
