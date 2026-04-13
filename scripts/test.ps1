$ErrorActionPreference = "Stop"

Push-Location $PSScriptRoot\..
try {
    Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
    New-Item -ItemType Directory -Force out | Out-Null

    $stdout = Join-Path $PWD "javac.stdout.log"
    $stderr = Join-Path $PWD "javac.stderr.log"

    $process = Start-Process `
        -FilePath "cmd.exe" `
        -ArgumentList "/c", "javac -encoding UTF-8 --release 17 -d out src/main/java/io/ascnpj/Cnpj.java src/main/java/io/ascnpj/ValidationOptions.java src/main/java/io/ascnpj/ValidationReason.java src/main/java/io/ascnpj/ValidationItem.java src/main/java/io/ascnpj/BatchSummary.java src/main/java/io/ascnpj/BatchValidationResult.java src/test/java/io/ascnpj/CnpjTest.java" `
        -Wait `
        -PassThru `
        -NoNewWindow `
        -RedirectStandardOutput $stdout `
        -RedirectStandardError $stderr

    if (-not (Test-Path out\io\ascnpj\Cnpj.class) -or -not (Test-Path out\io\ascnpj\CnpjTest.class)) {
        if (Test-Path $stdout) { Get-Content $stdout }
        if (Test-Path $stderr) { Get-Content $stderr }
        exit $process.ExitCode
    }

    java -cp out io.ascnpj.CnpjTest
} finally {
    Remove-Item -LiteralPath javac.stdout.log,javac.stderr.log -ErrorAction SilentlyContinue
    Pop-Location
}
