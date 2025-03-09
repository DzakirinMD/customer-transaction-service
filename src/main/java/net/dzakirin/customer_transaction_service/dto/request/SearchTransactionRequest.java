package net.dzakirin.customer_transaction_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchTransactionRequest {

    @Schema(defaultValue = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(1)
    private int pageNumber;

    @Schema(defaultValue = "25", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(1)
    private int pageSize;

    @Schema(defaultValue = "transactionDate", description = "Field to sort by (e.g., trxAmount, transactionDate, description)")
    private String sortField = "transactionDate";

    @Schema(defaultValue = "DESC", description = "Sort direction (ASC or DESC)")
    private String sortDirection = "DESC"; // Default sorting order
}
