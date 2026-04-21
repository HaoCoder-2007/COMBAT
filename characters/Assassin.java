package characters;
import engines.*;

public class Assassin extends FuncForCombat
{
    public Assassin(String name)
    {
        this.name = name;
        this.role = "assassin";
        this.hp = NORMAL_HP - 15;
        this.damage = NORMAL_DAMAGE + 5;
        this.defense = 0;
        this.strength = 10;
        this.mana = 0;
    }

    @Override public void def()
    {
        for(int i=0; i<RAND.nextInt(8)+1; i++)
        {
            this.setDefense(RAND.nextDouble() + 0.1);
        }
        this.defense = Math.round(this.defense * 10.0) / 10.0;
        this.setStrength(-2);
    }

    @Override public void atk(FuncForCombat other, int type)
    {
        if(other.dodge())
        {
            other.setMana((other.getRole().equals("tanker")) ? 10 : 5);
            System.out.println(GrapForCombat.printNO("<[" + other.getName() + "] DODGE>"));
            return;
        }

        double baseDamage = this.damage;
        double extraDamage = 0;
        int extraDamageTank = 0;
        double damageToHP;
        double riskExNor, riskExHea, riskFiNor, riskFiHea;
        
        riskExNor = 0.6;
        riskExHea = 0.4;
        riskFiNor = 0.4;
        riskFiHea = 0.3;

        if(RAND.nextDouble() < ((type == 1) ? riskExNor : riskExHea))
        {
            extraDamage = RAND.nextInt(6) + 1;
            System.out.println(GrapForCombat.printDA("<CRITICAL HIT! (" + baseDamage + " + " + extraDamage + ")>"));
        }

        if(other.getRole().equals("tanker"))
        {
            extraDamageTank = RAND.nextInt(6) + 1;
            System.out.println(GrapForCombat.printDA("<BONUS FOR TANKER! (" + extraDamageTank + ")>"));
        }

        double totalATK = baseDamage + extraDamage + extraDamageTank;

        if(other.getDefense() > 0)
        {
            if(RAND.nextDouble() < ((type == 1) ? riskFiNor : riskFiHea))
            {
                damageToHP = totalATK; //armor piercing
                System.out.println(GrapForCombat.printDA("<ARMOR PIERCING!>"));
            }
            else
            {
                if(totalATK <= other.getDefense())
                {
                    System.out.println(GrapForCombat.printDF("<[" + other.getName() + "] ARMOR ABSORBED " + totalATK + " DAMAGE>"));
                    other.setDefense((-totalATK));
                    damageToHP = 0;
                }
                else
                {
                    System.out.println(GrapForCombat.printDF("<[" + other.getName() + "] ARMOR BROKEN>"));
                    this.setMana(15);
                    damageToHP = totalATK - other.getDefense();
                    other.setDefense((-other.getDefense()));
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
        this.setMana((type == 1) ? 20 : 30);
        other.setMana(((other.getRole().equals("tanker")) ? (int)(damageToHP / 2) : 0));
    }

    @Override public void manaTurn()
    {
        this.setMana(5);
    }

    @Override public void ultimate(FuncForCombat other)
    {
        GrapForCombat.getGrapRole(this.role);
        GrapForCombat.waitTime(200);
        System.out.print("////////////////////////");
        GrapForCombat.waitTime(100);
        System.out.print(GrapForCombat.printHP("\r////////////////////////"));
        GrapForCombat.waitTime(200);
        System.out.print("\r////////////////////////");
        GrapForCombat.waitTime(100);
        System.out.print(GrapForCombat.printHP("\r<====}+-   -+{====>     \n"));
        System.out.println(GrapForCombat.printDA("\n<[" + this.name + "] DEALT " + (this.damage * 1.5) + " DAMAGE TO [" + other.getName() + "]>"));
        other.setHP(other.getHP() - this.damage * 1.5);
        this.setMana(-100);
    }

    @Override public boolean dodge()
    {
        int chance = RAND.nextInt(100) + 1;
            return chance <= 20;
    }
}