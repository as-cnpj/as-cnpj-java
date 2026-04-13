<div align="center">
  <img src="https://raw.githubusercontent.com/as-cnpj/as-cnpj-java/main/assets/brand/as-cnpj-logo-dark.svg" alt="AS-CNPJ Java" width="860" />
</div>

<p align="center">
  Bibliotheque auteur Java de l'ecosysteme AS-CNPJ pour la validation, la normalisation, le formatage, le calcul des chiffres de controle et la validation en lot du CNPJ numerique et alphanumerique.
</p>

<p align="center">
  <a href="https://github.com/as-cnpj/as-cnpj-java">Depot</a> |
  <a href="https://as-cnpj.org">Site</a> |
  <a href="https://github.com/as-cnpj/as-cnpj">Hub ecosysteme</a> |
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.fr.md">API</a> |
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.fr.md">Tests</a>
</p>

<p align="center">
  <a href="https://github.com/as-cnpj/as-cnpj-java/actions/workflows/ci.yml"><img alt="CI as-cnpj-java" src="https://img.shields.io/github/actions/workflow/status/as-cnpj/as-cnpj-java/ci.yml?branch=main&style=flat-square&label=ci&labelColor=1C1917"></a>
  <a href="https://github.com/as-cnpj/as-cnpj-java/blob/main/LICENSE"><img alt="License MIT" src="https://img.shields.io/github/license/as-cnpj/as-cnpj-java?style=flat-square&label=license&labelColor=1C1917&color=84A870"></a>
  <a href="https://as-cnpj.org"><img alt="Site as-cnpj.org" src="https://img.shields.io/badge/as--cnpj.org-documentation-FB923C?style=flat-square&labelColor=1C1917"></a>
</p>

Langues: [Português (Brasil)](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.md) | [English](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.en.md) | [Español](https://github.com/as-cnpj/as-cnpj-java/blob/main/README.es.md) | **Français**

## Statut

- depot public et actif ;
- API unitaire et validation en lot deja implementees ;
- CI publique executee sur `Java 17` ;
- algorithme aligne sur les vecteurs partages de l'ecosysteme ;
- artefact Maven Central pas encore publie.

## Installation

Etat actuel :

- le code est public et pret a l'usage dans le depot ;
- la distribution en registry Java n'a pas encore ete publiee ;
- la premiere publication prevue utilise les coordonnees `io.ascnpj:as-cnpj-java`.

Coordonnees prevues pour la premiere publication :

```xml
<dependency>
  <groupId>io.ascnpj</groupId>
  <artifactId>as-cnpj-java</artifactId>
  <version>0.1.0</version>
</dependency>
```

Tant que l'artefact n'est pas disponible sur Maven Central, l'usage actuel passe par le checkout du depot et l'integration locale dans votre build Java.

## Exemple rapide

```java
import io.ascnpj.Cnpj;
import io.ascnpj.ValidationOptions;

boolean valid = Cnpj.isValid("12.ABC.345/01DE-35");
String normalized = Cnpj.normalize("12.abc.345/01de-35");
String formatted = Cnpj.format("12ABC34501DE35");
String asserted = Cnpj.assertValid("12.ABC.345/01DE-35", ValidationOptions.strict());
String dv = Cnpj.calculateCnpjCheckDigits("12ABC34501DE");
```

## Validation en lot

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

## Cas d'usage

- backends corporate Java qui doivent accepter le CNPJ historique et alphanumerique ;
- validation de payloads avant persistance, facturation ou integrations ;
- nettoyage de base et normalisation coherente lors des migrations ;
- suites d'homologation qui doivent valider plusieurs CNPJ avec un resume par motif.

## Ce que cette bibliotheque fournit

- validation du CNPJ numerique historique ;
- validation du CNPJ alphanumerique prevu par la Receita Federal pour juillet 2026 ;
- prise en charge des entrees masquees et non masquees ;
- mode permissif et mode strict ;
- validation en lot avec `items` et `summary` ;
- zero dependance externe dans le noyau de l'algorithme.

## API publique

Methodes principales :

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

Aliases explicites :

- `normalizeCnpj(String value)`
- `isValidCnpj(...)`
- `formatCnpj(...)`
- `assertValidCnpj(...)`
- `calculateCnpjCheckDigits(String base12)`
- `validateManyCnpj(...)`

## Garanties centrales

- accepte `A-Z0-9` dans les 12 premiers caracteres ;
- conserve les 2 chiffres de controle numeriques ;
- utilise le module 11 avec conversion `ASCII - 48` ;
- normalise l'entree en majuscules ;
- rejette les repetitions triviales invalides ;
- preserve l'ordre et le motif par element dans la validation en lot.

## Documentation et references

- [API de la bibliotheque](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/api.fr.md)
- [Strategie de tests](https://github.com/as-cnpj/as-cnpj-java/blob/main/test/README.fr.md)
- [Checklist de release](https://github.com/as-cnpj/as-cnpj-java/blob/main/docs/release-checklist.fr.md)
- [Politique de securite](https://github.com/as-cnpj/as-cnpj-java/blob/main/SECURITY.fr.md)
- [Hub AS-CNPJ](https://github.com/as-cnpj/as-cnpj)

## Publication

- depot publie : `as-cnpj-java`
- coordonnees prevues : `io.ascnpj:as-cnpj-java`
- prochaine etape : publication de l'artefact dans un registry Java

## Vecteurs partages

`as-cnpj-java` ne definit pas la verite a lui seul.

Le contrat de l'ecosysteme depend aussi de :

- vecteurs partages dans le hub ;
- regles documentees a partir des sources officielles ;
- convergence entre implementations dans des langages differents.

## Maintenance

Maintainer: `@0moura`  
Contact institutionnel: `ascnpj@0moura.io`
