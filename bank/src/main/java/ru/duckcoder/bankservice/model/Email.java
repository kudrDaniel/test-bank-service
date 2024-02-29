package ru.duckcoder.bankservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "emails")
@Getter
@Setter
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @jakarta.validation.constraints.Email
    private String email;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;
}
