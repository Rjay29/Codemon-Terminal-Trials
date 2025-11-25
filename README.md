# 🎮 Codémon: Terminal Trials

A fully terminal-based, object-oriented Pokémon battle simulator powered by real-time data from the [PokéAPI](https://pokeapi.co/). Inspired by the classic Pokémon Red/Blue battle system, this Java project brings turn-based combat, leveling, type effectiveness, and save/load mechanics to your command line.

---

## 📌 Introduction

This project is a Java-based command-line game that simulates Pokémon battles using real data from the PokéAPI. It features a main menu, difficulty modes, strategic turn-based combat, and a save/load system. The game is designed to demonstrate core Object-Oriented Programming (OOP) principles while delivering a nostalgic and educational experience.

---

## 🧠 How It Works + OOP Concepts

"""
# 🎮 Codémon: Terminal Trials

A terminal-based, object-oriented Pokémon-style battle simulator that fetches real move and species data from the PokéAPI. The program demonstrates OOP concepts while providing a nostalgic, turn-based battle experience in the console.

---

## 1 — Project Title

Codémon: Terminal Trials

---

## 2 — Description / Overview

Codémon is a Java command-line game that simulates turn-based battles between Pokémon-like species. Each species is constructed from real API data (moves, types, base stats) and battles use type effectiveness, STAB, critical hits, and random variance to compute damage. The project is primarily educational: it demonstrates object-oriented design while producing a small playable game.

Main features:
- Fetch species and move data from PokéAPI
- Turn-based combat with move accuracy, criticals, STAB, and type multipliers
- Simple console UI with a Codéx (Pokémon list), battle menu, and colored prompts

---

## 3 — OOP Concepts Applied

The project applies core OOP principles. Below are the main concepts and how they appear in the codebase.

- Abstraction
   - `PKM` (abstract) defines the shared attributes and behavior common to all species.

- Encapsulation
   - Fields such as `hp`, `attack`, and `defense` are private to classes like `Species` and exposed through getters/setters.

- Inheritance
   - `Species` and (project pattern) classes share some base attributes via `PKM`; concrete classes represent specific functionality built from this base.

- Polymorphism
   - Methods that operate on `PKM` or `Species` can accept any concrete species instance. Move behavior is represented by `Move` objects and used uniformly.

---

## 4 — Program Structure

Top-level packages and classes (brief):

- `Codemon.MainMenu` — application entry point; prints the title, receives user input, navigates to Battle or Codéx.
- `Codemon.BattleGame` — contains the battle loop, move selection, damage calculation, and turn resolution.
- `Codemon.Factory` — fetches and builds `Species` and `Move` objects from the PokéAPI.
- `Codemon.Species` — holds species data (name, type, hp, attack, defense, moves).
- `Codemon.Move` — represents a move (name, type, power, accuracy, damage class).
- `Codemon.PKMList` — prints the first 151 Pokémon names (the Codéx).
- `Codemon.TypeEffectiveness` — helper for type matchups (returns multipliers).

Class relationships (simple list):

- `MainMenu` → uses `BattleGame` and `PKMList`.
- `BattleGame` → uses `Species`, `Move`, `TypeEffectiveness`, and `Factory`.
- `Factory` → constructs `Species` and `Move` objects.

---

## 5 — How to Run the Program

Requirements:
- Java JDK 17 or later
- Apache Maven

Steps (Windows PowerShell):

1. Open PowerShell and change to the project directory (where `pom.xml` is):

```powershell
cd "C:\Users\<you>\path\to\Codemon"
```

2. Compile the project with Maven:

```powershell
mvn clean compile
```

3. Run the program using the exec plugin (the main class is configured in `pom.xml`):

```powershell
mvn exec:java
```

Notes:
- If VS Code reports missing class files, run the Maven compile above and then reload/clean the Java language server (Ctrl+Shift+P → "Java: Clean the Java language server workspace").

---

## 6 — Sample Output

Below is a short example of what you will see when you run the game (trimmed):

```text
--- Terminal Pokémon Battle ---
1. Battle
2. Pokémon List
3. Credits
4. End Game
Choose: 1

Choose difficulty:
1. Easy  2. Hard
1 or 2?: 1
Opponent: Pikachu (Type: electric)
Choose your Pokémon ID (1-151): 25

⚔️ Battle Start! ⚔️
Go! Pikachu!

=== Battle Menu ===
Pikachu HP: [██████████----------] 35/70   Bulbasaur HP: [██████------------] 22/50
1. Fight
2. Run
Choose: 1

Your Moves:
1. Thunder Shock (electric, 40)
2. Growl (normal, 0)
Choose a move: 1
Pikachu used Thunder Shock! It's super effective! Dealt 15 damage.

*** Victory! ***
Press Enter to continue...
```

---

## 7 — Author and Acknowledgements

Authors:
- Jev Austin Apolinar
- Rjay Arazula
- Ken Frankie Mendoza

Acknowledgements:
- PokéAPI (https://pokeapi.co/) for species and move data
- The Java community and open-source libraries used during development

---

## 8 — Other Sections (optional)

### Future Enhancements
- Add persistent save/load with a consistent file format
- Implement more complete battle rules (status conditions, abilities, items)
- Add automated tests and CI checks

### References
- PokéAPI — https://pokeapi.co/
- Maven Exec Plugin — https://www.mojohaus.org/exec-maven-plugin/

"""