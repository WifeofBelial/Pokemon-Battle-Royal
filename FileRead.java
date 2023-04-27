package utils;

import model.AttackInfo;
import model.Kind;
import model.PokemonInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileRead {

    public static List<PokemonInfo> readPokemon(String pokemonFilePath) {
        List<PokemonInfo> pokemonInfoList = new ArrayList<>();
        try {
            List<String> pokemonLines = Files.readAllLines(Paths.get(pokemonFilePath));
            for (String pokemonLine : pokemonLines) {
                String[] line = pokemonLine.split(";");
                if (line[0].contains("#")) {
                    continue;
                }
                int idx = Integer.parseInt(line[0]);
                String name = line[1];
                String typeOne = line[2];
                String typeTwo = line[3];
                int totalHP = Integer.parseInt(line[5]);
                int attack = Integer.parseInt(line[6]);
                int defense = Integer.parseInt(line[7]);
                int spAtk = Integer.parseInt(line[8]);
                int spDef = Integer.parseInt(line[9]);
                int speed = Integer.parseInt(line[10]);
                PokemonInfo pokemonInfo = new PokemonInfo(idx, name, typeOne, typeTwo, totalHP, attack, defense, spAtk, spDef, speed);
                pokemonInfoList.add(pokemonInfo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pokemonInfoList;
    }

    public static List<AttackInfo> readAttack(String attackFilePath) {
        List<AttackInfo> attackInfoList = new ArrayList<>();
        try {
            List<String> attackLines = Files.readAllLines(Paths.get(attackFilePath));
            for (String attackLine : attackLines) {
                String[] line = attackLine.split(";");
                if (line[0].contains("#")) {
                    continue;
                }
                int idx = Integer.parseInt(line[0]);
                String name = line[1];
                String effect = line[2];
                String type = line[3];
                String kindString = line[4];
                Kind kind;
                if (kindString.equals("Special")) {
                    kind = Kind.Special;
                } else {
                    kind = Kind.Physical;
                }
                int dmg = Integer.parseInt(line[5]);
                int accuracy = Integer.parseInt(line[6].replace("%", ""));
                int attackCount = Integer.parseInt(line[7]);
                AttackInfo attackInfo = new AttackInfo(idx, name, effect, type, kind, dmg, accuracy, attackCount);
                attackInfoList.add(attackInfo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return attackInfoList;
    }

    public static HashMap<String, HashMap<String, Double>> readEffectiveness(String effectivenessFilePath) {
        HashMap<String, HashMap<String, Double>> effectivenessTable = new HashMap<>();
        try {
            List<String> effLines = Files.readAllLines(Paths.get(effectivenessFilePath));
            String[] typeHeader = effLines.get(0).split(",");
            for (int i = 1; i < effLines.size(); i++) {
                HashMap<String, Double> valueMap = new HashMap<>();
                String[] line = effLines.get(i).split(",");
                for (int j = 1; j < line.length; j++) {
                    Double eff = Double.valueOf(line[j]);
                    valueMap.put(typeHeader[j], eff);
                }
                effectivenessTable.put(line[0], valueMap);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return effectivenessTable;
    }
}

