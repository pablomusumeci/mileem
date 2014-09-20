package ar.uba.fi.proyectos2.mileem.model;

/**
 * Created by javier on 20/09/14.
 */
public class PublicationSearchRequest {

    private String neighbourhood_name;
    private String operation;
    private String property_name;
    private int min_price;
    private int max_price;
    private int min_antiquity;
    private int max_antiquity;
    private int min_surface;
    private int max_surface;
    private int min_expenses;
    private int max_expenses;
    private String currency;
    private int number_spaces;

    public String getNeighbourhood_name() {
        return neighbourhood_name;
    }

    public void setNeighbourhood_name(String neighbourhood_name) {
        this.neighbourhood_name = neighbourhood_name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getProperty_name() {
        return property_name;
    }

    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public int getMin_price() {
        return min_price;
    }

    public void setMin_price(int min_price) {
        this.min_price = min_price;
    }

    public int getMax_price() {
        return max_price;
    }

    public void setMax_price(int max_price) {
        this.max_price = max_price;
    }

    public int getMin_antiquity() {
        return min_antiquity;
    }

    public void setMin_antiquity(int min_antiquity) {
        this.min_antiquity = min_antiquity;
    }

    public int getMax_antiquity() {
        return max_antiquity;
    }

    public void setMax_antiquity(int max_antiquity) {
        this.max_antiquity = max_antiquity;
    }

    public int getMin_surface() {
        return min_surface;
    }

    public void setMin_surface(int min_surface) {
        this.min_surface = min_surface;
    }

    public int getMax_surface() {
        return max_surface;
    }

    public void setMax_surface(int max_surface) {
        this.max_surface = max_surface;
    }

    public int getMin_expenses() {
        return min_expenses;
    }

    public void setMin_expenses(int min_expenses) {
        this.min_expenses = min_expenses;
    }

    public int getMax_expenses() {
        return max_expenses;
    }

    public void setMax_expenses(int max_expenses) {
        this.max_expenses = max_expenses;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getNumber_spaces() {
        return number_spaces;
    }

    public void setNumber_spaces(int number_spaces) {
        this.number_spaces = number_spaces;
    }
}
