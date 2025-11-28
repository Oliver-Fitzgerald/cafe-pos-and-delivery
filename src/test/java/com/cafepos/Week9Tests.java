package com.cafepos;

// Juinit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// Cafepos
import com.cafepos.menu.Menu;
import com.cafepos.menu.MenuItem;
import com.cafepos.menu.MenuComponent;
import com.cafepos.state.OrderFSM;
import com.cafepos.common.Money;
// Java
import java.util.List;

public class Week9Tests {

    @Test
    void State_contains_collects_all_nodes() {

        Menu a = new Menu("A");
        a.add(new MenuItem("x", Money.of(1.0), true));

        Menu b = new Menu("B");
        b.add(new MenuItem("y", Money.of(2.0), false));

        Menu c = new Menu("C");
        c.add(new MenuItem("z", Money.of(2.0), false));
        c.add(b);

        Menu root = new Menu("ROOT");
        root.add(a);
        root.add(c);

        List<String> names = root.allItems()
                                 .stream()
                                 .map(MenuComponent::name)
                                 .toList();

        assertTrue(names.contains("x"));
        assertTrue(names.contains("y"));
        assertTrue(names.contains("z"));
    }

    @Test
    void OrderFsm_status() {

        OrderFSM fsm = new OrderFSM();
        assertEquals("NEW", fsm.status());

        fsm.pay();
        assertEquals("PREPARING", fsm.status());

        fsm.markReady();
        assertEquals("READY", fsm.status());

        fsm.deliver();
        assertEquals("DELIVERED", fsm.status());

        fsm.cancel();
        assertEquals("DELIVERED", fsm.status());
    }
}
