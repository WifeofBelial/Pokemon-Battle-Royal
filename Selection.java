package utils;

import model.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Selection {
    Scanner sc = new Scanner(System.in);
    Random r = new Random();

    public List<Pokemon> playerSelection(Pokedex pokedex) {
        List<Pokemon> team = new ArrayList<>(6);
        while (team.size() != 6) {
            System.out.println("Choose a Pokemon!");
            Pokemon pokemon = null;

            if (sc.hasNextInt()) {
                int idx = Integer.parseInt(sc.nextLine());
                pokemon = pokedex.getPokemon(idx);
            } else if (sc.hasNextLine()) {
                String name = sc.nextLine();
                pokemon = pokedex.getPokemon(name);
            }
            if (pokemon == null) {
                System.out.println("Invalid input");
                continue;
            }
            team.add(pokemon);
            System.out.println("Your team: ");
            int pokemonNum = 1;
            for (Pokemon pokemon1 : team) {
                String str = String.format("(" + pokemonNum + ")%-20s", " " + pokemon1.getPokemonDesc());
                System.out.println(str);
                pokemonNum++;
            }
            System.out.println();
        }
        return team;
    }

    public List<Pokemon> compSelection(Pokedex pokedex) {
        List<Pokemon> team = new ArrayList<>(6);
        while (team.size() != 6) {
            int idx = r.nextInt(1, 152);
            Pokemon pokemon = pokedex.getPokemon(idx);
            team.add(pokemon);
        }
        System.out.println("Enemy team: ");
        int pokemonNum = 1;
        for (Pokemon pokemon1 : team) {
            String str = String.format("(" + pokemonNum + ")%-20s", " " + pokemon1.getPokemonDesc());
            System.out.println(str);
            pokemonNum++;
        }
        System.out.println();
        return team;
    }
}
