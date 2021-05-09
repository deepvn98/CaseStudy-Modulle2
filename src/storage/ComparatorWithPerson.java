package storage;

import model.Person;

import java.util.Comparator;

public class ComparatorWithPerson implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        if (o1.getName().compareTo(o2.getName()) > 0){
            return 1;
        }else
            if (o1.getName().compareTo(o2.getName())<0){
            return -1 ;
        }else {
                return o1.getAge()-o2.getAge();
            }
    }
}
