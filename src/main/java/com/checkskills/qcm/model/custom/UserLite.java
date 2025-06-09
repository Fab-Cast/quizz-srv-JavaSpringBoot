package com.checkskills.qcm.model.custom;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserLite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min=3, max = 50)
    private String username;
    private String description;
    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private byte[] picture;
}
