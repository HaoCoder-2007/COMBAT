import java.util.Random;
import java.util.Scanner;

public class FuncForCombat
{
    private double hp;
    private double damage;
    private double defense;
    private int strength;
    private int mana;
    private final String name;
    private final String role;
    private static final int NORMAL_HP = 100;
    private static final int MAX_DEFENSE = 50;
    private static final int MAX_STRENGTH = 10;
    private static final int MAX_MANA = 100;
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
                this.hp = NORMAL_HP + 20;
                this.damage = 15;
                this.defense = 0;
                this.strength = 10;
                break;
            }

            default: //unknown
            {
                this.name = "";
                this.role = "unknown";
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

    private void setDefense(double defense)
    {
        if(this.defense + defense >= MAX_DEFENSE)
        {
            this.defense = MAX_DEFENSE;
        }
        else
        {
            this.defense += defense;
        }
    }

    private void setStrength(int strength)
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

    private void setMana(int mana)
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

    public void def()
    {
        if(this.role.equals("tanker"))
        {
            for(int i=0; i<RAND.nextInt(10)+1; i++)
            {
                this.setDefense(RAND.nextDouble() + 0.1);
            }
        }
        else
        {
            for(int i=0; i<RAND.nextInt(8)+1; i++)
            {
                this.setDefense(RAND.nextDouble() + 0.1);
            }
        }
        this.defense = Math.round(this.defense * 10.0) / 10.0;
        this.setStrength(-2);
    }

    public void skip()
    {
        this.setStrength(2);
        this.setMana(20);
    }

    public void atk(FuncForCombat other, int type)
    {
        double baseDamage = this.damage;
        double extraDamage = 0;
        double damageToHP;
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
            System.out.println(GrapForCombat.printDA("<CRITICAL HIT! (" + baseDamage + " + " + extraDamage + ")>"));
        }
        double totalATK = baseDamage + extraDamage + ((other.role.equals("tanker")) ? 3 : 0);
        if(other.defense > 0)
        {
            if(RAND.nextDouble() < ((type == 1) ? riskFiNor : riskFiHea))
            {
                damageToHP = totalATK; //armor piercing
                System.out.println(GrapForCombat.printDA("<ARMOR PIERCING!>"));
            }
            else
            {
                if(totalATK <= other.defense)
                {
                    System.out.println(GrapForCombat.printDF("<[" + other.name + "] ARMOR ABSORBED " + totalATK + " DAMAGE>"));
                    other.defense -= totalATK;
                    damageToHP = 0;
                }
                else
                {
                    System.out.println(GrapForCombat.printDF("<[" + other.name + "] ARMOR BROKEN>"));
                    this.setMana(15);
                    damageToHP = totalATK - other.defense;
                    other.defense = 0;
                }
            }
        }
        else
        {
            damageToHP = totalATK;
        }
        
        System.out.println(GrapForCombat.printDA("<[" + this.name + "] DEALT " + damageToHP + " DAMAGE TO [" + other.getName() + "]>"));
        other.setHP(other.getHP() - damageToHP);
        this.setStrength((type == 1) ? -1 : -2);
        this.setMana(((this.role.equals("assassin") && extraDamage > 0) ? ((type == 1) ? 20 : 30) : ((type == 1) ? 10 : 15)));
        other.setMana(((other.role.equals("tanker")) ? (int)(damageToHP / 2) : 0));
    }

    public void manaTurn()
    {
        if(this.role.equals("warrior"))
        {
            this.setMana(5);
        }
        else if(this.role.equals("assassin"))
        {
            this.setMana(3);
        }
        else if(this.role.equals("tanker"))
        {
            this.setMana(10);
        }
    }

    public void ultimate(FuncForCombat other)
    {
        if(this.role.equals("warrior"))
        {
            GrapForCombat.getGrapRole(this.role);
            System.out.println(GrapForCombat.printHP("\n<[" + this.name + "] GET " + (this.hp * 0.3) + " HP>"));
            System.out.println(GrapForCombat.printST("<[" + this.name + "] GET 5 STRENGTH>"));
            this.hp += this.hp * 0.3;
            this.setStrength(5);
        }
        else if(this.role.equals("assassin"))
        {
            GrapForCombat.getGrapRole(this.role);
            System.out.println(GrapForCombat.printDA("\n<[" + this.name + "] DEALT " + (this.damage * 1.5) + " DAMAGE ARMOR PIERCING TO [" + other.name + "]>"));
            other.setHP(other.getHP() - this.damage * 1.5);
        }
        else if(this.role.equals("tanker"))
        {
            GrapForCombat.getGrapRole(this.role);
            System.out.println(GrapForCombat.printDF("\n<[" + this.name + "] GET 50 DEFENSE>"));
            this.setDefense(MAX_DEFENSE);
        }
        this.setMana(-100);
    }
}