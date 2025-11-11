package Codemon;

public class Move {
    private final String name;
    private final String type;
    private final int power;
    private final int accuracy;
    private final String damageClass;

    public Move(String name, String type, int power, int accuracy, String damageClass) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.damageClass = damageClass;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getPower() { return power; }
    public int getAccuracy() { return accuracy; }
    public String getDamageClass() { return damageClass; }
}