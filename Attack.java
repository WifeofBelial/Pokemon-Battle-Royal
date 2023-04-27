package model;

public class Attack {

    public String name;
    public String effect;
    public String type;
    public Kind kind;
    public int dmg;
    public int accuracy;
    public int attackCount;

    public Attack(String name, String effect, String type, Kind kind, int dmg, int accuracy, int attackCount) {
        this.name = name;
        this.effect = effect;
        this.type = type;
        this.kind = kind;
        this.dmg = dmg;
        this.accuracy = accuracy;
        this.attackCount = attackCount;
    }
    public String getAttackInfo() {
        return name + " / " + attackCount;
    }
    public String getAttackEffect(){
        return effect;
    }

    public String getAttackName(){
        return name;
    }
}
