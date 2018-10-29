package com.iisereb.sensorms.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 64)
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 64, message = "Password should be more than 6 but less than 64 symbols in length")
    @Lazy
    private String password;

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @Pattern(
        regexp = "[0-9]{3}-[0-9]{3}-[0-9]{4}",
        message = "Phone should be in following format \"647-678-1234\""
    )
    private String phone;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<SensorEntity> sensors = new ArrayList<>();

    public UserEntity() {}
    public UserEntity(String email, String password) {
        setEmail(email);
        setPassword(password);
        setFirstName("a");
        setLastName("a");
    }
}
