package member;

public class MemberDatabase {
    private Member [] mlist;
    private int size;
    private static final int NOT_FOUND = -1;

    public MemberDatabase() {
        this.size = 0;
        mlist = new Member[4]; // Default Size
    }

    private int find(Member member) {
        for (int x = 0; x < this.size; x++) { // Not binary search, this is a naive method, so it's fine
            if (this.mlist[x].equals(member)) {
                return x;
            }
        }

        return NOT_FOUND;
    }

    public void grow() {
        Member[] newMemberList = new Member[this.size + 4];

        for (int x = 0; x < this.size; x++) {
            newMemberList[x] = this.mlist[x];
        }

        this.mlist = newMemberList;
    }

    public boolean add(Member member) {
        if (this.size == this.mlist.length) {
            this.grow();
        }


        try {
            this.mlist[this.size] = member; // FIXME: Setting member to a reference??
            this.size++;
        } catch (IndexOutOfBoundsException | ArrayStoreException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
