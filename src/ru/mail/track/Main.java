package ru.mail.track;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        UserStore userStore = new UserStore();
        AuthorizationService authService = new AuthorizationService(userStore);

        User user = null;
        while (user == null) {
            user = authService.startAuthorization();
        }

        WorkSpace workSpace = new WorkSpace(user);
        workSpace.startWork();
    }
}
