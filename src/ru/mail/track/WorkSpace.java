package ru.mail.track;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class WorkSpace {
    private User user;
    private File userHistory;

    public WorkSpace(User user) {
        this.user = user;
        userHistory = new File("src/users/" + user.getName() + ".txt");

        if (!userHistory.exists())
            try {
                userHistory.createNewFile();
            } catch (IOException e) {}
    }

    public void startWork() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter message: ");
            String comm = scanner.nextLine();
            String[] commArray = comm.split(" ");

            switch (commArray[0]) {
                case "/help": {
                    showHelp();
                    break;
                }
                case "/history": {
                    if (commArray.length == 2)
                        showHistory(Integer.valueOf(commArray[1]));
                    else
                        showHistory(-1);
                    break;
                }
                case "/user": {
                    setNickname(commArray[1]);
                    System.out.println("Nickname was successfully changed");
                    break;
                }
                case "/stop": {
                    return;
                }
                default: {
                    updateHistory(comm, new Date());
                }
            }
        }
    }

    private void updateHistory(String message, Date messageTime) {
        try {
            RandomAccessFile file = new RandomAccessFile(userHistory, "rw");
            file.skipBytes((int)userHistory.length());

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd.MM.yy");
            String line = "[" + sdf.format(messageTime) + "]" + message + "\n";
            file.writeBytes(line);

            file.close();
        } catch (IOException e) {
            System.out.println("Error on updating user history");
        }
    }

    private void setNickname(String nickname) {
        user.setNickname(nickname);
    }
    
    private void showHistory(int n) {
        try {
            RandomAccessFile file = new RandomAccessFile(userHistory, "r");                
            Vector<String> lineArray = new Vector<String>();
            String line = file.readLine();
            
            while (line != null) {
                lineArray.add(line);
                line = file.readLine();
            }            
            if (n == -1) { n = lineArray.size(); }
            
            Iterator<String> iter = lineArray.listIterator(lineArray.size() - n);
            for (; iter.hasNext(); ) {
                System.out.println(iter.next());
            }                           
            file.close();
        } catch (IOException e) {
            System.out.println("Error on updating user history");
        } 
        
        
    }

    private void showHelp() {
        System.out.println("This is a list of commands:");
        System.out.println("\t/history [N] - shows your last N messages (all, by default)");
        System.out.println("\t/find [keyword] - find in history");
        System.out.println("\t/stop - finish work and close connections");
        System.out.println("\t/user [nickname] - set new nickname");
    }
}
