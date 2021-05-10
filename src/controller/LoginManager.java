package controller;

import model.Login;

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

    public static LoginManager getInstance(String name, List<Login> list){
        if (INSTANCE == null){
            INSTANCE = new LoginManager(name,list);
        }return INSTANCE;
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

    public static LoginManager getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(LoginManager INSTANCE) {
        LoginManager.INSTANCE = INSTANCE;
    }

    public void addAccount(Login login){
        list.add(login);
        System.out.println("Đăng ký thành công");
    }

}
