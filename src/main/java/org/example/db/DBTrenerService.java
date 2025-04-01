package org.example.db;


import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class DBTrenerService {
    private DBPokemonServie pokemonServie;
    private static final Logger logger = getLogger(DBTrenerService.class);

    private final static String READ_ALL_TRAINERS = "SELECT * from trener";
    private final static String CREATE_TRAIER = "INSERT INTO trener (name, age) VALUES (?, ?)";
    private final static String DELETE_TRAINER = "DELETE from trener WHERE id = ?";
    private final static String DELETE_TRAINER_ID_FROM_POKEMON = "UPDATE pokemon set trener_id = null WHERE trener_id = ?";
    private final static String EDIT_TRAINER = "UPDATE trener set name = ?, age = ? WHERE id = ?";
    private final static String GET_TRAINER_BY_ID = "SELECT * FROM trener WHERE id = ?";

    public Trener getById(int id) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_TRAINER_BY_ID)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Trener(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
            } else {
                return null;
            }


        } catch (SQLException e) {
            logger.error("Error while getting trainer by id", e);
            return null;
        }
    }

    public int udpateTrainer(int id, String name, int age) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(EDIT_TRAINER)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setInt(3, id);

            return ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while editing trainer", e);
            return 0;
        }
    }


    public int deleteTrainer(int id) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_TRAINER);
             PreparedStatement ps2 = connection.prepareStatement(DELETE_TRAINER_ID_FROM_POKEMON)) {

            ps2.setInt(1, id);

            ps.setInt(1, id);
            return ps2.executeUpdate() + ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while deleting trainer", e);
            return 0;
        }

    }

    public int addTrainer(String name, int age) { // vracia number of effected rows
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_TRAIER)) {

            ps.setString(1, name);
            ps.setInt(2, age);

            return ps.executeUpdate();


        } catch (SQLException e) {
            logger.error("Error while creating new contact", e);
            return 0;
        }
    }


    public List<Trener> readAllTrainers() {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(READ_ALL_TRAINERS)) {

            ResultSet rs = ps.executeQuery();
            List<Trener> trainers = new ArrayList<>();

            while (rs.next()) {
                trainers.add(new Trener(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                ));

            }
            return trainers;
        } catch (SQLException e) {
            logger.error("Error while connecting to database");
            return null;
        }

    }

}
