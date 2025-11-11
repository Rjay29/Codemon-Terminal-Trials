package Codemon;

public abstract class PKM {
    protected String name;
    protected String type;
    protected int level;
    protected int hp;
    protected int attack;
    protected int defense;

    public abstract void useMove();

    // Getters
    public String getName() { return name; }
    public String getType() { return type; }
    public int getLevel() { return level; }
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }

    // Setters
    public void setHp(int hp) { this.hp = Math.max(0, hp); }
    public void setLevel(int level) { this.level = level; }
}