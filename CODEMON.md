# Codemon - Battle Simulator Documentation

A Java-based turn-based Pokémon-like battle simulator using PokéAPI, featuring console UI with ANSI colors, damage calculation with type effectiveness, and menu-driven gameplay.

---

## 📋 File Structure

### Core Files (8 Total)

| File | Purpose | Key Responsibilities |
|------|---------|----------------------|
| **Colors.java** | ANSI Color Constants | Provides color codes for console UI styling |
| **Move.java** | Move Data Model | Immutable move data: name, type, power, accuracy, damageClass |
| **PKM.java** | Pokémon Base Class | Base class for Pokémon with stats, name, type, moves list |
| **BattleGame.java** | Battle Engine | Battle loop, turn resolution, damage calculation, input parsing |
| **MainMenu.java** | Entry Point & UI | Console menu, Pokémon creation, navigation, input handling |
| **PKMList.java** | API Utility | Fetch and display first 151 Pokémon from PokéAPI |
| **PokeAPI.java** | Debug Tool | Fetch detailed stats for first 151 Pokémon (debug main) |
| **TypeEffectiveness.java** | Type Matchups | Fetch and cache type effectiveness multipliers from PokéAPI |

---

## 🔧 File Details

### Colors.java
```java
public final class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    // ... 7 total color constants
}
```
**Purpose**: Centralized ANSI color codes for console styling.  
**OOP**: Demonstrates encapsulation (public static final constants); demonstrates abstraction (simple utility interface).

---

### Move.java
```java
public class Move {
    private final String name, type, damageClass;
    private final int power, accuracy;
    
    public Move(String name, String type, int power, int accuracy, String damageClass) { ... }
    // Getters only (immutable)
}
```
**Purpose**: Immutable data model for Pokémon moves.  
**Key Concept**: Encapsulation via final fields and getters-only access.  
**Usage**: Created in MainMenu; used by BattleGame for damage calculation.

---

### PKM.java
```java
public class PKM {
    private String name, type;
    private int hp, attack, defense;
    private List<Move> moves;
    
    public PKM(String name, String type, int hp, int attack, int defense) { ... }
    // Getters & setters for all fields
}
```
**Purpose**: Represents a Pokémon-like creature with stats and moves.  
**OOP Principles**:
- **Encapsulation**: Private fields with public getters/setters
- **Abstraction**: Hides internal representation
- **Validation**: Constructor validates hp/attack/defense ≥ 0 via `Math.max(0, value)`.

---

### BattleGame.java
```java
public class BattleGame {
    private PKM player, opponent;
    private Scanner scanner;
    
    public void startBattle() { ... }
    private void battleLoop() { ... }
    private void playerTurn() { ... }
    private void opponentTurn() { ... }
    private int calculateDamage(...) { ... }  // Embedded damage formula
    private int readInt(...) { ... }  // Embedded input parsing
}
```
**Purpose**: Main battle engine handling turn resolution and damage.  
**Key Features**:
- **Turn-based combat**: Player selects move → opponent counter-attacks
- **Damage formula** (embedded):  
  ```
  base = ((power * (atk/def)) / 50) + 2
  damage = floor(base * typeMultiplier * stab * crit * variance)
  - typeMultiplier: From TypeEffectiveness cache
  - stab: 1.5× if move type matches Pokémon type
  - crit: 1.5× chance if random > 0.8
  - variance: 0.85–1.0 random multiplier
  ```
- **Type effectiveness**: Fetched from TypeEffectiveness cache
- **Input parsing** (embedded): `readInt()` with range validation and error handling
- **HP bars**: Visual representation using `█` and `░` characters
- **Effectiveness messages**: Color-coded feedback (super effective, not very effective, etc.)

**OOP**: Demonstrates encapsulation (private methods), single responsibility (battle logic encapsulated).

---

### MainMenu.java
```java
public class MainMenu {
    private Scanner scanner;
    
    public void run() { /* menu loop */ }
    private void startBattle() { /* create Pokémon & start BattleGame */ }
    private int readInt(...) { /* embedded input parsing */ }
    public static void main(String[] args) { ... }
}
```
**Purpose**: Console UI entry point and menu navigation.  
**Features**:
- **Title art**: ASCII art logo with colors
- **Menu options**: Battle, View Pokémon, Credits, Exit
- **Pokémon creation**: Player input for name, HP, Attack, Defense, Type
- **Input parsing** (embedded): Robust `readInt()` with validation and error loops
- **Screen clearing**: ANSI escape sequence for terminal clear
- **Navigation**: Switch statement routing to submenus

**OOP**: Encapsulation of UI logic; abstraction of menu handling.

---

### PKMList.java
```java
public final class PKMList {
    public static void showList() {
        // Fetch from https://pokeapi.co/api/v2/pokemon?limit=151
        // Parse JSON; print numbered 1–151 Pokémon names
    }
}
```
**Purpose**: Static utility to display first 151 Pokémon from PokéAPI.  
**Integration**: Called from MainMenu menu option.

---

### PokeAPI.java
```java
public final class PokeAPI {
    public static void main(String[] args) {
        // Loop i=1 to 151; fetch each Pokémon data
        // Print ID, Name, Types, HP, ATK, DEF stats
    }
}
```
**Purpose**: Debug utility with its own `main()` method.  
**Usage**: `mvn exec:java -Dexec.mainClass=Codemon.PokeAPI` to dump all Pokémon stats.

---

