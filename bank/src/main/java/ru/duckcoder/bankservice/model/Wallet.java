package ru.duckcoder.bankservice.model;

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

    @OneToOne(mappedBy = "wallet")
    private User user;

    public Double getBalance() {
        return deposit + accrual;
    }

    public void changeAccrual() {
        if (this.getBalance() * 0.05 < deposit * 2.07) {
            accrual = getBalance() * 0.05;
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
}
