package com.cafepos.command;

public final class MacroCommand implements Command {

    private int ptr;
    private final Command[] steps;

    public MacroCommand(Command... steps) {
        this.steps = steps;
        ptr = steps.length - 1;
    }

    /**
     * excecute
     */
    @Override
    public void execute() {
        for (Command c : steps)
            c.execute();
    }

    @Override
    public void undo() {
        for (int i = steps.length-1; i >= 0; i--)
            steps[i].undo();
    }

    public void undoOnce() {

        if (ptr < 0)
            return;
        steps[ptr].undo();
        ptr--;
    }

    public void reDoOnce() {
        if (ptr == steps.length)
            return;
        steps[ptr].execute();
        ptr++;
    }
}
