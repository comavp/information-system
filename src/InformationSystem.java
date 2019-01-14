import controller.Controller;
import model.Model;
import view.ConsoleView;
import view.fxview.FXViewFacade;

import java.io.IOException;

public class InformationSystem {

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        InformationSystem musicLibrary = new InformationSystem();
        musicLibrary.startFXApp();
    }

    private void startConsoleApp() throws IOException, ClassNotFoundException {
        ConsoleView view = new ConsoleView();
        Model model = new Model();
        Controller controller = new Controller(model, view);
        view.execute();
    }

    private void startFXApp() throws IOException, ClassNotFoundException {
        FXViewFacade facade = new FXViewFacade();
        Model model = new Model();
        Controller controller = new Controller(model, facade);
        facade.execute();
    }
}
