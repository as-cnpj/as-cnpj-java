# Decisions

## Bootstrap without Maven

This seed uses `javac/java` because the current local environment does not have `mvn`.

## Java 17

Java 17 is the local baseline because it is available and LTS.

## No external runtime dependencies

The core and the test runner do not depend on third-party libraries.

