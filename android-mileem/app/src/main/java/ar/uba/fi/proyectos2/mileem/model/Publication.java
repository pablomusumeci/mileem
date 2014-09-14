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
    private String url;

    public Publication() {
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

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        parcel.writeString(url);
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
            p.url = source.readString();
            return p;
        }
        public Publication[] newArray(int size) {
            return new Publication[size];
        }
    };
}
