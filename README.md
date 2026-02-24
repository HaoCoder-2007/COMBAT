⚔️ Console Combat RPG
    -A turn-based RPG combat engine built with Java. This project features a dynamic battle system, character classes, and a localized database for match history, all running directly in your terminal with vibrant ASCII graphics.

✨ Key Features
    -Unique Character Classes: Choose between Warrior (Balanced), Assassin (High Evasion/Damage), and Tanker (High Defense).
    
    -Mana & Ultimate System: Build energy through combat actions to unleash powerful class-specific abilities:

        🛡️ King's Restoration (Warrior)

        🗡️ Blade of Retribution (Assassin)

        🏰 Iron Fortress (Tanker)

    -Advanced Combat Logic: Includes Armor Absorption, Armor Piercing, Critical Hits, and a dynamic Evasion (Dodge) system.

    -Automatic Match Logging: Every battle result, including timestamps and duration, is saved to a local result.txt file.

    -ANSI Color UI: Enhanced visual experience using terminal colors and custom ASCII art for each role.


🛠️ Project Structure
    -Combat.java: The main entry point. Handles the game loop, player turns, and replay logic.

    -FuncForCombat.java: The core engine. Manages character stats, damage formulas, and skill mechanics.

    -GrapForCombat.java: The graphics controller. Handles terminal rendering, ASCII art, and ANSI color formatting.


🚀 Getting Started
    -Prerequisites
    
    -JDK 17 or higher installed on your machine.
    
    -A terminal that supports ANSI colors (VS Code, IntelliJ, Windows Terminal, or macOS Terminal).
    
    -Installation & Execution
    
    -Clone the repository:  'git clone https://github.com/HaoCoder-2007/COMBAT.git'

    -Compile the source code:   'javac *.java'
    
    -Launch the game:    'java Combat'


🕹️ How to Play
    -Enter names for Player 1 and Player 2.

    -Select your preferred character roles.

    -Choose your actions using numeric keys:

        [1] Attack: Standard damage, low strength cost.

        [2] Heavy Attack: High damage, high strength cost.

        [3] Defense: Boost your armor stats.

        [4] ULTIMATE: Unleash your special move (Requires 100 Mana).

        [0] Skip: Recover strength and build Mana.


📝 Author Note
    -Developed as a personal project to master Java Object-Oriented Programming (OOP), File I/O, and game logic design.
