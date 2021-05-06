package com.example.aska;


public class StateCounterNum {

    public String childCounter;

    public StateCounterNum() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public StateCounterNum(String ChildCounter) {
        this.childCounter = ChildCounter;

    }

    public String getChildCounter() {
        return childCounter;
    }

    public void setChildCounter(String childCounter) {
        this.childCounter = childCounter;
    }
}
