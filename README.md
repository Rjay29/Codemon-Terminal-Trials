# 🎮 Codémon: Terminal Trials

A fully terminal-based, object-oriented Pokémon battle simulator powered by real-time data from the [PokéAPI](https://pokeapi.co/). Inspired by the classic Pokémon Red/Blue battle system, this Java project brings turn-based combat, leveling, type effectiveness, and save/load mechanics to your command line.

---

## 📌 Introduction

This project is a Java-based command-line game that simulates Pokémon battles using real data from the PokéAPI. It features a main menu, difficulty modes, strategic turn-based combat, and a save/load system. The game is designed to demonstrate core Object-Oriented Programming (OOP) principles while delivering a nostalgic and educational experience.

---

## 🧠 How It Works + OOP Concepts

The game is built around four key OOP principles:

| Concept         | Implementation                                                                 |
|------------------|-------------------------------------------------------------------------------|
| **Abstraction**   | `Pokemon` is an abstract class defining shared attributes and behaviors.     |
| **Encapsulation** | Pokémon stats and moves are private, accessed via getters/setters.           |
| **Inheritance**   | `PokemonSpecies` extends `Pokemon` and represents any species dynamically.   |
| **Polymorphism**  | `useMove()` behaves differently depending on the Pokémon subclass.           |

### 🎮 Pokémon-Inspired Mechanics

- **Turn-based combat**: Players and opponents take turns using moves.
- **Moves**: Each Pokémon has 4 real moves with power, accuracy, and type.
- **Type effectiveness**: Fire > Grass, Water > Fire, etc., based on PokéAPI data.
- **Leveling**: Pokémon gain XP and level up after battles.
- **Difficulty modes**:
  - **Easy**: See opponent before choosing your Pokémon.
  - **Hard**: Choose blindly; opponent is stronger.

---

## 🧩 Components Overview

| File/Class            | Purpose                                                                 |
|------------------------|------------------------------------------------------------------------|
| `MainMenu.java`        | CLI entry point with menu: Battle, Load, Pokémon List, Credits, Exit   |
| `BattleGame.java`      | Handles battle logic, difficulty, turn order, and XP system            |
| `Pokemon.java`         | Abstract base class for all Pokémon                                    |
| `PokemonSpecies.java`  | Generic subclass representing any Pokémon using API data               |
| `Move.java`            | Represents a move with power, accuracy, type, and damage class         |
| `PokemonFactory.java`  | Fetches Pokémon and moves from PokéAPI                                 |
| `TypeEffectiveness.java` | Calculates damage multipliers based on type matchups                 |
| `PokemonList.java`     | Displays the first 151 Pokémon names from PokéAPI                      |

---

## 🛠️ What to Download First

1. **Java Development Kit (JDK 17 or higher)**  
   [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)

2. **Apache Maven**  
   [Download Maven](https://maven.apache.org/download.cgi)

3. **An IDE or text editor**  
   - Recommended: [Visual Studio Code](https://code.visualstudio.com/) or [IntelliJ IDEA](https://www.jetbrains.com/idea/)

---

## ⚙️ Project Setup

1. **Clone or download the project folder**  
   Place all `.java` files inside:

   ```
   Codemon/src/main/java/Codemon/
   ```

2. **Create a `pom.xml`** in the root `Termimon/` folder with the following plugin:

   ```xml
   <build>
     <plugins>
       <plugin>
         <groupId>org.codehaus.mojo</groupId>
         <artifactId>exec-maven-plugin</artifactId>
         <version>3.1.0</version>
       </plugin>
     </plugins>
   </build>
   ```

3. **Compile the project**:

   ```bash
   mvn compile
   ```

---

## 🕹️ How to Use It

### 🔸 Start the Game

```bash
mvn exec:java
```

### 🔸 Main Menu Options

- **Battle**: Choose difficulty and fight a random opponent
- **Pokémon List**: View the first 151 Pokémon names
- **Credits**: View the creators
- **End Game**: Exit the program

### 🔸 In Battle

- **Fight**: Choose a move to attack
- **Run**: Flee the battle

---

## 👥 Credits

Created by:
- **Apolinar, Jev Austin**
- **Arazula, Rjay**
- **Mendoza, Ken Frankie**