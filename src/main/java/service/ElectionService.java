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
                    System.out.println("Error: Year must be between 1800 and 2026.\n");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid year. Please enter a numeric year.\n");
            }
        }

        String state = "";
        while (state.isBlank()) {
            System.out.print("Enter State: ");
            state = sc.nextLine().trim();
            if (state.isBlank()) {
                System.out.println("Error: State name cannot be empty.\n");
            }
        }

        String party = "";
        while (party.isBlank()) {
            System.out.print("Enter Party: ");
            party = sc.nextLine().trim();
            if (party.isBlank()) {
                System.out.println("Error: Party name cannot be empty.\n");
            }
        }

        String candidate = "";
        while (candidate.isBlank()) {
            System.out.print("Enter Candidate Name: ");
            candidate = sc.nextLine().trim();
            if (candidate.isBlank()) {
                System.out.println("Error: Candidate name cannot be empty.\n");
            }
        }

        long votes = -1;
        while (true) {
            System.out.print("Enter Votes: ");
            String input = sc.nextLine();
            try {
                votes = Long.parseLong(input);
                if (votes < 0) {
                    System.out.println("Error: Votes cannot be negative.\n");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number. Please enter a numeric vote count.\n");
            }
        }

        ElectionData data = new ElectionData(year, state, party, candidate, votes);
        myLinkedList.addFirst(data);
        int idx = myArrayList.size();
        myArrayList.add(idx, data);
        myStack.push(new UndoAction(Action.INSERT, data, myLinkedList.getTail()));
        myBST.insert(data);
        myQueue.enQueue(data.getYear(), data.getState(), data.getParty(), data.getCandidateName(),data.getVotesReceived());

        System.out.println(BAR);
        System.out.println("    *** CANDIDATE INSERTED ***\n");
        System.out.println(header());
        System.out.println(data);
        System.out.println(BAR);
    }


    /**
     * search election data from the DoublyLinkedList data structure
     */
    public void searchRecord(Scanner sc) {
        System.out.println(BAR);
        System.out.print("Enter Candidate Name to search for: ");
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
        System.out.print("Enter Candidate Name to update: ");
        DoublyLinkedList.Node<ElectionData> node = getElectionRecordNode(sc);
        if (node == null) return;

        ElectionData data = node.getData();
        ElectionData oldData = new ElectionData(data.getYear(), data.getState(), data.getParty(), data.getCandidateName(), data.getVotesReceived());
        myStack.push(new UndoAction(Action.UPDATE, oldData, node));

        System.out.println("What do you want to update?");
        System.out.println("  1. Year");
        System.out.println("  2. State");
        System.out.println("  3. Party");
        System.out.println("  4. Candidate Name");
        System.out.println("  5. Votes");
        System.out.print("Enter choice: ");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> { System.out.print("New Year: "); data.setYear(Integer.parseInt(sc.nextLine()));}
                case 2 -> { System.out.print("New State: "); data.setState(sc.nextLine()); }
                case 3 -> { System.out.print("New Party: "); data.setParty(sc.nextLine()); }
                case 4 -> { System.out.print("New Name: "); data.setCandidateName(sc.nextLine()); }
                case 5 -> { System.out.print("New Votes: "); data.setVotesReceived(Long.parseLong(sc.nextLine())); }
                default -> System.out.println("Error: Invalid choice.\n");
            }
        }catch (NumberFormatException e) {
            System.out.println("Error: Please enter a number for your choice (e.g., 1, 2, 3, 4, 5).\n");
        }
        System.out.println("    *** RECORD UPDATED ***\n");
        var foundNode = myBST.find(data); // Extract variable for the found node
        myBST.delete(foundNode.getData());
        myBST.insert(data);
        System.out.println(header());
        System.out.println(data);
        System.out.println(BAR);
    }

    public  void deleteRecord(Scanner sc) {
        System.out.println(BAR);
        System.out.print("Enter Candidate Name to delete its record: ");
        DoublyLinkedList.Node<ElectionData> current = getElectionRecordNode(sc);
        if (current == null) return;
        myLinkedList.deleteNode(current.getData());
        myStack.push(new UndoAction(Action.DELETE, current.getData(), current.getPrev()));
        myBST.delete(current.getData());
        System.out.println("    *** CANDIDATE WAS DELETED ***\n");
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
        int choice = -1;
        do {
            System.out.println(BAR);
            System.out.println("--- BST Menu ---");
            System.out.println("  1. Inorder");
            System.out.println("  2. Preorder");
            System.out.println("  3. Postorder");
            System.out.println("  4. Candidate with Most Votes");
            System.out.println("  5. Candidate with Least Votes");
            System.out.println("  0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.\n");
                continue;
            }
            System.out.println(BAR);
            switch (choice) {
                case 1 -> myBST.inorder(myBST.getRoot());
                case 2 -> myBST.preorder(myBST.getRoot());
                case 3 -> myBST.postorder(myBST.getRoot());
                case 4 -> System.out.println("Most Votes: " + myBST.getMaximum(myBST.getRoot()));
                case 5 -> System.out.println("Least Votes: " + myBST.getMinimum(myBST.getRoot()));
                case 0 -> System.out.println("Returning to previous menu...\n");
                default -> System.out.println("Error: Invalid choice.\n");
            }
            System.out.println(BAR);
        } while (choice != 0);
    }

    /**
     * Generates a report based on the election data.
     */
    public void generateReport() {
        int choice = -1;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(BAR);
            System.out.println("--- Election Report ---");
            System.out.println("  1. Summary Report");
            System.out.println("  2. Top Candidates");
            System.out.println("  3. Votes by State");
            System.out.println("  0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.\n");
                continue;
            }
            switch (choice) {
                case 1 -> generateSummaryReport();
                case 2 -> generateTopCandidatesReport();
                case 3 -> generateVotesByStateReport();
                case 0 -> System.out.println("Returning to main menu...\n");
                default -> System.out.println("Error: Invalid choice.\n");
            }
        }while (choice != 0);

    }


    private void generateSummaryReport() {
        System.out.println(BAR);
        System.out.println("    *** ELECTION SUMMARY REPORT ***\n");
        System.out.printf("    Total States      : %d\n", myArray.size());
        System.out.printf("    Total Candidates  : %d\n", myLinkedList.size());
        System.out.printf("    Total Votes       : %d\n", totalVotes());
        System.out.println();
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
        System.out.println("    *** Top Candidates by Votes ***\n");
        while(!myQueue.isEmpty()) {
            String[] entry = myQueue.deQueueVote();
            System.out.printf("      %s : %s\n", entry[0], entry[1]);
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
        String name = sc.nextLine().trim();
        DoublyLinkedList.Node<ElectionData> current = myLinkedList.getHead();
        while (current != null) {
            if (current.getData().getCandidateName().equalsIgnoreCase(name)) {
                break;
            }
            current = current.getNext();
        }
        if (current == null) {
            System.out.println("Error: Candidate \"" + name + "\" not found.\n");
            return null;
        }
        return current;
    }

    public void undoLast() {
        if (myStack.isEmpty()) {
            System.out.println("Nothing to undo.\n");
        } else {
            UndoAction undoAction = myStack.pop();
            switch (undoAction.getAction()) {
                case INSERT -> deleteAction(undoAction.getData());
                case UPDATE -> restoreAction(undoAction.getNode(), undoAction.getData());
                case DELETE -> insertAction(undoAction.getData(), undoAction.getNode());
            }
            System.out.println("    *** LAST OPERATION HAS BEEN UNDONE ***\n");
            System.out.println(BAR);
        }
    }

    private void insertAction(ElectionData data, DoublyLinkedList.Node<ElectionData> node) {
        if(node == null) {
           myLinkedList.addFirst(data);
           myBST.insert(data);
        }else {
            myLinkedList.addAfter(node, data);
        }
    }

    private void deleteAction(ElectionData data) {
        myLinkedList.deleteFirst();
        myBST.delete(data);
    }

    private void restoreAction(DoublyLinkedList.Node<ElectionData> targetNode, ElectionData oldData) {
        targetNode.getData().setYear(oldData.getYear());
        targetNode.getData().setParty(oldData.getParty());
        targetNode.getData().setState(oldData.getState());
        targetNode.getData().setCandidateName(oldData.getCandidateName());
        targetNode.getData().setVotesReceived(oldData.getVotesReceived());

        myBST.delete(oldData);
        myBST.insert(targetNode.getData());
    }
}
