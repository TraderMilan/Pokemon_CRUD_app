package org.example.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DBPokemonServie {
    private static final Logger logger = getLogger(DBPokemonServie.class);

    private static final String GET_ALL_POKEMONS = "SELECT * from pokemon";
    private final static String ADD_POKEMON = "INSERT INTO pokemon (name, ability, weakness, lvl) VALUES (?, ?, ?, ?)";
    private final static String CATCH_POKEMON = "UPDATE pokemon set trener_id = ? WHERE id = ?";
    private final static String DELETE_POKEMON = "DELETE from pokemon WHERE id = ?";
    private final static String EVOLVE_POKEMON = "UPDATE pokemon set name = ?, lvl = ? WHERE id = ?";




    public int evolvePokemon(String name, int lvl, int pokemonId) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(EVOLVE_POKEMON)) {

            ps.setString(1, name);
            ps.setInt(2, lvl);
            ps.setInt(3, pokemonId);

            return ps.executeUpdate();


        } catch (SQLException e) {
            logger.error("Error while evolving pokemon", e);
            return 0;
        }
    }


    public int deletePokemon(int pokemonId) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_POKEMON)) {

            ps.setInt(1, pokemonId);

            return ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while deleting pokemon", e);
            return 0;
        }

    }


    public int catchPokemon(int pokemonId, int trenerID) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(CATCH_POKEMON)) {

            ps.setInt(1, trenerID);
            ps.setInt(2, pokemonId);

            return ps.executeUpdate();


        } catch (SQLException e) {
            logger.error("Error while catching pokemon", e);
            return 0;
        }
    }


    public int addPokemon(String name, String ability, String weakness, int lvl) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(ADD_POKEMON)) {

            ps.setString(1, name);
            ps.setString(2, ability);
            ps.setString(3, weakness);
            ps.setInt(4, lvl);

            return ps.executeUpdate();


        } catch (SQLException e) {
            logger.error("Error while adding new pokemon", e);
            return 0;
        }

    }

    public List<Pokemon> getAllPokemons() {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_ALL_POKEMONS)) {

            ResultSet rs = ps.executeQuery();
            List<Pokemon> pokemons = new ArrayList<>();

            while (rs.next()) {
                pokemons.add(new Pokemon(
                        rs.getInt("id"),
                        rs.getInt("trener_id"),
                        rs.getString("name"),
                        rs.getString("ability"),
                        rs.getString("weakness"),
                        rs.getInt("lvl")
                ));
            }

            return pokemons;


        } catch (SQLException e) {
            logger.error("Error while getting all pokemons", e);
            return null;
        }
    }
}
