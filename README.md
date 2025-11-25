## Sample Output

Below are concise examples of what you'll see while running the game. Color names are shown as labels (e.g. [GREEN]) for readability — the terminal shows ANSI colors.

### Main Menu (Colorful Terminal)
```
[GREEN] 1. Battle
[BLUE]  2. Pokémon List
[YELLOW]3. Credits
[RED]   4. End Game

Choose: 1
```

### Difficulty Selection
```
Choose difficulty:
1. Easy
2. Hard
Choice: 1

Opponent: #006 Charizard (fire)
Choose your Pokémon ID (1-151): 25  (Pikachu)
```

### Battle in Progress (Colorful with HP Bars)
```
~~Battle Start!~~
Go! Pikachu (Lv 5)

Pikachu HP: [██████████----------]   Charizard HP: [██████████████------]
2. Run

Choose: 1

Your Moves:
1. Thunder Shock (electric, 40)
2. Quick Attack (normal, 40)

Choose a move: 1

Pikachu used Thunder Shock! It's super effective! Dealt 65 damage.
Charizard used Flamethrower! Not very effective... Dealt 22 damage.

=== Battle Menu ===
Pikachu HP: [███████████---------]   Charizard HP: [█████████-----------]
...
```

### Victory Screen
```
*** Victory! ***
Pikachu gained 28 XP!
Pikachu leveled up to Lv 6!
Max HP increased to 62

[BLUE]Press Enter to continue...
```

### Pokémon List
=== First 151 Codémon ===
1. bulbasaur
2. ivysaur
3. venusaur
4. charmander
5. charmeleon
... (total 151)

[BLUE]Press Enter to continue...
```
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
- `PKM` (base class) - defines shared Pokémon properties
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

## Gameplay Mechanics

### Battle System Features
- **Difficulty Modes**: 
  - Easy: Opponent level = Player level - 5
  - Hard: Opponent level = Player level + 0 to +2
- **Turn Order**: Determined by Pokémon level + RNG (higher level acts first)
- **Move Selection**: Player chooses from 2-4 available moves per battle
- **Opponent AI**: Randomly selects moves (simple but effective)
- **Running Away**: 50% success rate to escape battle

### Damage Calculation
```
Base Damage = (Level × 0.2 + 1) × Power × (Attack / Defense) × Effectiveness × STAB × Variance × Crit

Where:
- Effectiveness: 2.0 (super effective), 1.0 (neutral), 0.5 (not very effective), 0.0 (immune)
- STAB: 1.5× if move type matches Pokémon type, else 1.0×
- Variance: 0.85–1.0 random multiplier for unpredictability
- Crit: 1.5× on critical hit (6.25% chance per attack)
```

### Experience & Leveling
- **XP Gain**: 10–30 XP per battle (scales with opponent level)
- **Level Up Threshold**: 100 XP per level
- **HP Growth**: +2 HP per level (both current and max)
- **Max Level**: 99
- **Example**: 
  - Start Lv 1, 0 XP
  - Win battle, gain 25 XP → Lv 1, 25 XP
  - Win 3 more battles → Lv 2, 0 XP (level up!)

### Type Effectiveness System
- **18 Pokémon Types**: normal, fire, water, electric, grass, ice, fighting, poison, ground, flying, psychic, bug, rock, ghost, dragon, dark, steel, fairy
- **Caching**: First lookup hits PokéAPI; subsequent lookups use in-memory cache
- **Multipliers**: 
  - 2.0× (super effective): e.g., Water beats Fire
  - 1.0× (neutral): most matchups
  - 0.5× (not very effective): e.g., Fire resists Grass
  - 0.0× (immune): e.g., Electric can't affect Ground-types

### Colorization & UI
- **Colors Used**:
  - 🟢 **Green**: Player actions, "Battle" menu
  - 🔵 **Blue**: Pause prompts, "Continue" messages
  - 🟡 **Yellow**: "Pokémon List" menu, titles
  - 🔴 **Red**: Errors, opponent actions, "End Game" menu
  - 🟣 **Purple**: Credits, special messages
  - 🟦 **Cyan**: Informational text, Pokédex
  - ⚪ **Reset**: Clears color formatting
- **HP Bars**: Visual 20-character gauge showing remaining health
  - Example: `[██████████----------]` = 50% HP

### Pause Prompts
- **After battle** (victory/defeat)
- **When running away** (success or fail)
- **After Pokémon list** (for better UX)
- **Purpose**: Allow players time to read battle results

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

## Author & Acknowledgement

**Author**: LazyAustin525  
**Date**: November 2025  
**License**: MIT

**Acknowledgements**:
- 🙏 [PokéAPI](https://pokeapi.co/) - Comprehensive Pokémon data API
- 🎓 Object-Oriented Programming principles & design patterns
- 📚 Java documentation & Maven build tools

---

## Future Enhancements

- ⚡ **Leveling System** - Pokémon gain experience and level up
- 💾 **Save/Load Game** - Persist player progress to file
- 🎯 **Difficulty Modes** - Easy, Normal, Hard with AI strategies
- 🏆 **Leaderboard** - Track high scores and win streaks
- 🌐 **Multiplayer** - Network-based battles between players
- 📊 **Statistics Tracking** - Win/loss ratios, damage dealt, etc.
- 🎵 **Sound Effects** - ASCII-based sound or integration with system audio
- 🗺️ **Gym Leaders** - Pre-built boss Pokémon to challenge

---

## References

- [PokéAPI Documentation](https://pokeapi.co/docs/v2)
- [Java OOP Concepts](https://docs.oracle.com/javase/tutorial/java/concepts/)
- [Pokémon Type Effectiveness Chart](https://bulbapedia.bulbagarden.net/wiki/Type)
- [Maven Build Tool](https://maven.apache.org/)
- [Effective Java (3rd Edition)](https://www.oreilly.com/library/view/effective-java/9780134685991/) - Design patterns & best practices

---

**Build Status**: ✅ SUCCESS (9 source files)  
**Last Updated**: November 25, 2025  
**Version**: 1.0 (Finalized)