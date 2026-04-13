package io.ascnpj;

public enum ValidationReason {
    VALID,
    INVALID_TYPE,
    INVALID_LENGTH,
    INVALID_ASCII,
    INVALID_STRICT_FORMAT,
    INVALID_BASE,
    TRIVIAL_REPETITION,
    INVALID_CHECK_DIGIT
}

