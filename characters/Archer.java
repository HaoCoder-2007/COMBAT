package characters;
import engines.*;
import java.util.concurrent.*;

public class Archer extends FuncForCombat
{
    public Archer(String name)
    {
        this.name = name;
        this.role = "archer";
        this.hp = NORMAL_HP - 25;
        this.damage = 20;
        this.defense = 0;
        this.strength = 10;
        this.mana = 0;
    }

    @Override public void def()
    {
        for(int i=0; i<RAND.nextInt(7)+1; i++)
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
        double riskExNor, riskExHea, riskFiNor, riskFiHea, riskBonusShot, extraDamageBonusShot = 0;
        
        riskExNor = 0.8;
        riskExHea = 0.6;
        riskFiNor = 0.6;
        riskFiHea = 0.5;
        riskBonusShot = 0.5;

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
        if(RAND.nextDouble() < riskBonusShot)
        {
            extraDamageBonusShot = this.damage * RAND.nextDouble();
            System.out.println(GrapForCombat.printDA("<BONUS HIT! (" + extraDamageBonusShot + ")>"));
        }

        double totalATK = baseDamage + extraDamage + extraDamageTank + extraDamageBonusShot;

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
        this.setMana((type == 1) ? 15 : 20);
        other.setMana(((other.getRole().equals("tanker")) ? (int)(damageToHP / 2) : 0));
    }

    @Override public void manaTurn()
    {
        this.setMana(15);
    }

    @Override public void ultimate(FuncForCombat other) 
    {
        int rangeVal = RAND.nextInt(11) + 10;
        GrapForCombat.getGrapRole(this.role);
        
        CountDownLatch latch = new CountDownLatch(rangeVal);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        System.out.println(GrapForCombat.printDA("\n<[" + this.name + "] RELEASED AN ARROW RAIN!>\n"));

        for (int i=0; i<rangeVal; i++) 
        {
            final int index = i;
            scheduler.schedule(() -> {
                if(index < rangeVal - 1) System.out.print(GrapForCombat.printDA("<HIT> "));
                latch.countDown();

                if(index == rangeVal - 2) System.out.print(GrapForCombat.printDA("<HIT> \n\n<[" + this.name + "] DEALT A TOTAL OF " + rangeVal + " HITS WITH " + (this.damage * 0.1 * rangeVal) + " DAMAGE TO [" + other.getName() + "]>\n"));
                if(index == rangeVal - 1) scheduler.shutdown();
            }, i * 200, TimeUnit.MILLISECONDS);
        }

        try 
        {
            latch.await(); 
        } 
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }

        other.setHP(other.getHP() - (this.damage * 0.1 * rangeVal));
        this.setMana(-100);
    }

    @Override public boolean dodge()
    {
        int chance = RAND.nextInt(100) + 1;
            return chance <= 35;
    }
}