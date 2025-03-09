package net.dzakirin.customer_transaction_service.repository;

import net.dzakirin.customer_transaction_service.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    @Query("""
            SELECT t FROM Transaction t
            WHERE (:transactionId IS NULL OR t.id = :transactionId)
            AND (:customerId IS NULL OR t.customerId = :customerId)
            AND (:accountNumber IS NULL OR t.accountNumber LIKE %:accountNumber%)
            AND (:description IS NULL OR t.description LIKE %:description%)
            """)
    Page<Transaction> searchTransactions(Long transactionId, Integer customerId, String accountNumber, String description, Pageable pageable);
}
