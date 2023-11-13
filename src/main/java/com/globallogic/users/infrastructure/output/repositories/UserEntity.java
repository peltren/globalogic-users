package com.globallogic.users.infrastructure.output.repositories;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Data
public class UserEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    @Column(nullable = false)
    private String password;

    private Boolean isActive;

    private LocalDateTime created;

    private LocalDateTime lastLogin;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<UserPhoneEntity> phones;
}
