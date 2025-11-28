package com.cafepos;

// cafepos
import com.cafepos.common.Money;
import com.cafepos.payment.CardPayment;
import com.cafepos.command.*;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
// juinit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandTests {

    /**
     * undo
     * Validates that the undo functionality works correctly by counting the items
     * in an order before and after an undo operation is perfromed
     */
    @Test
    public void undo(){

        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        PosRemote remote = new PosRemote(1);

        remote.setSlot(0, new AddItemCommand(service, "ESP+SHOT+OAT", 1));

        remote.press(0);
        int expectedItemCount = service.order().items().size();
        remote.undo();
        int actualItemCount = service.order().items().size();
        assertEquals(expectedItemCount - 1, actualItemCount);

    }

    /**
     * macroCommand_undo
     * executes the add item command twice then calls undo once to confirm that the
     * change was undone.
     */
    @Test
    public void macroCommand_undo() {

        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        MacroCommand macroCommand = new MacroCommand(new AddItemCommand(service, "ESP+SHOT+OAT", 1),new AddItemCommand(service, "ESP+SHOT+OAT", 1));

        macroCommand.execute();
        int expectedItemCount = service.order().items().size();
        macroCommand.undoOnce();
        int actualItemCount = service.order().items().size();
        assertEquals(expectedItemCount - 1, actualItemCount);

    }

    /**
     * remote_commandIntegration
     * Integration test, binds two AddItemCommands and a PayOrderCommand to a PosRemote,
     * press them in sequence, and assert the order subtotal equals what you expect from your Week 5 prices.
     * The purpose is to show end-to-end wiring without touching UI or domain internals.
    */
    @Test
    public void remote_commandIntegration(){

        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        PosRemote remote = new PosRemote(3);

        remote.setSlot(0, new AddItemCommand(service, "ESP+SHOT+OAT", 1));
        remote.setSlot(1, new AddItemCommand(service, "LAT+L", 2));
        remote.setSlot(2, new PayOrderCommand(service, new CardPayment("1234567890123456"), 10));

        remote.press(0);
        remote.press(1);
        assertEquals(Money.of(12.76), service.order().totalWithTax(10));

    }
}

