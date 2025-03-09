package net.dzakirin.customer_transaction_service.service;

import lombok.RequiredArgsConstructor;
import net.dzakirin.customer_transaction_service.dto.request.SearchTransactionRequest;
import net.dzakirin.customer_transaction_service.dto.request.UpdateTransactionRequest;
import net.dzakirin.customer_transaction_service.dto.response.BaseListResponse;
import net.dzakirin.customer_transaction_service.dto.response.TransactionResponse;
import net.dzakirin.customer_transaction_service.mapper.TransactionMapper;
import net.dzakirin.customer_transaction_service.model.Transaction;
import net.dzakirin.customer_transaction_service.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public BaseListResponse<TransactionResponse> getTransactions(SearchTransactionRequest request) {
        Sort.Direction direction = Sort.Direction.fromString(request.getSortDirection());
        Pageable pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize(), Sort.by(direction, request.getSortField()));

        Page<Transaction> transactionsPage = transactionRepository.searchTransactions(
                request.getCustomerId(),
                request.getAccountNumber(),
                request.getDescription(),
                pageable
        );

        List<TransactionResponse> transactionResponses = transactionsPage.getContent().stream()
                .map(TransactionMapper::toDto)
                .toList();

        return BaseListResponse.<TransactionResponse>builder()
                .success(true)
                .message("Transactions retrieved successfully")
                .data(transactionResponses)
                .totalRecords(transactionsPage.getTotalElements())
                .totalPages(transactionsPage.getTotalPages())
                .build();
    }

    /**
     * @param id
     * @param request Concurrency Handling for Updates
     * @return
     */
    @Transactional
    public TransactionResponse updateTransaction(Long id, UpdateTransactionRequest request) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        synchronized (existingTransaction) {
            existingTransaction.setDescription(request.getDescription().toUpperCase());
            transactionRepository.save(existingTransaction);
        }

        return TransactionMapper.toDto(existingTransaction);
    }
}
