package repository;

import entity.ElectionData;

public class ArrayStack {
    private ElectionData[] stack;
    private int top;
    private int CAPACITY=100;

    public ArrayStack(){
        this.stack = new ElectionData[CAPACITY];
        top=-1;
    }

    public void push (ElectionData electionData){
        if (top==CAPACITY-1){
            System.out.println("Stack is full!");
        }else{
            top+=1;
            stack[top]=electionData;
        }
    }

    public ElectionData peek(){
        if(isEmpty()){
            System.out.println("Stack is empty.");
            return null;
        }
        return stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}