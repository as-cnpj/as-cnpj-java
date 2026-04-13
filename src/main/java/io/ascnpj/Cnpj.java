package io.ascnpj;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Cnpj {
    private static final int CNPJ_LENGTH = 14;
    private static final int CNPJ_BASE_LENGTH = 12;
    private static final int MAX_INPUT_LENGTH = 18;

    private static final Pattern PLAIN_CNPJ_PATTERN = Pattern.compile("^[A-Z0-9]{12}[0-9]{2}$");
    private static final Pattern MASKED_CNPJ_PATTERN = Pattern.compile("^[A-Z0-9]{2}\\.[A-Z0-9]{3}\\.[A-Z0-9]{3}/[A-Z0-9]{4}-[0-9]{2}$");
    private static final Pattern CNPJ_BASE_PATTERN = Pattern.compile("^[A-Z0-9]{12}$");
    private static final Pattern CNPJ_DIGITS_PATTERN = Pattern.compile("^[0-9]{2}$");
    private static final Pattern REPEATED_CHARS_PATTERN = Pattern.compile("^([A-Z0-9])\\1{13}$");

    private static final int[] FIRST_DIGIT_WEIGHTS = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] SECOND_DIGIT_WEIGHTS = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private Cnpj() {
    }

    public static String normalize(String value) {
        return normalizeCnpj(value);
    }

    public static String normalizeCnpj(String value) {
        ensureString(value, "A entrada deve ser uma string.");
        return sanitize(value);
    }

    public static String calculateCheckDigits(String base12) {
        return calculateCnpjCheckDigits(base12);
    }

    public static String calculateCnpjCheckDigits(String base12) {
        ensureString(base12, "A base do CNPJ deve ser uma string.");

        String normalizedBase = sanitize(base12);

        if (!CNPJ_BASE_PATTERN.matcher(normalizedBase).matches()) {
            throw new IllegalArgumentException("A base do CNPJ deve conter exatamente 12 caracteres alfanumericos.");
        }

        int firstDigit = calculateDigit(normalizedBase, FIRST_DIGIT_WEIGHTS);
        int secondDigit = calculateDigit(normalizedBase + firstDigit, SECOND_DIGIT_WEIGHTS);

        return Integer.toString(firstDigit) + secondDigit;
    }

    public static boolean isValid(String value) {
        return isValid(value, ValidationOptions.DEFAULT);
    }

    public static boolean isValid(String value, ValidationOptions options) {
        return isValidCnpj(value, options);
    }

    public static boolean isValidCnpj(String value) {
        return isValidCnpj(value, ValidationOptions.DEFAULT);
    }

    public static boolean isValidCnpj(String value, ValidationOptions options) {
        return analyze(value, sanitizeOptions(options)).valid();
    }

    public static String format(String value) {
        return format(value, ValidationOptions.DEFAULT);
    }

    public static String format(String value, ValidationOptions options) {
        return formatCnpj(value, options);
    }

    public static String formatCnpj(String value) {
        return formatCnpj(value, ValidationOptions.DEFAULT);
    }

    public static String formatCnpj(String value, ValidationOptions options) {
        ValidationItem item = analyze(value, sanitizeOptions(options));
        return item.valid() ? item.formatted() : null;
    }

    public static String assertValid(String value) {
        return assertValid(value, ValidationOptions.DEFAULT);
    }

    public static String assertValid(String value, ValidationOptions options) {
        return assertValidCnpj(value, options);
    }

    public static String assertValidCnpj(String value) {
        return assertValidCnpj(value, ValidationOptions.DEFAULT);
    }

    public static String assertValidCnpj(String value, ValidationOptions options) {
        ValidationItem item = analyze(value, sanitizeOptions(options));

        if (!item.valid()) {
            throw new IllegalArgumentException("CNPJ invalido.");
        }

        return item.normalized();
    }

    public static BatchValidationResult validateMany(List<?> values) {
        return validateMany(values, ValidationOptions.DEFAULT);
    }

    public static BatchValidationResult validateMany(List<?> values, ValidationOptions options) {
        return validateManyCnpj(values, options);
    }

    public static BatchValidationResult validateManyCnpj(List<?> values) {
        return validateManyCnpj(values, ValidationOptions.DEFAULT);
    }

    public static BatchValidationResult validateManyCnpj(List<?> values, ValidationOptions options) {
        if (values == null) {
            throw new IllegalArgumentException("A entrada em lote deve ser uma lista.");
        }

        ValidationOptions safeOptions = sanitizeOptions(options);
        List<ValidationItem> items = new ArrayList<>();
        EnumMap<ValidationReason, Integer> reasons = new EnumMap<>(ValidationReason.class);

        int validCount = 0;
        int invalidCount = 0;

        for (int index = 0; index < values.size(); index++) {
            ValidationItem item = withIndex(analyze(values.get(index), safeOptions), index);
            items.add(item);

            if (item.valid()) {
                validCount += 1;
            } else {
                invalidCount += 1;
                reasons.merge(item.reason(), 1, Integer::sum);
            }
        }

        return new BatchValidationResult(
            items,
            new BatchSummary(items.size(), validCount, invalidCount, reasons)
        );
    }

    private static ValidationItem analyze(Object value, ValidationOptions options) {
        if (!(value instanceof String stringValue)) {
            return invalid(-1, value, ValidationReason.INVALID_TYPE);
        }

        if (stringValue.length() > MAX_INPUT_LENGTH) {
            return invalid(-1, value, ValidationReason.INVALID_LENGTH);
        }

        if (!isAsciiPrintable(stringValue)) {
            return invalid(-1, value, ValidationReason.INVALID_ASCII);
        }

        String normalized = sanitize(stringValue);
        String uppercase = stringValue.toUpperCase(Locale.ROOT);

        if (options.isStrict() && !isStrictFormat(uppercase)) {
            return invalid(-1, value, ValidationReason.INVALID_STRICT_FORMAT);
        }

        if (normalized.length() != CNPJ_LENGTH) {
            return invalid(-1, value, ValidationReason.INVALID_LENGTH);
        }

        String base = normalized.substring(0, CNPJ_BASE_LENGTH);
        String digits = normalized.substring(CNPJ_BASE_LENGTH);

        if (!CNPJ_BASE_PATTERN.matcher(base).matches() || !CNPJ_DIGITS_PATTERN.matcher(digits).matches()) {
            return invalid(-1, value, ValidationReason.INVALID_BASE);
        }

        if (REPEATED_CHARS_PATTERN.matcher(normalized).matches()) {
            return invalid(-1, value, ValidationReason.TRIVIAL_REPETITION);
        }

        String expectedDigits = calculateCnpjCheckDigits(base);

        if (!Objects.equals(normalized, base + expectedDigits)) {
            return invalid(-1, value, ValidationReason.INVALID_CHECK_DIGIT);
        }

        return new ValidationItem(
            -1,
            value,
            normalized,
            formatNormalized(normalized),
            true,
            isStrictFormat(uppercase),
            ValidationReason.VALID
        );
    }

    private static ValidationItem withIndex(ValidationItem item, int index) {
        return new ValidationItem(
            index,
            item.input(),
            item.normalized(),
            item.formatted(),
            item.valid(),
            item.strictValid(),
            item.reason()
        );
    }

    private static ValidationItem invalid(int index, Object input, ValidationReason reason) {
        return new ValidationItem(index, input, null, null, false, false, reason);
    }

    private static ValidationOptions sanitizeOptions(ValidationOptions options) {
        return options == null ? ValidationOptions.DEFAULT : options;
    }

    private static void ensureString(String value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }

        if (!isAsciiPrintable(value)) {
            throw new IllegalArgumentException("A entrada deve conter apenas caracteres ASCII imprimiveis.");
        }
    }

    private static boolean isStrictFormat(String value) {
        return PLAIN_CNPJ_PATTERN.matcher(value).matches() || MASKED_CNPJ_PATTERN.matcher(value).matches();
    }

    private static boolean isAsciiPrintable(String value) {
        for (int index = 0; index < value.length(); index++) {
            char current = value.charAt(index);

            if (current < 32 || current > 126) {
                return false;
            }
        }

        return true;
    }

    private static String sanitize(String value) {
        String uppercase = value.toUpperCase(Locale.ROOT);
        StringBuilder builder = new StringBuilder(uppercase.length());

        for (int index = 0; index < uppercase.length(); index++) {
            char current = uppercase.charAt(index);

            if ((current >= 'A' && current <= 'Z') || (current >= '0' && current <= '9')) {
                builder.append(current);
            }
        }

        return builder.toString();
    }

    private static int calculateDigit(String value, int[] weights) {
        int total = 0;

        for (int index = 0; index < weights.length; index++) {
            total += charToValue(value.charAt(index)) * weights[index];
        }

        int remainder = total % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    private static int charToValue(char value) {
        return value - '0';
    }

    private static String formatNormalized(String normalized) {
        return normalized.substring(0, 2)
            + "."
            + normalized.substring(2, 5)
            + "."
            + normalized.substring(5, 8)
            + "/"
            + normalized.substring(8, 12)
            + "-"
            + normalized.substring(12, 14);
    }
}
