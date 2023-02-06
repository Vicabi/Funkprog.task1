package Inlämningsuppgift_1;

import java.util.List;

public class Customer {

    private int id;
    private String name;
    private String password;

    public Customer() {}

    public Customer(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int extractCustomerId (List<Customer> allCustomers, String name, String password) {

        List <Customer> filteredList = allCustomers.stream().
                filter(e -> e.getName().equals(name)).toList();
        for (Customer c : filteredList) {
            if (c.getPassword().equals(password))
                return c.getId();
        }

        return -1;
    }
}
