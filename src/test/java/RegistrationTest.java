import model.Database;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.RegistrationMenu;
import view.Request;
import view.enums.Menus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class RegistrationTest {
    private Path input, output;
    private RegistrationMenu menu;

    @Before
    public void init() {
        System.out.println("setting up registration test ...");
        Database.prepareDatabase();
        this.menu = new RegistrationMenu();
        this.output = Paths.get("src/test/resources").resolve("registrationOut.file");
        this.input = Paths.get("src/test/resources").resolve("registrationIn.file");
    }

    @Test
    public void testRegistration() throws IOException {

        List<String> commands = Files.readAllLines(input);
        List<String> results = Files.readAllLines(output);

        for (int i = 0; i < commands.size(); i++) {
            menu.setCurrentMenu(Menus.REGISTER_MENU);
            menu.register(commands.get(i));
            Assert.assertEquals(results.get(i), Request.getResponse());
        }

    }

}
