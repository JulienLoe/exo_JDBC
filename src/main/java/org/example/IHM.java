package org.example;

import org.example.utils.DatabaseManager;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class IHM {

    public IHM() throws SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DatabaseManager.getPostgreSQLConnection();
        int choix = 0;

        do {
            System.out.println("1. Ajouter un étudiant");
            System.out.println("2. Afficher la totalité des étudiants");
            System.out.println("3. Afficher les étudiants d'une classe");
            System.out.println("4. Supprimer un étudiant");
            choix = scanner.nextInt();

            switch (choix) {
                case 1 :
                try {

                    scanner.nextLine();
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
                    String request = "INSERT INTO student (first_name, last_name, nb_class, date_of_birth) VALUES (?, ?, ?, ?)";


                    PreparedStatement preparedStatement = connection.prepareStatement(request);
                    preparedStatement.setString(1, firstname);
                    preparedStatement.setString(2, lastname);
                    preparedStatement.setInt(3, nbclass);
                    preparedStatement.setDate(4, new java.sql.Date(date.getTime()));
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

                case 2 :
                    String request = "SELECT * FROM student";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(request);
                    while(resultSet.next()){
                        System.out.println(resultSet.getInt("id")+ " , "+ resultSet.getString("first_name")+
                                " , "+ resultSet.getString("last_name"));
                    }
                    System.out.println();

                case 3 :
                    System.out.println("Entrez l'identifiant :");
                    int id = scanner.nextInt();
                    System.out.println();
                    String requestStudent = "SELECT * FROM student WHERE student.id = '"+id+"'";
                    Statement statementStudent = connection.createStatement();
                    ResultSet resultSetStudent = statementStudent.executeQuery(requestStudent);
                    while(resultSetStudent.next()){
                        System.out.println(resultSetStudent.getInt("id")+ " , "+ resultSetStudent.getString("first_name")+
                                " , "+ resultSetStudent.getString("last_name"));
                    }
                    System.out.println();

                case 4 :
                    try {


                        System.out.println("Entrez l'identifiant :");
                        int idDelete = scanner.nextInt();
                        System.out.println();
                        String requestUpdate = "SELECT * FROM student WHERE student.id = '" + idDelete + "'";
                        Statement statementStudentUpdate = connection.createStatement();
                        int resultSetUpdate = statementStudentUpdate.executeUpdate(requestUpdate);

                        if (resultSetUpdate > 0) {
                            System.out.println("Des données renvoyées par la requête");
                        } else {
                            System.out.println("Aucune données renvoyées par la requête");
                        }
                    }catch (SQLException e) {
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
        }while(choix != 0);
    }
    }
