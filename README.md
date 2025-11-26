# 🎮 Codémon: Terminal Trials

---

## Project Title
**Codémon: Terminal Trials**

---

## Description / Overview
Codémon is a command-line, turn-based Pokémon-inspired battle game built in Java.  
It retrieves real Pokémon data from the PokéAPI and simulates classic mechanics such as moves, accuracy, type effectiveness, and leveling.  
The project demonstrates core Object-Oriented Programming concepts while providing a terminal-based battle experience.

## 3️⃣ OOP Concepts Applied

### 🧩 Abstraction
- The `Pokemon` abstract class defines shared Pokémon properties and behaviors.

### 🔐 Encapsulation
- Pokémon attributes (stats, moves) are private and accessed through getters and setters.

### 🧬 Inheritance
- `PokemonSpecies` inherits from `Pokemon` and loads species data from the PokéAPI.

### 🎭 Polymorphism
- Methods like `useMove()` behave differently depending on the Pokémon object invoking them.

---

## Program Structure

### 📂 Class / File Descriptions

| Class / File | Description |
|--------------|-------------|
| `MainMenu.java` | Entry point; displays the main menu (Battle, Load, Pokémon List, Credits, Exit). |
| `BattleGame.java` | Handles turn order, damage formulas, XP system, and difficulty. |
| `PKM.java` | Abstract class defining base Pokémon attributes. |
| `Species.java` | Subclass representing API-loaded Pokémon species. |
| `Move.java` | Stores move power, accuracy, and type. |
| `MoveFactory.java` | Retrieves Pokémon move data from PokéAPI. |
| `TypeEffectiveness.java` | Calculates type matchup multipliers. |
| `PKMList.java` | Pokédex Viewer | Displays all 151 Pokémon with pause prompt |
| `PokeAPI.java` | Debug Tool | Exports detailed stats for all Pokémon |

---

### Class Diagram (Text-Based)

```
┌─────────────────────────────────────────────────────────┐
│                  MainMenu (Entry)                       │
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
    │  (name,  │
    │   type,  │
    │   power) │
    └──────────┘

┌──────────────────────────────────────────┐
│              Utility Classes             │
├──────────────────────────────────────────┤
│ Colors       → ANSI color constants      │
│ PKMList      → Fetch & display Pokémon   │
│ PokeAPI      → Debug tool (main method)  │
└──────────────────────────────────────────┘
```

### Key Classes (9 Files)

| Class | Purpose | Responsibilities |
|-------|---------|------------------|
| `MainMenu.java` | Entry Point & UI | Menu loop, colorized options, Pokémon ID selection |
| `BattleGame.java` | Battle Engine | Turn-based combat, damage calc, difficulty modes, pause |
| `PKM.java` | Base Pokémon Model | Base class with stats (name, type, hp, attack, defense) |
| `Species.java` | Concrete Pokémon | Level, experience, maxHp tracking, level-ups |
| `Move.java` | Immutable Move Data | Move properties (name, type, power, accuracy) |
| `Factory.java` | Pokémon Creator | Creates Species from PokéAPI (fetches 1-151) |
| `TypeEffectiveness.java` | Type Cache | Caches type effectiveness multipliers from PokéAPI |
| `PKMList.java` | Pokédex Viewer | Displays all 151 Pokémon with pause prompt |
| `PokeAPI.java` | Debug Tool | Exports detailed stats for all Pokémon |

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
mvn exec:java
```
The game features **colorful ANSI terminal UI** with color-coded menus and battle output.

### Step 4 (Optional): Run Debug Tool
Fetch and display all 151 Pokémon stats:
```bash
mvn exec:java -Dexec.mainClass=Codemon.PokeAPI
```

### Step 5 (Optional): View Pokémon List
View and browse the first 151 Pokémon:
```bash
mvn exec:java -Dexec.mainClass=Codemon.PKMList
```

---

## Sample Output

### Main Menu (Colorful Terminal)
```
--- Terminal Pokémon Battle ---
1. Battle
2. Pokémon List
3. Credits
4. End Game

Choose: 1
```

### Difficulty Selection
```
Choose difficulty:
1. Easy
2. Hard
1 or 2?: 1

Opponent: Charizard (Type: fire)
Choose your Pokémon ID (1-151): 25
```

### Battle in Progress (Colorful with HP Bars)
```
⚔️ Battle Start! ⚔️
Go! Pikachu!

=== Battle Menu ===
Pikachu HP: [████████████████████]   Charizard HP: [████████████████████]
1. Fight
2. Run

Choose: 1

Your Moves:
1. Thunder Shock (electric, 40)
2. Thunder Wave (electric, 0)

Choose a move: 1

Pikachu used Thunder Shock! It's super effective! Dealt 65 damage.
Charizard used Flame Burst! Not very effective... Dealt 22 damage.

=== Battle Menu ===
Pikachu HP: [████████████████████]   Charizard HP: [█████████████████---]

Choose: 1

Pikachu used Thunder Shock! A critical hit! It's super effective! Dealt 97 damage.
*** Charizard fainted! ***

*** Victory! ***
Pikachu gained 28 XP!
Pikachu leveled up to Lv 2!
Max HP increased to 36!

Press Enter to continue...
```

### Pokémon List
```
=== First 151 Codémon ===
1. bulbasaur
2. ivysaur
3. venusaur
4. charmander
5. charmeleon
... (151 total)

Press Enter to continue...
```

---
## Author and Acknowledgement

Created by:  
- Apolinar, Jev Austin  
- Arazula, Rjay  
- Mendoza, Ken Frankie  

**Acknowledgements**:
- 🙏 [PokéAPI](https://pokeapi.co/) - Comprehensive Pokémon data API
- 🎓 Object-Oriented Programming principles & design patterns
- 📚 Java documentation & Maven build tools

---

### 🛠️ Future Enhancements
- Additional Pokémon generations  
- More battle game modes  
- Expanded save/load system  

---

## References

- [PokéAPI Documentation](https://pokeapi.co/docs/v2)
- [Java OOP Concepts](https://docs.oracle.com/javase/tutorial/java/concepts/)
- [Pokémon Type Effectiveness Chart](https://bulbapedia.bulbagarden.net/wiki/Type)
- [Maven Build Tool](https://maven.apache.org/)
- [Effective Java (3rd Edition)](https://www.oreilly.com/library/view/effective-java/9780134685991/) - Design patterns & best practices

---
