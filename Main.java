package service;

import utils.Pokedex;
import model.Pokemon;
import utils.Selection;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Pokedex pokedex = new Pokedex();
        Selection selection = new Selection();
        pokedex.printPokedex();

        List<Pokemon> playerPokemon = selection.playerSelection(pokedex);
        List<Pokemon> cpuPokemon = selection.compSelection(pokedex);

        new Battle().battle(playerPokemon, cpuPokemon);
    }
}
