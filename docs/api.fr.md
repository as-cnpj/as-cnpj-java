# API

## Classe principale

`io.ascnpj.Cnpj`

## Methodes unitaires

- `normalize` et `normalizeCnpj`
- `isValid` et `isValidCnpj`
- `format` et `formatCnpj`
- `assertValid` et `assertValidCnpj`
- `calculateCheckDigits` et `calculateCnpjCheckDigits`

## Options

`ValidationOptions`

- `ValidationOptions.DEFAULT`
- `ValidationOptions.strict()`
- `new ValidationOptions(boolean strict)`

## Batch

`validateMany` et `validateManyCnpj` retournent `BatchValidationResult`.

