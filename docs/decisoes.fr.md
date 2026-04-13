# Decisions

## Bootstrap sans Maven

Cette seed utilise `javac/java` parce que l'environnement local actuel n'a pas `mvn`.

## Java 17

Java 17 est la base locale car il est disponible et LTS.

## Sans dependances externes de runtime

Le noyau et le runner de test ne dependent pas de bibliotheques tierces.

