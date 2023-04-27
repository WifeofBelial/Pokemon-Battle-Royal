package model;

public class AttackInfo {
    public int idx;
    public String name;
    public String effect;
    public String type;
    public Kind kind;
    public int dmg;
    public int accuracy;
    public int attackCount;

    public AttackInfo(int idx, String name, String effect, String type, Kind kind, int dmg, int accuracy, int attackCount) {
        this.idx = idx;
        this.name = name;
        this.effect = effect;
        this.type = type;
        this.kind = kind;
        this.dmg = dmg;
        this.accuracy = accuracy;
        this.attackCount = attackCount;
    }
}
