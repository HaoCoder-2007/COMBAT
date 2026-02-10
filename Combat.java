import java.util.Scanner;

public class Combat
{
    private static final Scanner sc = new Scanner(System.in);
    private static FuncForCombat p1, p2;
    private static int turn=2, step=1, p=1, choice;
    public static void main(String[] args)
    {
        Combat game = new Combat();
        p1 = FuncForCombat.createPlayers(p++, sc);
        p2 = FuncForCombat.createPlayers(p++, sc);

        game.gamePlay();

        sc.close();
    }

    private void gamePlay()
    {
        while(p1.getHP() > 0 && p2.getHP() > 0)
        {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.printf("*STEP [%d]*\n", step);
            turn = (turn == 1 ? 2 : 1);
            FuncForCombat activePlayer = (turn == 1) ? p1 : p2;
            FuncForCombat opponent = (turn == 1) ? p2 : p1;
            System.out.println(GrapForCombat.printNO("\n" + activePlayer.getName() + " TURN:\n"));
            GrapForCombat.tutor();

            GrapForCombat.infoAll(p1, p2);
            if (activePlayer.getStrength() <= 2)
            {
                GrapForCombat.printST("\n(!) WARNING: LOW STRENGTH!\n");
            }

            do {
                System.out.println("=> Your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if(!FuncForCombat.isValidChoice(choice))
                {
                    GrapForCombat.printNO("<INVALID! PLEASE TRY AGAIN!>");
                }
            } while(!FuncForCombat.isValidChoice(choice));

            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.printf("*STEP [%d]*\n", step);

            switch(choice)
            {
                case 1:
                case 2:
                {
                    if((choice == 1 && activePlayer.getStrength() < 1) || (choice == 2 && activePlayer.getStrength() < 2))
                    {
                        System.out.println(GrapForCombat.printST("\n<[" + activePlayer.getName() + "] BURNED OUT>\n"));
                        GrapForCombat.infoAll(p1, p2);
                        break;
                    }
                    System.out.println(GrapForCombat.printDA(choice == 1 ? "\n<ATTACK>" : "\n<HEAVY ATTACK>"));
                    System.out.println(GrapForCombat.printDA("\n<[" + activePlayer.getName() + "] ATTACKED [" + opponent.getName() + "]>\n"));
                    activePlayer.atk(opponent, choice);
                    if (opponent.getHP() <= activePlayer.getDamage() && opponent.getHP() > 0)
                    {
                        GrapForCombat.flashWarning(opponent.getName());
                    }
                    GrapForCombat.infoAll(p1, p2);
                    break;
                }

                case 3:
                {
                    if(activePlayer.getStrength() < 1)
                    {
                        System.out.println(GrapForCombat.printST("\n[" + activePlayer.getName() + "] BURNED OUT!\n"));
                        GrapForCombat.infoAll(p1, p2);
                        break;
                    }
                    System.out.println(GrapForCombat.printDF("\n<DEFENSE>"));
                    System.out.println(GrapForCombat.printDF("\n<[" + activePlayer.getName() + "] DEFENSE>"));
                    activePlayer.def();
                    GrapForCombat.infoAll(p1, p2);
                    break;
                }

                default:
                {
                    System.out.println(GrapForCombat.printNO("\n<SKIP>\n"));
                    activePlayer.skip();
                    GrapForCombat.infoAll(p1, p2);
                    break;
                }
            }

            System.out.println(GrapForCombat.printNO("\n<PRESS [Enter] TO CONTINUE>"));
            sc.nextLine();
            step++;
        }

        GrapForCombat.result(p1.getHP() > 0 ? p1 : p2);
    }
}
