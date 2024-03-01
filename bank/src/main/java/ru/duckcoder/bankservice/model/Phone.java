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
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

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
        if (obj instanceof Phone)
            return Objects.equals(this.id, ((Phone) obj).id);
        else
            return false;
    }

    @Override
    public String toString() {
        return "{\"id\":" + this.id + ","
                + "\"email\":\"" + this.phone + "\","
                + "\"userId\":" + this.user.getId() + "}";
    }
}
