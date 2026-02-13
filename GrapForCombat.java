public class GrapForCombat
{
    private static final String HP_COLOR = "\u001B[31m";
    private static final String DF_COLOR = "\u001B[32m";
    private static final String DA_COLOR = "\u001B[33m";
    private static final String ST_COLOR = "\u001B[34m";
    private static final String NO_COLOR = "\u001B[35m";
    private static final String RESET_COLOR = "\u001B[0m";

    public static String printHP(String s)
    {
        return HP_COLOR + s + RESET_COLOR;
    }

    public static String printDF(String s)
    {
        return DF_COLOR + s + RESET_COLOR;
    }

    public static String printDA(String s)
    {
        return DA_COLOR + s + RESET_COLOR;
    }

    public static String printST(String s)
    {
        return ST_COLOR + s + RESET_COLOR;
    }

    public static String printNO(String s)
    {
        return NO_COLOR + s + RESET_COLOR;
    }

    public static void getGrapRole(String role)
    {
        if(role.equals("warrior"))
        {
            warrior();
        }
        else if(role.equals("assassin"))
        {
            assassin();
        }
        else if(role.equals("tanker"))
        {
            tanker();
        }
    }

    public static void thumbnail()
    {
        clearScreen();
        System.out.println("\n/=========================================================\\");
        System.out.println("|<>                   /~~~" + HP_COLOR + " COMBAT " + RESET_COLOR + "~~~\\                  <>|");
        System.out.println("|<><>                                                 <><>|");
        System.out.println("|<><><>                    \\    /                   <><><>|");
        System.out.println("|<><><><>                   \\  /                  <><><><>|");
        System.out.println("|<><><><><>                  \\/                 <><><><><>|");
        System.out.println("|<><><><><>                  /\\                 <><><><><>|");
        System.out.println("|<><><><>                   /  \\                  <><><><>|");
        System.out.println("|<><><>                   _/_  _\\_                  <><><>|");
        System.out.println("|<><>" +   DA_COLOR   + "        [1]" +    RESET_COLOR    + "          /      \\" +   DA_COLOR    + "         [0]" +   RESET_COLOR   + "        <><>|");
        System.out.println("|<>" +   DA_COLOR   + "        <START>                        <QUIT>" +   RESET_COLOR   + "        <>|");
        System.out.println("\\=========================================================/\n");
    }

    public static void tutor()
    {
        System.out.println("[1]" + DA_COLOR + " ATTACK" + ST_COLOR + " (-1 STRENGTH)" + RESET_COLOR + "\n[2]" + DA_COLOR + " HEAVY ATTACK" + ST_COLOR + " (-2 STRENGTH)" + RESET_COLOR + "\n[3]" + DF_COLOR + " DEFENSE" + ST_COLOR + " (-2 STRENGTH)" + RESET_COLOR + "\n[4]" + HP_COLOR + " >>>ULTMATE<<<" + ST_COLOR + " (-100 MANA)" + RESET_COLOR + "\n[0] SKIP" + ST_COLOR + " (+2 STRENGTH)\n" + RESET_COLOR);
    }

    public static void role()
    {
        System.out.println("\nROLE:");
        System.out.println("[1] WARRIOR (HP: " + HP_COLOR + "100" + RESET_COLOR + "| DAMAGE: " + DA_COLOR + "20" + RESET_COLOR + ")");
        System.out.println("[2] ASSASSIN (HP: " + HP_COLOR + "85" + RESET_COLOR + "| DAMAGE: " + DA_COLOR + "25" + RESET_COLOR + ")");
        System.out.println("[3] TANKER (HP: " + HP_COLOR + "120" + RESET_COLOR + "| DAMAGE: " + DA_COLOR + "15" + RESET_COLOR + ")");
    }

    public static void infoAll(FuncForCombat p1, FuncForCombat p2)
    {
        System.out.println("\n/=====================================================\\");
        System.out.printf("|%-15s |%-10s         |%-10s      |\n", "CHARACTER", p1.getName(), p2.getName());
        System.out.printf("|%-15s |" + HP_COLOR + "%5.1f" + RESET_COLOR + "              |" + HP_COLOR + "%5.1f           " + RESET_COLOR + "|\n", "HP", p1.getHP(), p2.getHP());
        System.out.printf("|%-15s |" + DA_COLOR + "%5.1f" + RESET_COLOR + "              |" + DA_COLOR + "%5.1f           " + RESET_COLOR + "|\n", "DAMAGE", p1.getDamage(), p2.getDamage());
        System.out.printf("|%-15s |" + DF_COLOR + "%5.1f" + RESET_COLOR + "              |" + DF_COLOR + "%5.1f           " + RESET_COLOR + "|\n", "DEFENSE", p1.getDefense(), p2.getDefense());
        System.out.printf("|%-15s |" + ST_COLOR + "   %2d" + RESET_COLOR + "              |" + ST_COLOR + "   %2d           " + RESET_COLOR + "|\n", "STRENGTH", p1.getStrength(), p2.getStrength());
        System.out.printf("|%-15s |" + ST_COLOR + "   %2d" + RESET_COLOR + "              |" + ST_COLOR + "   %2d           " + RESET_COLOR + "|\n", "MANA", p1.getMana(), p2.getMana());
        System.out.println("\\=====================================================/\n\n");
    }

    public static void result(FuncForCombat p)
    {
        clearScreen();
        System.out.println(printNO("\n    <FINISHED>"));
        System.out.println(printDA("     <WINNER>"));
        System.out.println(printNO("\n/-/-/-/ [" + p.getName() + "] \\-\\-\\-\\"));
    }

    public static void flashWarning(String name)
    {
        String white = "\u001B[37m";
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        System.out.print("\n" + red + "<!!! WARNING: " + reset);
        try
        {
            for (int i = 0; i < 3; i++)
            {
                System.out.print("\r" + red + "<!!! WARNING: [" + name + "] IS DYING !!!>" + reset);
                Thread.sleep(200);
                System.out.print("\r" + white + "<!!! WARNING: [" + name + "] IS DYING !!!>" + reset);
                Thread.sleep(200);
            }
            System.out.println();
        } catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static void clearScreen(int step)
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.printf("*STEP [%d]*\n", step);
    }

    public static void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void warrior()
    {
        System.out.println("\n");
        System.out.println(GrapForCombat.printNO("<KING'S RESTORATION>"));
        System.out.println("                   ");
        System.out.println("        /||\\       ");
        System.out.println("        ||||       ");
        System.out.println("        ||||       ");
        System.out.println("        ||||       ");
        System.out.println("       =\\||/=      ");
        System.out.println("         ||        ");
        System.out.println("         ||        ");
        System.out.println("                   ");
        System.out.println("      {WARRIOR}    ");
        System.out.println("\n");
    }

    private static void assassin()
    {
        System.out.println("\n");
        System.out.println(GrapForCombat.printNO("<BLADE OF RETRIBUTION>"));
        System.out.println("                   ");
        System.out.println("     [ \\           ");
        System.out.println("     [ /     <>    ");
        System.out.println("     [/      ||    ");
        System.out.println("     []      []    ");
        System.out.println("     ||      /]    ");
        System.out.println("     <>     / ]    ");
        System.out.println("            \\ ]    ");
        System.out.println("                   ");
        System.out.println("     {ASSASSIN}    ");
        System.out.println("\n");
    }

    private static void tanker()
    {
        System.out.println("\n");
        System.out.println(GrapForCombat.printNO("  <IRON FORTRESS>"));
        System.out.println("                   ");
        System.out.println("      ________     ");
        System.out.println("     /        \\    ");
        System.out.println("     |  ||||  |    ");
        System.out.println("     | |||||| |    ");
        System.out.println("     |  ||||  |    ");
        System.out.println("     |   ||   |    ");
        System.out.println("     \\________/    ");
        System.out.println("                   ");
        System.out.println("      {TANKER}     ");
        System.out.println("\n");
    }
}