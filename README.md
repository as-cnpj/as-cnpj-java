<div align="center">
  <img src="https://raw.githubusercontent.com/as-cnpj/as-cnpj-java/main/assets/brand/as-cnpj-logo-dark.svg" alt="AS-CNPJ Java" width="860" />
</div>

<p align="center">
  Biblioteca autoral do ecossistema AS-CNPJ para validacao, normalizacao, formatacao, calculo de digitos verificadores e validacao em lote de CNPJ numerico e alfanumerico em Java.
</p>

<p align="center">
  <a href="https://github.com/as-cnpj/as-cnpj-java">Repositorio</a> ·
  <a href="https://as-cnpj.org">Site</a> ·
  <a href="https://github.com/as-cnpj/as-cnpj">Hub do ecossistema</a> ·
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.md">API</a> ·
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.md">Testes</a>
</p>

## Status

- repo-ready para o futuro `as-cnpj-java`;
- algoritmo validado localmente com `javac/java`;
- sem dependencias externas no nucleo;
- contrato alinhado aos vetores compartilhados do hub.

## API publica planejada

Metodos principais:

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

Aliases explicitos:

- `normalizeCnpj(String value)`
- `isValidCnpj(...)`
- `formatCnpj(...)`
- `assertValidCnpj(...)`
- `calculateCnpjCheckDigits(String base12)`
- `validateManyCnpj(...)`

## Exemplo rapido

```java
import io.ascnpj.Cnpj;
import io.ascnpj.ValidationOptions;

boolean valid = Cnpj.isValid("12.ABC.345/01DE-35");
String normalized = Cnpj.normalize("12.abc.345/01de-35");
String formatted = Cnpj.format("12ABC34501DE35");
String asserted = Cnpj.assertValid("12.ABC.345/01DE-35", ValidationOptions.strict());
String dv = Cnpj.calculateCnpjCheckDigits("12ABC34501DE");
```

## Validacao em lote

```java
var result = Cnpj.validateMany(java.util.Arrays.asList(
    "12.ABC.345/01DE-35",
    "12.ABC.345/01DE-36",
    null
));

result.items().get(0).valid();
result.items().get(1).reason();
result.summary().reasons();
```

## Comando local de teste

```powershell
powershell -ExecutionPolicy Bypass -File scripts/test.ps1
```

## Documentacao

- [API](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.md)
- [Decisoes](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/decisoes.md)
- [Checklist de release](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/release-checklist.md)
- [Testes](https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.md)

## Toolchain local validado

- `java 17`
- `javac 17`
- sem `mvn` no ambiente atual

## Manutencao

Maintainer: `@0moura`  
Contato institucional: `ascnpj@0moura.io`
