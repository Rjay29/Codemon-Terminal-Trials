# 🎮 Codémon: Terminal Trials

**A terminal-based, object-oriented Pokémon-style battle simulator** that fetches real species data from [PokéAPI](https://pokeapi.co/). Turn-based combat with type effectiveness, critical hits, and strategic move selection—all in your console.

---

## 📋 Table of Contents

1. [Project Title](#project-title)
2. [Description & Overview](#description--overview)
3. [OOP Concepts Applied](#oop-concepts-applied)
4. [Program Structure](#program-structure)
5. [How to Run](#how-to-run)
6. [Sample Output](#sample-output)
7. [Author & Acknowledgement](#author--acknowledgement)
8. [Future Enhancements](#future-enhancements)
9. [References](#references)

---

## Project Title

**Codémon: Terminal Trials** — A Turn-Based Battle Simulator

---

## Description & Overview

**Purpose**: Codémon is a Java command-line game demonstrating object-oriented design principles while delivering a playable turn-based battle experience. Players create custom Pokémon-like creatures or fetch real species from PokéAPI, then engage in strategic turn-based combat.

**Main Features**:
- 🎮 **Turn-based combat** with move selection, type effectiveness, STAB bonuses, and critical hits
- 🌐 **PokéAPI integration** for real Pokémon type relationships and stats
- 🎨 **ANSI-colored console UI** with title art, menus, and HP bars
- 📊 **Damage formula** incorporating type matchups, attack/defense stats, and variance
- 📜 **Pokédex viewer** to browse first 151 Pokémon names
- 🔍 **Type effectiveness system** with cached lookups for performance

**Problem Solved**: Educational tool for learning OOP design patterns while creating an engaging, interactive console application that integrates with real-world APIs.

---

## OOP Concepts Applied

### 1. **Encapsulation**
Private fields with controlled access through getters and setters ensure data integrity.

**Example: `PKM.java`**
```java
public class PKM {
    private String name, type;
    private int hp, attack, defense;
    private List<Move> moves;
    
    public PKM(String name, String type, int hp, int attack, int defense) {
        this.hp = Math.max(0, hp);  // Validation
        this.attack = Math.max(0, attack);
        this.defense = Math.max(0, defense);
    }
    
    public void setHp(int hp) { this.hp = Math.max(0, hp); }  // Setter validation
    public int getHp() { return hp; }
}
```
- Private fields prevent direct manipulation
- Constructor & setters validate inputs (e.g., no negative stats)
- Public getters provide safe read access

### 2. **Abstraction**
Complex operations are hidden behind simple interfaces.

**Example: `TypeEffectiveness.java`**
```java
public static double getMultiplier(String attackType, String defenderType) {
    // Complex logic: HTTP fetch, JSON parsing, caching
    // User sees only: getMultiplier(type1, type2) → 1.0, 2.0, 0.5, etc.
}
```
- Users don't see HTTP calls, JSON parsing, or caching details
- Simple method signature masks complex API integration

### 3. **Inheritance & Polymorphism**
Base class defines common structure; subclasses extend functionality.

**Example: Class Hierarchy**
- `PKM` (base class) — defines shared Pokémon properties
- Future subclasses could specialize (e.g., `LegendaryPKM`, `MythicalPKM`)

### 4. **Exception Handling**
Graceful error management for network and user input errors.

**Example: `TypeEffectiveness.java`**
```java
try {
    URL url = URI.create("https://pokeapi.co/api/v2/type/" + attackType).toURL();
    // ... fetch and parse JSON
} catch (Exception e) {
    return 1.0;  // Default multiplier on error
}
```
- Network failures return sensible defaults
- No crashes; game continues

### 5. **Collections & Generics**
Type-safe data structures for managing moves, type caches, and Pokémon lists.

**Example: `PKM.java`**
```java
private List<Move> moves;  // Type-safe list of moves
```

**Example: `TypeEffectiveness.java`**
```java
private static final Map<String, Map<String, Double>> cache;  // 2D type cache
```

### 6. **Single Responsibility Principle**
Each class has one clear purpose.

| Class | Responsibility |
|-------|-----------------|
| `PKM` | Pokémon data model (stats, moves) |
| `Move` | Move data model (name, type, power) |
| `BattleGame` | Battle logic (turns, damage) |
| `MainMenu` | Console UI & navigation |
| `TypeEffectiveness` | Type matchup caching |
| `Colors` | ANSI color constants |

---

## Program Structure

### Class Diagram (Text-Based)

```
┌─────────────────────────────────────────────────────────┐
│                      MainMenu (Entry)                    │
│  - main(): Console menu & Pokémon creation              │
│  - startBattle(): Initiate BattleGame                   │
└────────────────────────┬────────────────────────────────┘
                         │ creates
                         ↓
        ┌────────────────────────────┐
        │       BattleGame           │
        │  - startBattle()           │
        │  - battleLoop()            │
        │  - calculateDamage()       │
        └────────┬───────────────────┘
                 │ uses
        ┌────────┴───────────────────┐
        ↓                             ↓
    ┌─────────┐              ┌──────────────────┐
    │   PKM   │              │ TypeEffectiveness│
    │ (name,  │              │ - getMultiplier()│
    │  type,  │              │ - cache          │
    │  stats) │              └──────────────────┘
    └────┬────┘
         │ contains
         ↓
    ┌──────────┐
    │   Move   │
    │ (name,   │
    │  type,   │
    │  power)  │
    └──────────┘

┌──────────────────────────────────────────┐
│         Utility Classes                   │
├──────────────────────────────────────────┤
│ Colors       → ANSI color constants      │
│ PKMList      → Fetch & display Pokémon   │
│ PokeAPI      → Debug tool (main method)  │
└──────────────────────────────────────────┘
```

### Key Classes

| Class | Lines | Purpose |
|-------|-------|---------|
| `MainMenu.java` | 110 | Entry point; console UI; Pokémon creation |
| `BattleGame.java` | 120 | Battle loop; turn resolution; damage calc |
| `PKM.java` | 25 | Pokémon data model with stats & moves |
| `Move.java` | 20 | Immutable move data model |
| `TypeEffectiveness.java` | 45 | Type cache & API fetching |
| `PKMList.java` | 30 | Display first 151 Pokémon |
| `Colors.java` | 11 | ANSI color constants |
| `PokeAPI.java` | 35 | Debug utility |

---

## How to Run

### Prerequisites
- **JDK 21** or later
- **Maven 3.8** or later
- **Internet connection** (for PokéAPI calls)

### Step 1: Clone the Repository
```bash
git clone https://github.com/LazyAustin525/Codemon-Terminal-Trials.git
cd Codemon
```

### Step 2: Compile the Project
```bash
mvn clean compile
```

### Step 3: Run the Game
```bash
mvn exec:java -Dexec.mainClass=Codemon.MainMenu
```

### Step 4 (Optional): Run Debug Tool
Fetch and display all 151 Pokémon stats:
```bash
mvn exec:java -Dexec.mainClass=Codemon.PokeAPI
```

---

## Sample Output

### Main Menu
```
  \  \___/ / \_\_  |
   \  /  \__  ___/  |
    \ \___/  \___/  |
     \ \___/  \___/ |
      \\\___/  \___/  |
    CODEMON - Battle Simulator

=== Main Menu ===
1. Start Battle
2. View Pokémon List
3. Credits
4. Exit

Enter your choice (1-4): 1
```

### Battle Prompt
```
Enter player Pokémon name: Pikachu
Enter HP (50-200): 100
Enter Attack (30-150): 90
Enter Defense (30-150): 60
Enter type: electric

Enter opponent Pokémon name: Squirtle
Enter HP (50-200): 100
Enter Attack (30-150): 70
Enter Defense (30-150): 80
Enter type: water
```

### Battle In Progress
```
=== Battle Start ===
Player: Pikachu (HP: 100) | Opponent: Squirtle (HP: 100)

--- Turn 1 ---
Player: [██████████░░░░░░░░] 100 | Opponent: [██████████░░░░░░░░] 100

Player's turn:
1. Tackle
2. Ember
Choose move (1-2): 2

Player used Ember!
Super effective!
Damage dealt: 42

Opponent's turn:
Opponent used Surf!
Super effective!
Damage taken: 58

--- Turn 2 ---
Player: [████████░░░░░░░░░░] 42 | Opponent: [████████░░░░░░░░░░] 58
...

=== You won! ===
Press Enter to continue...
```

---

## Author & Acknowledgement

**Author**: LazyAustin525  
**Date**: November 2025  
**License**: MIT

**Acknowledgements**:
- 🙏 [PokéAPI](https://pokeapi.co/) — Comprehensive Pokémon data API
- 🎓 Object-Oriented Programming principles & design patterns
- 📚 Java documentation & Maven build tools

---

## Future Enhancements

- ⚡ **Leveling System** — Pokémon gain experience and level up
- 💾 **Save/Load Game** — Persist player progress to file
- 🎯 **Difficulty Modes** — Easy, Normal, Hard with AI strategies
- 🏆 **Leaderboard** — Track high scores and win streaks
- 🌐 **Multiplayer** — Network-based battles between players
- 📊 **Statistics Tracking** — Win/loss ratios, damage dealt, etc.
- 🎵 **Sound Effects** — ASCII-based sound or integration with system audio
- 🗺️ **Gym Leaders** — Pre-built boss Pokémon to challenge

---

## References

- [PokéAPI Documentation](https://pokeapi.co/docs/v2)
- [Java OOP Concepts](https://docs.oracle.com/javase/tutorial/java/concepts/)
- [Pokémon Type Effectiveness Chart](https://bulbapedia.bulbagarden.net/wiki/Type)
- [Maven Build Tool](https://maven.apache.org/)
- [Effective Java (3rd Edition)](https://www.oreilly.com/library/view/effective-java/9780134685991/) — Design patterns & best practices

---

**Build Status**: ✅ SUCCESS (8 source files)  
**Last Updated**: November 25, 2025

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