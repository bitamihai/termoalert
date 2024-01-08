package mbita.termoalert.app;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException, InterruptedException {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        container.select(App.class).get().run();
        container.shutdown();
    }
}
