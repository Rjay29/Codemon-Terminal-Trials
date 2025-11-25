package Codemon;

import java.util.List;

public class Species {
    private String name;
    private String type;
    private int level;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private List<Move> moves;

    public Species(String name, String type, int level, int hp, int attack, int defense, List<Move> moves) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.moves = moves;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public List<Move> getMoves() { return moves; }
}