package ExpenseTrackerProject.Version1;

import java.util.Scanner;

public class ExpenseTracker {

    private final Scanner scan = new Scanner(System.in);

    String[] category = {
            "Food",
            "Travel",
            "Shopping",
            "Entertainment",
            "Health",
            "Education",
            "Other"
    };

    private static final int MAX_EXPENSES = 100;

    int expenseCount = 0;

    String[] expenseNames = new String[MAX_EXPENSES];
    double[] expenseAmounts = new double[MAX_EXPENSES];
    int[] expenseCategories = new int[MAX_EXPENSES];

    public static void main(String[] args) {

        ExpenseTracker obj = new ExpenseTracker();

        while (true) {

            obj.showMenu();

            int choice = obj.scan.nextInt();

            switch (choice) {

                case 1:
                    obj.addExpense();
                    break;

                case 2:
                    obj.viewAllExpenses();
                    break;
                case 3:
                    obj.searchExpense();
                    break;
                case 4:
                    obj.filterExpenses();
                    break;
                case 5:
                    obj.sortExpenses();
                    break;

                case 6:
                    obj.showTotalSpending();
                    break;
                case 7:
                    obj.deleteExpense();
                    break;
                case 8:
                    System.out.println("\nThank you for using Expense Tracker!");
                    obj.scan.close();
                    return;

                default:
                    System.out.println("\nInvalid Choice! Please try again.");
            }
        }
    }

    // ================= MAIN MENU =================

    private void showMenu() {

        System.out.println("""

                =========== Expense Tracker ===========
                1. Add Expense
                2. View All Expenses
                3. Search Expense
                4. Filter Expenses
                5. Sort Expenses
                6. Show Total Spending
                7. Delete Expense
                8. Exit
                """);

        System.out.print("Enter Choice : ");
    }

    // ================= ADD EXPENSE =================

    private void addExpense() {

        showCategory();

        int amount;

        while (true) {

            System.out.print("Enter Amount : ");

            if (scan.hasNextInt()) {

                amount = scan.nextInt();

                if (amount > 0) {

                    expenseAmounts[expenseCount] = amount;
                    expenseCount++;

                    System.out.println("\nExpense Added Successfully.\n");
                    return;
                }
            } else {
                scan.next();
            }

            System.out.println("Please enter a valid positive amount.");
        }
    }

    private void showCategory() {

        while (true) {

            System.out.println("\nChoose Category");

            for (int i = 0; i < category.length; i++) {
                System.out.println((i + 1) + ". " + category[i]);
            }

            System.out.print("Enter Choice : ");

            if (!scan.hasNextInt()) {
                scan.next();
                System.out.println("Invalid Input.");
                continue;
            }

            int choice = scan.nextInt();

            if (choice >= 1 && choice <= category.length) {

                expenseCategories[expenseCount] = choice;

                scan.nextLine();

                System.out.print("Enter Expense Name : ");
                expenseNames[expenseCount] = scan.nextLine();

                return;
            }

            System.out.println("Invalid Category.\n");
        }
    }

    private void viewAllExpenses() {

        if (expenseCount == 0) {

            System.out.println("\nNo Expenses Found.\n");
            return;
        }

        printTableHeader();

        for (int i = 0; i < expenseCount; i++) {
            printExpense(i);
        }

        printTableFooter();

        System.out.println("Total Expenses : " + expenseCount);
    }

    // ================= SEARCH EXPENSE =================

