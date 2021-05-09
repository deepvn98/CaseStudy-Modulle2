package storage;

import model.House;

import java.util.Comparator;

public class ComparatorWithAddress implements Comparator<House> {

    @Override
    public int compare(House o1, House o2) {
        return o1.getAddress().compareTo(o2.getAddress());
    }
}
