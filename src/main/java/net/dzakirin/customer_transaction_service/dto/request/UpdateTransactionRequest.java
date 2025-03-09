package net.dzakirin.customer_transaction_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransactionRequest {
    private String accountNumber;
    private Integer customerId;
    private String description;
    private BigDecimal trxAmount;
}
