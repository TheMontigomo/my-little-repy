package ru.mail.track;

import java.io.*;
import java.util.*;

public class UserStore {
    private Map<String,User> users = new TreeMap<String,User>();
    private final File database = new File("src/database.txt");

    public UserStore() throws IOException {
        String name;
        if (!database.exists()) {
            database.createNewFile();
        }
        
        InputStream fis = new FileInputStream(database);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        while ((name = br.readLine()) != null) {
            String pass  = br.readLine();
            pass = pass.substring(1, pass.length());
            users.put(name, new User(name, pass));
            //addUser(new User(name, pass));
        }
        br.close();
    }

    // проверить, есть ли пользователь с таким именем
    // если есть, вернуть true
    public boolean isUserExist(String name) {
        if (users.containsKey(name)) {
            System.out.println("Login is already occupied. Be more creative!");
            return true;
        } else {
            return false;
        }
    }

    // Добавить пользователя в хранилище
    public User addUser(User user) {
        try {            
            //Записывает в базу данных
            RandomAccessFile file = new RandomAccessFile(database, "rw");
            file.skipBytes((int)database.length());
            file.writeBytes(user.getName() + "\n\t" + user.getPass() + "\n");
            file.close();
//            //Создает папку профиля
//            File profile = new File("src/" + user.getName());
//            profile.mkdir();
//            //Создает конфиг-файл
//            profile = new File("src/users/" + user.getName() + "/settings.conf");
//            profile.createNewFile();
        } catch (IOException ex) {
            System.out.println("Error on adding new user");
        }
        return users.put(user.getName(), user);
    }

    // Получить пользователя по имени
    public User getUser(String name) {
        return users.get(name);
    }
}
