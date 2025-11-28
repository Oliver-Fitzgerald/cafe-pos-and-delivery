package com.cafepos.command;

import com.cafepos.domain.LineItem;

public final class AddItemCommand implements Command {

    private final OrderService service;
    private final String recipe;
    private final int qty;

    public AddItemCommand(OrderService service, String recipe, int qty) {

        this.service = service;
        this.recipe = recipe;
        this.qty = qty;
    }

    /**
     * execute
     */
    @Override public void execute() {
        LineItem item = service.addItem(recipe, qty);
        //System.out.println("[DEBUG] New LineItem Total: " + item.lineTotal());
    }

    /**
     * undo
     */
    @Override
    public void undo() {
        service.removeLastItem();
    }
}

