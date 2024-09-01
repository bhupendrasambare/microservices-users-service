/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :1:23 am
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
@Table(name = "favourite")
public class Favourite {

    @EmbeddedId
    private UserProductId id;

    @Column(name = "added_date", nullable = false)
    private LocalDateTime addedDate;
}
