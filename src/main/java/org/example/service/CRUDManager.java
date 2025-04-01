package org.example.service;

import org.example.db.DBPokemonServie;
import org.example.db.DBTrenerService;
import org.example.db.Pokemon;
import org.example.db.Trener;
import org.example.utility.InputUtils;

import java.util.List;

public class CRUDManager {
    private final DBTrenerService trenerService;
    private final DBPokemonServie pokemonService;

    public CRUDManager() {
        this.pokemonService = new DBPokemonServie();
        this.trenerService = new DBTrenerService();
    }

    public void printOptions() {
        System.out.println("\n--- Welcome in game pokemon ---\n");

        while (true) {
            System.out.println("\n0. Get all trainers");
            System.out.println("1. Get trainer by id");
            System.out.println("2. Add trainer");
            System.out.println("3. Edit trainer ");
            System.out.println("4. Delete trainer\n");
            System.out.println("5. Get all pokemons");
            System.out.println("6. Add Pokemon");
            System.out.println("7. Catch pokemon");
            System.out.println("8. Evolve pokemon");
            System.out.println("9. Delete pokemon");
            System.out.println("10. Exit");
            System.out.println("\n--- Zadaj prikaz:");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 0 -> getAllTrainers();
                case 1 -> getTrainerById();
                case 2 -> createTrainer();
                case 3 -> editTrainer();
                case 4 -> deleteTrainer();
                case 5 -> getAllPokemons();
                case 6 -> addPokemon();
                case 7 -> catchPokemon();// Nespravne inputy
                case 8 -> evolvePokemon();
                case 9 -> deletePokemon();
                case 10 -> {
                    System.out.println("--- Goodbye ---");
                    return;
                }
                default -> System.out.println("Invalid input, try again");

            }
        }

    }

    public void evolvePokemon() {
        List<Pokemon> pokemons = pokemonService.getAllPokemons();
        System.out.println("Zadaj pokemona kotreho chces vyvinut:");
        for (int i = 0; i < pokemons.size(); i++) {
            System.out.println((i + 1) + ". " + pokemons.get(i));
        }
        int choice;
        while (true) {
            choice = InputUtils.readInt();
            if (choice > 0 && choice <= pokemons.size()) {
                break;
            } else {
                System.out.println("Evolvnut mozes len existujuceho pokemna, skus este raz");
            }
        }


        if (pokemons.get(choice - 1).getLvl() >= 3) {
            System.out.println("Pokemon je uz na maximalnom leveli");
            return;
        }

        System.out.println("zadaj vyvin pokemona: ");
        String name = InputUtils.readString();
        if (pokemonService.evolvePokemon(name, pokemons.get(choice - 1).getLvl() + 1, pokemons.get(choice - 1).getId()) > 0) {
            System.out.println("Pokemon successfully evolved");
        }


    }


    public void deletePokemon() {
        List<Pokemon> pokemons = pokemonService.getAllPokemons();
        for (int i = 0; i < pokemons.size(); i++) {
            System.out.println((i + 1) + ". " + pokemons.get(i));
        }
        System.out.println("Zadaj pokemona ktoreho chces vymazat:");

        int choice;
        while (true) {
            choice = InputUtils.readInt();
            if (choice > 0 && choice <= pokemons.size()) {
                break;
            } else {
                System.out.println("Nespravny vstup, skus este raz");
            }

        }


        if (pokemonService.deletePokemon(pokemons.get(choice - 1).getId()) > 0) {
            System.out.println("Pokemon successfully deleted");
        }

    }


    public void catchPokemon() {
        List<Trener> trainers = trenerService.readAllTrainers();


        for (int i = 0; i < trainers.size(); i++) {
            System.out.println((i + 1) + ". " + trainers.get(i));
        }
        System.out.println("Zadaj trenera ktory chyta pokemona:");
        int trener;

        while (true) {
            trener = InputUtils.readInt();
            if (trener > 0 && trener <= trainers.size()) {
                break;
            } else {
                System.out.println("Zadaj iba existujuceho trenera:");
            }
        }

        List<Pokemon> pokemons = pokemonService.getAllPokemons();
        for (int j = 0; j < pokemons.size(); j++) {
            System.out.println((j + 1) + ". " + pokemons.get(j));
        }
        System.out.println("Zadaj pokemona ktoreho " + trainers.get(trener - 1).getName() + " chyta:");

        int pokemon;
        while (true) {
            pokemon = InputUtils.readInt();
            if (pokemon > 0 && pokemon <= pokemons.size()) {
                break;
            } else {
                System.out.println("Zadaj iba existujuceho pokemona");
            }

        }

        if (pokemonService.catchPokemon(pokemons.get(pokemon - 1).getId(), trainers.get(trener - 1).getId()) > 0) {
            System.out.println("Pokemon successfully caught");
        }


    }

    public void addPokemon() {
        System.out.println("Zadaj meno:");
        String name = InputUtils.readString();
        System.out.println("Zadaj ability:");
        String ability = InputUtils.readString();
        System.out.println("Zadaj weakness:");
        String weakness = InputUtils.readString();
        int lvl = 1;

        if (pokemonService.addPokemon(name, ability, weakness, lvl) > 0) {
            System.out.println("Pokemon successfully added");
        }
    }

    public void getAllPokemons() {
        List<Pokemon> pokemons = pokemonService.getAllPokemons();
        pokemons.forEach(System.out::println);

    }

    public void getTrainerById() {//TODO opravit, ošetriť všetky výnimky
        System.out.println("Zadaj id trenera ktore chces vypisat: ");

        int choice = InputUtils.readInt();
        Trener trener = trenerService.getById(choice);

        if (trener != null) {
            System.out.println(trener);
        } else {
            System.out.println("Trener s id " + choice + " neexistuje");
        }


    }


    public void editTrainer() {
        List<Trener> trainers = trenerService.readAllTrainers();

        System.out.println("Zadaj trenera ktoreho chces zmenit");
        for (int i = 0; i < trainers.size(); i++) {
            System.out.println((i + 1) + ". " + trainers.get(i));
        }

        int choice;
        while (true) {
            choice = InputUtils.readInt();
            if (choice < 1 || choice > trainers.size()) {
                System.out.println("Invalid choice, try again");
                continue;
            }
            break;

        }


        System.out.println("\nZadaj meno: ");
        String name = InputUtils.readString();
        System.out.println("Zadaj vek: ");
        int age = InputUtils.readInt();

        if ((trenerService.udpateTrainer(trainers.get(choice - 1).getId(), name, age) > 0)) {
            System.out.println("Trainer successfully updated");
        }


    }

    public void deleteTrainer() {
        List<Trener> trainers = trenerService.readAllTrainers();


        System.out.println("Zadaj id trenera kotreho chces vymazat:");
        int choice;
        while (true) {
            for (int i = 0; i < trainers.size(); i++) {
                System.out.println((i + 1) + ". " + trainers.get(i));
            }
            choice = InputUtils.readInt();
            if (choice < 1 || choice > trainers.size()) {
                System.out.println("Invalid choice");
                continue;
            }
            break;
        }
        if (trenerService.deleteTrainer(trainers.get(choice - 1).getId()) > 0) {
            System.out.println("Trainer deleted successfully");
        }
    }

    private void createTrainer() {
        System.out.println("Zadaj meno: ");
        String name = InputUtils.readString();
        System.out.println("Zadaj vek: ");
        int age = InputUtils.readInt();

        if (trenerService.addTrainer(name, age) > 0) {
            System.out.println("Trainer created successfully");
        } else {
            System.out.println("Could not create trainer");
        }
    }


    private void getAllTrainers() {
        List<Trener> trainers = trenerService.readAllTrainers();
        trainers.forEach(System.out::println);
    }

}
