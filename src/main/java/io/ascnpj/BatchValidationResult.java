package io.ascnpj;

import java.util.List;

public record BatchValidationResult(
    List<ValidationItem> items,
    BatchSummary summary
) {
    public BatchValidationResult {
        items = List.copyOf(items);
    }
}

