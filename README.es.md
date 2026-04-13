<div align="center">
  <img src="https://raw.githubusercontent.com/as-cnpj/as-cnpj-java/main/assets/brand/as-cnpj-logo-dark.svg" alt="AS-CNPJ Java" width="860" />
</div>

<p align="center">
  Biblioteca autoral en Java del ecosistema AS-CNPJ para validacion, normalizacion, formateo, calculo de digitos verificadores y validacion en lote de CNPJ numerico y alfanumerico.
</p>

<p align="center">
  <a href="https://github.com/as-cnpj/as-cnpj-java">Repositorio</a> |
  <a href="https://as-cnpj.org">Sitio</a> |
  <a href="https://github.com/as-cnpj/as-cnpj">Hub del ecosistema</a> |
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.es.md">API</a> |
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.es.md">Pruebas</a>
</p>

<p align="center">
  <a href="https://github.com/as-cnpj/as-cnpj-java/actions/workflows/ci.yml"><img alt="CI as-cnpj-java" src="https://img.shields.io/github/actions/workflow/status/as-cnpj/as-cnpj-java/ci.yml?branch=main&style=flat-square&label=ci&labelColor=1C1917"></a>
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/LICENSE"><img alt="License MIT" src="https://img.shields.io/github/license/as-cnpj/as-cnpj-java?style=flat-square&label=license&labelColor=1C1917&color=84A870"></a>
  <a href="https://as-cnpj.org"><img alt="Site as-cnpj.org" src="https://img.shields.io/badge/as--cnpj.org-documentacion-FB923C?style=flat-square&labelColor=1C1917"></a>
</p>

Idiomas: [Português (Brasil)](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.md) | [English](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.en.md) | **Español** | [Français](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.fr.md)

## Estado

- repositorio publico y activo;
- API unitaria y validacion en lote ya implementadas;
- CI publica ejecutandose en `Java 17`;
- algoritmo alineado con los vectores compartidos del ecosistema;
- artefacto Maven Central aun no publicado.

## Instalacion

Estado actual:

- el codigo es publico y esta listo para usarse en el repositorio;
- la distribucion en registry Java todavia no fue publicada;
- la primera publicacion prevista usa las coordenadas `io.ascnpj:as-cnpj-java`.

Coordenadas previstas para la primera publicacion:

```xml
<dependency>
  <groupId>io.ascnpj</groupId>
  <artifactId>as-cnpj-java</artifactId>
  <version>0.1.0</version>
</dependency>
```

Mientras el artefacto no este en Maven Central, el consumo actual es mediante checkout del repositorio e integracion local en tu build Java.

## Ejemplo rapido

```java
import io.ascnpj.Cnpj;
import io.ascnpj.ValidationOptions;

boolean valid = Cnpj.isValid("12.ABC.345/01DE-35");
String normalized = Cnpj.normalize("12.abc.345/01de-35");
String formatted = Cnpj.format("12ABC34501DE35");
String asserted = Cnpj.assertValid("12.ABC.345/01DE-35", ValidationOptions.strict());
String dv = Cnpj.calculateCnpjCheckDigits("12ABC34501DE");
```

## Validacion en lote

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

- backends corporativos en Java que necesitan aceptar CNPJ legado y alfanumerico;
- validacion de payloads antes de persistencia, facturacion o integraciones;
- saneamiento de bases y normalizacion consistente en migraciones;
- suites de homologacion que necesitan validar multiples CNPJ con resumen por motivo.

## Lo que entrega esta biblioteca

- validacion de CNPJ numerico legado;
- validacion de CNPJ alfanumerico previsto por la Receita Federal para julio de 2026;
- soporte para entradas con mascara y sin mascara;
- modo permisivo y modo estricto;
- validacion en lote con `items` y `summary`;
- cero dependencias externas en el nucleo del algoritmo.

## API publica

Metodos principales:

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

## Garantias centrales

- acepta `A-Z0-9` en los primeros 12 caracteres;
- mantiene los 2 digitos verificadores numericos;
- usa modulo 11 con conversion `ASCII - 48`;
- normaliza la entrada a mayusculas;
- rechaza repeticiones triviales invalidas;
- preserva orden y motivo por item en la validacion en lote.

## Documentacion y referencias

- [API de la biblioteca](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.es.md)
- [Estrategia de pruebas](https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.es.md)
- [Checklist de release](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/release-checklist.es.md)
- [Politica de seguridad](https://github.com/as-cnpj/as-cnpj-java/blob/main/SECURITY.es.md)
- [Hub del ecosistema AS-CNPJ](https://github.com/as-cnpj/as-cnpj)

## Publicacion

- repositorio publicado: `as-cnpj-java`
- coordenadas previstas: `io.ascnpj:as-cnpj-java`
- siguiente hito: publicacion del artefacto en registry Java

## Vectores compartidos

`as-cnpj-java` no define la verdad por si solo.

El contrato del ecosistema tambien depende de:

- vectores compartidos en el hub;
- reglas documentadas a partir de fuentes oficiales;
- convergencia entre implementaciones en distintos lenguajes.

## Mantenimiento

Maintainer: `@0moura`  
Contacto institucional: `ascnpj@0moura.io`
