package repository;

public class Quistion {
	private class Node{
		private int year;
		private String stat;
		private String part;
		private String name;
		private long vote;
		private Node next;
		
		public Node(int newYears, String newState, String newParty, String newNames, long newVotes, Node newNexts){
			this.year = newYears;
			this.stat = newState;
			this.part = newParty;
			this.name = newNames;
			this.vote = newVotes;
			this.next = newNexts;
		}
		public int getYea(){
			return this.year;
		}
		public void setYea(int fish){
			this.year = fish;
		}
		public String getSta(){
			return this.stat;
		}
		public void setStat(String fish){
			this.stat = fish;
		}
		public String getPar(){
			return this.part;
		}
		public void setPar(String fish){
			this.part = fish;
		}
		public String getNam(){
			return this.name;
		}
		public void setNam(String fish){
			this.name = fish;
		}
		public long getVot(){
			return this.vote;
		}
		public void setVot(long fish){
			this.vote = fish;
		}
	}
	
	private Node head = null;
	private Node tail = null;
	private long highVote = 0;
	private int size = 0;
	public Quistion(){}
	
	public int getSize(){
		return this.size;
	}
	public boolean isEmpty(){
		if(head == null){return true;}
		else{return false;}
	}
	public Node getFront(){
		return this.head;
	}

    public void enQueue(int year, String state, String party, String name, long votes) {
        Node newNode = new Node(year, state, party, name, votes, null);

        if (isEmpty() || votes > head.getVot()) {
            // Insert at head
            newNode.next = head;
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else {
            // insert at the correct position
            Node current = head;
            while (current.next != null && current.next.getVot() >= votes) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
            if (newNode.next == null) {
                tail = newNode;
            }
        }
        size++;
    }

    public String[] deQueueVote() {
        if (isEmpty()) {
            System.out.println("List is empty.");
            return null;
        }
        String name = head.getNam();
        String votes = Long.toString(head.getVot());
        String[] info = new String[2];
        info[0] = name;
        info[1] = votes;
        head = head.next;
        size--;

        if (head == null) {
            tail = null;
        }

        return info;
    }

    public void generateReport() {
        Node current = head;
        String[] stateNames = new String[this.getSize()];
        long[] stateVotes = new long[this.getSize()];
        int uniqueCount = 0;

        while (current != null) {
            String state = current.getSta();
            long votes = current.getVot();
            boolean found = false;

            // Check if state already exists
            for (int i = 0; i < uniqueCount; i++) {
                if (state.equals(stateNames[i])) {
                    stateVotes[i] += votes;
                    found = true;
                    break;
                }
            }

            // If it's a new state, add to arrays
            if (!found) {
                stateNames[uniqueCount] = state;
                stateVotes[uniqueCount] = votes;
                uniqueCount++;
            }

            current = current.next;
        }

        // Print results
        System.out.println("*** Votes by State ***");
        for (int i = 0; i < uniqueCount; i++) {
            System.out.printf("State '%s' has %d votes.%n", stateNames[i], stateVotes[i]);
        }
        System.out.println();
    }

}