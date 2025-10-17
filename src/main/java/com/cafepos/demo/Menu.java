package com.cafepos.demo;

// java
import java.util.HashMap;
import java.util.Scanner;
// cafepos
import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.domain.Product;
import com.cafepos.domain.SimpleProduct;

abstract class Menu {

    private String menu = "" +
        "____________________________________\n" + 
        "|                                  |\n" +
        "|              Cafe Menu           |\n" +
        "|                                  |\n" +
        "|----------------------------------|\n" +
        "";
    private final HashMap<Integer,String> menuOptions = new HashMap<Integer,String>();
    private int count = 1;
    public final static Scanner scan = new Scanner(System.in);
    public static Catalog catalog = new InMemoryCatalog();
    public static Order order = new Order(OrderIds.next());

    /**
     * setTitle
     * Constructs a new menu with a given title
     * @param menuTitle - The title of the menu
     * @return The menu object
     */
    public Menu(String menuTitle) {


        int maxLength = 33;

        // Format menu
        if (menuTitle.length() >= maxLength) {

            menuTitle = menuTitle.substring(0, maxLength);
            menuTitle = "| " + menuTitle + " |\n";
        } else {

            menuTitle = calculatePadding(menuTitle, maxLength);
            menuTitle = "| " + menuTitle + " |\n";
        }


        this.menu = "" +
        "____________________________________\n" + 
        "|                                  |\n" +
       menuTitle +
        "|                                  |\n" +
        "|----------------------------------|\n" +
        "";
    }

    /**
     * add
     * Adds an option to the current menu
     * @param optionTitle - The title of the new menu option
     * @return This Menu object to allow method chaining
     */
    public Menu add(String optionTitle) {

        int maxLength = 32;

        // Format option
        if (optionTitle.length() >= maxLength) {
            optionTitle = optionTitle.substring(0, maxLength);
            optionTitle = "| " + optionTitle + " |\n";

        } else {
            optionTitle = calculatePadding(optionTitle, maxLength);
            optionTitle = "| " + optionTitle + " |\n";
        }

        // Add option
        menu += String.format(
                    "| %d. %s |\n" +
                    "|----------------------------------|\n",count,optionTitle);
        count++;

        // Add to menu Options
        menuOptions.put(count,optionTitle);

        return this; // To allow method chaining
    }

    /**
     * calculatePadding
     * calculates the padding for a given text such that the empty space to the left and rigth are
     * as close to equal as possible and fits within a string of length charachters
     * @param text - The text to be altered
     * @param length - The number of characthers the resulting string should contain
     * @return the formating text
     */
    private String calculatePadding(String text, int length) {

        // Option padding
        int padding = length - text.length();
        int paddingLeft = padding / 2;
        int paddingRigth;

        if (padding % 2 == 0)
            paddingRigth = padding / 2 - 1;
        else
            paddingRigth = paddingLeft;

        return " ".repeat(paddingLeft) + text + " ".repeat(paddingRigth);
    }

    /**
     * count
     * @return the number of options currently available
     */
    public int count() {
        return this.menuOptions.size();
    }

    /**
     * begin
     * Prints the options for this menu and requests input from the user
     */
    public void begin() {

        System.out.println("" +
            "                 __\n" +
            "               _/  \\_\n" +
            "            __/      \\__\n" +
            "          _/            \\_\n" +
            "        _/                \\_\n" +
            "     __/                    \\__\n" +
            "   _/                          \\_\n" +
            "__/______________________________\\__\n" +
            "  |      Welcome To My Cafe      |\n" +
            "  |------------------------------|\n" +
            "  |                              |\n" +
            "  |           ( (                |\n" +
            "  |            ) )               |\n" +
            "  |          ........            |\n" +
            "  |          |      |]           |\n" +
            "  |          \\      /            |\n" +
            "  |           `----'             |\n" +
            "  |______________________________|\n");

        boolean next = true;
        while (next) {
            // Print menu
            System.out.println(menu);

            // Get user input
            System.out.print("Enter Selection Here: ");
            int choice = sanatize(scan.next(), menuOptions.size());

            // Execute user choice
            if (choice == -1)
                continue;

            System.out.println("================================\n" +
                               "  Proccessing ....\n" +
                               "================================\n");
            boolean quit = execute(choice);
            System.out.println("\n================================\n");

            // Continue ?
            next = !quit;
        }
    };

    /**
     * sanatize
     * Casts the input string to an int and confirms that it is within the valid range of menu
     * options
     * @param input - The input to be sanatized
     * @param maxRange - The number of inputs that can be chosen
     * @return the sanatized input or -1 if an error has occured
     */
    public static int sanatize(String input, int maxRange) {

        int choice = -1;
        String error = "The input: " + input + "\n" +
                       "Is not a valid input, Please enter a number corresponding to one of the menu options";

        // valiate and cast input
        try {

            choice = Integer.parseInt(input);
            if (maxRange <= 0)
                throw new IllegalArgumentException("The maximum range must be atleast 1");

            if (choice <= 0 || choice > maxRange)
                throw new IllegalArgumentException(error);
            

        } catch (NumberFormatException exception) {
            choice = -1;
            System.out.println(error);

        } catch (IllegalArgumentException exception) {
            choice = -1;
            System.out.println(exception.getMessage());

        }

        return choice;
    }


    /**
     * printMenu
     * prints the current menu
     */
    public void printMenu() {
        System.out.println(menu);
    }

    /**
     * addMenuItem
     * Adds a new option to this menu
     * @param newEntry The name of the new option to be added to the menu
     */
    public Menu addMenuItem(String newEntry) {
        
        // Apply padding/triming if required
        int maxLength = 30;
        if (newEntry.length() > 30)
            newEntry = newEntry.substring(0,30);

        else if (newEntry.length() < 30) {
            
            int padding = 30 - newEntry.length();

            int paddingLeft = padding / 2;
            int paddingRigth;
            if (padding % 2 == 0)
                paddingRigth = padding / 2 - 1;
            else
                paddingRigth = paddingLeft;

            newEntry = " ".repeat(paddingLeft) + newEntry + " ".repeat(paddingRigth);
        }

        // Add entry to CLI
        menu += String.format(
                    "| %d. %s |\n" +
                    "|----------------------------------|\n",count,newEntry);
        count++;

        // Add to menu Options
        menuOptions.put(count,newEntry);

        return this; // To allow method chaining
    }

    /**
     * execute
     * Opens the selected sub menu or executes the appropriate actions
     * @param choice The action to be performed
     */
    abstract boolean execute(int choice);
}
