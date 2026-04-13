# Decisoes

## Build sem Maven no bootstrap

Esta seed usa `javac/java` puro porque o ambiente local atual nao tem `mvn`.

## Java 17

Java 17 foi adotado como baseline local por estar disponivel e ser LTS.

## Sem dependencias externas

O nucleo e os testes nao dependem de bibliotecas terceiras.

## Batch com records

`ValidationItem`, `BatchSummary` e `BatchValidationResult` usam `record` para manter retorno simples e imutavel.

