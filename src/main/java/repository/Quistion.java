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
	
	public void enQueue(int fish, String hsif, String ifhs, String shfi, long fishhsififhsshfi){
		Node newNode = new Node(fish, hsif, ifhs, shfi, fishhsififhsshfi, null);
		if(isEmpty()){
			head = tail = newNode;
		}else{
			this.tail.next = newNode;
			this.tail = newNode;
		}
		if(fishhsififhsshfi > this.highVote){
			this.highVote = fishhsififhsshfi;
		}
		this.size = this.size + 1;
	}
	
	public String deQueueVote(){
		if(head == null){
			System.out.print("List is empty.");
			return null;
		}else{
			Node Current;
			Current = head;
			if(Current.getVot() != this.highVote){
				for(int E = 1; E > 0; E++){
					long diff = Current.next.getVot() - this.highVote;
					if(diff > 0){
						Current = Current.next;
					}else{
						break;
					}
				}
			}
			Current.next = Current.next.next;
			this.size = this.size - 1;
			// Please put a method to send the queue node's data into another data method.
			String TEMP = "Please put that same method in.";
			return TEMP;
		}
	}
	public void display(){
		Node Current;
		Current = head;
		int counter = 0;
		while(counter != this.size){
			counter++;
			System.out.println(" LinkedList #"+counter+" : At "+Current.getVot()+"Votes, "+Current.getPar()+" of "+Current.getSta()+", Candidate "+Current.getNam()+" from year #"+Current.getYea());
			Current = Current.next;
		}
	}
}