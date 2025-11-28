package service;

import entity.ElectionData;
import repository.*;

import java.util.Scanner;

import static entity.ElectionData.header;

/**
 * The ElectionService class provides methods to manage election data.
 */

public class ElectionService {
    private static final String BAR = "=====================================================================================";
    private Array<ElectionData> myArray;
    private LinkedList<ElectionData> myLinkedList;
    private MyArrayList<ElectionData> myArrayList;
    private BinarySearchTree<ElectionData> myBST;
    private Quistion myQueue;
    /**
     * Loads election data from the raw data source into multiple data structures:
     * LinkedList, MyArrayList, MyStack, MyQueue and BinarySearchTree.
     */
    public void loadData() {
        this.myArray = Array.loadData();
        myLinkedList = new LinkedList<>();
        myArrayList = new MyArrayList<>();
        myBST = new BinarySearchTree<>();
        myQueue = new Quistion();

        int pos = 0;
        for (int i = 0; i < myArray.size(); i++) {
            ElectionData dataRow = myArray.get(i);
            myLinkedList.addLast(dataRow);
            myArrayList.add(pos++, dataRow);
            myBST.insert(dataRow);
            myQueue.enQueue(dataRow.getYear(), dataRow.getState(), dataRow.getParty(), dataRow.getCandidateName(), dataRow.getVotesReceived());
        }
    }

    /**
     * Inserts new election data into the LinkedList  data structure.
     */
    public void insertRecord(Scanner sc) {
        int year = 0;
        while (true) {
            System.out.print("Enter Year (e.g. 2024): ");
            String input = sc.nextLine();
            try {
                year = Integer.parseInt(input);
                if (year < 1800 || year > 2026) {
                    System.out.println("Year must be between 1800 and 2026.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid year. Please enter a numeric year.");
            }
        }

        String state = "";
        while (state.isBlank()) {
            System.out.print("Enter State: ");
            state = sc.nextLine().trim();
            if (state.isBlank()) {
                System.out.println("State name cannot be empty.");
            }
        }

        String party = "";
        while (party.isBlank()) {
            System.out.print("Enter Party: ");
            party = sc.nextLine().trim();
            if (party.isBlank()) {
                System.out.println("Party name cannot be empty.");
            }
        }

        String candidate = "";
        while (candidate.isBlank()) {
            System.out.print("Enter Candidate Name: ");
            candidate = sc.nextLine().trim();
            if (candidate.isBlank()) {
                System.out.println("Candidate name cannot be empty.");
            }
        }

        long votes = -1;
        while (true) {
            System.out.print("Enter Votes: ");
            String input = sc.nextLine();
            try {
                votes = Long.parseLong(input);
                if (votes < 0) {
                    System.out.println("Votes cannot be negative.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a numeric vote count.");
            }
        }

        ElectionData data = new ElectionData(year, state, party, candidate, votes);
        myLinkedList.addLast(data);
        System.out.println(header());
        System.out.println(data);
        System.out.println("Record inserted successfully.");
    }


    /**
     * search election data from the LinkedList data structure
     */
    public void searchRecord(Scanner sc) {
        System.out.println(BAR);
        System.out.print("Enter candidate name to search for: ");
        LinkedList.Node<ElectionData> current = getElectionRecordNode(sc);
        if(current != null) {
            System.out.println(header());
            System.out.println(current.getData());
        }
        System.out.println(BAR);
    }

    /**
     * Updates election data from the LinkedList data structure
     */
    public void updateRecord(Scanner sc) {
        System.out.println(BAR);
        System.out.print("Enter candidate name to update: ");
        LinkedList.Node<ElectionData> node = getElectionRecordNode(sc);
        if (node == null) return;

        ElectionData data = node.getData();

        System.out.println("What do you want to update?");
        System.out.println("1. Year");
        System.out.println("2. State");
        System.out.println("3. Party");
        System.out.println("4. Candidate");
        System.out.println("5. Votes");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> { System.out.print("New Year: "); data.setYear(Integer.parseInt(sc.nextLine()));}
                case 2 -> { System.out.print("New State: "); data.setState(sc.nextLine()); }
                case 3 -> { System.out.print("New Party: "); data.setParty(sc.nextLine()); }
                case 4 -> { System.out.print("New Name: "); data.setCandidateName(sc.nextLine()); }
                case 5 -> { System.out.print("New Votes: "); data.setVotesReceived(Long.parseLong(sc.nextLine())); }
            }
        }catch (NumberFormatException e) {
            System.out.println("Please Enter a Choice as a Number for Example 1,2,3,4,5");
        }

        System.out.println(header());
        System.out.println(data);
        System.out.println("Record updated.");
        System.out.println(BAR);
    }

