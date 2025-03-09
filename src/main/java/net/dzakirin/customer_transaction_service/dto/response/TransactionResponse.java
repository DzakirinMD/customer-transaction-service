package net.dzakirin.customer_transaction_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private String accountNumber;
    private BigDecimal trxAmount;
    private String description;
    private LocalDate transactionDate;
    private LocalTime transactionTime;
    private int customerId;
}
