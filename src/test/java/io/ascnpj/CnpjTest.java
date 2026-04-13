package io.ascnpj;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CnpjTest {
    private static int executed = 0;

    private CnpjTest() {
    }

    public static void main(String[] args) throws Exception {
        testNormalize();
        testCalculateCheckDigits();
        testStrictFormat();
        testAsciiGuard();
        testBatchSummary();
        testBatchRejectsNullList();
        testVectorConformance();

        System.out.println("JAVA TESTS OK: " + executed);
    }

    private static void testNormalize() {
        executed += 1;
        assertEquals("12ABC34501DE35", io.ascnpj.Cnpj.normalize("12.abc.345/01de-35"), "normalize should uppercase and strip mask");
        assertThrows(IllegalArgumentException.class, () -> io.ascnpj.Cnpj.normalize(null), "normalize should reject null");
    }

    private static void testCalculateCheckDigits() {
        executed += 1;
        assertEquals("35", io.ascnpj.Cnpj.calculateCnpjCheckDigits("12ABC34501DE"), "check digits should match official example");
        assertThrows(IllegalArgumentException.class, () -> io.ascnpj.Cnpj.calculateCnpjCheckDigits("ß2345678901"), "check digits should reject non ASCII");
    }

    private static void testStrictFormat() {
        executed += 1;
        assertTrue(io.ascnpj.Cnpj.isValid("12.ABC.345/01DE-35", io.ascnpj.ValidationOptions.strict()), "strict should accept masked canonical input");
        assertTrue(io.ascnpj.Cnpj.isValid("12ABC34501DE35", io.ascnpj.ValidationOptions.strict()), "strict should accept uppercase unmasked canonical input");
        assertFalse(io.ascnpj.Cnpj.isValid("12ABC34501DE35 ", io.ascnpj.ValidationOptions.strict()), "strict should reject non canonical input with trailing space");
        assertEquals("12.ABC.345/01DE-35", io.ascnpj.Cnpj.format("12ABC34501DE35"), "format should mask normalized input");
    }

    private static void testAsciiGuard() {
        executed += 1;
        assertFalse(io.ascnpj.Cnpj.isValid("12.ABC.345/01DÉ-35"), "unicode input should be invalid");
        assertThrows(IllegalArgumentException.class, () -> io.ascnpj.Cnpj.normalize("12.ABC.345/01DÉ-35"), "normalize should reject unicode");
    }

    private static void testBatchSummary() {
        executed += 1;
        io.ascnpj.BatchValidationResult result = io.ascnpj.Cnpj.validateMany(Arrays.asList(
            "12.ABC.345/01DE-35",
            "12.ABC.345/01DE-36",
            null
        ));

        assertEquals(3, result.summary().total(), "batch total");
        assertEquals(1, result.summary().valid(), "batch valid count");
        assertEquals(2, result.summary().invalid(), "batch invalid count");
        assertEquals(1, result.summary().reasons().get(io.ascnpj.ValidationReason.INVALID_CHECK_DIGIT), "batch invalid DV count");
        assertEquals(1, result.summary().reasons().get(io.ascnpj.ValidationReason.INVALID_TYPE), "batch invalid type count");
        assertEquals(0, result.items().get(0).index(), "batch should preserve indexes");
    }

    private static void testBatchRejectsNullList() {
        executed += 1;
        assertThrows(IllegalArgumentException.class, () -> io.ascnpj.Cnpj.validateMany(null), "batch should reject null list");
    }

    private static void testVectorConformance() throws IOException {
        executed += 1;

        VectorDocument document = readVectors();

        for (VectorCase valid : document.valid()) {
            assertTrue(io.ascnpj.Cnpj.isValid(valid.value()), "valid vector should be accepted: " + valid.id());
            assertEquals(valid.normalized(), io.ascnpj.Cnpj.normalize(valid.value()), "normalized vector should match: " + valid.id());
            assertEquals(valid.formatted(), io.ascnpj.Cnpj.format(valid.value()), "formatted vector should match: " + valid.id());
        }

        for (VectorCase invalid : document.invalid()) {
            assertFalse(io.ascnpj.Cnpj.isValid(invalid.value()), "invalid vector should be rejected: " + invalid.id());
        }
    }

    private static VectorDocument readVectors() throws IOException {
        String content = Files.readString(Path.of("vectors", "cnpj.json"), StandardCharsets.UTF_8);
        return new VectorDocument(parseSection(content, "valid"), parseSection(content, "invalid"));
    }

    private static List<VectorCase> parseSection(String content, String section) {
        Matcher sectionMatcher = Pattern.compile("\"" + section + "\"\\s*:\\s*\\[(.*?)]", Pattern.DOTALL).matcher(content);

        if (!sectionMatcher.find()) {
            throw new IllegalStateException("Section not found: " + section);
        }

        String arrayContent = sectionMatcher.group(1);
        Matcher objectMatcher = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL).matcher(arrayContent);
        List<VectorCase> cases = new ArrayList<>();

        while (objectMatcher.find()) {
            String object = objectMatcher.group(1);
            cases.add(new VectorCase(
                extract(object, "id"),
                extract(object, "value"),
                extractOptional(object, "normalized"),
                extractOptional(object, "formatted"),
                extractOptional(object, "reason")
            ));
        }

        return cases;
    }

    private static String extract(String object, String field) {
        String value = extractOptional(object, field);

        if (value == null) {
            throw new IllegalStateException("Missing field: " + field);
        }

        return value;
    }

    private static String extractOptional(String object, String field) {
        Matcher matcher = Pattern.compile("\"" + field + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"", Pattern.DOTALL).matcher(object);

        if (!matcher.find()) {
            return null;
        }

        return matcher.group(1)
            .replace("\\\"", "\"")
            .replace("\\\\", "\\");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message + " | expected=" + expected + " actual=" + actual);
        }
    }

    private static void assertThrows(Class<? extends Throwable> expectedType, ThrowingRunnable runnable, String message) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            if (expectedType.isInstance(throwable)) {
                return;
            }

            throw new AssertionError(message + " | wrong exception type: " + throwable.getClass().getName(), throwable);
        }

        throw new AssertionError(message + " | expected exception was not thrown");
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private record VectorCase(String id, String value, String normalized, String formatted, String reason) {
    }

    private record VectorDocument(List<VectorCase> valid, List<VectorCase> invalid) {
    }
}
