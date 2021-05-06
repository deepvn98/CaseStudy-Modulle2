package model;

import java.io.Serializable;
import java.util.List;

public class House implements Serializable {
    private String householder;
    private String address;
    private int numberOfPeople;
    private List<Person> personList;

    public House() {
    }

    public House(String address) {
        this.address = address;
    }

    public House(String householder, String address, int numberOfPeople, List<Person> personList) {
        this.householder = householder;
        this.address = address;
        this.numberOfPeople = numberOfPeople;
        this.personList = personList;
    }

    public String getHouseholder() {
        return householder;
    }

    public void setHouseholder(String householder) {
        this.householder = householder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public String toString() {
        return "House{" +
                "householder='" + householder +
                ", address='" + address +
                ", numberOfPeople=" + numberOfPeople +"\n"+
                "personList=" + personList +
                '}';
    }
}
