package Inlämningsuppgift_1;

import java.util.List;

public class Shoe {

    private int id;
    private String brand;
    private int price;
    private int size;
    private Color color;
    private int quantity;

    public Shoe() {}

    public Shoe(int id, String brand, int price, int size, Color color, int antal) {
        this.id = id;
        this.brand = brand;
        this.price = price;
        this.size = size;
        this.color = color;
        this.quantity = antal;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setQuantity (int antal) {
        this.quantity = antal;
    }

    public int extractShoeId (List<Shoe> allShoes,String brand, String colorName, int size) {
        List <Shoe> filteredList = allShoes.stream().filter(e -> e.getBrand().equals(brand)).
                filter(e -> e.getColor().getColorName().equals(colorName)).toList();

        for (Shoe s : filteredList) {
            if (s.getSize() == size)
                return s.getId();
        }

        return -1;
    }

}
