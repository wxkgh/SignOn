package taskLife.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.ModelResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.junit.Test;
import org.life.task.Controller.home;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;

public class homeControllerTest {
    @Test
    public void testHomePage() throws Exception {
        home controller = new home();
        MockMvc mockMvc = standaloneSetup(controller).build();
        assertEquals("home", controller.home());
    }
}
