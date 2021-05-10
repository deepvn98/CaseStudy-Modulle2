package view;

import controller.LoginManager;
import controller.TownManager;
import model.House;
import model.Login;
import model.Person;
import storage.ComparatorWithAddress;
import storage.ComparatorWithPerson;
import storage.FileHouse;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        FileHouse fileHouse = FileHouse.getInstance();
        List<House> houses = new ArrayList<>();
        List<Login>list = new ArrayList<>();
        try {houses = fileHouse.readFile("house.dat");
            list= fileHouse.readFileLogin("login.dat");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        LoginManager loginManager = LoginManager.getInstance("Sang",list);
        TownManager townManager = TownManager.getInstance("Sang", houses);
        login(loginManager);
        menuAll(townManager);
    }

    //    Menu Quản lý chính
    public static void menuAll(TownManager manager) {
        int choice = 0;
        while (choice != 4) {
            System.out.println("---------Danh sách lựa chọn________");
            System.out.println("Enter 1: Quản lý nhà ở: ");
            System.out.println("Enter 2: Quản lý Hộ Dân trong Khu phố: ");
            System.out.println("Enter 3: Quản lý Thông tin người dân : ");
            System.out.println("Enter 4: Thoát:  ");
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
                    menuManagerPeople(manager);
                    break;
                }
                case 4: {
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
            System.out.println("---------Danh sách lựa chọn________");
            System.out.println("Enter 1: Thêm mới nhà: ");
            System.out.println("Enter 2: Xoá nhà: ");
            System.out.println("Enter 3: Sửa địa chỉ nhà: ");
            System.out.println("Enter 4: Danh sách căn nhà chưa có người ở: ");
            System.out.println("Enter 5: Danh sách Nhà thuộc diện quản lý: ");
            System.out.println("Enter 6: Trở về Menu chính ");
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
                    if (house != null) {
                        if (manager.checkHouse(house)) {
                            System.err.println("Nhà có Hộ gia đình đang sinh sống!");
                            int choice1;
                            while (true) {
                                System.out.println("Nhấn 1: Nếu bạn vẫn muốn xoá: ");
                                System.out.println("Nhấn 2: Nếu bạn không muốn xoá: ");
                                try {
                                    Scanner scanner = new Scanner(System.in);
                                    choice1 = scanner.nextInt();
                                    break;

                                } catch (Exception e) {
                                    System.err.println("Nhập vào một trong những lựa chọn phía trên,Mời bạn nhập lại!");
                                }
                            }
                            switch (choice1) {
                                    case 1:
                                    {
                                        try {
                                            manager.deleteHouse(house);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                case 2: {
                                    break;
                                }
                            }
                        } else {
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
                    List<House> houses1 = manager.listHouseNoPeople();
                    Collections.sort(houses1, new ComparatorWithAddress());
                    manager.showHouse(houses1);
                    break;
                }
                case 5: {
                    System.out.println("\t\t\t_______Danh sách Địa chỉ toàn bộ ngôi nhà thuộc diện quản lý_______");
                    Collections.sort(manager.getHouses(), new ComparatorWithAddress());
                    manager.showAllHouse();
                    break;
                }
                case 6: {
                    break;
                }
            }
        }
        while (choice != 6);
    }

    //    Quản lý hộ dân
    public static void menuManagerHouseHoldr(TownManager manager) {

        int choice;
        boolean check = true;
        do {
            System.out.println("---------Danh sách lựa chọn________");
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
            } catch (Exception e) {
                System.err.println("Nhập vào một trong những lựa chọn phía trên,Mời bạn nhập lại!");
                choice = 0;
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
                    if (houses.size() > 0) {
                        manager.showHouseHoollds(houses);
                    } else {
                        System.err.println("\t\t\tHiện tại không có hộ nào quản lý trong khu phố");
                        System.out.println();
                    }
                    break;
                }
                case 3: {
                    System.out.print("Nhập địa chỉ nhà có hộ gia đình chuyển đi: ");
                    Scanner scanner1 = new Scanner(System.in);
                    String address = scanner1.nextLine();
                    House house = manager.findHouse(address);
                    if (manager.checkHouse(house)) {
                        try {
                            manager.deleteHouseHolds(house);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
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
                        int putNumber;
                        while (true){
                            try {
                                Scanner scanner3 = new Scanner(System.in);
                                putNumber = scanner3.nextInt();
                                if (putNumber<=0){
                                    throw new Exception();
                                }
                                break;
                            }catch (Exception e){
                                System.out.println("số lượng nhập vào không được âm, và không được nhập chuỗi");
                            }
                        }
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
                            Person person = manager.checkPersonByName(house, newName);
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
                    } else {
                        System.out.println("Hiện tại nhà chưa có hộ dân nào sinh sống! Kiểm tra lại!");
                    }
                    break;
                }
                case 6: {
                    check = false;
                    break;
                }
            }
        } while (check);

    }

    //    Menu Quản lý Con người
    public static void menuManagerPeople(TownManager manager) {
        int choice = 0;
        while (choice != 4) {
            System.out.println("__________Danh sách lựa chọn________");
            System.out.println("Enter 1: Danh sách người Dân Trong khu dân cư: ");
            System.out.println("Enter 2: Tìm thông tin người theo tên: ");
//            System.out.println("Enter 3: Lấy danh sách người có cùng độ tuổi nhập vào: ");
            System.out.println("Enter 3: Trở về Menu chính: ");
            System.out.println("Nhập vào lựa chọn của bạn: ");
            try {
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.err.println("Nhập vào một trong những lựa chọn phía trên,Mời bạn nhập lại!");
                choice = 0;
            }
            switch (choice) {
                case 1: {
                    List<Person> personList = manager.getPersonInHouse();
                    Collections.sort(personList, new ComparatorWithPerson());
                    manager.showPerson(personList);
                    break;
                }
                case 2: {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Nhập tên người cần lấy thông tìm: ");
                    String name = scanner.nextLine();
                    List<House> houses1 = manager.listHousHavePeople();
                    if (houses1.size() > 0) {
                        List<Person> personList = manager.getPersonInHouse();
                        if (personList.size() > 0) {
                            List<Person> personList1 = manager.getInforPersonByName(personList, name);
                            if (personList1.size() > 0) {

                                Collections.sort(personList1, new ComparatorWithPerson());
                                System.out.printf("\n\t\t\t\t_________%-60s", "Thông tin người cần tìm________");
                                System.out.println();
                                System.out.printf("\n\t\t\t\t%-30s %-40s", "Địa chỉ", "Thông tin");
                                for (int i = 0; i < houses1.size(); i++) {
                                    for (int j = 0; j < personList1.size(); j++) {
                                        List<Person> personList2 = houses1.get(i).getPersonList();
                                        if (personList2.contains(personList.get(j))) {
                                            String address = houses1.get(i).getAddress();
                                            Person person = personList.get(j);
                                            System.out.printf("\n\t\t\t\t%-20s %-50s", address, person.toString());

                                        }
                                    }
                                }
                                System.out.println();
                                System.out.println("\t\t\t\t\t______________________________");
                                System.out.println();
                            } else {
                                System.out.println("Người cần tìm không thuộc diện quản lý!");
                                System.out.println();
                            }
                        } else {
                            System.out.println("Người cần tìm không thuộc diện quản lý!");
                            System.out.println();
                        }
                    } else {
                        System.out.println("Người cần tìm không thuộc diện quản lý!");
                        System.out.println();
                    }
                    break;
                }
//                case 3:
//                {
//                    System.out.println("Nhập vào độ tuổi bạn muốn lấy danh sách: ");
//                    Scanner scanner = new Scanner(System.in);
//                    int age = scanner.nextInt();
//                    List<Person> personList =manager.getPersonInHouse();
//                    if (personList.size()>0){
//                        List<Person>personList1 = manager.getInforPersonByAge(personList,age);
//                        if (personList1.size()>0){
//
//                            Collections.sort(personList1,new ComparatorWithPerson());
//                            System.out.println("\t\t\t\t\t_____Danh Sách người có cùng độ tuổi cần tìm_____");
//                            System.out.println();
//                            System.out.printf("\n\t\t\t\t%-20s %-50s","STT","Thông tin:");
//                            for (int i = 0 ; i< personList1.size();i++){
//                                Person person = personList1.get(i);
//                                System.out.printf("\n\t\t\t\t%-20s %-50s",(i+1),person.toString());
//                            }
//                        }else {
//                            System.out.println("Không có người có độ tuổi trên trong khu phố!");
//                        }
//                    }else {
//                        System.out.println("Không có người có độ tuổi trên trong khu phố!");
//                    }
//                    System.out.println();
//                    break;
//                }
                case 3: {
                    break;
                }
            }
        }
    }


    //    Tạo mới một nhà trống không người
    public static House createNewHouse() {
        Scanner scanner = new Scanner(System.in);
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

    //
    public static void login(LoginManager loginManager) {
        int choice;
        do {
            System.out.println("_______Danh sách lựa chọn______");
            System.out.println("Nhập vào sự lựa chọn cửa bạn:");
            System.out.println("Nhấn 1: Đăng ký tài khoản: ");
            System.out.println("Nhấn 2: Đăng nhập: ");
            System.out.println("Nhấn 3: Thoát: ");

            while (true){
                try {Scanner scanner = new Scanner(System.in);
                    choice = scanner.nextInt();
                    break;
                }catch (Exception e){
                    System.out.println("Vui lòng nhập vào những lựa chọn phía trên!");
                    choice = 0;
                }
            }


            switch (choice) {
                case 1: {
                    Login login = createNewAccount();
                    try {
                        loginManager.addAccount(login);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2: {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Enter Account: ");
                    String account = scanner1.nextLine();
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("Enter password: ");
                    String pass = scanner2.nextLine();
                    Login login = new Login(account,pass);
                    boolean check = loginManager.checkAccount(login);
                    if (check){
                        System.out.println("Đăng nhập thành công!");
                        choice = 3;
                    }else {
                        System.out.println("Tài khoản hoặc mật khẩu của bạn sai, VUi lòng nhập lại!");
                    }
                    break;
                }
                case 3: {
                    System.exit(0);
                    break;
                }
            }
        } while (choice != 3);
    }

    //    Tạo mới Một Account
    public static Login createNewAccount() {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Nhập Tài khoản: ");
        String account = scanner.nextLine();
        System.out.println("Nhập Mật khẩu: ");
        String pass = scanner1.nextLine();
        Login login = new Login(account, pass);
        return login;

    }
}
