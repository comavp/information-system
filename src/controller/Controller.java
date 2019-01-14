package controller;

import view.*;
import model.*;
import view.fxview.FXViewFacade;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer
{
    private ModelView view;
    private Model model;

    public Controller(Model model, FXViewFacade view) throws IOException, ClassNotFoundException {
        this.model = model;
        this.view = view;
        model.addObserver(this);
        view.addObserver(this);
    }

    public Controller(Model model, ConsoleView view) throws IOException, ClassNotFoundException {
        this.model = model;
        this.view = view;
        model.addObserver(this);
        view.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (view.equals(o)) {
            model.helper((String)arg);
        }
        if (model.equals(o)) {
            view.receiveResult((String)arg);
        }
    }
}
