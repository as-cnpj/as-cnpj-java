package io.ascnpj;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public record BatchSummary(
    int total,
    int valid,
    int invalid,
    Map<ValidationReason, Integer> reasons
) {
    public BatchSummary {
        EnumMap<ValidationReason, Integer> copy = new EnumMap<>(ValidationReason.class);
        copy.putAll(reasons);
        reasons = Collections.unmodifiableMap(copy);
    }
}

