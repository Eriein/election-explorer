package repository;

public class ArrayStack<T> {
    private final T[] stack;
    private int top = -1;
    private final int CAPACITY=100;

    @SuppressWarnings("unchecked")
    public ArrayStack(){
        this.stack = (T[]) new Object[CAPACITY];
    }

    public void push (T data){
        if (top==CAPACITY-1){
            System.out.println("Stack is full!");
        }else{
            top+=1;
            stack[top]=data;
        }
    }

    public T peek(){
        if(isEmpty()){
            System.out.println("Stack is empty.");
            return null;
        }
        return stack[top];
    }

    public T pop(){
        if(isEmpty()){
            System.out.println("Stack is empty.");
            return null;
        }
        return stack[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}