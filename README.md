<div align="center">
  <img src="https://raw.githubusercontent.com/as-cnpj/as-cnpj-java/main/assets/brand/as-cnpj-logo-dark.svg" alt="AS-CNPJ Java" width="860" />
</div>

<p align="center">
  Biblioteca autoral do ecossistema AS-CNPJ para validacao, normalizacao, formatacao, calculo de digitos verificadores e validacao em lote de CNPJ numerico e alfanumerico em Java.
</p>

<p align="center">
  <a href="https://github.com/as-cnpj/as-cnpj-java">Repositorio</a> |
  <a href="https://as-cnpj.org">Site</a> |
  <a href="https://github.com/as-cnpj/as-cnpj">Hub do ecossistema</a> |
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.md">API</a> |
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.md">Testes</a>
</p>

<p align="center">
  <a href="https://github.com/as-cnpj/as-cnpj-java/actions/workflows/ci.yml"><img alt="CI as-cnpj-java" src="https://img.shields.io/github/actions/workflow/status/as-cnpj/as-cnpj-java/ci.yml?branch=main&style=flat-square&label=ci&labelColor=1C1917"></a>
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/LICENSE"><img alt="License MIT" src="https://img.shields.io/github/license/as-cnpj/as-cnpj-java?style=flat-square&label=license&labelColor=1C1917&color=84A870"></a>
  <a href="https://as-cnpj.org"><img alt="Site as-cnpj.org" src="https://img.shields.io/badge/as--cnpj.org-documentacao-FB923C?style=flat-square&labelColor=1C1917"></a>
</p>

Idiomas: **Portugues (Brasil)** | [English](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.en.md) | [Espanol](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.es.md) | [Francais](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.fr.md)

## Status

- repositorio publico e ativo;
- API unitaria e validacao em lote ja implementadas;
- CI publica rodando em `Java 17`;
- algoritmo alinhado aos vetores compartilhados do ecossistema;
- artefato Maven Central ainda nao publicado.

## Instalacao

Estado atual:

- o codigo esta publico e pronto para uso no repositorio;
- a distribuicao em registry Java ainda nao foi publicada;
- a primeira publicacao planejada usa as coordenadas `io.ascnpj:as-cnpj-java`.

Coordenadas previstas para a primeira publicacao:

```xml
<dependency>
  <groupId>io.ascnpj</groupId>
  <artifactId>as-cnpj-java</artifactId>
  <version>0.1.0</version>
</dependency>
```

Enquanto o artefato nao estiver no Maven Central, o consumo atual e via checkout do repositorio e integracao local do codigo ao seu build Java.

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

## Casos de uso

- backends corporativos em Java que precisam aceitar CNPJ legado e alfanumerico;
- validacao de payloads antes de persistencia, faturamento ou integracao;
- saneamento de base e normalizacao consistente em migracoes;
- suites de homologacao que precisam validar multiplos CNPJs com resumo por motivo.

## O que esta biblioteca entrega

- validacao de CNPJ numerico legado;
- validacao de CNPJ alfanumerico previsto pela Receita Federal para julho de 2026;
- suporte a entradas com mascara e sem mascara;
- modo permissivo e modo estrito;
- validacao em lote com `items` e `summary`;
- zero dependencias externas no nucleo do algoritmo.

## API publica

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

## Garantias centrais

- aceita `A-Z0-9` nos 12 primeiros caracteres;
- mantem os 2 digitos verificadores como numericos;
- usa modulo 11 com conversao `ASCII - 48`;
- normaliza entrada para caixa alta;
- rejeita repeticoes triviais invalidas;
- preserva ordem e motivo por item na validacao em lote.

## Documentacao e referencias

- [API da biblioteca](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.md)
- [Estrategia de testes](https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.md)
- [Checklist de release](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/release-checklist.md)
- [Politica de seguranca](https://github.com/as-cnpj/as-cnpj-java/blob/main/SECURITY.md)
- [Hub do ecossistema AS-CNPJ](https://github.com/as-cnpj/as-cnpj)

## Publicacao

- repositorio publicado: `as-cnpj-java`
- coordenadas planejadas: `io.ascnpj:as-cnpj-java`
- proximo marco: publicacao do artefato em registry Java

## Vetores compartilhados

O `as-cnpj-java` nao define a verdade sozinho.

O contrato do ecossistema depende tambem de:

- vetores compartilhados no hub;
- regras documentadas a partir das fontes oficiais;
- convergencia entre implementacoes em linguagens diferentes.

## Manutencao

Maintainer: `@0moura`  
Contato institucional: `ascnpj@0moura.io`
