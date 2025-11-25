# Codemon - Battle Simulator Documentation

A Java-based turn-based Pokémon-like battle simulator using PokéAPI, featuring:
- Console UI with ANSI colorization (7 colors)
- Damage calculation with type effectiveness, STAB, critical hits
- Experience & leveling system (levels 1–99)
- Difficulty modes (Easy/Hard)
- Pause prompts for better UX
- Menu-driven gameplay

---

## 📋 File Structure

### Core Files (10 Total)

| File | Purpose | Key Responsibilities |
|------|---------|----------------------|
| **MainMenu.java** | Entry Point & UI | Menu navigation, colorized output (Colors inner class), Pokémon selection |
| **BattleGame.java** | Battle Engine | Battle loop, turn order, difficulty modes, damage calc, pause() utility (Colors inner class) |
| **PKM.java** | Pokémon Base/Concrete Class | Base model with stats, name, type, moves list |
| **Species.java** | Pokémon Concrete Impl. | Level, HP, maxHp, attack, defense, moves (restored from old design) |
| **Move.java** | Move Data Model | Immutable: name, type, power, accuracy, damageClass |
| **Factory.java** | Pokémon Creator | Creates Species from PokéAPI (1-151) by ID |
| **TypeEffectiveness.java** | Type Cache & Matchups | Lazy-load + cache type effectiveness multipliers from PokéAPI |
| **PKMList.java** | Pokémon List Viewer | Fetch and display first 151 Pokémon; pause prompt after list |
| **PokeAPI.java** | Debug Tool | Debug `main()` to fetch detailed stats for first 151 Pokémon |
| **Colors.java** | ANSI Color Utility | 7 color constants (RESET, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN) |

---

## 🔧 File Details

### MainMenu.java
```java
public class MainMenu {
    private static class Colors {
        static final String RESET = "\u001B[0m";
        static final String RED = "\u001B[31m";
        static final String GREEN = "\u001B[32m";
        // ... 5 more colors
    }
    
    public static void main(String[] args) { /* entry point */ }
    public static void run() { /* menu loop */ }
}
```
**Purpose**: Entry point with colorized menu system.  
**Features**:
- **Colors inner class**: 7 ANSI color constants (avoids external Colors.java dependency)
- **Color-coded menu options**: Battle (green), Pokémon List (blue), Credits (yellow), End Game (red)
- **Title art**: Displays with ANSI colors
- **Navigation**: Switch statement routing to submenus
- **Pokémon selection**: Prompts player for ID (1-151) to fetch from Factory

**OOP**: Encapsulation of UI logic; Colors inner class abstracts ANSI codes.

---

### BattleGame.java
```java
public class BattleGame {
    private static class Colors {
        // Same 7 colors as MainMenu
    }
    
    public static void startBattle(Scanner scanner) { ... }
    private static void battleLoop(Scanner scanner, Species player, Species opponent) { ... }
    private static void pause(Scanner scanner) {
        System.out.print(Colors.BLUE + "\nPress Enter to continue..." + Colors.RESET);
        scanner.nextLine();
    }
    private static String hpBar(int hp, int maxHp) {
        // Visual 20-char HP bar: [########----]
    }
}
```
**Purpose**: Main battle engine with difficulty modes, levels, and colorized output.  
**Key Features**:
- **Difficulty selection**: Easy (opponent -5 levels) vs Hard (opponent +0 to +2 levels)
- **Turn order**: Determined by level + RNG (higher level goes first)
- **Damage formula** (embedded):
  ```
  base = (level * 0.2 + 1) * power * (attack / defense)
  damage = floor(base * effectiveness * stab * variance * crit)
  - effectiveness: 2.0, 1.0, 0.5, 0.0 from TypeEffectiveness cache
  - stab: 1.5× if move type matches Pokémon type
  - variance: 0.85–1.0 random multiplier
  - crit: 1.5× on critical hit (6.25% chance)
  ```
- **HP bar visualization**: `hpBar(hp, maxHp)` returns visual 20-char gauge
- **Colors inner class**: ANSI colors for colorized battle output
- **pause() method**: Centralizes "Press Enter..." prompts; called after:
  - Battle victory/defeat
  - Player runs away
- **Experience gain**: 10–30 XP per battle
- **Leveling system**: Level up when XP ≥ 100 × (level - 1)

**OOP**: Encapsulation of battle logic; Colors inner class; static utility methods.

---

