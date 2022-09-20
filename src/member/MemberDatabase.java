package member;

public class MemberDatabase {
    private Member [] mlist;
    private int size;

    public MemberDatabase() {

    }

    private int find(Member member) { }
    private void grow() { }
    public boolean add(Member member) { }
    public boolean remove(Member member) { }
    public void print () { } //print the array contents as is
    public void printByCounty() { } //sort by county and then zipcode
    public void printByExpirationDate() { } //sort by the expiration date
    public void printByName() { } //sort by last name and then first name
}
