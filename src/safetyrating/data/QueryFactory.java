package safetyrating.data;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/**
 * CLI to query CollisionDatabase.
 * 
 * @author Harkeerat Kanwal
 */
public class QueryFactory {
    private CollisionDatabase data;
    private String[] attrs;

    public QueryFactory(CollisionDatabase data) {
        this.data = data;
        attrs = data.getAttrs();
    }

    public void start() {
        System.out.println("\nThis command line utility is designed to make it easy to query the collision database and to help generate data that would be useful to train a machine learning algorithm.");
        System.out.println("Please refer to the .docx file in the root of this project to understand what the values for each attribute represent (Eg. C_WTHR = 1 means the weather was clear and sunny).");
        readQuery();
    }

    /**
     * Validate given attribute by checking whether it is in the array of attributes.
     * 
     * @param attr A String representing an attribute whose validity to check.
     * @return A boolean. True if the given attr is in the array of attributes of the CollisionDatabase, false otherwise.
     */
    private boolean validAttr(String attr) {
        for (int i = 0; i < attrs.length; i++) {
            if (attrs[i].equals(attr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate given attributes by checking whehter they are in the array of attributes.
     * 
     * @param attributes An array of Strings. The attributes whose validity to check.
     * @return A boolean. True if all of the given attributes are valid, false otherwise.
     */
    private boolean validAttrs(String[] attributes) {
        for (String attr : attributes) {
            if (!validAttr(attr)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Turns array of attributes into a String of options the user can input.
     * Eg. "(C_WHTR, C_MNTH, C_HOUR)".
     * 
     * @return A String. The attributes are put inside brackets and separated by commas.
     */
    private String attrsAsOptions() {
        String options = "(";
        for (int i = 0; i < attrs.length; i++) {
            options += attrs[i];
            if (i == attrs.length-1) {
                options += ") ";
            }
            else {
                options += ", ";
            }
        }
        return options;
    }

    private void readQuery() {
        Scanner scanner = new Scanner(System.in);
        String queryType;
        System.out.print("\nWould you like to see the percentages or query specific data? (p/q) ");
        queryType = scanner.nextLine();

        if (queryType.equals("p")) {
            System.out.print("For which attribute? " + attrsAsOptions() + " ");
            String attr = scanner.nextLine();
            if (validAttr(attr)) {
                printPercentages(attr);
            }
            else {
                System.out.println("The attribute is invalid. Please refer to the data dictionary .docx file.");
            }
        }
        else if (queryType.equals("q")) {
            System.out.print("For which attributes (you may enter multiple, seperated by spaced)? " + attrsAsOptions() + " ");
            String[] selectdAttrs = scanner.nextLine().split(" ", 6);
            String[] vals;
            if (validAttrs(selectdAttrs)) {
                System.out.print("What are the corresponding values (separated by spaces)? ");
                vals = scanner.nextLine().split(" ", 6);

                if (vals.length < selectdAttrs.length) {
                    System.out.println("Length mismatch.");
                }
                else {
                    System.out.print("Print in human readable format? (y/n) ");
                    String humanReadable = scanner.nextLine();
                    printQuery(selectdAttrs, vals, !(humanReadable.equals("n") || humanReadable.equals("N")));
                }
            }
            else {
                System.out.println("One or more of the attributes is invalid. Please refer to the data dictionary .docx file.");
            }
        }
        else {
            System.out.println("Invalid option.");
        }

        readQuery();
    }

    private void printPercentages(String attr) {
        Set<Integer> keys = data.get(attr).keySet();

        for (Integer key : keys) {
            Double percentage = 100.0*Double.valueOf(data.count(attr, key))/Double.valueOf(data.getNumEntries());
            System.out.format("%s = %d: %f%s\n", attr, key, percentage, "%");
        }
    }

    private void printQuery(String[] selectdAttrs, String[] vals, boolean humanReadable) {
        // Converts vals from String[] to int[].
        int[] valsAsInts = new int[vals.length];
        try {
            for (int i = 0; i < vals.length; i++) {
                valsAsInts[i] = Integer.parseInt(vals[i]);
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Values must be integers.");
            return;
        }

        // Display found entries.
        ArrayList<Entry> results = null;
        try {
            results = data.get(selectdAttrs, valsAsInts);
        }
        catch (NullPointerException e) {
            System.out.println("One or more of the requested values was out of range.");
            return;
        }
        for (int i = 0; i < results.size(); i++) {
            Entry entry = results.get(i);
            if (humanReadable) {
                System.out.print("Month (C_MNTH) = " + entry.C_MNTH() + ", ");
                System.out.print("Day (C_WDAY) = " + entry.C_WDAY() + ", ");
                System.out.print("Hour (C_HOUR) = " + entry.C_HOUR() + ", ");
                System.out.print("Weather (C_WHTR) = " + entry.C_WTHR() + ", ");
                System.out.print("Roadway configuration (C_RCFG) = " + entry.C_RCFG() + ", ");
                System.out.print("Road surface (C_RSUR) = " + entry.C_RSUR() + ", ");
                System.out.print("Vehicle type (V_TYPE) = " + entry.V_TYPE() + ", ");
                System.out.println("Vehicle model year (V_YEAR) = " + entry.V_YEAR());
            }
            else {
                System.out.println(entry.toString());
            }
        }
        System.out.print("\n");
    }
}