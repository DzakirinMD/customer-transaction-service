package net.dzakirin.customer_transaction_service.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.customer_transaction_service.dto.response.TransactionResponse;
import net.dzakirin.customer_transaction_service.model.Transaction;

@UtilityClass
public class TransactionMapper {

    public TransactionResponse toDto(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountNumber(transaction.getAccountNumber())
                .customerId(transaction.getCustomerId())
                .description(transaction.getDescription())
                .trxAmount(transaction.getTrxAmount())
                .transactionDate(transaction.getTransactionDate())
                .transactionTime(transaction.getTransactionTime())
                .build();
    }
}
