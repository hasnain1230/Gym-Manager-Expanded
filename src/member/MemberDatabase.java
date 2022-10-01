package member;

import constants.Constants;
import date.Date;
import enums.Location;

import java.util.Calendar;

public class MemberDatabase {
    private Member[] mlist;
    private int size;

    public MemberDatabase() {
        this.size = 0;
        mlist = new Member[4]; // FIXME: MAGIC NUMBER Default Size
    }

    private int find(Member member) {
        for (int x = 0; x < this.size; x++) { // Not binary search, this is a naive method, so it's fine
            if (this.mlist[x].equals(member)) {
                return x;
            }
        }

        return -1;
    }

    public void grow() {
        Member[] newMemberList = new Member[this.size + 4];

        for (int x = 0; x < this.size; x++) {
            newMemberList[x] = this.mlist[x];
        }

        this.mlist = newMemberList;
    }

    public boolean checkMemberAge(Member member) {
        Calendar calendar = Calendar.getInstance();
        Date date = member.getDob();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int dobYear = date.getYear();
        int dobMonth = date.getMonth();
        int dobDay = date.getDay();

        int yearDifference = currentYear - dobYear;

        if (yearDifference > Constants.MINIMUM_AGE) {
            return true;
        } else if (yearDifference == Constants.MINIMUM_AGE) {
            if ((currentMonth - dobMonth) > 0) {
                return true;
            } else if ((currentMonth - dobMonth) == 0) {
                if ((currentDay - dobDay) >= 0) {
                    return true; // Happy Birthday!
                }
            }
        }

        return false;
    }

    public boolean add(Member member) {
        if (this.find(member) >= 0) { // This means we found a member
            return false;
        }
        if (this.size == this.mlist.length) {
            this.grow();
        }

        this.mlist[size] = member;
        this.size++;

        return true;
    }

    public boolean remove(Member member) {
        int index = this.find(member);

        if (index == -1) {
            return false;
        }

        for (int x = index; x < this.size - 1; x++) {
            this.mlist[x] = this.mlist[x + 1]; // Might be wrong?
        }

        this.mlist[this.size - 1] = null;
        this.size--;

        return true;
    }
    public void print() {
        for (int x = 0; x < this.size; x++) {
            System.out.println(this.mlist[x]);
        }
    }

    public void printByCounty() {
        Member tempMember;
        // Isn't Java so beautiful and readable? Doesn't this make so much sense? Ahhh yes, nice to be back in Java. :)
        for (int x = 0; x < this.size; x++) {
            for (int y = x + 1; y < this.size; y++) {
                if (this.mlist[x].getLocation().getCounty().compareTo(this.mlist[y].getLocation().getCounty()) > 0) {
                    tempMember = this.mlist[x];
                    this.mlist[x] = this.mlist[y];
                    this.mlist[y] = tempMember;
                } else if (this.mlist[x].getLocation().getCounty().compareTo(this.mlist[y].getLocation().getCounty()) == 0) {
                    if (this.mlist[x].getLocation().getPostalCode().compareTo(this.mlist[y].getLocation().getPostalCode()) > 0) {
                        tempMember = this.mlist[x];
                        this.mlist[x] = this.mlist[y];
                        this.mlist[y] = tempMember;
                    }
                }
            }

            System.out.println(this.mlist[x]);
        }
    }

    public void printByExpirationDate() {
        Member tempMember;
        for (int x = 0; x < this.size; x++) {
            for (int y = x + 1; y < this.size; y++) { // x = 09/29/2022 y
                if (this.mlist[x].getExpire().compareTo(this.mlist[y].getExpire()) > 0) {
                    tempMember = this.mlist[x];
                    this.mlist[x] = this.mlist[y];
                    this.mlist[y] = tempMember;
                }
            }

            System.out.println(this.mlist[x]);
        }
    }

    public void printByName() {
        Member tempMember;

        for (int x = 0; x < this.size; x++) {
            for (int y = x + 1; y < this.size; y++) {
                if (this.mlist[x].getLname().compareTo(this.mlist[y].getLname()) > 0) {
                    tempMember = this.mlist[x];
                    this.mlist[x] = this.mlist[y];
                    this.mlist[y] = tempMember;
                } else if (this.mlist[x].getLname().compareTo(this.mlist[y].getLname()) == 0) {
                    if (this.mlist[x].getFname().compareTo(this.mlist[y].getFname()) > 0) {
                        tempMember = this.mlist[x];
                        this.mlist[x] = this.mlist[y];
                        this.mlist[y] = tempMember;
                    }
                }
            }

            System.out.println(this.mlist[x]);
        }
    }
}
