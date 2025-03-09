package net.dzakirin.customer_transaction_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.dzakirin.customer_transaction_service.dto.request.SearchTransactionRequest;
import net.dzakirin.customer_transaction_service.dto.request.UpdateTransactionRequest;
import net.dzakirin.customer_transaction_service.dto.response.BaseListResponse;
import net.dzakirin.customer_transaction_service.dto.response.TransactionResponse;
import net.dzakirin.customer_transaction_service.model.Transaction;
import net.dzakirin.customer_transaction_service.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(
            summary = "Get Transactions",
            description = "Retrieves a list of transactions with sorting, pagination, and filtering."
    )
    @GetMapping
    public ResponseEntity<BaseListResponse<TransactionResponse>> getTransactions(SearchTransactionRequest searchTransactionRequest) {
        return ResponseEntity.ok(transactionService.getTransactions(searchTransactionRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable Long id,
            @RequestBody UpdateTransactionRequest request
    ) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, request));
    }
}
