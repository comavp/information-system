package view;

import java.util.Scanner;

public interface ModelView {

    void showMenu();
    String parseUserInput(Integer chOperation, Scanner in);
    void execute();
    void receiveResult(String arg);
}
