package ru.duckcoder.bankservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "emails")
@Getter
@Setter
public class Email implements Mappable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @jakarta.validation.constraints.Email
    private String email;

    @ManyToOne
    private User user;

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Email)
            return Objects.equals(this.id, ((Email) obj).id);
        else
            return false;
    }

    @Override
    public String toString() {
        return "{\"id\":" + this.id + ","
                + "\"email\":\"" + this.email + "\","
                + "\"userId\":" + this.user.getId() + "}";
    }
}
