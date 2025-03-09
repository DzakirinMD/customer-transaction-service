package net.dzakirin.customer_transaction_service.service;

import lombok.RequiredArgsConstructor;
import net.dzakirin.customer_transaction_service.model.Transaction;
import net.dzakirin.customer_transaction_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByCustomerId(String customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }

    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    public List<Transaction> searchByDescription(String description) {
        return transactionRepository.findByDescriptionContaining(description);
    }

    /**
     *
     * @param id
     * @param updatedTransaction
     *
     * Concurrency Handling for Updates
     *
     * @return
     */
    @Transactional
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        synchronized (existingTransaction) {
            existingTransaction.setDescription(updatedTransaction.getDescription());
            return transactionRepository.save(existingTransaction);
        }
    }
}
