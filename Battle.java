package service;

import model.Attack;
import model.Kind;
import model.Pokemon;
import utils.FileRead;

import java.util.*;

public class Battle {
    Scanner sc = new Scanner(System.in);
    Random r = new Random();
    HashMap<String, HashMap<String, Double>> effTable = FileRead.readEffectiveness("src/main/resources/effectiveness.csv");

    public void battle(List<Pokemon> teamPlayer, List<Pokemon> teamComp) {
        System.out.println("The battle begins!");
        Pokemon currentFighterPlayer = playerChoice(teamPlayer);
        Pokemon currentFighterComp = compChoice(teamComp);

        while (true) {
            if (currentFighterPlayer.totalHP <= 0) {
                System.out.println("Your Pokemon was defeated.");
                System.out.println();
                currentFighterPlayer = playerChoice(teamPlayer);
                if (currentFighterPlayer.totalHP <= 0) {
                    System.out.println("Computer has won the battle.");
                    break;
                }
            }
            if (currentFighterComp.totalHP <= 0) {
                System.out.println("The enemy Pokemon was defeated.");
                System.out.println();
                currentFighterComp = compChoice(teamComp);
                if (currentFighterComp.totalHP <= 0) {
                    System.out.println("You won the battle.");
                    break;
                }
            }

            System.out.println("What would you like to do?");
            System.out.println("(1) Attack!");
            System.out.println("(2) Change Pokemon!");
            System.out.println("(3) Run away!");
            System.out.println();
            int option = sc.nextInt();
            System.out.println();

            switch (option) {
                case 1 -> {
                    fightRound(currentFighterPlayer, currentFighterComp);
                }
                case 2 -> currentFighterPlayer = playerChoice(teamPlayer);
                case 3 -> {
                    System.out.println("You left the battle.");
                    System.exit(0);
                }
            }
        }
    }

    public Pokemon playerChoice(List<Pokemon> playerTeam) {
        if (allPokemonAreDead(playerTeam)) {
            return playerTeam.get(0);
        }
        System.out.println("Choose a Pokemon! Select 1-6.");
        int pokemonNum = 1;
        for (Pokemon pokemon1 : playerTeam) {
            String str = String.format("(" + pokemonNum + ")%-20s", " " + pokemon1.getPokemonDesc());
            System.out.println(str);
            pokemonNum++;
        }
        System.out.println();
        int selection = sc.nextInt();
        if (selection < 0 || selection > playerTeam.size()) {
            System.out.println("Invalid choice. Try again!");
            return playerChoice(playerTeam);
        }
        Pokemon choice = playerTeam.get(selection - 1);
        if (choice.totalHP <= 0) {
            System.out.println("Pokemon has been defeated (HP = 0). Choose another Pokemon!");
            return playerChoice(playerTeam);
        }
        if (choice.currentlyFighting) {
            System.out.println("This Pokemon is already fighting. Choose another one!");
            return playerChoice(playerTeam);
        }
        for (Pokemon pokemon : playerTeam) {
            pokemon.currentlyFighting = false;
        }
        choice.currentlyFighting = true;
        System.out.println();
        System.out.println("You chose " + choice.getPokemonDesc());
        return choice;
    }

    public Attack attackChoicePlayer(List<Attack> attackList) {
        System.out.println("Choose an attack!");
        int attackNum = 1;
        for (Attack attack : attackList) {
            String str1 = String.format("(" + attackNum + ")%-20s", " " + attack.getAttackInfo());
            String str2 = String.format("->%-20s", " " + attack.getAttackEffect());
            System.out.println(str1);
            System.out.println(str2);
            attackNum++;
        }
        int selection = sc.nextInt();
        if (selection < 1 || selection > 4) {
            System.out.println("Invalid choice. Try again!");
            return attackChoicePlayer(attackList);
        }
        Attack choice = attackList.get(selection - 1);
        if (choice.attackCount <= 0) {
            System.out.println("No more attacks available.");
            return attackChoicePlayer(attackList);
        }
        choice.attackCount--;
        System.out.println();
        return choice;
    }

