# API

## Clase principal

`io.ascnpj.Cnpj`

## Metodos unitarios

- `normalize` y `normalizeCnpj`
- `isValid` y `isValidCnpj`
- `format` y `formatCnpj`
- `assertValid` y `assertValidCnpj`
- `calculateCheckDigits` y `calculateCnpjCheckDigits`

## Opciones

`ValidationOptions`

- `ValidationOptions.DEFAULT`
- `ValidationOptions.strict()`
- `new ValidationOptions(boolean strict)`

## Batch

`validateMany` y `validateManyCnpj` retornan `BatchValidationResult`.

