/**
 * author @bhupendrasambare
 * Date   :27/06/24
 * Time   :5:01 pm
 * Project:microservices-registry
 **/
package com.service.users.model;


import com.service.users.dto.request.UserUpdateRequest;
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

    private String title;

    @Lob
    private String about;
    private String address;
    private String company;

    private String youtube;
    private String twitter;
    private String facebook;
    private String linkedin;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Roles roles;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

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
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            this.title = request.getTitle();
        }
        if (request.getAbout() != null && !request.getAbout().isEmpty()) {
            this.about = request.getAbout();
        }
        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            this.address = request.getAddress();
        }
        if (request.getCompany() != null && !request.getCompany().isEmpty()) {
            this.company = request.getCompany();
        }
        if (request.getYoutube() != null && !request.getYoutube().isEmpty()) {
            this.youtube = request.getYoutube();
        }
        if (request.getTwitter() != null && !request.getTwitter().isEmpty()) {
            this.twitter = request.getTwitter();
        }
        if (request.getFacebook() != null && !request.getFacebook().isEmpty()) {
            this.facebook = request.getFacebook();
        }
        if (request.getLinkedin() != null && !request.getLinkedin().isEmpty()) {
            this.linkedin = request.getLinkedin();
        }

        if(this.createdAt==null){
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }



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
}
