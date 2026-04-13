package io.ascnpj;

public record ValidationItem(
    int index,
    Object input,
    String normalized,
    String formatted,
    boolean valid,
    boolean strictValid,
    ValidationReason reason
) {
}

