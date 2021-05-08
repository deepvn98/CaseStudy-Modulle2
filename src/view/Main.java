package view;

import controller.TownManager;
import model.House;
import model.Person;
import storage.FileHouse;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileHouse fileHouse = FileHouse.getInstance();
        List<House> houses = new ArrayList<>();
        try {
            houses = fileHouse.readFile("house.dat");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TownManager townManager = TownManager.getInstance("Sang", houses);
        menuAll(townManager);

    }

    //    Menu Quản lý chính
    public static void menuAll(TownManager manager) {
        int choice = 0;
        while (choice != 3) {
            System.err.println("---------Danh sách lựa chọn________");
            System.out.println("Enter 1: Quản lý nhà ở ");
            System.out.println("Enter 2: Quản lý Hộ Dân trong Khu phố ");
            System.out.println("Enter 3: Thoát  ");
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Nhập vào lựa chọn của bạn: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Nhập vào một trong những lựa chọn phía trên,Mời bạn nhập lại!");
                choice = 0;
            }

            switch (choice) {
                case 1: {
                    menuManagerHouse(manager);
                    break;
                }
                case 2: {
                    menuManagerHouseHoldr(manager);
                    break;
                }
                case 3: {
                    break;
                }
            }
        }
    }

    //    Quản lý nhà ở
    public static void menuManagerHouse(TownManager manager) {
        int number = 0;
        int choice = 0;
        do {
            System.err.println("---------Danh sách lựa chọn________");
            System.out.println("Enter 1: Thêm mới nhà: ");
            System.out.println("Enter 2: Xoá nhà: ");
            System.out.println("Enter 3: Sửa địa chỉ nhà: ");
            System.out.println("Enter 4: Danh sách căn nhà chưa có người ở: ");
            System.out.println("Enter 5: Trở về Menu chính ");
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Nhập vào lựa chọn của bạn: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("ngoại lệ, Mời nhập lại!");
                choice = 0;
            }
            switch (choice) {
                case 1: {
                    while (true) {
                        Scanner scanner1 = new Scanner(System.in);
                        System.out.println("Nhập số lượng ngôi nhà bạn muốn thêm mới: ");
                        try {
                            number = scanner1.nextInt();
                            if (number < 0) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.err.println("Nhập vào một trong những lựa chọn phía trên,Mời bạn nhập lại!");
                        }
                    }
                    int[] arr = new int[number];
                    for (int i = 0; i < arr.length; i++) {
                        System.out.println("Nhập địa chỉ nhà thứ " + (i + 1));
                        House house = createNewHouse();
                        try {
                            manager.addHouse(house);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 2: {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Nhập địa chỉ nhà muốn xoá:");
                    String address = scanner1.nextLine();
                    House house = manager.findHouse(address);
                    if (house!=null){
                        if (manager.checkHouse(house)){
                            System.err.println("Nhà có Hộ gia đình đang sinh sống!");
                             int choice1;
                                while (true){
                                    System.out.println("Nhấn 1: Nếu bạn vẫn muốn xoá: ");
                                    System.out.println("Nhấn 2: Nếu bạn không muốn xoá: ");
                                    try {
                                        Scanner scanner = new Scanner(System.in);
                                        choice1 = scanner.nextInt();
                                        break;

                                    }catch (Exception e){
                                        System.err.println("Nhập vào một trong những lựa chọn phía trên,Mời bạn nhập lại!");
                                    }
                                }
                                switch (choice1){
                                    case 1:
                                    {
                                        try {
                                            manager.deleteHouse(house);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                    case 2:
                                    {
                                        break;
                                    }
                                }
                        }else {
                            try {
                                manager.deleteHouse(house);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    break;
                }
                case 3: {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.print("Nhập địa chỉ nhà cũ :");
                    String address = scanner1.nextLine();
                    System.out.print("Nhập địa chỉ mới: ");
                    Scanner scanner2 = new Scanner(System.in);
                    String newAddress = scanner2.nextLine();
                    House house = manager.findHouse(address);
                    try {
                        manager.editHouseAddress(house,newAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.err.print("Địa chỉ nhà đã được thay đổi! ");
                    System.out.println();
                    break;
                }
                case 4: {
                    List<String> house = manager.listHouseNoPeople();
                    manager.showHouse(house);
                    break;
                }
                case 5: {
                    break;
                }
            }
        }
        while (choice != 5);
    }

    //    Quản lý hộ dân
    public static void menuManagerHouseHoldr(TownManager manager) {

        int choice;
        boolean check = true;
        do {
            System.out.println();
            System.err.println("---------Danh sách lựa chọn________");
            System.out.println("Enter 1: Nhập thông tin của Hộ dân mới chuyển đến: ");
            System.out.println("Enter 2: Hiển thị danh sách hộ dân đang sinh sống: ");
            System.out.println("Enter 3: Xoá Hộ gia đình không Còn thuộc diện quản lý");
            System.out.println("Enter 4: Thêm thành viên vào hộ gia đình:");
            System.out.println("Enter 5: Xoá Thành viên Trong hộ gia đình: ");
            System.out.println("Enter 6: Trở về menu chính ");
            System.out.println("Nhập vào lựa chọn của bạn: ");
            try {
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextInt();
            }catch (Exception e){
                System.err.println("Nhập vào một trong những lựa chọn phía trên,Mời bạn nhập lại!");
                choice=0;
            }
            switch (choice) {
                case 1: {
                    System.out.print("Nhập Địa chỉ nhà có Hộ mới chuyển đến: ");
                    Scanner scanner1 = new Scanner(System.in);
                    String id = scanner1.nextLine();
                    House house = manager.findHouse(id);
                    if (house != null) {
                        if (house.getPersonList() == null) {
                            Scanner scanner2 = new Scanner(System.in);
                            System.out.print("Nhập Tên chủ hộ: ");
                            String name1 = scanner2.nextLine();
                            Scanner scanner3 = new Scanner(System.in);
                            System.out.print("Số thành viên của hộ là: ");
                            int number1 = scanner3.nextInt();
                            List<Person> personList = new ArrayList<>();
                            for (int i = 0; i < number1; i++) {
                                int serial = i + 1;
                                System.out.print("người thứ " + serial);
                                System.out.println();
                                Person person = createNewPerson();
                                try {
                                    manager.addPersonAtHome(name1, house, number1, person, personList);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            System.out.println("Nhà đã có người ở!");
                        }
                    } else {
                        System.err.println("không có căn hộ trên!");
                    }
                    break;
                }
                case 2: {
                    List<House> houses = manager.listHousHavePeople();
                    if (houses.size()>0){
                        manager.showHouseHoollds(houses);
                    }
                    else {
                        System.err.println("\t\t\tHiện tại không có hộ nào quản lý trong khu phố");
                        System.out.println();
                    }
                    break;
                }
                case 3:
                {
                    System.out.print("Nhập địa chỉ nhà có hộ gia đình chuyển đi: ");
                    Scanner scanner1 = new Scanner(System.in);
                    String address = scanner1.nextLine();
                    House house = manager.findHouse(address);
                    if (manager.checkHouse(house)){
                        manager.getHouses().remove(house);
                    }else {
                        System.err.println("Nhà Đang trống không có người sử dụng, Kiểm tra lại");
                    }
                    break;
                }
                case 4: {
                    System.out.println("Nhập vào địa chỉ nhà muốn thêm thành viên:");
                    Scanner scanner1 = new Scanner(System.in);
                    String address = scanner1.nextLine();
                    House house = manager.findHouse(address);
                    if (manager.checkHouse(house)) {
                        System.out.println("Nhập số lượng người muốn thêm!");
                        Scanner scanner3 = new Scanner(System.in);
                        int putNumber = scanner3.nextInt();
                        for (int i = 0; i < putNumber; i++) {
                            int serial = 1 + i;
                            System.out.println("Thông tin người thứ " + serial);
                            System.out.println("Nhập tên người thêm vào hộ: ");
                            Scanner scanner2 = new Scanner(System.in);
                            String newName = scanner2.nextLine();
                            Scanner scanner5 = new Scanner(System.in);
                            System.out.println("Nhập tuổi: ");
                            int age = scanner5.nextInt();
                            System.out.println("Giới tính: ");
                            Scanner scanner4 = new Scanner(System.in);
                            String gender = scanner4.nextLine();
                            Person person = new Person(newName, age, gender);
                            try {
                                manager.addPersonInHouseHavePerson(person, house);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        System.err.println("Hiện chưa có hộ dân nào sống trong căn nhà này!");
                    }
                    break;
                }
                case 5: {
                    System.out.println("Nhập vào địa chỉ nhà muốn xoá thành viên:");
                    Scanner scanner1 = new Scanner(System.in);
                    String address = scanner1.nextLine();
                    House house = manager.findHouse(address);
                    if (manager.checkHouse(house)) {
                        System.out.println("Nhập số lượng người muốn xoá!");
                        Scanner scanner3 = new Scanner(System.in);
                        int putNumber = scanner3.nextInt();
                        for (int i = 0; i < putNumber; i++) {
                            int serial = 1 + i;
                            System.out.println("Người thứ " + serial);
                            System.out.println("Nhập tên người muốn xoá khổi hộ: ");
                            Scanner scanner2 = new Scanner(System.in);
                            String newName = scanner2.nextLine();
                            Person person = manager.getPersonByName(house, newName);
                            if (person != null) {
                                try {
                                    manager.deletePersonInHouse(house, person);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.err.println("Người bạn vừa nhập vào không có khẩu trong hộ vừa trên!");
                            }

                        }
                    }
                    break;
                } case 6:
                    {
                        check = false;
                    break;
                    }
            }
        } while (check);

    }

    //    Tạo mới một nhà trống không người
    public static House createNewHouse() {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Nhập địa chỉ nhà: ");
        String address = scanner.nextLine();
        House house = new House(address);
        return house;
    }

    //    Tạo mới một người
    public static Person createNewPerson() {
        System.out.print("Nhập FullName:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.print("Nhập Age:");
        Scanner scanner1 = new Scanner(System.in);
        int age = scanner1.nextInt();
        System.out.print("Nhập Gender:");
        Scanner scanner2 = new Scanner(System.in);
        String gender = scanner2.nextLine();
        Person person = new Person(name, age, gender);
        return person;
    }
}
