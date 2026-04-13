<div align="center">
  <img src="https://raw.githubusercontent.com/as-cnpj/as-cnpj-java/main/assets/brand/as-cnpj-logo-dark.svg" alt="AS-CNPJ Java" width="860" />
</div>

<p align="center">
  Biblioteca autoral en Java del ecosistema AS-CNPJ para validacion, normalizacion, formateo, digitos verificadores y validacion en lote de CNPJ numerico y alfanumerico.
</p>

## Estado

- base repo-ready para el futuro `as-cnpj-java`;
- algoritmo validado localmente con `javac/java`;
- sin dependencias externas de runtime;
- contrato alineado con los vectores compartidos del hub.

## API publica

- `normalize`
- `isValid`
- `format`
- `assertValid`
- `calculateCheckDigits`
- `validateMany`

Aliases explicitos:

- `normalizeCnpj`
- `isValidCnpj`
- `formatCnpj`
- `assertValidCnpj`
- `calculateCnpjCheckDigits`
- `validateManyCnpj`

## Comando local de prueba

```powershell
powershell -ExecutionPolicy Bypass -File scripts/test.ps1
```

## Documentacion

- [API](docs/api.es.md)
- [Decisiones](docs/decisoes.es.md)
- [Checklist de release](docs/release-checklist.es.md)
- [Pruebas](test/README.es.md)
