import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Combat
{
    private static final Scanner sc = new Scanner(System.in);
    private static FuncForCombat p1, p2;
    private static int turn=2, step, choice;
    private static String choiceS;
    public static void main(String[] args)
    {
        GrapForCombat.thumbnail();
        if(!sc.nextLine().equals("1"))
        {
            sc.close();
            System.exit(0);
        }
    
        Combat game = new Combat();

        do {
            step=1;

            p1 = FuncForCombat.createPlayers(1, sc);
            p2 = FuncForCombat.createPlayers(2, sc);

            game.gamePlay();

            System.out.println(GrapForCombat.printNO("\n<PLAY AGAIN? [1]: YES | [other]: NO>"));
            choiceS = sc.next();
            sc.nextLine(); 
        } while(choiceS.equals("1"));

        System.out.println(GrapForCombat.printNO("<THANKS FOR PLAYING>"));
        sc.nextLine();
        sc.close();
        System.exit(0);
    }

    private void gamePlay()
    {
        while(p1.getHP() > 0 && p2.getHP() > 0)
        {
            GrapForCombat.clearScreen(step);
            turn = (turn == 1 ? 2 : 1);
            FuncForCombat activePlayer = (turn == 1) ? p1 : p2;
            FuncForCombat opponent = (turn == 1) ? p2 : p1;

            int isDone = 0;
            do {
                GrapForCombat.clearScreen(step);
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

                GrapForCombat.clearScreen(step);

                switch(choice)
                {
                    case 1:
                    case 2:
                    {
                        GrapForCombat.clearScreen(step);
                        if((choice == 1 && activePlayer.getStrength() < 1) || (choice == 2 && activePlayer.getStrength() < 2))
                        {
                            System.out.println(GrapForCombat.printST("\n<[" + activePlayer.getName() + "] BURNED OUT>\n"));
                            GrapForCombat.infoAll(p1, p2);
                            break;
                        }
                        System.out.println(GrapForCombat.printDA(choice == 1 ? "\n<ATTACK>" : "\n<HEAVY ATTACK>"));
                        System.out.println(GrapForCombat.printDA("\n<[" + activePlayer.getName() + "] ATTACKED [" + opponent.getName() + "]>\n"));
                        activePlayer.atk(opponent, choice);
                        if ((opponent.getHP() + opponent.getDefense()) <= activePlayer.getDamage() && opponent.getHP() > 0)
                        {
                            GrapForCombat.flashWarning(opponent.getName());
                        }
                        GrapForCombat.infoAll(p1, p2);
                        isDone = 1;
                        break;
                    }

                    case 3:
                    {
                        GrapForCombat.clearScreen(step);
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

                    case 4:
                    {
                        GrapForCombat.clearScreen(step);
                        if(activePlayer.getMana() == 100)
                        {
                            activePlayer.ultimate(opponent);
                            isDone = 1;
                            GrapForCombat.infoAll(p1, p2);
                            break;
                        }
                        else
                        {
                            System.out.println(GrapForCombat.printNO("\n<NOT ENOUGH MANA!>"));
                            GrapForCombat.infoAll(p1, p2);
                            break;
                        }
                    }

                    default:
                    {
                        GrapForCombat.clearScreen(step);
                        System.out.println(GrapForCombat.printNO("\n<SKIP>\n"));
                        activePlayer.skip();
                        GrapForCombat.infoAll(p1, p2);
                        isDone = 1;
                        break;
                    }
                }

                if(isDone == 1)
                {
                    System.out.println(GrapForCombat.printNO("\n<TURN ENDED>"));
                    activePlayer.manaTurn();
                }
                System.out.println(GrapForCombat.printNO("\n<PRESS [Enter] TO CONTINUE>"));
                sc.nextLine();
            } while(isDone == 0);

            step++;
        }

        FuncForCombat winner = (p1.getHP() > 0) ? p1 : p2;
        FuncForCombat loser = (p1.getHP() > 0) ? p2 : p1;
        GrapForCombat.result(winner);
        saveResult(winner, loser, step);
    }

    public void saveResult(FuncForCombat winner, FuncForCombat loser, int steps)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt", true)))
        {
            writer.write("==========================================");
            writer.newLine();
            writer.write("DATE: " + dtf.format(now));
            writer.newLine();
            writer.write("RESULT: [" + winner.getName() + "] (" + winner.getRole() + ") > [" + loser.getName() + "] (" + loser.getRole() + ")");
            writer.newLine();
            writer.write("DURATION: " + steps + " steps.");
            writer.newLine();
            writer.write("==========================================");
            writer.newLine();
            writer.newLine();
        }
        catch (IOException e)
        {
        }
}
}
