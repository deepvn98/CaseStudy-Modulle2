package controller;

import model.Login;
import storage.FileManager;

import java.io.IOException;
import java.util.List;

public class LoginManager {
    private String name;
    private List<Login> list;
    private static LoginManager INSTANCE;

    private LoginManager(String name, List<Login> list) {
        this.name = name;
        this.list = list;
    }

    private LoginManager() {
    }

    public static LoginManager getInstance(String name, List<Login> list) {
        if (INSTANCE == null) {
            INSTANCE = new LoginManager(name, list);
        }
        return INSTANCE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Login> getList() {
        return list;
    }

    public void setList(List<Login> list) {
        this.list = list;
    }

    public static void setINSTANCE(LoginManager INSTANCE) {
        LoginManager.INSTANCE = INSTANCE;
    }

    public void addAccount(Login login) throws IOException {
        list.add(login);
        FileManager fileManager = FileManager.getInstance();
        fileManager.writeFileLogin("login.dat",list);
        System.out.println("Đăng ký thành công");
    }

    public boolean checkAccount(Login login) {
        boolean check = false;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getAccount().equals(login.getAccount())) {
                    if (list.get(i).getPass().equals(login.getPass())){
                        check = true;
                        return check;
                    }
                }
            }
        }return check;
    }
}
