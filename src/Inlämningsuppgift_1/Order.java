package Inlämningsuppgift_1;

import java.sql.*;
import java.util.List;


public class Order {


    private int id;
    private Date date;
    private Customer customer;

    public Order() {}

    public Order(int id, Date date, Customer customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int extractOrderId (List <Order> allOrders, int customerID) {
        for (Order o : allOrders) {
            if (o.getCustomer().getId() == customerID)
                    return o.getId();
        }
        return -1;
    }
}
