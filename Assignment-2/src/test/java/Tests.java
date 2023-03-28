import observer.ConcreteMember;
import observer.GroupAdmin;
import observer.JvmUtilities;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);

    // stub method to check external dependencies compatibility
    @Test
    public void test() {
        String s1 = "Alice";
        String s2 = "Bob";

        logger.info(() -> JvmUtilities.objectFootprint(s1));

        logger.info(() -> JvmUtilities.objectFootprint(s1, s2));

        logger.info(() -> JvmUtilities.objectTotalSize(s1));

        logger.info(() -> JvmUtilities.jvmInfo());

    }

    // ConcreteMemberTest
    @Test
    void update() {
        GroupAdmin test = new GroupAdmin();
        ConcreteMember name1 = new ConcreteMember();
        ConcreteMember name2 = new ConcreteMember();
        test.register(name1);
        test.register(name2);
        test.append("This is a test");
        assertEquals("This is a test", name1.getUsb().toString());
        assertEquals("This is a test", name2.getUsb().toString());
        // test for shallow
        assertEquals(JvmUtilities.objectFootprint(test.USB), JvmUtilities.objectFootprint(name1.getUsb()));
        assertEquals(JvmUtilities.objectFootprint(test.USB), JvmUtilities.objectFootprint(name2.getUsb()));
    }

    // GroupAdminTest
    @Test
    void register() {
        GroupAdmin test = new GroupAdmin();
        ConcreteMember name1 = new ConcreteMember();
        test.register(name1);
        assertEquals(name1, test.getLast());

    }

    @Test
    void unregister() {
        GroupAdmin test = new GroupAdmin();
        ConcreteMember name1 = new ConcreteMember();
        test.register(name1);
        test.unregister(name1);
        assertNull(test.getLast());

    }

    @Test
    void insert() {
        GroupAdmin tets = new GroupAdmin();
        tets.append("This is");
        tets.insert(7, " a test");
        assertEquals("This is a test", tets.getUSB().toString());

    }

    @Test
    void append() {
        GroupAdmin test = new GroupAdmin();
        test.append("This is a test");
        assertEquals("This is a test", test.getUSB().toString());

    }

    @Test
    void delete() {
        GroupAdmin test = new GroupAdmin();
        test.append("This is a test");
        test.delete(7, 14);
        assertEquals("This is", test.getUSB().toString());

    }

    @Test
    void undo() {
        GroupAdmin test = new GroupAdmin();
        test.append("This is a test");
        test.delete(4, 8);
        test.undo();
        assertEquals("This is a test", test.getUSB().toString());
    }

}
