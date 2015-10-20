package ru.mail.track;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.CRC32;

public class AuthorizationService {

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    User startAuthorization() throws IOException {

        System.out.println("Please, choose option:");
        System.out.println("\t> login\n\t> register");
        System.out.print("Your choice: ");

        Scanner scanner = new Scanner(System.in);
        String comm = scanner.next();


        switch (comm) {
            case "login": {
                System.out.print("Enter login: ");
                String name = scanner.next();
                System.out.print("Enter password: ");
                String pass = scanner.next();

                User user = login(name, pass);
                if (user != null) {
                    System.out.println("Greetings, " + user.getName() + "\n");
                    return user;
                } else {
                    System.out.println("Wrong login-password. Please, try again\n");
                }
                break;
            }
            case "register": {
                String name;
                do {
                    System.out.print("Enter desired login: ");
                    name = scanner.next();
                } while (userStore.isUserExist(name));

                System.out.print("Enter password: ");
                String pass = scanner.next();
                //Console console = System.console();
                //char[] passwordArray = console.readPassword("Enter password: ");
                //String pass = new String(passwordArray);

                createUser(name, pass);
                System.out.println("You successfully registered in system\n");
                break;
            }
            default:
                System.out.println("Unknown command. Please, try again\n");
        }
        return null;
    }

    User login(String name, String pass) {
        User user = userStore.getUser(name);

        if ((user != null) && (user.getPass().equals(pass)))
            return user;
        return null;
    }

    void createUser(String name, String pass) {
        userStore.addUser(new User(name, pass));
    }

    boolean isLogin() { return false; }
}
