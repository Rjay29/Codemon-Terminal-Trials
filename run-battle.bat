@echo off
REM Run BattleGame using Maven (Windows cmd)
REM Usage: run-battle.bat

cd /d "%~dp0"
REM Pass the -Dexec.mainClass before/with proper quoting
mvn compile
mvn exec:java
pause
