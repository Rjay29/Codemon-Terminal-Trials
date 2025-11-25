# Codemon - Complete Technical Documentation

A Java-based turn-based Pokémon-like battle simulator using PokéAPI, featuring ANSI-colorized console UI, dynamic damage calculation with type effectiveness, experience & leveling system, and difficulty modes.

---

## 📋 Table of Contents

1. [File Structure](#file-structure)
2. [Detailed Class Documentation](#detailed-class-documentation)
3. [Method Reference](#method-reference)
4. [OOP Principles](#oop-principles)
5. [Gameplay Mechanics](#gameplay-mechanics)
6. [How to Run](#how-to-run)

---

## 📋 File Structure

### 9 Core Java Files

| File | Type | Purpose | Key Responsibilities |
|------|------|---------|----------------------|
| **MainMenu.java** | Entry Point | Main menu & navigation | Colorized UI, Pokémon ID selection, menu routing |
| **BattleGame.java** | Battle Engine | Turn-based combat | Battle loop, turn order, damage calc, pause system |
| **PKM.java** | Base Model | Pokémon base class | Abstract/concrete base with stats and moves |
| **Species.java** | Data Model | Concrete Pokémon | Level, HP tracking, experience management |
| **Move.java** | Data Model | Immutable move | Move properties (name, type, power, accuracy) |
| **Factory.java** | Utility | Pokémon creation | Creates Species from PokéAPI data (1-151) |
| **TypeEffectiveness.java** | Utility | Type matchups | Caches type effectiveness from PokéAPI |
| **PKMList.java** | Utility | Pokédex viewer | Displays all 151 Pokémon with pause prompt |
| **PokeAPI.java** | Debug Tool | Data dumper | Debug main() to export all Pokémon stats |

---

## 🔧 Detailed Class Documentation

### 1. MainMenu.java

**Purpose**: Entry point and console UI manager for the game.

**Class Structure**:
```java
public class MainMenu {
    private static class Colors { /* inner class */ }
    public static void main(String[] args) { /* entry */ }
    public static void run() { /* menu loop */ }
    private static void startBattle() { /* battle setup */ }
    private static int readInt(...) { /* input helper */ }
}
```

**Inner Class: Colors**
```java
private static class Colors {
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String PURPLE = "\u001B[35m";
    static final String CYAN = "\u001B[36m";
}
```
- **Purpose**: ANSI color constants for terminal colorization
- **Fields**: 7 static final color codes
- **Usage**: `Colors.GREEN + "text" + Colors.RESET` for green text

**Public Methods**:

1. **`main(String[] args)`**
   - **Purpose**: Application entry point
   - **Parameters**: `args` - command-line arguments (unused)
   - **Logic**: Creates Scanner, calls `run()`, closes Scanner
   - **Returns**: `void`

2. **`run()`**
   - **Purpose**: Main menu loop
   - **Logic**:
     ```
     Loop until user exits:
       Display colorized menu options:
         1. Battle (green)
         2. Pokémon List (blue)
         3. Credits (yellow)
         4. End Game (red)
       Read user choice (1-4)
       Route to appropriate method
     ```
   - **Returns**: `void`

**Private Methods**:

1. **`startBattle()`**
   - **Purpose**: Initialize and start a new battle
   - **Logic**:
     ```
     Display difficulty selection
     Read choice: 1=Easy, 2=Hard
     Display random opponent Pokémon info
     Prompt for player Pokémon ID (1-151)
     Fetch player Species from Factory
     Fetch opponent Species from Factory
     Adjust opponent level:
       Easy: opponent.level = playerLevel - 5
       Hard: opponent.level = playerLevel + (0-2)
     Start BattleGame
     ```
   - **Returns**: `void`

2. **`readInt(String prompt, int min, int max)`**
   - **Purpose**: Safe integer input with validation
   - **Parameters**:
     - `prompt` - message to display
     - `min` - minimum acceptable value
     - `max` - maximum acceptable value
   - **Logic**:
     ```
     Loop until valid input:
       Display prompt
       Try to parse input as integer
       If out of range or invalid:
         Show error message
         Retry
     Return validated integer
     ```
   - **Returns**: `int` - validated user input

---

### 2. BattleGame.java

**Purpose**: Core battle engine implementing turn-based combat mechanics.

**Class Structure**:
```java
public class BattleGame {
    private static class Colors { /* inner class */ }
    
    public static void startBattle(Scanner scanner) { /* entry */ }
    private static void battleLoop(...) { /* combat loop */ }
    private static void playerTurn(...) { /* player action */ }
    private static void opponentTurn(...) { /* opponent action */ }
    private static int calculateDamage(...) { /* damage formula */ }
    private static String hpBar(int hp, int maxHp) { /* visual bar */ }
    private static String effectivenessText(double multiplier) { /* feedback */ }
    private static void pause(Scanner scanner) { /* UX pause */ }
    private static int readInt(...) { /* input helper */ }
}
```

**Inner Class: Colors**
- Same 7 colors as MainMenu
- **Purpose**: Local color constants for battle output

**Public Methods**:

1. **`startBattle(Scanner scanner)`**
   - **Purpose**: Initialize battle with difficulty selection
   - **Parameters**: `scanner` - user input reader
   - **Logic**:
     ```
     Display difficulty selection:
       1 = Easy (opponent -5 levels)
       2 = Hard (opponent +0 to +2 levels)
     Read difficulty choice
     Select random opponent ID (1-151)
     Display opponent info
     Prompt player for Pokémon ID
     
     Create player Species from Factory.createFromAPI(playerID)
     Create opponent Species from Factory.createFromAPI(opponentID)
     
     Adjust opponent level based on difficulty
     Call battleLoop()
     ```
   - **Returns**: `void`

**Private Methods**:

1. **`battleLoop(Scanner scanner, Species player, Species opponent)`**
   - **Purpose**: Main combat loop - runs until one Pokémon faints
   - **Parameters**:
     - `scanner` - user input reader
     - `player` - player's Pokémon
     - `opponent` - opponent's Pokémon
   - **Logic**:
     ```
     Loop while both have HP > 0:
       Display both Pokémon HP bars
       Display battle menu (Fight/Run options)
       Read user choice
       
       If choice == 1 (Fight):
         Call playerTurn(scanner, player, opponent)
         If opponent.hp > 0:
           Call opponentTurn(scanner, player, opponent)
       
       If choice == 2 (Run):
         If Math.random() > 0.5:  // 50% success
           Print "You escaped safely!"
           Call pause(scanner)
           Return
         Else:
           Print "Couldn't escape!"
           Call opponentTurn(scanner, player, opponent)
       
       Continue loop
     
     When one faints (hp <= 0):
       Determine winner
       Calculate XP gain: (10 + opponent.level / 2)
       Player gains XP: player.addExperience(xp)
       Check level ups
       Call pause(scanner)
     ```
   - **Returns**: `void`

2. **`playerTurn(Scanner scanner, Species player, Species opponent)`**
   - **Purpose**: Execute player's turn
   - **Parameters**:
     - `scanner` - user input reader
     - `player` - player's Pokémon
     - `opponent` - opponent's Pokémon
   - **Logic**:
     ```
     Display available moves:
       "Your Moves:"
       "1. [MoveName] ([Type], [Power])"
       ...
     Read move index
     Select move: player.getMoves().get(index-1)
     
     Calculate damage:
       damage = calculateDamage(player, opponent, move)
     
     Display attack message:
       "[PlayerName] used [MoveName]!"
     
     Get effectiveness text
     Apply damage to opponent: opponent.hp -= damage
     If opponent.hp < 0: opponent.hp = 0
     ```
   - **Returns**: `void`

3. **`opponentTurn(Scanner scanner, Species player, Species opponent)`**
   - **Purpose**: Execute opponent's turn (AI)
   - **Parameters**:
     - `scanner` - user input reader
     - `player` - player's Pokémon
     - `opponent` - opponent's Pokémon
   - **Logic**:
     ```
     Select random move from opponent moves
     Calculate damage:
       damage = calculateDamage(opponent, player, move)
     
     Display attack message:
       "[OpponentName] used [MoveName]!"
     
     Get effectiveness text
     Apply damage to player: player.hp -= damage
     If player.hp < 0: player.hp = 0
     ```
   - **Returns**: `void`

4. **`calculateDamage(Species attacker, Species defender, Move move)`**
   - **Purpose**: Core damage calculation engine
   - **Parameters**:
     - `attacker` - Pokémon using the move
     - `defender` - Pokémon taking damage
     - `move` - Move being used
   - **Damage Formula**:
     ```
     base = (attacker.level * 0.2 + 1) * move.power * (attacker.attack / defender.defense)
     
     effectiveness = TypeEffectiveness.getMultiplier(move.type, defender.type)
     
     stab = (move.type.equals(attacker.type)) ? 1.5 : 1.0
     
     isCritical = Math.random() > 0.9375  // 6.25% chance
     crit = isCritical ? 1.5 : 1.0
     
     variance = 0.85 + Math.random() * 0.15  // 0.85-1.0 range
     
     damage = (int)(base * effectiveness * stab * crit * variance)
     
     if (damage < 1) damage = 1  // Minimum 1 damage
     return damage
     ```
   - **Returns**: `int` - calculated damage (1+)

5. **`hpBar(int hp, int maxHp)`**
   - **Purpose**: Generate visual HP bar for display
   - **Parameters**:
     - `hp` - current HP
     - `maxHp` - maximum HP
   - **Logic**:
     ```
     Calculate filled sections:
       filled = (hp * 20) / maxHp
       empty = 20 - filled
     
     Build string:
       "[" + ("#".repeat(filled)) + ("-".repeat(empty)) + "]"
     
     Examples:
       20/100 HP: [####----------------]
       50/100 HP: [##########----------]
       100/100 HP: [####################]
     ```
   - **Returns**: `String` - formatted HP bar

6. **`effectivenessText(double multiplier)`**
   - **Purpose**: Provide human-readable effectiveness feedback
   - **Parameters**: `multiplier` - damage multiplier
   - **Logic**:
     ```
     Switch on multiplier:
       2.0 -> return "It's super effective!"
       0.5 -> return "It's not very effective..."
       0.0 -> return "It has no effect..."
       1.0 -> return ""
     ```
   - **Returns**: `String` - effectiveness message

7. **`pause(Scanner scanner)`**
   - **Purpose**: Display pause prompt for UX flow
   - **Parameters**: `scanner` - user input reader
   - **Logic**:
     ```
     Print: "[BLUE]Press Enter to continue...[RESET]"
     Call scanner.nextLine() to block until user presses Enter
     ```
   - **Returns**: `void`
   - **Called after**: Battle victory/defeat, running away

8. **`readInt(String prompt, int min, int max)`**
   - **Purpose**: Safe integer input with validation
   - **Same as MainMenu.readInt()**
   - **Returns**: `int` - validated user input

---

### 3. PKM.java

**Purpose**: Base or concrete model for Pokémon entities.

**Class Structure**:
```java
public class PKM {
    private String name, type;
    private int hp, attack, defense;
    private List<Move> moves;
    
    public PKM(String name, String type, int hp, int attack, int defense)
    // Getters & Setters for all fields
}
```

**Fields**:
- `String name` - Pokémon name (e.g., "Pikachu")
- `String type` - Primary type (e.g., "electric")
- `int hp` - Current hit points (0-200)
- `int attack` - Attack stat (30-150)
- `int defense` - Defense stat (30-150)
- `List<Move> moves` - List of known moves (2-4 moves)

**Constructor**:
```java
public PKM(String name, String type, int hp, int attack, int defense)
```
- **Purpose**: Initialize a Pokémon with base stats
- **Validation**: All stats ≥ 0 (uses `Math.max(0, value)`)
- **Parameters**: As listed in fields above

**Public Methods**:

1. **`getName()`, `getType()`, `getHp()`, `getAttack()`, `getDefense()`, `getMoves()`**
   - **Purpose**: Retrieve field values
   - **Returns**: `String` or `int` or `List<Move>`

2. **`setName(String name)`, `setType(String type)`, `setHp(int hp)`, etc.**
   - **Purpose**: Set field values with validation
   - **Parameters**: New value
   - **Validation**: HP ≥ 0 via `Math.max(0, value)`
   - **Returns**: `void`

3. **`addMove(Move move)`**
   - **Purpose**: Add move to move list
   - **Parameters**: `Move` - move to add
   - **Returns**: `void`

---

### 4. Species.java

**Purpose**: Concrete Pokémon implementation with level and experience tracking.

**Class Structure**:
```java
public class Species extends PKM {
    private int level;
    private int experience;
    private int maxHp;
    
    public Species(String name, String type, int level, int hp, int attack, int defense, List<Move> moves)
    // Level & XP methods
}
```

**Fields**:
- `int level` - Pokémon level (1-99)
- `int experience` - Total accumulated XP (0+)
- `int maxHp` - Original/max HP value (for HP bar calculations)

**Constructor**:
```java
public Species(String name, String type, int level, int hp, int attack, int defense, List<Move> moves)
```
- **Purpose**: Initialize a Species Pokémon
- **Parameters**: As listed above + level
- **Logic**:
  ```
  Call parent constructor: super(name, type, hp, attack, defense)
  Set this.level = level
  Set this.maxHp = hp  // Store original HP as max
  Set this.experience = 0  // Start with 0 XP
  Add all moves to move list
  ```

**Public Methods**:

1. **`getLevel()`**
   - **Returns**: `int` - current level (1-99)

2. **`setLevel(int level)`**
   - **Parameters**: `level` - new level (1-99)
   - **Returns**: `void`

3. **`getExperience()`**
   - **Returns**: `int` - total XP accumulated

4. **`addExperience(int xp)`**
   - **Purpose**: Add XP and handle level ups
   - **Parameters**: `xp` - XP to add (10-30 from battles)
   - **Logic**:
     ```
     this.experience += xp
     Print: "[Name] gained [xp] XP!"
     
     While experience >= (100 * level):
       experience -= (100 * level)
       level++
       hp += 2  // HP growth per level
       maxHp += 2
       Print: "[Name] leveled up to Lv [level]!"
       Print: "Max HP increased to [maxHp]!"
     ```
   - **Returns**: `void`

5. **`getMaxHp()`**
   - **Purpose**: Get original/max HP value
   - **Returns**: `int` - maximum HP

---

### 5. Move.java

**Purpose**: Immutable data model for Pokémon moves.

**Class Structure**:
```java
public class Move {
    private final String name, type, damageClass;
    private final int power, accuracy;
    
    public Move(String name, String type, int power, int accuracy, String damageClass)
    // Immutable getters only
}
```

**Fields** (all `final`):
- `String name` - Move name (e.g., "Thunder Punch")
- `String type` - Move type (e.g., "electric")
- `int power` - Damage power (0-150)
- `int accuracy` - Hit accuracy percentage (0-100)
- `String damageClass` - "physical", "special", or "status"

**Constructor**:
```java
public Move(String name, String type, int power, int accuracy, String damageClass)
```
- **Purpose**: Create an immutable move
- **Parameters**: As listed in fields
- **Immutability**: All fields are final; no setters provided

**Public Methods** (Immutable Getters):

1. **`getName()`** → `String`
2. **`getType()`** → `String`
3. **`getPower()`** → `int`
4. **`getAccuracy()`** → `int`
5. **`getDamageClass()`** → `String`

---

### 6. Factory.java

**Purpose**: Creates Species instances from PokéAPI data.

**Class Structure**:
```java
public final class Factory {
    public static Species createFromAPI(int id)
}
```

**Public Static Method**:

**`createFromAPI(int id)`**
- **Purpose**: Fetch Pokémon data from PokéAPI and create Species
- **Parameters**: `id` - Pokémon ID (1-151)
- **API Endpoint**: `https://pokeapi.co/api/v2/pokemon/{id}`
- **Logic**:
  ```
  1. Fetch JSON from PokéAPI endpoint
  2. Parse Pokémon data:
     - name: from "name" field
     - type: from "types[0].type.name"
     - hp: from "stats[0].base_stat"  (HP stat)
     - attack: from "stats[1].base_stat"  (Attack stat)
     - defense: from "stats[2].base_stat"  (Defense stat)
  
  3. Fetch moves:
     Loop through "moves" array:
       - Get move name and URL
       - Fetch move details from API
       - Extract: name, type, power, accuracy, damage_class
       - Create Move object
  
  4. Return new Species(name, type, level=1, hp, attack, defense, moves)
  ```
- **Error Handling**: Try-catch block; returns dummy Species on API failure
- **Returns**: `Species` - Pokémon created from API data

---

### 7. TypeEffectiveness.java

**Purpose**: Caches type effectiveness multipliers from PokéAPI.

**Class Structure**:
```java
public final class TypeEffectiveness {
    private static final Map<String, Map<String, Double>> cache = new HashMap<>();
    
    public static double getMultiplier(String attackType, String defenderType)
}
```

**Static Field**:
- `cache` - 2D map storing type matchups
  - Key 1: attacker type (e.g., "fire")
  - Key 2: defender type (e.g., "grass")
  - Value: multiplier (2.0, 1.0, 0.5, 0.0)

**Public Static Method**:

**`getMultiplier(String attackType, String defenderType)`**
- **Purpose**: Get type effectiveness multiplier with automatic caching
- **Parameters**:
  - `attackType` - attacker's move type (e.g., "fire")
  - `defenderType` - defender's Pokémon type (e.g., "grass")
- **Logic**:
  ```
  If cache.containsKey(attackType):
    Return cached multiplier for (attackType, defenderType)
  
  Else:
    Fetch from PokéAPI /type/{attackType} endpoint
    Parse JSON damage_relations:
      - "double_damage_to": types that take 2.0× damage from this type
      - "half_damage_to": types that take 0.5× damage from this type
      - "no_damage_to": types immune to this type (0.0×)
    
    For each defender type:
      Store multiplier in cache
      cache.get(attackType).put(defenderType, multiplier)
    
    Return multiplier for (attackType, defenderType)
  
  On error (network/parse):
    Return 1.0 (neutral damage)
  ```
- **Multipliers**:
  - `2.0` = super effective
  - `1.0` = neutral
  - `0.5` = not very effective
  - `0.0` = no effect (immune)
- **Returns**: `double` - multiplier (0.0, 0.5, 1.0, or 2.0)

---

### 8. PKMList.java

**Purpose**: Display all 151 Pokémon from PokéAPI.

**Class Structure**:
```java
public final class PKMList {
    private static class Colors { /* inner class */ }
    
    public static void showList()
}
```

**Inner Class: Colors**
- **Fields**:
  - `RESET = "\u001B[0m"`
  - `CYAN = "\u001B[36m"`

**Public Static Method**:

**`showList()`**
- **Purpose**: Fetch and display all 151 Pokémon
- **API Endpoint**: `https://pokeapi.co/api/v2/pokemon?limit=151`
- **Logic**:
  ```
  1. Fetch JSON from PokéAPI
  2. Parse "results" array (contains name & URL for each Pokémon)
  3. Loop through results:
     For index 0-150:
       Print: "[CYAN]{index+1}. {name}[RESET]"
       Example: "1. bulbasaur"
  
  4. After list display:
     Print: "[BLUE]Press Enter to continue...[RESET]"
     Call System.in.read() to pause/wait for input
  
  5. On error: Catch exception and print error message
  ```
- **Returns**: `void`

---

### 9. PokeAPI.java

**Purpose**: Debug utility for dumping detailed Pokémon data.

**Class Structure**:
```java
public final class PokeAPI {
    public static void main(String[] args)
}
```

**Public Static Method**:

**`main(String[] args)`**
- **Purpose**: Export detailed stats for first 151 Pokémon (debug tool)
- **Logic**:
  ```
  Initialize main execution
  Loop i = 1 to 151:
    Fetch Pokémon from Factory.createFromAPI(i)
    Print:
      - ID number
      - Name
      - Type
      - HP stat
      - Attack stat
      - Defense stat
      - List of moves (name, type, power)
  
  Exit program
  ```
- **Execution Command**: `mvn exec:java -Dexec.mainClass=Codemon.PokeAPI`
- **Returns**: `void`

---

## 🎮 Gameplay Mechanics

### Battle Flow Diagram

```
1. MainMenu.run()
   ↓ (User selects "Battle")
2. MainMenu.startBattle()
   - Ask difficulty (Easy/Hard)
   - Show random opponent
   - Ask player Pokémon ID (1-151)
   ↓
3. BattleGame.startBattle()
   - Create player Species from Factory
   - Create opponent Species from Factory
   - Adjust opponent level based on difficulty
   ↓
4. BattleGame.battleLoop()
   ↓
   Loop until one faints:
   - Display both Pokémon + HP bars
   - Show battle menu (Fight/Run)
   ↓
   If Fight:
     - Player selects move
     - playerTurn() calculates & applies damage
     - If opponent alive: opponentTurn() attacks back
   ↓
   If Run:
     - 50% success rate
     - If success: escape and return
     - If fail: opponent attacks anyway
   ↓
5. When one faints:
   - Display winner
   - Calculate XP (10-30)
   - Add to player: player.addExperience(xp)
   - Check level ups
   - pause() waits for user
   ↓
6. Return to MainMenu
```

### Damage Calculation Formula

```
damage = floor(base × effectiveness × STAB × critical × variance)

Where each component:

base = (level × 0.2 + 1) × power × (attack / defense)
  Level scaling: higher level = higher damage
  Power: move's inherent power (e.g., 75 for Thunder Punch)
  Attack/Defense ratio: attacker's attack vs defender's defense

effectiveness = TypeEffectiveness.getMultiplier(moveType, defenderType)
  Super effective: 2.0 (e.g., Water vs Fire)
  Neutral: 1.0 (e.g., Normal vs any)
  Not very effective: 0.5 (e.g., Fire vs Water)
  Immune: 0.0 (e.g., Normal vs Ghost)

STAB (Same Type Attack Bonus) = 1.5 or 1.0
  1.5× if move.type == attacker.type
  1.0× otherwise
  (e.g., Fire-type using Fire-type move = STAB bonus)

critical = 1.5 or 1.0
  1.5× if RNG > 0.9375 (6.25% chance for critical hit)
  1.0× otherwise

variance = random value between 0.85 and 1.0
  Adds randomness to prevent identical damage outcomes

Final calculation:
  damage = (int)(base × effectiveness × STAB × critical × variance)
  If damage < 1: damage = 1 (minimum 1 damage guaranteed)
```

### Experience & Leveling System

**XP Gain**:
- Formula: `xp = 10 + (opponent.level / 2)`
- Range: 10–30 XP per battle
- Scaling: Higher opponent level = more XP

**Level Up Threshold**:
- XP needed per level: `100 × (current_level - 1)`
- Example progression:
  ```
  Lv 1: 0 XP (start)
  Lv 2: 100 XP (gain 100 XP)
  Lv 3: 200 XP (gain 100 more)
  Lv 4: 300 XP (gain 100 more)
  ```

**HP Growth**:
- Per level up: +2 HP
- Both `hp` and `maxHp` increase
- Example:
  ```
  Level up: 50 HP → 52 HP
  Max HP: 100 → 102
  ```

**Max Level**: 99

---

## ▶️ How to Run

### Compile
```bash
cd c:\Users\jevau\Downloads\Documents\CODING SHET\Codemon
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

### Debug: Dump All Pokémon Data
```bash
mvn exec:java -Dexec.mainClass=Codemon.PokeAPI
```

---

## Sample Output

### Battle Example (concise)
```
[GREEN] Go! Pikachu (Lv 5)
Pikachu HP: [##########----------]   Charizard HP: [##############------]
Pikachu used Thunder Shock! It's super effective! Dealt 65 damage.
*** Victory! ***
[BLUE] Press Enter to continue...
```

(Colors shown as labels for clarity — terminal uses ANSI color codes.)

---

## 📝 OOP Principles Demonstrated

### 1. Encapsulation
- **Private fields** with public getters/setters (PKM, Species)
- **Validation** in constructors and setters (Math.max for non-negative values)
- **Data hiding** - internal logic not exposed

### 2. Abstraction
- **Colors inner classes** hide ANSI escape sequences
- **TypeEffectiveness** abstracts HTTP + JSON parsing into simple `getMultiplier()`
- **Factory** abstracts PokéAPI fetching into `createFromAPI()`
- **pause()** centralizes UX flow

### 3. Inheritance
- **Species extends PKM**: Adds level, experience, maxHp
- **Reuses base stats** from parent class

### 4. Polymorphism
- Move selection (player vs opponent) uses same damage formula
- Turn resolution applies to both player and opponent

### 5. Collections
- **List<Move>**: Dynamic move storage
- **Map<String, Map<String, Double>>**: 2D type effectiveness cache

### 6. Exception Handling
- Try-catch in Factory for API failures
- Try-catch in TypeEffectiveness for network errors
- Try-catch in PKMList for data parsing
- Graceful fallbacks (dummy Pokémon, default multipliers)

### 7. Static Utility Pattern
- **Factory**: Static factory method
- **TypeEffectiveness**: Static cache + retrieval
- **PKMList**: Static display method
- **PokeAPI**: Static main debug tool

---

## 📝 Build & Dependencies

- **JDK**: 21+
- **Maven**: 3.8+
- **Dependencies**:
  - `org.json:json:20230618` (PokéAPI JSON parsing)
  - External: `PokéAPI v2` (https://pokeapi.co/api/v2/)

**Build Status**: ✅ SUCCESS (9 source files, 1.5-2s compile time)

---

**Last Updated**: November 25, 2025  
**Version**: 1.0 (Finalized)  
**Status**: Production Ready


