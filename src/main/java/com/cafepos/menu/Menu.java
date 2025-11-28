package com.cafepos.menu;

import java.util.*;
import java.util.stream.Collectors;

/*
 * Menu
 *
 */
public final class Menu extends MenuComponent {

    private final String name;
    private final List<MenuComponent> children = new ArrayList<>();

    /**
     * Menu
     * Constructor for a composite menu
     * @param name The name of the menu
     */
    public Menu(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name required");

        this.name = name;
    }

    
    /**
     * childrenIterator
     * Expose child iterator for CompositeIterator
     */
    public Iterator<MenuComponent> childrenIterator() {
        return children.iterator();
    }

    /**
     * allItems
     * @return list of all sub menus
     */
    public List<MenuComponent> allItems() {

        List<MenuComponent> out = new ArrayList<>();
        var it = iterator();
        while (it.hasNext())
            out.add(it.next());

        return out;
    }

    /**
     * vegetarianItems
     * @return list of all vegitarian leaf sub menus
     */
    public List<MenuItem> vegetarianItems() {

        return allItems().stream()
            .filter(mc -> mc instanceof MenuItem mi && mi.vegetarian())
            .map(mc -> (MenuItem) mc)
            .collect(Collectors.toList());
   }

    /*
     * Overriden Methods
     */

    @Override
    public void add(MenuComponent c) {
        children.add(c);
    }

    @Override
    public void remove(MenuComponent c) {
        children.remove(c);
    }

    @Override
    public MenuComponent getChild(int i) {
        return children.get(i);
    }

    @Override
    public String name() {
        return name;
    }


    @Override
    public Iterator<MenuComponent> iterator() {
        return new CompositeIterator(childrenIterator());
    }

    @Override
    public void print() {
        System.out.println(name);
        for (MenuComponent c : children)
            c.print();
    }
}
