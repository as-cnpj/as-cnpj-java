@echo off
setlocal

pushd "%~dp0.."
if exist out rmdir /s /q out
mkdir out

javac -encoding UTF-8 --release 17 -d out src\main\java\io\ascnpj\Cnpj.java src\main\java\io\ascnpj\ValidationOptions.java src\main\java\io\ascnpj\ValidationReason.java src\main\java\io\ascnpj\ValidationItem.java src\main\java\io\ascnpj\BatchSummary.java src\main\java\io\ascnpj\BatchValidationResult.java src\test\java\io\ascnpj\CnpjTest.java
if not exist out\io\ascnpj\Cnpj.class (
  popd
  exit /b 1
)
if not exist out\io\ascnpj\CnpjTest.class (
  popd
  exit /b 1
)

java -cp out io.ascnpj.CnpjTest
set EXIT_CODE=%ERRORLEVEL%

popd
exit /b %EXIT_CODE%
