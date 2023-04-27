package model;

public class PokemonInfo {
    public int idx;
    public String name;
    public String typeOne;
    public String typeTwo;
    public int totalHP;
    public int attack;
    public int defense;
    public int spAtk;
    public int spDef;
    public int speed;

    public PokemonInfo(int idx, String name, String typeOne, String typeTwo, int totalHP, int attack, int defense, int spAtk, int spDef, int speed) {
        this.idx = idx;
        this.name = name;
        this.typeOne = typeOne;
        this.typeTwo = typeTwo;
        this.totalHP = totalHP;
        this.attack = attack;
        this.defense = defense;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.speed = speed;
    }

    public String getIndexAndName() {
        return idx + " " + name;
    }
}