    public  void deleteRecord(Scanner sc) {
        System.out.println(BAR);
        System.out.println("Enter candidate name to delete its record");
        LinkedList.Node<ElectionData> current = getElectionRecordNode(sc);
        if (current == null) return;
        myLinkedList.removeNode(current.getData());
        System.out.println("Candidate record deleted.");
        System.out.println(BAR);
    }
    public void display() {
        System.out.println(BAR);
        System.out.println(header());
        myLinkedList.display();
        System.out.println(BAR);
    }

    public void bstMenu(Scanner sc) {
        int choice;
        do {
            System.out.println(BAR);
            System.out.println("--- BST Menu ---");
            System.out.println("1. Inorder");
            System.out.println("2. Preorder");
            System.out.println("3. Postorder");
            System.out.println("4. Candidate with Most Votes");
            System.out.println("5. Candidate with Least Votes");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            System.out.println(BAR);
            switch (choice) {
                case 1 -> myBST.inorder(myBST.getRoot());
                case 2 -> myBST.preorder(myBST.getRoot());
                case 3 -> myBST.postorder(myBST.getRoot());
                case 4 -> System.out.println("Most Votes: " + myBST.getMaximum(myBST.getRoot()));
                case 5 -> System.out.println("Least Votes: " + myBST.getMinimum(myBST.getRoot()));
            }
            System.out.println(BAR);
        } while (choice != 0);
    }

    /**
     * Generates a report based on the election data.
     */
    public void generateReport() {
        int choice;
        do {
            System.out.println(BAR);
            System.out.println("--- Election Report ---");
            System.out.println("1. Summary Report");
            System.out.println("2. Top Candidates");
            System.out.println("3. Votes by State");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            Scanner sc = new Scanner(System.in);

           choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> generateSummaryReport();
                case 2 -> generateTopCandidatesReport();
                case 3 -> generateVotesByStateReport();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        }while (choice != 0);

    }

    private void generateSummaryReport() {
        System.out.println(BAR);
        System.out.println("*** ELECTION SUMMARY REPORT ***");
        System.out.println("Total States    :" + myArray.size());
        System.out.println("Total Candidates    :" + myLinkedList.size());
        System.out.println("Total Votes    :" + totalVotes());

        generateVotesByStateReport();
        generateTopCandidatesReport();
    }

    private long totalVotes() {
        LinkedList.Node<ElectionData> current = myLinkedList.getHead();
        long total = 0;
        while (current != null ) {
            total += current.getData().getVotesReceived();
            current = current.getNext();
        }
        return total;
    }

    private void generateTopCandidatesReport() {
        System.out.println(BAR);
        System.out.println("*** Top Candidates by Votes ***");
        while(!myQueue.isEmpty()) {
            String[] entry = myQueue.deQueueVote();
            System.out.printf("  %s : %s\n", entry[0], entry[1]);
        }
        System.out.println(BAR);
        // Refill the Queue with data
        for(int i = 0; i < myArray.size(); i++) {
            ElectionData data = myArray.get(i);
            myQueue.enQueue(data.getYear(), data.getState(), data.getParty(), data.getCandidateName(), data.getVotesReceived());
        }
    }

    private void generateVotesByStateReport() {
        System.out.println(BAR);
        myQueue.generateReport();
        System.out.println(BAR);
    }

    private LinkedList.Node<ElectionData> getElectionRecordNode(Scanner sc) {
        String name = sc.nextLine();

        LinkedList.Node<ElectionData> current = myLinkedList.getHead();
        while (current != null ) {
            if (current.getData().getCandidateName().equalsIgnoreCase(name)) {
                break;
            }
            current = current.getNext();
        }

        if (current == null) {
            System.out.println("Candidate not found.");
            return null;
        }
        return current;
    }
}