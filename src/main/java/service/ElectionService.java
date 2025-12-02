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
    private DoublyLinkedList<ElectionData> myLinkedList;
    private MyArrayList<ElectionData> myArrayList;
    private BinarySearchTree<ElectionData> myBST;
    private ArrayStack<UndoAction> myStack;
    private Quistion myQueue;
    /**
     * Loads election data from the raw data source into multiple data structures:
     * LinkedList, MyArrayList, MyStack, MyQueue and BinarySearchTree.
     */
    public void loadData() {
        this.myArray = Array.loadData();
        myLinkedList = new DoublyLinkedList<>();
        myArrayList = new MyArrayList<>();
        myBST = new BinarySearchTree<>();
        myQueue = new Quistion();
        myStack = new ArrayStack<>();

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
        myLinkedList.addFirst(data);
        myStack.push(new UndoAction(Action.INSERT, data, myLinkedList.getTail()));

        System.out.println(BAR);
        System.out.println("\t\t*** CANDIDATE INSERTED ***");
        System.out.println(header());
        System.out.println(data);
    }


    /**
     * search election data from the DoublyLinkedList data structure
     */
    public void searchRecord(Scanner sc) {
        System.out.println(BAR);
        System.out.print("Enter candidate name to search for: ");
        DoublyLinkedList.Node<ElectionData> current = getElectionRecordNode(sc);
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
        DoublyLinkedList.Node<ElectionData> node = getElectionRecordNode(sc);
        if (node == null) return;

        ElectionData data = node.getData();
        ElectionData oldData = new ElectionData(data.getYear(), data.getState(), data.getParty(), data.getCandidateName(), data.getVotesReceived());
        myStack.push(new UndoAction(Action.UPDATE, oldData, node));

        System.out.println("What do you want to update?");
        System.out.println("1. Year");
        System.out.println("2. State");
        System.out.println("3. Party");
        System.out.println("4. Candidate Name");
        System.out.println("5. Votes");
        System.out.print("Enter Choice: ");

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
        System.out.println("\t\t*** RECORD UPDATED ***");
        System.out.println(header());
        System.out.println(data);
        
        System.out.println(BAR);
    }

    public  void deleteRecord(Scanner sc) {
        System.out.println(BAR);
        System.out.println("Enter candidate name to delete its record: ");
        DoublyLinkedList.Node<ElectionData> current = getElectionRecordNode(sc);
        if (current == null) return;
        myLinkedList.deleteNode(current.getData());
        myStack.push(new UndoAction(Action.DELETE, current.getData(), current.getPrev()));
        System.out.println("\t\t*** CANDIDATE WAS DELETED ***");
        System.out.println(header());
        System.out.println(current.getData());
        System.out.println(BAR);
    }
    public void display() {
        System.out.println(BAR);
        System.out.println(header());
        myLinkedList.displayForward();
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
                default -> throw new IllegalStateException("Unexpected value: " + choice);
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
        DoublyLinkedList.Node<ElectionData> current = myLinkedList.getHead();
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

    private DoublyLinkedList.Node<ElectionData> getElectionRecordNode(Scanner sc) {
        // Read and store the candidate name
        String name = sc.nextLine().trim(); // .trim() to clean up whitespace

        DoublyLinkedList.Node<ElectionData> current = myLinkedList.getHead();

        // Use the loop to find the record
        while (current != null) {
            if (current.getData().getCandidateName().equalsIgnoreCase(name)) {
                break; // Found the record
            }
            current = current.getNext();
        }

        if (current == null) {
            System.out.println("Error: Candidate \"" + name + "\" not found.");
            return null;
        }

        return current;
    }

    public void undoLast() {
        if (myStack.isEmpty()) {
            System.out.println("Nothing to undo");
        }else {
            UndoAction undoAction = myStack.pop();
            switch (undoAction.getAction()) {
                case INSERT -> deleteAction();
                case UPDATE -> restoreAction(undoAction.getNode(), undoAction.getData());
                case DELETE -> insertAction(undoAction.getData(), undoAction.getNode());
            }
            System.out.println("LAST OPERATION HAS BEEN UNDONE");
            System.out.println(BAR);
        }

    }

    private void insertAction(ElectionData data, DoublyLinkedList.Node<ElectionData> node) {
        if(node == null) {
           myLinkedList.addFirst(data);
        }else {
            myLinkedList.addAfter(node, data);
        }
    }

    private void deleteAction() {
        myLinkedList.deleteFirst();
    }

    private void restoreAction(DoublyLinkedList.Node<ElectionData> targetNode, ElectionData oldData) {
        targetNode.getData().setYear(oldData.getYear());
        targetNode.getData().setParty(oldData.getParty());
        targetNode.getData().setState(oldData.getState());
        targetNode.getData().setCandidateName(oldData.getCandidateName());
        targetNode.getData().setVotesReceived(oldData.getVotesReceived());
    }
}