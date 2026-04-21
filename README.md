⚔️ COMBAT
A professional turn-based RPG combat engine built with Java. This project features a modular architecture using Java Packages, a dynamic multi-threaded battle system, and localized match history logging.

✨ Key Features
Unique Character Classes: Each class has its own stats and survival logic:

    🛡️ Warrior: Balanced stats with high sustain.

    🗡️ Assassin: High critical rates but lower health.

    🏰 Tanker: Massive health pool and superior armor absorption.

    🏹 Archer: High evasion and a multi-hit.

    🔮 Mage: A glass cannon that ignores armor and drains enemy mana.

Modular Architecture: Organized into engines and characters packages for better maintainability and scalability.

Advanced Logic: Features Armor Piercing, Critical Hits, and a class-specific Evasion (Dodge) system.

Automatic Match Logging: Battle results, including timestamps and duration, are saved to a local result.txt file.

ANSI Color UI: Enhanced terminal experience with vibrant colors and custom ASCII art.

🛠️ Project Structure

    📂 engines/: The core logic of the game.

        Combat.java: Main entry point. Handles the game loop and replay logic.

        FuncForCombat.java: Abstract base class managing stats and combat formulas.

        GrapForCombat.java: Graphics controller for ANSI rendering and ASCII art.

    📂 characters/: Contains specialized character classes.

        Warrior.java, Assassin.java, Tanker.java, Archer.java.

🚀 Getting Started
    Prerequisites
    JDK 17 or higher.

    A terminal that supports ANSI colors (VS Code, IntelliJ, Windows Terminal, or macOS Terminal).

    Installation & Execution
        Clone the repository:
            git clone https://github.com/HaoCoder-2007/COMBAT.git
            cd COMBAT
        Compile all packages:
            javac engines/*.java characters/*.java
        Launch the game:
            java engines.Combat

🕹️ How to Play
    Choose your actions using numeric keys:

    [1] Attack: Standard damage, low strength cost.

    [2] Heavy Attack: High damage, high strength cost.

    [3] Defense: Boost armor stats.

    [4] ULTIMATE: Unleash class-specific moves (Requires 100 Mana).

    [0] Skip: Recover strength and build Mana.

📝 Author Note
Developed as a personal project to master Java Object-Oriented Programming (OOP), Concurrency (Multithreading), and File I/O.