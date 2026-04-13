#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."
rm -rf out
mkdir -p out
javac -encoding UTF-8 --release 17 -d out $(find src/main/java src/test/java -name '*.java')
java -cp out io.ascnpj.CnpjTest

