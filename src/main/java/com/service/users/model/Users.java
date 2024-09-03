/**
 * author @bhupendrasambare
 * Date   :27/06/24
 * Time   :5:01 pm
 * Project:microservices-registry
 **/
package com.service.users.model;


import com.service.users.dto.request.UserUpdateRequest;
import com.service.users.dto.response.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "profile_picture", unique = true)
    private String profilePicture;

    @Column(nullable = false, updatable = false,name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false,name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "roles_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Roles roles;

    public Users(UserDto request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.email = request.getEmail();
        this.password= request.getPassword();
    }

    public void updateFields(UserUpdateRequest request) {
        if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
            this.firstName = request.getFirstName();
        }
        if (request.getLastName() != null && !request.getLastName().isEmpty()) {
            this.lastName = request.getLastName();
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
            this.phoneNumber = request.getPhoneNumber();
        }
        if (request.getProfilePictureUrl() != null && !request.getProfilePictureUrl().isEmpty()) {
            this.profilePicture = request.getProfilePictureUrl();
        }
        if(this.createdAt==null){
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }
}