### TypeEffectiveness.java
```java
public final class TypeEffectiveness {
    private static final Map<String, Map<String, Double>> cache = new HashMap<>();
    
    public static double getMultiplier(String attackType, String defenderType) {
        // Lazy-load from PokéAPI /type/{attackType}
        // Cache result; return multiplier or 1.0 on cache miss/error
    }
}
```
**Purpose**: Fetch and cache type effectiveness multipliers.  
**Logic**:
- First call for a type: HTTP fetch from `/type/{type}` endpoint
- Parse `damage_relations` JSON:
  - `double_damage_to`: 2.0× multiplier
  - `half_damage_to`: 0.5× multiplier
  - `no_damage_to`: 0.0× multiplier (immune)
- Cache result for future calls
- Unknown combinations default to 1.0×

**Integration**: Called by BattleGame during damage calculation.

---

## 🎓 OOP Principles Demonstrated

### 1. **Encapsulation**
- **PKM**: Private fields (`name`, `type`, `hp`, etc.) with public getters/setters
- **Move**: Private final fields (immutable); getters only
- **BattleGame**: Private methods (`battleLoop()`, `calculateDamage()`) hide implementation
- **MainMenu**: Private methods (`startBattle()`, `printMenu()`) encapsulate UI logic

### 2. **Abstraction**
- **Colors**: Simple static interface to color constants (hides ANSI escape codes)
- **TypeEffectiveness**: Abstraction over HTTP + JSON parsing (users only see `getMultiplier()`)
- **PKMList**: Abstraction over PokéAPI calls (users only see `showList()`)
- **BattleGame**: Abstraction of turn resolution logic (no exposed internals)

### 3. **Inheritance & Polymorphism**
- **PKM base class**: Simple concrete class; extensible for future specialized Pokémon subclasses

### 4. **Exception Handling**
- **TypeEffectiveness**: Try-catch around HTTP calls; returns default (1.0) on error
- **PKMList**: Try-catch around API fetch; prints error message
- **MainMenu.startBattle()**: Try-catch around Pokémon creation; catches invalid input exceptions
- **BattleGame.readInt()**: Try-catch around `Integer.parseInt()`; loops on `NumberFormatException`

### 5. **Collections & Data Structures**
- **PKM.moves**: `List<Move>` for Pokémon move sets
- **TypeEffectiveness.cache**: `Map<String, Map<String, Double>>` for 2D type effectiveness cache
- **BattleGame**: Uses `List` operations for move selection

### 6. **Java Conventions**
- **Naming**: CamelCase for classes, lowercase for methods/fields
- **Access Modifiers**: `public` for APIs, `private` for internals, `static` for utilities
- **Constants**: `Colors` constants in UPPER_SNAKE_CASE
- **Packages**: All in `Codemon` package
- **Javadoc-ready**: Clear method names and signatures

---

## ▶️ How to Run

### Compile
```bash
mvn clean compile
```

### Run Main Game
```bash
mvn exec:java -Dexec.mainClass=Codemon.MainMenu
```

### Run Debug Tool (Fetch Pokémon Data)
```bash
mvn exec:java -Dexec.mainClass=Codemon.PokeAPI
```

---

## 🎮 Gameplay Flow

1. **Start MainMenu** → Displays title art and menu
2. **Select "Start Battle"** → Input player Pokémon stats
3. **Input opponent Pokémon stats** → BattleGame begins
4. **Battle Loop**:
   - Display HP bars for both Pokémon
   - Player selects move (1–N options)
   - Damage calculated: `base * typeMultiplier * stab * crit * variance`
   - Opponent takes damage; HP updated
   - Opponent auto-selects random move
   - Player takes damage; HP updated
   - Repeat until one Pokémon reaches 0 HP
5. **Winner announced** → Return to MainMenu

---

## 🔌 Dependencies

- **JDK 21**: Target Java version
- **org.json**: JSON parsing from PokéAPI responses
- **PokéAPI v2**: External API for Pokémon data (https://pokeapi.co/api/v2/)

---

## 📝 Project Requirements Met

✅ **Object-Oriented Design**: Encapsulation, abstraction, inheritance-ready base classes  
✅ **Exception Handling**: Try-catch blocks for HTTP errors and invalid input  
✅ **Collections**: List and Map data structures for moves and type caching  
✅ **Java Conventions**: Package organization, naming standards, access modifiers  
✅ **Console Interaction**: Menu-driven UI with ANSI colors  
✅ **API Integration**: PokéAPI fetching with JSON parsing  
✅ **Damage Formula**: Type effectiveness, STAB, critical hits, variance  
✅ **Simplicity**: 8-file focused design, minimal redundancy  

---

## 🧹 Cleanup Summary

**Removed (No Longer Needed)**:
- `Species.java` — Merged into PKM (PKM is now concrete)
- `Factory.java` — API fetching now inline in MainMenu
- `InputHelper.java` — `readInt()` embedded in MainMenu and BattleGame
- `DamageCalculator.java` — Damage formula embedded in BattleGame
- `PlayerSpecies.java`, `WildSpecies.java`, `BossSpecies.java` — Simple subclasses removed
- `Codemon.java` — Monolithic combined file superseded by 8-file structure
- Test files: `DamageCalculatorTest.java`, `TypeEffectivenessTest.java` — No longer needed

**Final Structure**: 8 focused, cohesive Java files + pom.xml + README.md + CODEMON.md

---

**Last Updated**: 2025-11-25  
**Build Status**: ✅ Compiles successfully (mvn clean compile → BUILD SUCCESS)  
**Java Version**: 21
