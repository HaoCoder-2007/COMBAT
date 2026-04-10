package engines;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Combat
{
    private static final Scanner sc = new Scanner(System.in);
    private static FuncForCombat p1, p2;
    private static int turn=2, step, choice;
    private static boolean isDone, replay = false;
    
    public static void main(String[] args)
    {
        isDone = false;
        do {
            try
            {
                GrapForCombat.thumbnail();
                System.out.print("=> ");
                switch(sc.nextInt())   
                {
                    case 1: 
                    {
                        isDone = true;
                        break;
                    }
                    case 0: 
                    {
                        System.out.println(GrapForCombat.printNO("<THANKS FOR PLAYING>"));
                        sc.nextLine();
                        System.exit(0);
                    }
                    default: 
                    {
                        isDone = false;
                        break;
                    }
                }
            }
            catch(InputMismatchException e)
            {
                sc.nextLine();
                isDone = false;
            }
        } while(!isDone);
        sc.nextLine();

        Combat game = new Combat();

        isDone = true;
        do {
            step=1;
                
            p1 = FuncForCombat.createPlayers(1, sc);
            p2 = FuncForCombat.createPlayers(2, sc);
                
            game.gamePlay();
                
                do {
                    try
                    {
                        System.out.println(GrapForCombat.printNO("\n<PLAY AGAIN? [1]: YES | [other numbers]: NO>"));
                        System.out.print("=> ");
                        switch(sc.nextInt())   
                        {
                            case 1 -> 
                            {
                                isDone = true;
                                replay = true;
                                break;
                            }
                            default -> 
                            {
                                isDone = true;
                                replay = false;
                                break;
                            }
                        }
                    }
                    catch(InputMismatchException e)
                    {
                        sc.nextLine();
                        isDone = false;
                    }
                } while(!isDone);
                sc.nextLine();
        } while(replay);
        System.out.println(GrapForCombat.printNO("<THANKS FOR PLAYING>"));
        sc.nextLine();
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

            boolean next = true;
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
                    System.out.print("=> Your choice: ");
                    try
                    {
                        choice = sc.nextInt();
                        sc.nextLine();
                        if(!FuncForCombat.isValidChoice(choice))
                        {
                            GrapForCombat.printNO("<INVALID! PLEASE TRY AGAIN!>");
                        }
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println(GrapForCombat.printNO("\n<INVALID! PLEASE TRY AGAIN!>"));
                    }
                } while(!FuncForCombat.isValidChoice(choice));

                GrapForCombat.clearScreen(step);

                switch(choice)
                {
                    case 1, 2 -> {
                        GrapForCombat.clearScreen(step);
                        if ((choice == 1 && activePlayer.getStrength() < 1) || (choice == 2 && activePlayer.getStrength() < 2)) {
                            System.out.println(GrapForCombat.printST("\n<[" + activePlayer.getName() + "] BURNED OUT>\n"));
                            GrapForCombat.infoAll(p1, p2);
                        }
                        System.out.println(GrapForCombat.printDA(choice == 1 ? "\n<ATTACK>" : "\n<HEAVY ATTACK>"));
                        System.out.println(GrapForCombat.printDA("\n<[" + activePlayer.getName() + "] ATTACKED [" + opponent.getName() + "]>\n"));
                        activePlayer.atk(opponent, choice);
                        if ((opponent.getHP() + opponent.getDefense()) <= activePlayer.getDamage() && opponent.getHP() > 0)
                        {
                            GrapForCombat.flashWarning(opponent.getName());
                        }
                        GrapForCombat.infoAll(p1, p2);
                        next = false;
                    }
                    case 3 -> {
                        GrapForCombat.clearScreen(step);
                        if(activePlayer.getStrength() < 1)
                        {
                            System.out.println(GrapForCombat.printST("\n[" + activePlayer.getName() + "] BURNED OUT!\n"));
                            GrapForCombat.infoAll(p1, p2);
                        }
                        System.out.println(GrapForCombat.printDF("\n<DEFENSE>"));
                        System.out.println(GrapForCombat.printDF("\n<[" + activePlayer.getName() + "] DEFENSE>"));
                        activePlayer.def();
                        GrapForCombat.infoAll(p1, p2);
                    }

                    case 4 -> {
                        GrapForCombat.clearScreen(step);
                        if(activePlayer.getMana() == 100)
                        {
                            activePlayer.ultimate(opponent);
                            next = false;
                            GrapForCombat.infoAll(p1, p2);
                        } else {
                            System.out.println(GrapForCombat.printNO("\n<NOT ENOUGH MANA!>"));
                            GrapForCombat.infoAll(p1, p2);
                        }
                    }

                    default -> {
                        GrapForCombat.clearScreen(step);
                        System.out.println(GrapForCombat.printNO("\n<SKIP>\n"));
                        activePlayer.skip();
                        GrapForCombat.infoAll(p1, p2);
                        next = false;
                    }
                }

                if(!next)
                {
                    System.out.println(GrapForCombat.printNO("\n<TURN ENDED>"));
                    activePlayer.manaTurn();
                }
                System.out.println(GrapForCombat.printNO("\n<PRESS [Enter] TO CONTINUE>"));
                sc.nextLine();
            } while(next);

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

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt", true)))
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

            System.out.println(GrapForCombat.printDF("\n<SUCCESSFUL>"));
        }
        catch(IOException e)
        {
            System.out.println(GrapForCombat.printHP("\n<UNSUCCESSFUL>"));
        }
}
}
