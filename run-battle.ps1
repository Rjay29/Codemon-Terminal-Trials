# Run BattleGame using Maven (PowerShell-safe quoting)
# Usage: .\run-battle.ps1

Push-Location -Path $PSScriptRoot
try {
    # Quote the -D argument so PowerShell doesn't split it and Maven won't mis-parse it as a phase
    mvn -Dexec.mainClass="Codemon.BattleGame" compile exec:java
} finally {
    Pop-Location
}
