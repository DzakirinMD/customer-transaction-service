package net.dzakirin.customer_transaction_service.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataPreprocessor implements CommandLineRunner {
    @Override
    public void run(String... args) {
        TxtToCsvConverter.convertTxtToCsv();
    }
}
