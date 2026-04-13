package io.ascnpj;

public final class ValidationOptions {
    public static final ValidationOptions DEFAULT = new ValidationOptions(false);

    private final boolean strict;

    public ValidationOptions(boolean strict) {
        this.strict = strict;
    }

    public boolean isStrict() {
        return strict;
    }

    public static ValidationOptions strict() {
        return new ValidationOptions(true);
    }
}
