package ar.uba.fi.proyectos2.mileem.model;

/**
 * Created by javier on 07/09/14.
 */
public class Publication {
    private String name;
    private String street;
    private int price;

    public Publication(String name, String street, int price) {
        this.name = name;
        this.street = street;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
