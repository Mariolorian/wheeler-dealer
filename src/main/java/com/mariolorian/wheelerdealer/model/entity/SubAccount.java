package com.mariolorian.wheelerdealer.model.entity;

import com.mariolorian.wheelerdealer.enums.Currency;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "SUB_ACCOUNTS")
public class SubAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Currency currency;

    @Nullable
    private BigDecimal balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        SubAccount subAccount = (SubAccount) o;
        return Objects.equals(id, subAccount.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
