package com.domain;

import org.springframework.stereotype.Repository;

@Repository
public class MyFriends {
    private int mid;
    private int oid;

    @Override
    public String toString() {
        return "MyFriends{" +
                "mid=" + mid +
                ", oid=" + oid +
                '}';
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }
}
