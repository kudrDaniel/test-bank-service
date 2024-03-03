package ru.duckcoder.bankservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity
@Table(name = "wallets")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Wallet implements Mappable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double deposit;

    private Double accrual = 0.0;

    @JsonIgnore
    @OneToOne
    private User user;

    public Double getBalance() {
        return this.deposit + this.accrual;
    }

    public void changeAccrual() {
        if (this.getBalance() * 1.05 <= this.deposit * 2.07) {
            this.accrual += this.getBalance() * 0.05;
        }
    }

    public boolean removeFromDeposit(Double count) {
        if (this.deposit >= count) {
            this.deposit -= count;
            return true;
        }
        return false;
    }

    public void addToDeposit(Double count) {
        this.deposit += count;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Wallet)
            return Objects.equals(this.id, ((Wallet) obj).id);
        else
            return false;
    }

    @Override
    public String toString() {
        return "{\"id\":" + this.id + ","
                + "\"deposit\":" + this.deposit + ","
                + "\"accrual\":" + this.accrual + ","
                + "\"userId\":" + this.user.getId() + "}";
    }
}
