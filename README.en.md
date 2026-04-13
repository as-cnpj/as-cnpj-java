<div align="center">
  <img src="https://raw.githubusercontent.com/as-cnpj/as-cnpj-java/main/assets/brand/as-cnpj-logo-dark.svg" alt="AS-CNPJ Java" width="860" />
</div>

<p align="center">
  Author-led Java library of the AS-CNPJ ecosystem for numeric and alphanumeric CNPJ validation, normalization, formatting, check digits, and batch validation.
</p>

## Status

- repo-ready baseline for the future `as-cnpj-java`;
- algorithm validated locally with `javac/java`;
- no external runtime dependencies;
- contract aligned with the shared vectors from the hub.

## Public API

- `normalize`
- `isValid`
- `format`
- `assertValid`
- `calculateCheckDigits`
- `validateMany`

Explicit aliases:

- `normalizeCnpj`
- `isValidCnpj`
- `formatCnpj`
- `assertValidCnpj`
- `calculateCnpjCheckDigits`
- `validateManyCnpj`

## Local test command

```powershell
powershell -ExecutionPolicy Bypass -File scripts/test.ps1
```

## Documentation

- [API](docs/api.en.md)
- [Decisions](docs/decisoes.en.md)
- [Release checklist](docs/release-checklist.en.md)
- [Tests](test/README.en.md)
