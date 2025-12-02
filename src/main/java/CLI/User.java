package CLI;

import service.ElectionService;

import java.util.Scanner;

public class User {
    private static final ElectionService electionService = new ElectionService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String title = "\n===== Election Explorer System =====";
        System.out.println(title);
        System.out.println("Loading Data from CSV");
        electionService.loadData();

        int choice;
        int controlTitle = 0;
        do {
            System.out.println(controlTitle++ > 0 ? title : "");
            System.out.println("1. Insert a Record");
            System.out.println("2. Update a Record");
            System.out.println("3. Delete a Record");
            System.out.println("4. Search Records");
            System.out.println("5. Display All Candidates Information");
            System.out.println("6. BST Operations");
            System.out.println("8. Undo Last Operation");
            System.out.println("7. Generate Report");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> electionService.insertRecord(sc);
                    case 2 ->  electionService.updateRecord(sc);
                    case 3 -> electionService.deleteRecord(sc);
                    case 4 -> electionService.searchRecord(sc);
                    case 5 -> electionService.display();
                    case 6 -> electionService.bstMenu(sc);
                    case 7 -> electionService.generateReport();
                    case 8 -> electionService.undoLast();
                    case 0 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice!");
                }
            }catch (NumberFormatException e) {
                System.out.println("Please Enter a Choice as a Number for Example 1,2,3,4,5");
                choice = -1; // set it invalid
            }
        } while (choice != 0);

        sc.close();
    }
}
