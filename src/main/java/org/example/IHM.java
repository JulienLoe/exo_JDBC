package org.example;

import org.example.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class IHM {

    public IHM() throws SQLException, ParseException {

        Connection connection = null;
        try {
            connection = null;
            connection = DatabaseManager.getPostgreSQLConnection();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Merci de saisir le nom :");
            String lastname = scanner.nextLine();
            System.out.println("Merci de saisir le prénom :");
            String firstname = scanner.nextLine();
            System.out.println("Merci de saisir votre numéro de class :");
            int nbclass = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Merci de saisir votre date de diplôme :");
            String str = scanner.nextLine();

            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
            String request = "INSERT INTO person (first_name, last_name) VALUES (?, ?, ?, ?)";


            PreparedStatement preparedStatement = connection.prepareStatement(request);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setInt(3, nbclass);
            preparedStatement.setDate(2, (java.sql.Date) date);
            int nbRows = preparedStatement.executeUpdate();

            if (nbRows > 0) {
                System.out.println("Des données renvoyées par la requête");
            } else {
                System.out.println("Aucune données renvoyées par la requête");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    }
