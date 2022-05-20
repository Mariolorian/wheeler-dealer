package com.mariolorian.wheelerdealer.model.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @NonNull
    private String id;

    @NonNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_CLIENT", unique = true)
    private Client client;

    @NonNull
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_ACCOUNT")
    private List<SubAccount> subAccounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
