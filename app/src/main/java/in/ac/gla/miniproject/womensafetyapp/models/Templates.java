package in.ac.gla.miniproject.womensafetyapp.models;

import java.io.Serializable;

public class Templates implements Serializable {
    int id;
    String msg;
    boolean active;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
