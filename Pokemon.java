package model;

import java.util.List;

public class Pokemon {

    public String name;
    public String typeOne;
    public String typeTwo;
    public int lvl;
    public int totalHP;
    public int attack;
    public int defense;
    public int spAtk;
    public int spDef;
    public int speed;
    public List<Attack> attackList;
    public boolean currentlyFighting;

    public Pokemon(String name, String typeOne, String typeTwo, int lvl, int totalHP, int attack, int defense, int spAtk, int spDef, int speed, List<Attack> attackList) {
        this.name = name;
        this.typeOne = typeOne;
        this.typeTwo = typeTwo;
        this.lvl = lvl;
        this.totalHP = totalHP * lvl / 25;
        this.attack = attack * lvl / 50;
        this.defense = defense * lvl / 50;
        this.spAtk = spAtk * lvl / 50;
        this.spDef = spDef * lvl / 50;
        this.speed = speed * lvl / 100;
        this.attackList = attackList;
    }

    public String getPokemonDesc() {
        return name + " Lvl. " + lvl + " / " + "HP " + totalHP;
    }

    public String getPokemonName() {
        return name;
    }

    public String getAttackDesc() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < attackList.size(); i++) {
            Attack atk = attackList.get(i);
            sb.append("(").append(i + 1).append(") ").append(atk.name).append(" / ").append(atk.attackCount)
                    .append("\n").append("\t").append(atk.effect).append("\n");
        }
        return sb.toString();
    }
}