    private void searchExpense() {

        if (expenseCount == 0) {
            System.out.println("\nNo Expenses Found.\n");
            return;
        }

        while (true) {

            System.out.println("""

                    ===== Search Expense =====
                    1. Search by Expense Name
                    2. Search by Category
                    3. Search by Amount
                    4. View all
                    5. Back
                    """);

            System.out.print("Enter Choice : ");

            int choice = scan.nextInt();
            scan.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    searchByName();
                    break;

                case 2:
                    searchByCategory();
                    break;

                case 3:
                    searchByAmount();
                    break;
                case 4:
                    viewAllExpenses();
                    break;
                case 5:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    private void searchByName() {

        System.out.print("Enter Expense Name : ");
        String name = scan.nextLine();

        boolean found = false;

        printTableHeader();

        for (int i = 0; i < expenseCount; i++) {

            if (expenseNames[i].equalsIgnoreCase(name)) {

                printExpense(i);
                found = true;
            }
        }

        printTableFooter();

        if (!found) {
            System.out.println("No Expense Found.");
        }
    }

    private void sortExpenses() {

        if (expenseCount == 0) {
            System.out.println("\nNo Expenses Found.\n");
            return;
        }

        while (true) {

            System.out.println("""

                    =========== Sort Expenses ===========
                    1. Highest Amount First
                    2. Lowest Amount First
                    3. Name A-Z
                    4. Name Z-A
                    5. Category Wise
                    6. Back
                    """);

            System.out.print("Enter Choice : ");

            int choice = scan.nextInt();

            switch (choice) {

                case 1:
                    sortHighestAmount();
                    viewAllExpenses();
                    break;

                case 2:
                    sortLowestAmount();
                    viewAllExpenses();
                    break;

                case 3:
                    sortNameAscending();
                    viewAllExpenses();
                    break;

                case 4:
                    sortNameDescending();
                    viewAllExpenses();
                    break;

                case 5:
                    sortCategoryWise();
                    viewAllExpenses();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    private void deleteExpense() {

        if (expenseCount == 0) {
            System.out.println("\nNo Expenses Found.\n");
            return;
        }

        // Show all expenses first
        viewAllExpenses();

        System.out.print("\nEnter Expense ID to Delete (0 to Cancel): ");

        int id = scan.nextInt();

        if (id == 0) {
            System.out.println("Delete Cancelled.");
            return;
        }

        if (id < 1 || id > expenseCount) {
            System.out.println("Invalid Expense ID.");
            return;
        }

        int index = id - 1;

        System.out.println("\nDeleting...");
        System.out.println("Expense Name : " + expenseNames[index]);
        System.out.println("Category     : " + category[expenseCategories[index] - 1]);
        System.out.printf("Amount       : ₹%.2f%n", expenseAmounts[index]);

        // Shift all elements one position to the left
        for (int i = index; i < expenseCount - 1; i++) {

            expenseNames[i] = expenseNames[i + 1];
            expenseAmounts[i] = expenseAmounts[i + 1];
            expenseCategories[i] = expenseCategories[i + 1];
        }

        // Clear the last record
        expenseNames[expenseCount - 1] = null;
        expenseAmounts[expenseCount - 1] = 0;
        expenseCategories[expenseCount - 1] = 0;

        expenseCount--;

        System.out.println("\nExpense Deleted Successfully.");
    }
    // ================= FILTER EXPENSES =================

    private void filterExpenses() {

        if (expenseCount == 0) {
            System.out.println("\nNo Expenses Found.\n");
            return;
        }

        while (true) {

            System.out.println("""

                    =========== Filter Expenses ===========
                    1. Filter by Category
                    2. Above Amount
                    3. Below Amount
                    4. Between Amount Range
                    5. Back
                    """);

            System.out.print("Enter Choice : ");

            int choice = scan.nextInt();

            switch (choice) {

                case 1:
                    filterByCategory();
                    break;

                case 2:
                    filterAboveAmount();
                    break;

                case 3:
                    filterBelowAmount();
                    break;

                case 4:
                    filterBetweenAmount();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    private void showTotalSpending() {

        if (expenseCount == 0) {
            System.out.println("\nNo Expenses Found.\n");
            return;
        }

        double total = 0;

        for (int i = 0; i < expenseCount; i++) {
            total += expenseAmounts[i];
        }

        System.out.println("\n========== Total Spending ==========");
        System.out.printf("Total Expenses : %d%n", expenseCount);
        System.out.printf("Total Spending : ₹%.2f%n", total);
        System.out.println("====================================");
    }

    private void searchByCategory() {

        System.out.println("\nSelect Category");

        for (int i = 0; i < category.length; i++) {
            System.out.println((i + 1) + ". " + category[i]);
        }

        System.out.print("Enter Choice : ");

        int choice = scan.nextInt();

        if (choice < 1 || choice > category.length) {
            System.out.println("Invalid Category.");
            return;
        }

        boolean found = false;

        printTableHeader();

        for (int i = 0; i < expenseCount; i++) {

            if (expenseCategories[i] == choice) {

                printExpense(i);
                found = true;
            }
        }

        printTableFooter();

        if (!found) {
            System.out.println("No Expense Found.");
        }
    }

    private void searchByAmount() {

        System.out.print("Enter Amount : ");

        double amount = scan.nextDouble();

        boolean found = false;

        printTableHeader();

        for (int i = 0; i < expenseCount; i++) {

            if (expenseAmounts[i] == amount) {

                printExpense(i);
                found = true;
            }
        }

        printTableFooter();

        if (!found) {
            System.out.println("No Expense Found.");
        }
    }

    private void filterByCategory() {

        System.out.println("\nSelect Category");

        for (int i = 0; i < category.length; i++) {
            System.out.println((i + 1) + ". " + category[i]);
        }

        System.out.print("Enter Choice : ");

        int choice = scan.nextInt();

        boolean found = false;

        printTableHeader();

        for (int i = 0; i < expenseCount; i++) {

            if (expenseCategories[i] == choice) {

                printExpense(i);
                found = true;
            }
        }

        printTableFooter();

        if (!found) {
            System.out.println("No Expense Found.");
        }
    }

    private void filterAboveAmount() {

        System.out.print("Show Expenses Above : ");

        double amount = scan.nextDouble();

        boolean found = false;

        printTableHeader();

        for (int i = 0; i < expenseCount; i++) {

            if (expenseAmounts[i] > amount) {

                printExpense(i);
                found = true;
            }
        }

        printTableFooter();

        if (!found) {
            System.out.println("No Expense Found.");
        }
    }

    private void filterBelowAmount() {

        System.out.print("Show Expenses Below : ");

        double amount = scan.nextDouble();

        boolean found = false;

        printTableHeader();

        for (int i = 0; i < expenseCount; i++) {

            if (expenseAmounts[i] < amount) {

                printExpense(i);
                found = true;
            }
        }

        printTableFooter();

        if (!found) {
            System.out.println("No Expense Found.");
        }
    }

    private void filterBetweenAmount() {

        System.out.print("Enter Minimum Amount : ");
        double min = scan.nextDouble();

        System.out.print("Enter Maximum Amount : ");
        double max = scan.nextDouble();

        boolean found = false;

        printTableHeader();

        for (int i = 0; i < expenseCount; i++) {

            if (expenseAmounts[i] >= min && expenseAmounts[i] <= max) {

                printExpense(i);
                found = true;
            }
        }

        printTableFooter();

        if (!found) {
            System.out.println("No Expense Found.");
        }
    }

    private void sortHighestAmount() {

        for (int i = 0; i < expenseCount - 1; i++) {

            for (int j = 0; j < expenseCount - i - 1; j++) {

                if (expenseAmounts[j] < expenseAmounts[j + 1]) {

                    swap(j, j + 1);
                }
            }
        }

        System.out.println("\nSorted by Highest Amount.\n");
    }

    private void sortLowestAmount() {

        for (int i = 0; i < expenseCount - 1; i++) {

            for (int j = 0; j < expenseCount - i - 1; j++) {

                if (expenseAmounts[j] > expenseAmounts[j + 1]) {

                    swap(j, j + 1);
                }
            }
        }

        System.out.println("\nSorted by Lowest Amount.\n");
    }

    private void sortNameAscending() {

        for (int i = 0; i < expenseCount - 1; i++) {

            for (int j = 0; j < expenseCount - i - 1; j++) {

                if (expenseNames[j].compareToIgnoreCase(expenseNames[j + 1]) > 0) {

                    swap(j, j + 1);
                }
            }
        }

        System.out.println("\nSorted A-Z.\n");
    }

    private void sortNameDescending() {

        for (int i = 0; i < expenseCount - 1; i++) {

            for (int j = 0; j < expenseCount - i - 1; j++) {

                if (expenseNames[j].compareToIgnoreCase(expenseNames[j + 1]) < 0) {

                    swap(j, j + 1);
                }
            }
        }

        System.out.println("\nSorted Z-A.\n");
    }

    private void sortCategoryWise() {

        for (int i = 0; i < expenseCount - 1; i++) {

            for (int j = 0; j < expenseCount - i - 1; j++) {

                if (expenseCategories[j] > expenseCategories[j + 1]) {

                    swap(j, j + 1);
                }
            }
        }

        System.out.println("\nSorted Category Wise.\n");
    }

    private void swap(int i, int j) {

        double tempAmount = expenseAmounts[i];
        expenseAmounts[i] = expenseAmounts[j];
        expenseAmounts[j] = tempAmount;

        String tempName = expenseNames[i];
        expenseNames[i] = expenseNames[j];
        expenseNames[j] = tempName;

        int tempCategory = expenseCategories[i];
        expenseCategories[i] = expenseCategories[j];
        expenseCategories[j] = tempCategory;
    }

    // ================= TABLE METHODS =================

    private void printTableHeader() {

        System.out.println();

        System.out.println("=========================================================================================");
        System.out.printf("| %-4s | %-18s | %-35s | %-12s |%n",
                "ID",
                "Category",
                "Expense Name",
                "Amount");
        System.out.println("=========================================================================================");
    }

    private void printExpense(int index) {

        System.out.printf("| %-4d | %-18s | %-35s | %10.2f |%n",
                index + 1,
                category[expenseCategories[index] - 1],
                expenseNames[index],
                expenseAmounts[index]);
    }

    private void printTableFooter() {

        System.out.println("=========================================================================================");
    }
}