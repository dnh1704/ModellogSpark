package model;

import java.util.Objects;

public class IP {
    private long ip;
    private int count;

    public IP() {
    }

    public IP(long ip, int count) {
        this.ip = ip;
        this.count = count;
    }

    public IP(long ip) {
        this.ip = ip;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IP ip1 = (IP) o;
        return ip == ip1.ip;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

    @Override
    public String toString() {
        return "IP{" +
                "ip=" + ip +
                ", count=" + count +
                '}';
    }
}
