# API

## Classe principal

`io.ascnpj.Cnpj`

## Metodos unitarios

- `normalize` e `normalizeCnpj`
- `isValid` e `isValidCnpj`
- `format` e `formatCnpj`
- `assertValid` e `assertValidCnpj`
- `calculateCheckDigits` e `calculateCnpjCheckDigits`

## Opcoes

`ValidationOptions`

- `ValidationOptions.DEFAULT`
- `ValidationOptions.strict()`
- `new ValidationOptions(boolean strict)`

## Batch

`validateMany` e `validateManyCnpj` retornam `BatchValidationResult`.

Cada item contem:

- `index`
- `input`
- `normalized`
- `formatted`
- `valid`
- `strictValid`
- `reason`

O resumo contem:

- `total`
- `valid`
- `invalid`
- `reasons`

