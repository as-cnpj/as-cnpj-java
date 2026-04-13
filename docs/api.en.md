# API

## Main class

`io.ascnpj.Cnpj`

## Unit methods

- `normalize` and `normalizeCnpj`
- `isValid` and `isValidCnpj`
- `format` and `formatCnpj`
- `assertValid` and `assertValidCnpj`
- `calculateCheckDigits` and `calculateCnpjCheckDigits`

## Options

`ValidationOptions`

- `ValidationOptions.DEFAULT`
- `ValidationOptions.strict()`
- `new ValidationOptions(boolean strict)`

## Batch

`validateMany` and `validateManyCnpj` return `BatchValidationResult`.

