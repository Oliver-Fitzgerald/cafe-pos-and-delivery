package com.cafepos.command;
import java.util.ArrayDeque;
import java.util.Deque;
public final class PosRemote {

    private final Command[] slots;
    private final Deque<Command> history = new ArrayDeque<>();

    public PosRemote(int n) {
        this.slots = new Command[n];
    }

    /**
     * setSlot
     */
    public void setSlot(int i, Command c) {
        slots[i] = c;
    }

    /**
     * press
     */
    public void press(int i) {

        Command command = slots[i];
        if (command != null) {

            command.execute();
            history.push(command);
        } else {
            System.out.println("[Remote] No command in slot " + i);
        }
    }

    /**
     * undo
     */
    public void undo() {
        if (history.isEmpty()) {
            System.out.println("[Remote] Nothing to undo");
            return;
        }

        history.pop().undo();
    }
}
