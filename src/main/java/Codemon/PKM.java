package Codemon;

import java.util.List;

public class PKM {
    private String name, type;
    private int hp, attack, defense;
    private List<Move> moves;

    public PKM(String name, String type, int hp, int attack, int defense) {
        this.name = name;
        this.type = type;
        this.hp = Math.max(0, hp);
        this.attack = Math.max(0, attack);
        this.defense = Math.max(0, defense);
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public List<Move> getMoves() { return moves; }
    
    public void setHp(int hp) { this.hp = Math.max(0, hp); }
    public void setMoves(List<Move> moves) { this.moves = moves; }
}
