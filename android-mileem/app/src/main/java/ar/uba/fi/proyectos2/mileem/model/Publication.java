package ar.uba.fi.proyectos2.mileem.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by javier on 07/09/14.
 */
public class Publication implements Parcelable {

    public static final String KEY = "ar.uba.fi.proyectos2.mileem.model.publication";

    private int id;
    private String effective_date;
    private String operation;
    private String address;
    private int floor;
    private String apartment;
    private int number_spaces;
    private int surface;
    private int price;
    private int expenses;
    private int antiquity;
    private String description;
    private String additional_info;
    private String currency_name;
    private String property_name;
    private String neighbourhood_name;
    private String currency_symbol;
    private String user_phone_number;
    private String user_email;
    private String normalized_currency;

    public int getNormalized_price() {
        return normalized_price;
    }

    public void setNormalized_price(int normalized_price) {
        this.normalized_price = normalized_price;
    }

    public String getNormalized_currency() {
        return normalized_currency;
    }

    public void setNormalized_currency(String normalized_currency) {
        this.normalized_currency = normalized_currency;
    }

    private int normalized_price;

    public Publication() {

    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getProperty_name() {
        return property_name;
    }

    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public String getNeighbourhood_name() {
        return neighbourhood_name;
    }

    public void setNeighbourhood_name(String neighbourhood_name) {
        this.neighbourhood_name = neighbourhood_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(String effective_date) {
        this.effective_date = effective_date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public int getNumber_spaces() {
        return number_spaces;
    }

    public void setNumber_spaces(int number_spaces) {
        this.number_spaces = number_spaces;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public int getAntiquity() {
        return antiquity;
    }

    public void setAntiquity(int antiquity) {
        this.antiquity = antiquity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserPhoneNumber() {
        return user_phone_number;
    }

    public void setUserPhoneNumber(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public String getUserEmail() {
        return user_email;
    }

    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(effective_date);
        parcel.writeString(operation);
        parcel.writeString(address);
        parcel.writeInt(floor);
        parcel.writeString(apartment);
        parcel.writeInt(number_spaces);
        parcel.writeInt(surface);
        parcel.writeInt(price);
        parcel.writeInt(expenses);
        parcel.writeInt(antiquity);
        parcel.writeString(description);
        parcel.writeString(additional_info);
        parcel.writeString(currency_name);
        parcel.writeString(property_name);
        parcel.writeString(neighbourhood_name);
        parcel.writeString(currency_symbol);
        parcel.writeString(user_phone_number);
        parcel.writeString(user_email);
        parcel.writeString(normalized_currency);
        parcel.writeInt(normalized_price);
    }

    public static final Parcelable.Creator<Publication> CREATOR = new Creator<Publication>() {
        public Publication createFromParcel(Parcel source) {
            Publication p = new Publication();
            p.id = source.readInt();
            p.effective_date = source.readString();
            p.operation = source.readString();
            p.address = source.readString();
            p.floor = source.readInt();
            p.apartment = source.readString();
            p.number_spaces = source.readInt();
            p.surface = source.readInt();
            p.price = source.readInt();
            p.expenses = source.readInt();
            p.antiquity = source.readInt();
            p.description = source.readString();
            p.additional_info = source.readString();
            p.currency_name = source.readString();
            p.property_name = source.readString();
            p.neighbourhood_name = source.readString();
            p.currency_symbol = source.readString();
            p.user_phone_number = source.readString();
            p.user_email = source.readString();
            p.normalized_currency = source.readString();
            p.normalized_price = source.readInt();
            return p;
        }
        public Publication[] newArray(int size) {
            return new Publication[size];
        }
    };
}
