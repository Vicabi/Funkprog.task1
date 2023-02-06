package Inlämningsuppgift_1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Repository {

    Properties p = new Properties();

    public Repository() {

        try {
            p.load(new FileInputStream("src/Inlämningsuppgift_1/settings.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List <Customer> getAllCustomers() {
        List <Customer> allCustomers = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from kund")){

            while (rs.next()) {
                int id = rs.getInt("id");
                String namn = rs.getString("namn");
                String lösenord = rs.getString("lösenord");
                allCustomers.add(new Customer(id, namn, lösenord));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allCustomers;
    }

    public List <Color> getAllColors() {
        List <Color> allColors = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from färg")){

            while (rs.next()) {
                int id = rs.getInt("id");
                String namn = rs.getString("färgnamn");
                allColors.add(new Color(id, namn));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allColors;
    }

    public List <Shoe> getAllShoes() {

        List <Shoe> allShoes = new ArrayList<>();
        List <Color> allColors = getAllColors();

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from sko")){

            while (rs.next()) {
                int id = rs.getInt("id");
                String märke = rs.getString("märke");
                int pris = rs.getInt("pris");
                int storlek = rs.getInt("storlek");
                int färgID = rs.getInt("färg") -1;
                int antal = rs.getInt("antal");
                allShoes.add(new Shoe(id, märke, pris, storlek, allColors.get(färgID), antal));

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allShoes;
    }


    public List <Order> getAllOrders () {
        List <Order> allOrders = new ArrayList<>();
        List <Customer> allCustomers = getAllCustomers();

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from beställning")){

            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("datum");
                int customer = rs.getInt("kund")-1;
                allOrders.add(new Order(id, date, allCustomers.get(customer)));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return allOrders;
    }

    public boolean checkLogin (List <Customer> allCustomers, String anv, String lösen) {
        return allCustomers.stream().filter(e -> e.getName().
                equals(anv)).filter(e -> e.getPassword().equals(lösen)).count() > 0;
    }

    public boolean checkChoiceOfBrand (List <Shoe> allShoes, String brand) {
        return allShoes.stream().filter(e -> e.getBrand().equals(brand)).count() > 0;
    }

    public boolean checkChoiceOfColor (List <Shoe> allShoes, String brand, String color) {
        return allShoes.stream().filter(e -> e.getBrand().equals(brand)).
                filter(e -> e.getColor().getColorName().equals(color)).count() > 0;
    }

    public boolean checkChoiceOfExactShoe (List <Shoe> allShoes, String brand, String color, int size) {
        List <Shoe> valdShoe = allShoes.stream().filter(e -> e.getBrand().equals(brand)).
                filter(e -> e.getColor().getColorName().equals(color)).
                collect(Collectors.toList());
        for (Shoe shoe : valdShoe) {
            if (shoe.getSize() == size)
                return true;
        }
        return false;
    }

    public void printAllBrands (List <Shoe> allShoes) {
        allShoes.stream().map(Shoe::getBrand).distinct().forEach(e -> System.out.print(e + " "));
    }

    public void printAllColorsOfBrand (List <Shoe> allShoes ,String brand) {
        allShoes.stream().filter(e -> e.getBrand().equals(brand)).
                map(Shoe::getColor).distinct().forEach(e -> System.out.print(e.getColorName() + " "));
    }

    public void printAllAvailableSizes (List <Shoe> allShoes ,String brand, String color) {
        List <Shoe> allaSkor = getAllShoes();
        allaSkor.stream().filter(e -> e.getBrand().equals(brand)).
                filter(e -> e.getColor().getColorName().equals(color)).map(Shoe::getSize).
                forEach(e -> System.out.print(e + " "));

    }

    public void makeAnOrder (int customerID, int orderId, int shoeId) {

        if (orderId < 0) {

            try (Connection con = DriverManager.getConnection(
                    p.getProperty("connectionString"),
                    p.getProperty("name"),
                    p.getProperty("password"));
                 CallableStatement stmt = con.prepareCall("call AddToCart(null ,?, ?)")) {

                stmt.setInt(1, customerID);
                stmt.setInt(2, shoeId);
                stmt.executeQuery();

                System.out.println("Grattis! Du har gjort en ny beställning");
                System.exit(0);

            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
        else {
            try (Connection con = DriverManager.getConnection(
                    p.getProperty("connectionString"),
                    p.getProperty("name"),
                    p.getProperty("password"));
                 CallableStatement stmt = con.prepareCall("call AddToCart(? ,?, ?)")) {

                stmt.setInt(1, orderId);
                stmt.setInt(2, customerID);
                stmt.setInt(3, shoeId);
                stmt.executeQuery();

                System.out.println("Du har lagt till en produkt i din befintliga beställning");
                System.exit(0);

            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }


    }

}
