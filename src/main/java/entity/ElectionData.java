package entity;

public class ElectionData implements Comparable<ElectionData> {
    private int year;
    private String state;
    private String party;
    private String candidateName;
    private long votesReceived;

    public ElectionData() {
    }

    public ElectionData(int year, String state, String party, String candidateName, long votesReceived) {
        this.year = year;
        this.state = state;
        this.party = party;
        this.candidateName = candidateName;
        this.votesReceived = votesReceived;
    }

    @Override
    public String toString() {
        return String.format(
                "%-5d | %-12s | %-12s | %-20s | %-10d",
                year, state, party, candidateName, votesReceived
        );
    }
    public static String header() {
        return String.format(
                "%-5s | %-12s | %-12s | %-20s | %-10s",
                "Year", "State", "Party", "Candidate", "Votes"
        );
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidate) {
        this.candidateName = candidate;
    }

    public long getVotesReceived() {
        return votesReceived;
    }

    public void setVotesReceived(long votesReceived) {
        this.votesReceived = votesReceived;
    }

    @Override
    public int compareTo(ElectionData other) {
        return Long.compare(this.votesReceived, other.votesReceived);
    }
}
