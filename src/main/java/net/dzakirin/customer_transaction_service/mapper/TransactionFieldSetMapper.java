package net.dzakirin.customer_transaction_service.mapper;

import net.dzakirin.customer_transaction_service.model.Transaction;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TransactionFieldSetMapper implements FieldSetMapper<Transaction> {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public Transaction mapFieldSet(FieldSet fieldSet) throws BindException {
        return Transaction.builder()
                .accountNumber(fieldSet.readString("accountNumber"))
                .trxAmount(new BigDecimal(fieldSet.readString("trxAmount")))
                .description(fieldSet.readString("description"))
                .transactionDate(LocalDate.parse(fieldSet.readString("transactionDate"), dateFormatter))
                .transactionTime(LocalTime.parse(fieldSet.readString("transactionTime"), timeFormatter))
                .customerId(fieldSet.readInt("customerId"))
                .build();
    }
}
