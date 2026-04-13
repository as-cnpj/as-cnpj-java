<div align="center">
  <img src="https://raw.githubusercontent.com/as-cnpj/as-cnpj-java/main/assets/brand/as-cnpj-logo-dark.svg" alt="AS-CNPJ Java" width="860" />
</div>

<p align="center">
  Bibliotheque auteur en Java de l'ecosysteme AS-CNPJ pour la validation, la normalisation, le formatage, les chiffres de controle et la validation en lot du CNPJ numerique et alphanumerique.
</p>

## Statut

- base repo-ready pour le futur `as-cnpj-java` ;
- algorithme valide localement avec `javac/java` ;
- aucune dependance externe de runtime ;
- contrat aligne sur les vecteurs partages du hub.

## API publique

- `normalize`
- `isValid`
- `format`
- `assertValid`
- `calculateCheckDigits`
- `validateMany`

Aliases explicites :

- `normalizeCnpj`
- `isValidCnpj`
- `formatCnpj`
- `assertValidCnpj`
- `calculateCnpjCheckDigits`
- `validateManyCnpj`

## Commande locale de test

```powershell
powershell -ExecutionPolicy Bypass -File scripts/test.ps1
```

## Documentation

- [API](docs/api.fr.md)
- [Decisions](docs/decisoes.fr.md)
- [Checklist de release](docs/release-checklist.fr.md)
- [Tests](test/README.fr.md)