    public Pokemon compChoice(List<Pokemon> compTeam) {
        int selection = r.nextInt(0, compTeam.size());
        if (compTeam.get(selection).totalHP <= 0) {
            if (allPokemonAreDead(compTeam)) {
                return compTeam.get(selection);
            }
            return compChoice(compTeam);
        }
        System.out.println("Your enemy chose " + compTeam.get(selection).getPokemonDesc());
        System.out.println();
        return compTeam.get(selection);
    }

    private boolean allPokemonAreDead(List<Pokemon> compTeam) {
        for (Pokemon pokemon : compTeam) {
            if (pokemon.totalHP > 0) {
                return false;
            }
        }
        return true;
    }

    public Attack attackChoiceComp(List<Attack> attackList) {
        int selection = r.nextInt(0, attackList.size());
        if (attackList.get(selection).attackCount <= 0) {
            return attackChoiceComp(attackList);
        }
        attackList.get(selection).attackCount--;
        return attackList.get(selection);
    }

    public void fightRound(Pokemon playerChoice, Pokemon compChoice) {
        String strOne = String.format("%20s", playerChoice.getPokemonDesc());
        String strTwo = String.format(" VS %20s", compChoice.getPokemonDesc());
        System.out.print(strOne);
        System.out.println(strTwo);
        System.out.println("=".repeat(70));
        System.out.println();
        Attack playerAttack = attackChoicePlayer(playerChoice.attackList);
        Attack compAttack = attackChoiceComp(compChoice.attackList);

        int dmgPlayer = getDamage(playerChoice, compChoice, playerAttack);
        int dmgComp = getDamage(compChoice, playerChoice, compAttack);

        if (playerChoice.speed < compChoice.speed) {
            executeAttack(compChoice, playerChoice, dmgComp, dmgPlayer, compAttack, playerAttack);
        } else {
            executeAttack(playerChoice, compChoice, dmgPlayer, dmgComp, playerAttack, compAttack);
        }
    }

    private static void executeAttack(Pokemon firstHitPkm, Pokemon secondHitPkm, int firstHitDmg, int secondHitDmg, Attack attack, Attack secondAttack) {
        secondHitPkm.totalHP = secondHitPkm.totalHP - firstHitDmg;
        System.out.println(firstHitPkm.getPokemonName() + " attacks with " + attack.getAttackName() + " and deals " + firstHitDmg + " damage.");
        if (secondHitPkm.totalHP > 0) {
            firstHitPkm.totalHP = firstHitPkm.totalHP - secondHitDmg;
            System.out.println(secondHitPkm.getPokemonName() + " attacks with " + secondAttack.getAttackName() + " and deals " + secondHitDmg + " damage.");
        }
        System.out.println();
    }

    private int getDamage(Pokemon attackPokemon, Pokemon defendPokemon, Attack attack) {
        double atkDefCoef;
        if (attack.kind == Kind.Special) {
            atkDefCoef = (attackPokemon.spAtk + 0.0) / defendPokemon.spDef;
        } else {
            atkDefCoef = (attackPokemon.attack + 0.0) / defendPokemon.defense;
        }
        double stab;
        double randomFactor = r.nextDouble(0.25, 1);
        if (Objects.equals(attackPokemon.typeOne, attack.type)) {
            stab = 1.25;
        } else {
            stab = 1;
        }
        double lvlFactor = (0.0 + attackPokemon.lvl) / 50;

        Double effOne = effTable.get(attack.type).get(defendPokemon.typeOne);
        Double effTwo = effTable.get(attack.type).get(defendPokemon.typeTwo);
        if (effTwo == null) {
            effTwo = 1.0;
        }
        return (int) (attack.dmg * atkDefCoef * lvlFactor * stab * randomFactor * effOne * effTwo);
    }
}