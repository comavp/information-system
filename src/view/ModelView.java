package view;

import java.io.IOException;

public interface ModelView {

    void receiveResult(String arg);
    void execute() throws IOException;
}
