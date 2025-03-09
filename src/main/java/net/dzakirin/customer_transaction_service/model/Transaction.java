package net.dzakirin.customer_transaction_service.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "trx_amount", nullable = false)
    private BigDecimal trxAmount;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "trx_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "trx_time", nullable = false)
    private LocalTime transactionTime;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;
}
