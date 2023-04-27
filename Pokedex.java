package utils;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pokedex {
    List<PokemonInfo> pokedex;
    List<AttackInfo> attacks;

    Random random = new Random();

    public Pokedex() {
        pokedex = FileRead.readPokemon("src/main/resources/pokemon.csv");
        attacks = FileRead.readAttack("src/main/resources/Attacks.csv");
    }

    public Pokemon getPokemon(int id) {
        if (id < 1 || id > pokedex.size()) {
            return null;
        }
        PokemonInfo pI = pokedex.get(id - 1);
        List<Attack> attackList = getAttacks(pI.typeOne);
        return new Pokemon(pI.name, pI.typeOne, pI.typeTwo, random.nextInt(10, 101), pI.totalHP, pI.attack, pI.defense, pI.spAtk, pI.spDef, pI.speed, attackList);
    }

    public Pokemon getPokemon(String name) {
        PokemonInfo pI = null;
        for (PokemonInfo pokemonInfo : pokedex) {
            if (pokemonInfo.name.equals(name)) {
                pI = pokemonInfo;
                break;
            }
        }
        if (pI == null) {
            return null;
        }
        List<Attack> attackList = getAttacks(pI.typeOne);
        return new Pokemon(pI.name, pI.typeOne, pI.typeTwo, random.nextInt(10, 101), pI.totalHP, pI.attack, pI.defense, pI.spAtk, pI.spDef, pI.speed, attackList);
    }

    public List<Attack> getAttacks(String typeOne) {
        List<Attack> attackList = new ArrayList<>();

        boolean hasSelfType = false;
        boolean hasNormaltype = false;

        while (!hasSelfType || !hasNormaltype) {
            AttackInfo aI = attacks.get(random.nextInt(attacks.size()));
            if (aI.type.equals("Normal") && !hasNormaltype) {
                hasNormaltype = true;
                attackList.add(new Attack(aI.name, aI.effect, aI.type, aI.kind, aI.dmg, aI.accuracy, aI.attackCount));
            } else if (aI.type.equals(typeOne) && !hasSelfType) {
                hasSelfType = true;
                attackList.add(new Attack(aI.name, aI.effect, aI.type, aI.kind, aI.dmg, aI.accuracy, aI.attackCount));
            }
        }
        for (int i = 0; i < 2; i++) {
            AttackInfo aI = attacks.get(random.nextInt(attacks.size()));
            Attack attack = new Attack(aI.name, aI.effect, aI.type, aI.kind, aI.dmg, aI.accuracy, aI.attackCount);
            attackList.add(attack);
        }
        return attackList;
    }

    public void printPokedex() {
        int rowCount = 0;
        for (PokemonInfo pokemonInfo : pokedex) {
            String str = String.format("#%-20s", pokemonInfo.getIndexAndName());
            System.out.print(str);
            rowCount++;
            if (rowCount == 10) {
                rowCount = 0;
                System.out.println();
            }
        }
        System.out.println();
        System.out.println();
    }
}