package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Tinker", "Tailor", (byte) 30);
        userService.saveUser("Soldier", "Sailor", (byte) 30);
        userService.saveUser("Rich_man", "Poor_man", (byte) 30);
        userService.saveUser("Beggar_man", "Thief", (byte) 30);
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        //Util.closeConnection();
        Util.closeSessionFactory();
    }
}
