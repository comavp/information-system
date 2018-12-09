import controller.Controller;
import view.ConsoleView;
import model.*;
import java.io.Console;
import java.io.IOException;

public class InformationSystem {

    static public void main(String[] args) throws IOException, ClassNotFoundException {
        ConsoleView view = new ConsoleView();
        Model model = new Model();
        Controller controller = new Controller(model, view);
        view.execute();
    }
}
