package Assignment1;

import java.util.ArrayList;

public class Route {
    ArrayList<Flight> flightList = new ArrayList<>();
    private double cost;
    private double duration;
    private double stopovers;
    private double layover;
    private double flightTime;

    public Route(ArrayList<Flight> flightList) {
        this.flightList = flightList;
        setCost();
        setDuration();
        setStopovers();
        setLayover();
        setFlightTime();
    }

    public double getCost() {
        return cost;
    }

    public double getDuration() {
        return duration;
    }

    public double getStopovers() {
        return stopovers;
    }

    public double getLayover() {
        return layover;
    }

    public double getFlightTime() {
        return flightTime;
    }

    public void setCost() {
        double cost = 0;
        for (Flight f : this.flightList) {
            cost = cost + f.getTicketPrice();
        }
        this.cost = cost;
    }

    public void setDuration() {
        double duration = 0;
        for (int i = 0; i < flightList.size(); i++) {
            if (i < flightList.size() - 1) {
                duration = duration + flightList.get(i).getDuration() + Flight.layover(flightList.get(i), flightList.get(i + 1));
            }
            if (i == flightList.size() - 1) {
                duration = duration + flightList.get(i).getDuration();
            }
        }
        this.duration = duration;
    }

    public void setStopovers() {
        this.stopovers = flightList.size() - 1;
    }

    public void setLayover() {
        double layover = 0;
        for (int i = 0; i < flightList.size(); i++) {
            if (i < flightList.size() - 1) {
                layover = layover + Flight.layover(flightList.get(i), flightList.get(i + 1));
            }
        }
        this.layover = 1;
    }

    public void setFlightTime() {
        double time = 0;
        for (Flight f : this.flightList) {
            time = time + f.getDuration();
        }
        this.flightTime = 1;
    }


}
