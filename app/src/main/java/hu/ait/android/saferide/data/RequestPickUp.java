package hu.ait.android.saferide.data;

import java.io.Serializable;

/**
 * Created by emasten on 5/16/16.
 */
public class RequestPickUp implements Serializable {

    private String user;
    private String location;
    private String destination;
    private int numPeople;
    private boolean isEmergency;

    public RequestPickUp() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(boolean isEmergency) {
        this.isEmergency = isEmergency;
    }
}
