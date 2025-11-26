package service;

import entity.ElectionData;
import repository.Array;
import repository.BinarySearchTree;
import repository.LinkedList;
import repository.MyArrayList;

import java.util.Scanner;

/**
 * The ElectionService class provides methods to manage election data.
 */

public class ElectionService {
    private Array<ElectionData> myArray;
    private LinkedList<ElectionData> myLinkedList;
    private MyArrayList<ElectionData> myArrayList;
    private BinarySearchTree<ElectionData> myBST;

    /**
     * Loads election data from the raw data source into multiple data structures:
     * LinkedList, MyArrayList, MyStack, MyQueue and BinarySearchTree.
     */
    public void loadData() {
        Array<ElectionData> rawData = myArray.loadData();
        myLinkedList = new LinkedList<>();
        myArrayList = new MyArrayList<>();
        myBST = new BinarySearchTree<>();

        int pos = 0;
        for (int i = 0; i < rawData.size(); i++) {
            ElectionData dataRow = rawData.get(i);
            myLinkedList.addLast(dataRow);
            myArrayList.add(pos++, dataRow);
            myBST.insert(dataRow);
        }
    }

    /**
     * Inserts new election data into the LinkedList  data structure.
     */
    public void insertRecord(Scanner sc) {
        System.out.println("Enter Year:");
        int year = Integer.parseInt(sc.nextLine());

        System.out.println("Enter State:");
        String state = sc.nextLine();

        System.out.println("Enter Party:");
        String party = sc.nextLine();

        System.out.println("Enter Candidate Name:");
        String candidate = sc.nextLine();

        System.out.println("Enter Votes:");
        long votes = Long.parseLong(sc.nextLine());

        ElectionData data = new ElectionData(year, state, party, candidate, votes);
        myLinkedList.addLast(data);
        System.out.println("Record inserted.");
    }


    /**
     * search election data from the LinkedList data structure
     */
    public void searchRecord(Scanner sc) {
        System.out.print("Enter candidate name to search for: ");
        LinkedList.Node<ElectionData> current = getElectionRecordNode(sc);
        if(current != null) {
            System.out.println(current.getData());
        }
    }

    /**
     * Updates election data from the LinkedList data structure
     */
    public void updateRecord(Scanner sc) {
        if(myLinkedList.isEmpty()){
            throw new NullPointerException("Please Load First");
        }
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
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> { System.out.print("New Year: "); data.setYear(Integer.parseInt(sc.nextLine()));}
            case 2 -> { System.out.print("New State: "); data.setState(sc.nextLine()); }
            case 3 -> { System.out.print("New Party: "); data.setParty(sc.nextLine()); }
            case 4 -> { System.out.print("New Name: "); data.setCandidateName(sc.nextLine()); }
            case 5 -> { System.out.print("New Votes: "); data.setVotesReceived(Long.parseLong(sc.nextLine())); }
        }

        System.out.println("Record updated.");
    }

    public  void deleteRecord(Scanner sc) {
        System.out.println("Enter candidate name to delete its record");
        LinkedList.Node<ElectionData> current = getElectionRecordNode(sc);
        if (current == null) return;
        myLinkedList.removeNode(current.getData());
        System.out.println("Candidate record deleted.");
    }
    public void display() {
        System.out.println("\n========== DATA ==========\n");
        System.out.println(ElectionData.header());
        myLinkedList.display();
        System.out.println("\n======================================\n");
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

    public void bstMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("--- BST Menu ---");
            System.out.println("1. Inorder");
            System.out.println("2. Preorder");
            System.out.println("3. Postorder");
            System.out.println("4. Candidate with Most Votes");
            System.out.println("5. Candidate ith Least Votes");
            System.out.println("0. Back");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> myBST.inorder(myBST.getRoot());
                case 2 -> myBST.preorder(myBST.getRoot());
                case 3 -> myBST.postorder(myBST.getRoot());
                case 4 -> System.out.println("Most Votes: " + myBST.getMinimum(myBST.getRoot()));
                case 5 -> System.out.println("Least Votes: " + myBST.getMaximum(myBST.getRoot()));
            }
        } while (choice != 0);
    }
}