### Species.java
```java
public class Species extends PKM {
    private int level;
    private int hp, maxHp;  // NEW: Track max HP for visual bar
    private int attack, defense;
    private List<Move> moves;
    
    public Species(String name, String type, int level, int hp, int attack, int defense, List<Move> moves) {
        super(name, type, hp, attack, defense);  // May call PKM constructor
        this.level = level;
        this.hp = hp;
        this.maxHp = hp;  // Store original HP as max
        this.attack = attack;
        this.defense = defense;
        this.moves = moves;
    }
    
    public int getMaxHp() { return maxHp; }  // NEW: For HP bar calculations
    // Getters for level, hp, attack, defense, moves
}
```
**Purpose**: Concrete Pokémon implementation with level and experience tracking.  
**Key additions** (restored from old design):
- **level**: 1–99, determines turn order and damage scaling
- **maxHp**: Original HP value; used for visual HP bar calculations (NEW)
- **Experience tracking**: Gained from battles, determines leveling
- **Move list**: 2–4 moves per Pokémon (from PokéAPI)

**OOP**: Extends PKM; encapsulation of Pokémon state.

---

### Move.java
```java
public class Move {
    private final String name, type, damageClass;
    private final int power, accuracy;
    
    public Move(String name, String type, int power, int accuracy, String damageClass) { ... }
    // Immutable: getters only
}
```
**Purpose**: Immutable data model for moves.  
**OOP**: Encapsulation via final fields; demonstrates immutability pattern.

---

### Factory.java
```java
public final class Factory {
    public static Species createFromAPI(int id) {
        // Fetch from https://pokeapi.co/api/v2/pokemon/{id}
        // Extract: name, types, stats (hp, attack, defense)
        // Fetch moves for this Pokémon
        // Return new Species(name, type, level=1, hp, attack, defense, moves)
    }
}
```
**Purpose**: Creates Species instances from PokéAPI data.  
**Integration**: Called from MainMenu when player selects Pokémon ID.

---

### TypeEffectiveness.java
```java
public final class TypeEffectiveness {
    private static final Map<String, Map<String, Double>> cache = new HashMap<>();
    
    public static double getMultiplier(String attackType, String defenderType) {
        // Lazy-load from PokéAPI /type/{attackType}
        // Parse `damage_relations` JSON
        // Cache result; return multiplier or 1.0 on error
    }
}
```
**Purpose**: Fetch and cache type effectiveness multipliers.  
**Logic**:
- First call: HTTP fetch from `/type/{attackType}` endpoint
- Parse `damage_relations.double_damage_to`, `.half_damage_to`, `.no_damage_to`
- Map defender type → multiplier (2.0, 1.0, 0.5, 0.0)
- Cache for future calls
- Unknown → 1.0×

---

### PKMList.java
```java
public final class PKMList {
    public static void showList() {
        // Fetch from https://pokeapi.co/api/v2/pokemon?limit=151
        // Display: 1. bulbasaur, 2. ivysaur, ..., 151. mew
        // NEW: Pause prompt at end with ANSI blue color + System.in.read()
    }
}
```
**Purpose**: Display first 151 Pokémon.  
**NEW feature**: Pause prompt after list (UX improvement).

---

### PokeAPI.java
```java
public final class PokeAPI {
    public static void main(String[] args) {
        // Debug tool: Loop i=1 to 151
        // Fetch each Pokémon; print ID, Name, Types, Stats
    }
}
```
**Purpose**: Debug utility.  
**Usage**: `mvn exec:java -Dexec.mainClass=Codemon.PokeAPI`

---

### Colors.java (Standalone Utility)
```java
public final class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
}
```
**Purpose**: Centralized ANSI color constants.  
**Note**: Also defined as inner classes in MainMenu and BattleGame for local convenience.  
**OOP**: Demonstrates utility class pattern; static final constants.

---

## 🎨 Color Usage

**Colorization Strategy**:
1. MainMenu has inner Colors class → colorizes menu options
2. BattleGame has inner Colors class → colorizes battle UI (HP, damage, messages)
3. PKMList uses direct ANSI codes (blue) for pause prompt
4. Standalone Colors.java available for reuse

**Colors Applied**:
- **Red**: Errors, opponent actions, "End Game" menu
- **Green**: Player actions, "Battle" menu
- **Yellow**: Titles, "Pokémon List" menu
- **Blue**: Pause prompts, "Continue" messages
- **Purple**: Credits, special messages
- **Cyan**: Informational text
- **Reset**: Clears color formatting

---

## 🎮 Gameplay Flow

