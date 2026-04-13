<div align="center">
  <img src="https://raw.githubusercontent.com/as-cnpj/as-cnpj-java/main/assets/brand/as-cnpj-logo-dark.svg" alt="AS-CNPJ Java" width="860" />
</div>

<p align="center">
  Author-led Java library from the AS-CNPJ ecosystem for validation, normalization, formatting, check digit calculation, and batch validation of numeric and alphanumeric CNPJ.
</p>

<p align="center">
  <a href="https://github.com/as-cnpj/as-cnpj-java">Repository</a> |
  <a href="https://as-cnpj.org">Site</a> |
  <a href="https://github.com/as-cnpj/as-cnpj">Ecosystem hub</a> |
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.en.md">API</a> |
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.en.md">Tests</a>
</p>

<p align="center">
  <a href="https://github.com/as-cnpj/as-cnpj-java/actions/workflows/ci.yml"><img alt="CI as-cnpj-java" src="https://img.shields.io/github/actions/workflow/status/as-cnpj/as-cnpj-java/ci.yml?branch=main&style=flat-square&label=ci&labelColor=1C1917"></a>
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/LICENSE"><img alt="License MIT" src="https://img.shields.io/github/license/as-cnpj/as-cnpj-java?style=flat-square&label=license&labelColor=1C1917&color=84A870"></a>
  <a href="https://as-cnpj.org"><img alt="Site as-cnpj.org" src="https://img.shields.io/badge/as--cnpj.org-documentation-FB923C?style=flat-square&labelColor=1C1917"></a>
</p>

Languages: [Português (Brasil)](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.md) | **English** | [Español](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.es.md) | [Français](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.fr.md)

## Status

- public and active repository;
- unit API and batch validation already implemented;
- public CI running on `Java 17`;
- algorithm aligned with the shared ecosystem vectors;
- Maven Central artifact not published yet.

## Installation

Current state:

- the code is public and ready to use in the repository;
- the Java registry distribution has not been published yet;
- the first planned publication uses coordinates `io.ascnpj:as-cnpj-java`.

Planned coordinates for the first publication:

```xml
<dependency>
  <groupId>io.ascnpj</groupId>
  <artifactId>as-cnpj-java</artifactId>
  <version>0.1.0</version>
</dependency>
```

Until the artifact is available on Maven Central, current consumption is through repository checkout and local integration into your Java build.

## Quick example

```java
import io.ascnpj.Cnpj;
import io.ascnpj.ValidationOptions;

boolean valid = Cnpj.isValid("12.ABC.345/01DE-35");
String normalized = Cnpj.normalize("12.abc.345/01de-35");
String formatted = Cnpj.format("12ABC34501DE35");
String asserted = Cnpj.assertValid("12.ABC.345/01DE-35", ValidationOptions.strict());
String dv = Cnpj.calculateCnpjCheckDigits("12ABC34501DE");
```

## Batch validation

```java
import io.ascnpj.Cnpj;

var result = Cnpj.validateMany(java.util.Arrays.asList(
    "12.ABC.345/01DE-35",
    "12.ABC.345/01DE-36",
    null
));

result.items().get(0).valid();
result.items().get(1).reason();
result.summary().reasons();
```

## Use cases

- corporate Java backends that need to accept legacy and alphanumeric CNPJ;
- payload validation before persistence, billing, or integrations;
- database cleanup and consistent normalization in migrations;
- homologation suites that need to validate multiple CNPJs with grouped reasons.

## What this library provides

- validation for legacy numeric CNPJ;
- validation for alphanumeric CNPJ expected by Receita Federal in July 2026;
- support for masked and unmasked input;
- permissive mode and strict mode;
- batch validation with `items` and `summary`;
- zero external dependencies in the algorithm core.

## Public API

Main methods:

- `normalize(String value)`
- `isValid(String value)`
- `isValid(String value, ValidationOptions options)`
- `format(String value)`
- `format(String value, ValidationOptions options)`
- `assertValid(String value)`
- `assertValid(String value, ValidationOptions options)`
- `calculateCheckDigits(String base12)`
- `validateMany(List<?> values)`
- `validateMany(List<?> values, ValidationOptions options)`

Explicit aliases:

- `normalizeCnpj(String value)`
- `isValidCnpj(...)`
- `formatCnpj(...)`
- `assertValidCnpj(...)`
- `calculateCnpjCheckDigits(String base12)`
- `validateManyCnpj(...)`

## Core guarantees

- accepts `A-Z0-9` in the first 12 characters;
- keeps the 2 check digits numeric;
- uses modulo 11 with `ASCII - 48` conversion;
- normalizes input to uppercase;
- rejects trivial invalid repetition;
- preserves order and reason per item in batch validation.

## Documentation and references

- [Library API](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.en.md)
- [Testing strategy](https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.en.md)
- [Release checklist](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/release-checklist.en.md)
- [Security policy](https://github.com/as-cnpj/as-cnpj-java/blob/main/SECURITY.en.md)
- [AS-CNPJ ecosystem hub](https://github.com/as-cnpj/as-cnpj)

## Publication

- published repository: `as-cnpj-java`
- planned coordinates: `io.ascnpj:as-cnpj-java`
- next milestone: Java registry publication

## Shared vectors

`as-cnpj-java` does not define the truth alone.

The ecosystem contract also depends on:

- shared vectors in the hub;
- rules documented from official sources;
- convergence across implementations in different languages.

## Maintenance

Maintainer: `@0moura`  
Institutional contact: `ascnpj@0moura.io`
