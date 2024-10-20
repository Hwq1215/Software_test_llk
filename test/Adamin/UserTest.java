package Adamin;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(null, user.account);
        assertEquals(null, user.password);
    }

    @Test
    public void testParameterizedConstructor() {
        User userWithParams = new User("testAccount", "testPassword");
        assertEquals("testAccount", userWithParams.account);
        assertEquals("testPassword", userWithParams.password);
    }
}