1. **MainMenu starts** → Colorized title + menu options
2. **Player selects "Battle"** → Difficulty selection (Easy/Hard)
3. **Opponent selected** → Randomly from Pokémon 1-151
4. **Player selects ID** → Factory fetches Species from PokéAPI
5. **Battle starts** → Opponent level set based on difficulty
6. **Battle loop**:
   - Display HP bars for both Pokémon
   - Determine turn order (level + RNG)
   - Player selects move
   - Damage calculated: `base * effectiveness * stab * variance * crit`
   - Opponent takes damage; HP updated; HP bar redrawn
   - Opponent selects random move
   - Player takes damage; HP updated
   - Repeat until one reaches 0 HP
7. **Victory/Defeat**:
   - Winner announced
   - XP gained (10–30)
   - Level up check (XP ≥ 100)
   - **pause()** called → "Press Enter to continue..."
8. **Return to MainMenu** → Ready for next battle

---

## 🎓 OOP Principles Demonstrated

### 1. **Encapsulation**
- **Species**: Private fields (hp, maxHp, level, attack, defense) with public getters
- **Move**: Final fields (immutable)
- **BattleGame**: Private static methods encapsulate turn resolution
- **MainMenu**: Private methods encapsulate UI logic

### 2. **Abstraction**
- **Colors**: Simple static constants hide ANSI escape codes
- **TypeEffectiveness**: Abstraction over HTTP + JSON (user calls `getMultiplier()`)
- **Factory**: Abstraction over PokéAPI calls
- **BattleGame.pause()**: Centralizes pause logic (called from multiple places)

### 3. **Inheritance**
- **Species extends PKM**: Adds level, maxHp; extends base Pokémon

### 4. **Exception Handling**
- Try-catch in TypeEffectiveness for HTTP errors
- Try-catch in PKMList for API failures
- Try-catch in Factory for JSON parsing
- Fallback defaults (1.0× multiplier, error messages)

### 5. **Collections**
- **Species.moves**: `List<Move>` for move sets
- **TypeEffectiveness.cache**: `Map<String, Map<String, Double>>` for 2D type matrix
- **Collections.shuffle()** for random opponent move selection

### 6. **Static Utility Pattern**
- **Colors**: Static final constants
- **TypeEffectiveness**: Static cache + method
- **Factory**: Static factory method
- **PKMList**: Static showList() method
- **BattleGame.pause()**: Static utility method

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

### View Pokémon List
```bash
mvn exec:java -Dexec.mainClass=Codemon.PKMList
```

### Run Debug Tool (Dump All Pokémon Data)
```bash
mvn exec:java -Dexec.mainClass=Codemon.PokeAPI
```

---

## 🔌 Dependencies

- **JDK 21**: Target Java version
- **org.json:json:20230618**: JSON parsing for PokéAPI
- **PokéAPI v2**: External API (https://pokeapi.co/api/v2/)
- **JUnit 5 + Mockito**: For testing (optional)

---

## 📝 Current Features

✅ **Turn-based combat** with move selection  
✅ **Type effectiveness** with cached PokéAPI lookups  
✅ **STAB bonuses** (1.5× if type matches)  
✅ **Critical hits** (6.25% chance, 1.5× damage)  
✅ **Damage variance** (0.85–1.0 random)  
✅ **Experience & leveling** (10–30 XP, level up at 100 XP thresholds)  
✅ **Difficulty modes** (Easy/Hard)  
✅ **Level-based turn order** (higher level goes first + RNG)  
✅ **ANSI colorized UI** (7 colors, menu + battle output)  
✅ **Visual HP bars** (20-char gauge with # and -)  
✅ **Pause prompts** (UX: after battles, running away, Pokémon list)  
✅ **PokéAPI integration** (fetch real Pokémon 1-151)  
✅ **Simplified 10-file design** (focused, cohesive classes)  

---

## 🧹 Cleanup & Evolution

**Old (Removed)**:
- Initial 11-file design with redundancy
- Monolithic single-file approach (abandoned)
- Simple 8-file without levels/difficulty

**Current (Evolved to)**:
- 10-file focused design
- Species.java + Factory.java restored (levels, API-based creation)
- BattleGame enhanced with difficulty modes + level system
- Colors inner classes in MainMenu + BattleGame (localized convenience)
- pause() method centralized in BattleGame
- maxHp tracking in Species for HP bar support
- Pause prompts after battles, runs, and Pokémon list

**Build Status**: ✅ SUCCESS (10 source files, mvn clean compile)  
**Last Updated**: 2025-11-25  
**Java Version**: 21  
**Maven Version**: 3.11.0


