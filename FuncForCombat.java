import java.util.Random;
import java.util.Scanner;

public class FuncForCombat
{
    private double hp;
    private double damage;
    private double defense;
    private int strength;
    private final String name;
    private final String role;
    private static final double NORMAL_HP = 100;
    private static final double MAX_STRENGTH = 10; 
    private static final Random RAND = new Random();

    //---------------------------------------------------------------------CONSTRUCTORS----------------------------------------------------------------------------------

    public FuncForCombat(String name, int role)
    {
        switch(role)
        {
            case 1: //warrior (balance)
            {
                this.name = name;
                this.role = "warrior";
                this.hp = NORMAL_HP;
                this.damage = 20;
                this.defense = 0;
                this.strength = 10;
                break;
            }

            case 2: //assassin (low hp - high damage)
            {
                this.name = name;
                this.role = "assassin";
                this.hp = NORMAL_HP - 15;
                this.damage = 25;
                this.defense = 0;
                this.strength = 10;
                break;
            }

            case 3: //tanker (high hp - low damage)
            {
                this.name = name;
                this.role = "tanker";
                this.hp = NORMAL_HP + 40;
                this.damage = 17;
                this.defense = 0;
                this.strength = 10;
                break;
            }

            default: //unknow
            {
                this.name = "";
                this.role = "unknow";
                this.hp = 0;
                this.damage = 0;
                this.defense = 0;
                this.strength = 0;
                break;
            }
        }
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

    public String getName()
    {
        return name;
    }
    public String getRole()
    {
        return role;
    }

    private void setHP(double hp)
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

    //---------------------------------------------------------------------CHECKERS--------------------------------------------------------------------------------------

    public static boolean isValidChoice(int choice)
    {
        return (choice >= 0 && choice <=3);
    }

    private static boolean isValidRole(int role)
    {
        return (role >= 1 && role <=3);
    }

    //---------------------------------------------------------------------METHODS---------------------------------------------------------------------------------------

    //=====================================================================CREATE========================================================================================
    public static FuncForCombat createPlayers(int p, Scanner sc)
    {
        System.out.print("\033[H\033[2J");
        System.out.printf("=> Enter P%d name: ", p);
        String name = sc.nextLine();
        GrapForCombat.role();
        int role;
        do {
            System.out.printf("=> Enter P%d role: ", p);
            role = sc.nextInt();
            sc.nextLine();
            if(!isValidRole(role))
            {
                System.out.println(GrapForCombat.printNO("\n<INVALID! PLEASE TRY AGAIN!>"));
            }
        } while(!isValidRole(role));
        return new FuncForCombat(name, role);
    }

    //=====================================================================ACTION========================================================================================

    private void subDefense()
    {
        double sub = RAND.nextDouble()+0.1;
        this.defense -= Math.min(this.defense, sub);
        System.out.println(GrapForCombat.printDF("<[" + this.name + "] REDUCED " + sub + " ARMOR>"));
    }

    public void def()
    {
        if(this.role.equals("tanker"))
        {
            this.defense += RAND.nextDouble() + 0.4;
        }
        else
        {
            this.defense += RAND.nextDouble() + 0.1;
        }
        this.strength-=1;
    }

    public void skip()
    {
        if(this.strength < MAX_STRENGTH)
        {
            this.strength+=1;
        }
        else
        {
            System.out.println(GrapForCombat.printST("\n(!) FULL STRENGTH!\n"));
        }
    }

    public void atk(FuncForCombat other, int type)
    {
        double baseDamage = this.damage;
        double extraDamage = 0;
        double finalDamage;
        double riskExNor, riskExHea, riskFiNor, riskFiHea;
        
        if(this.role.equals("warrior"))
        {
            riskExNor = 0.4;
            riskExHea = 0.2;
            riskFiNor = 0.3;
            riskFiHea = 0.2;
        }
        else if(this.role.equals("assassin"))
        {
            riskExNor = 0.6;
            riskExHea = 0.4;
            riskFiNor = 0.4;
            riskFiHea = 0.3;
        }
        else
        {
            riskExNor = 0.2;
            riskExHea = 0.2;
            riskFiNor = 0.3;
            riskFiHea = 0.3;
        }

        if(RAND.nextDouble() < ((type == 1) ? riskExNor : riskExHea))
        {
            extraDamage = RAND.nextInt(6) + 1;
        }
        double totalATK = baseDamage + extraDamage;
        if(other.defense > 0)
        {
            if(RAND.nextDouble() < ((type == 1) ? riskFiNor : riskFiHea))
            {
                finalDamage = totalATK; //armor piercing
                System.out.println(GrapForCombat.printDA("<ARMOR PIERCING!>"));
            }
            else
            {
                finalDamage = Math.max(1, totalATK - other.defense);
                other.subDefense();
            }
        }
        else
        {
            finalDamage = totalATK;
        }
        
        if(extraDamage > 0)
        {
            System.out.println(GrapForCombat.printDA("<CRITICAL HIT! (" + baseDamage + " + " + extraDamage + ")>"));
        }
        System.out.println(GrapForCombat.printDA("<[" + this.name + "] DEALT " + finalDamage + " DAMAGE TO [" + other.getName() + "]>"));
        other.setHP(other.getHP() - finalDamage);
        this.strength-= ((type == 1) ? 1 : 2);
    }
}