package engines;
import characters.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public abstract class FuncForCombat
{
    protected double hp;
    protected double damage;
    protected double defense;
    protected int strength;
    protected int mana;
    protected String name;
    protected String role;
    protected static final int NORMAL_HP = 100;
    protected static final int MAX_DEFENSE = 50;
    protected static final int MAX_STRENGTH = 10;
    protected static final int MAX_MANA = 100;
    protected static final Random RAND = new Random();
    protected static final int NUM_ROLE = 4; //update every time a new character is added 

    //---------------------------------------------------------------------CONSTRUCTORS----------------------------------------------------------------------------------

    public FuncForCombat()
    {
    }

    //---------------------------------------------------------------------GETTERS/SETTERS-------------------------------------------------------------------------------

    public double getHP()
    {
        return hp;
    }

    public double getDamage()
    {
        return damage;
    }

    public double getDefense()
    {
        return defense;
    }

    public int getStrength()
    {
        return strength;
    }

    public int getMana()
    {
        return mana;
    }

    public String getName()
    {
        return name;
    }

    public String getRole()
    {
        return role;
    }

    public void setHP(double hp)
    {
        if(hp < 0)
        {
            this.hp = 0;
        }
        else
        {
            this.hp = hp;
        }
    }

    public void setDefense(double defense)
    {
        if(this.defense + defense >= MAX_DEFENSE)
        {
            this.defense = MAX_DEFENSE;
        }
        else if(this.defense + defense <= 0)
        {
            this.defense = 0;
        }
        else
        {
            this.defense += defense;
        }
    }

    public void setStrength(int strength)
    {
        if(this.strength + strength >= MAX_STRENGTH)
        {
            System.out.println(GrapForCombat.printST("\n(!) FULL STRENGTH!\n"));
            this.strength = MAX_STRENGTH;
        }
        else
        {
            this.strength += strength;
        }
    }

    public void setMana(int mana)
    {
        if(this.mana + mana >= MAX_MANA)
        {
            this.mana = MAX_MANA;
        }
        else
        {
            this.mana += mana;
        }
    }

    //---------------------------------------------------------------------MANAGERS---------------------------------------------------------------------------------------

    public static boolean isValidChoice(int choice)
    {
        return (choice >= 0 && choice <=4);
    }

    public static boolean isValidRole(int role)
    {
        return (role >= 1 && role <=NUM_ROLE);
    }

    //---------------------------------------------------------------------METHODS---------------------------------------------------------------------------------------

    //|||==================================================================CREATE========================================================================================
    
    public static FuncForCombat createPlayers(int p, Scanner sc)
    {
        System.out.print("\033[H\033[2J");
        System.out.printf("\n=> Enter P%d name: ", p);
        String name = sc.nextLine();
        GrapForCombat.role();
        int role = -1;
        do {
            System.out.printf("=> Enter P%d role: ", p);
            try 
            {
                role = sc.nextInt();
                sc.nextLine();
                if(!isValidRole(role))
                {
                    System.out.println(GrapForCombat.printNO("\n<INVALID! PLEASE TRY AGAIN!>"));
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println(GrapForCombat.printNO("\n<INVALID! PLEASE TRY AGAIN!>"));
            }
        } while(!isValidRole(role));
        
        switch(role) 
        {
            case 1 -> {
                return new Warrior(name);
            }
            case 2 -> {
                return new Assassin(name);
            }
            case 3 -> {  
                return new Tanker(name);
            }
            case 4 -> {
                return new Archer(name);
            }
            default -> {
                return null;
            }
        }
    }

    //|||==================================================================ACTION========================================================================================

    public abstract void def();
    public abstract void atk(FuncForCombat other, int type);
    public abstract void ultimate(FuncForCombat other);
    public abstract void manaTurn();
    public abstract boolean dodge();

    public void skip()
    {
        this.setStrength(2);
        this.setMana(20);
    }
}