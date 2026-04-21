package characters;
import engines.*;

public class Mage extends FuncForCombat 
{
    public Mage(String name) 
    {
        this.name = name;
        this.role = "mage";
        this.hp = NORMAL_HP - 35;
        this.damage = NORMAL_DAMAGE + 10;
        this.defense = 0;
        this.strength = 10;
        this.mana = 50;
    }

    @Override public void def()
    {
        int i;
        switch((int)this.mana / 10)
        {
            case 0 -> 
            {
                i = 1;
            }
            case 1, 2 -> 
            {
                i = 6;
            }
            case 3, 4 -> 
            {
                i = 11;
            }
            case 5, 6, 7 -> 
            {
                i = 16;
            }
            default -> 
            {
                i = 21;
            }
        }

        this.setDefense(RAND.nextInt(i) + 5);
        this.setMana(-(i-1));
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

        double baseDamage = (other.getRole().equals("mage")) ? this.damage * 0.7 : this.damage;
        double extraDamage = 0;
        int extraDamageTank = 0;
        double damageToHP;
        double riskExNor, riskExHea;
        
        riskExNor = 0.3;
        riskExHea = 0.2;

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
        damageToHP = totalATK;
        
        System.out.println(GrapForCombat.printDA("<[" + this.name + "] DEALT " + damageToHP + " DAMAGE TO [" + other.getName() + "]>"));
        other.setHP(other.getHP() - damageToHP);
        this.setStrength((type == 1) ? -1 : -2);
        this.setMana((type == 1) ? 15 : 20);
        other.setMana(((other.getRole().equals("tanker")) ? (int)(damageToHP / 2) : 0));
    }

    @Override public void manaTurn() 
    {
        this.setMana(20);
    }

    @Override public void ultimate(FuncForCombat other) 
    {
        GrapForCombat.getGrapRole(this.role);

        String[] s = {"§§Avada Kedavra§§", "§§Crucio§§", "§§Sectumsempra§§", "§§Reducto§§", "§§Bombarda§§", "§§Confringo§§"};
        System.out.print(GrapForCombat.printNO("[]"));
        for(int i=0; i<100; i++)
        {
            System.out.print("\r      " + s[RAND.nextInt(s.length)]);
            GrapForCombat.waitTime(RAND.nextInt(2) * 20);
        }
        System.out.print(GrapForCombat.printNO("\r-------------=<O>=-------------\n"));

        System.out.println(GrapForCombat.printDA("\n<[" + this.name + "] DEALT " + this.damage * 1.4 + " DAMAGE TO [" + other.getName() + "]>"));
        System.out.println(GrapForCombat.printST("<[" + this.name + "] DRAINED 20 MANA FROM [" + other.getName() + "]>"));
        other.setHP(other.getHP() - this.damage * 1.4);
        other.setMana(-20);
        this.setMana(-100);
        if(other.getMana() == 0)
        {
            this.setHP(this.getHP() + 10);
        }
    }

    @Override public boolean dodge() 
    {
        return RAND.nextInt(100) < 20;
    }
}