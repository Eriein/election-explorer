package CLI;

import service.ElectionService;

import java.util.Scanner;

public class User {
    private static ElectionService electionService;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        electionService = new ElectionService();
        System.out.println("\n===== Election Explorer System =====");
        System.out.println("Loading Data from CSV");
        electionService.loadData();
        do {
            System.out.println("1. Insert a Record");
            System.out.println("2. Update a Record");
            System.out.println("3. Delete a Record");
            System.out.println("4. Search Records");
            System.out.println("5. Display All Candidates Information");
            System.out.println("6. BST Operations");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> electionService.insertRecord(sc);
                case 2 ->  electionService.updateRecord(sc);
                case 3 -> electionService.deleteRecord(sc);
                case 4 -> electionService.searchRecord(sc);
                case 5 -> electionService.display();
                case 6 -> electionService.bstMenu(sc);
                //case 7 -> electionService.generateReport(sc);
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);

        sc.close();
    }
}
