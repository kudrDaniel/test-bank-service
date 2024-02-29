package ru.duckcoder.bankservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "phones")
@Getter
@Setter
public class Phone implements Mappable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Length(min = 10, max = 10)
    private String phone;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;
}
