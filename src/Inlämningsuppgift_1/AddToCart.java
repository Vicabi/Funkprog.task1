package Inlämningsuppgift_1;

import java.util.List;
import java.util.Scanner;

public class AddToCart {

    public AddToCart () {



        //Skapar upp alla nödvändiga objekt
        Scanner scan = new Scanner(System.in);
        Repository r = new Repository();
        Shoe s = new Shoe();
        Customer c = new Customer();
        Order o = new Order();

        // Hämtar alla nödvändiga listor
        List <Shoe> allShoes = r.getAllShoes();
        List <Customer> allCustomers = r.getAllCustomers();
        List<Order> allOrders = r.getAllOrders();


        while (true) {

            System.out.println("Hej! Börja med att skriva in ditt användarnamn");
            String userName = scan.nextLine();
            System.out.println("Vänligen skriv in ditt lösenord");
            String password = scan.nextLine();

            if (r.checkLogin(allCustomers, userName, password)) {
                System.out.println("Välkommen " + userName + "! Här kommer en lista på våra tillgängliga skor utifrån skomärken");
                r.printAllBrands(allShoes);
                System.out.println("\n");
                System.out.println("Skriv in vilket märke du önskar köpa");
                String brand = scan.nextLine().trim();
                if (!r.checkChoiceOfBrand(allShoes, brand)) {
                    System.out.println("Vi kunde inte hitta ett märke med det namnet");
                    System.exit(0);
                }
                System.out.println("Tillgängliga färger för skon");
                r.printAllColorsOfBrand(allShoes, brand);
                System.out.println();
                System.out.println("Skriv in vilken färg du önskar köpa");
                String färg = scan.nextLine().trim();
                if (!r.checkChoiceOfColor(allShoes, brand, färg)) {
                    System.out.println("Vi kunde inte hitta ett märke med det namnet");
                    System.exit(0);
                }
                System.out.println("Tillgängliga storlekar för skon med färgen " + färg + ":");
                r.printAllAvailableSizes(allShoes, brand, färg);
                System.out.println();
                System.out.println("Skriv in vilken storlek du önskar köpa");
                int storlek = Integer.parseInt(scan.nextLine());
                if (!r.checkChoiceOfExactShoe(allShoes, brand, färg, storlek)) {
                    System.out.println("Vi kunde inte hitta storleken du angav");
                    System.exit(0);
                }

                // Hämtar alla nödvändiga parametrar till min Stored Procedure
                int customerID = c.extractCustomerId(allCustomers, userName, password);
                int shoeID = s.extractShoeId(allShoes, brand, färg, storlek);
                int orderID = o.extractOrderId(allOrders, customerID);

                // Lägger en beställning och kund får återkoppling
                r.makeAnOrder(customerID,orderID, shoeID);


            }
            else {
                System.out.println("Fel användarnamn eller lösenord");
                System.out.println("Vi gör ett nytt försök");
                System.out.println();
            }


        }






    }

    public static void main (String args []) {

        AddToCart a = new AddToCart();

    }
}
