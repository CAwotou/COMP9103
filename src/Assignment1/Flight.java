package Assignment1;

public class Flight {
    private int flightID;
    private MyDate departureTime;
    private Location sourceLocation;
    private Location destinationLocation;
    private String capacity;
    private int booked;

    public Flight(int flightID, MyDate departureTime, Location sourceLocation, Location destinationLocation, String capacity, int booked) {
        this.flightID = flightID;
        this.departureTime = departureTime;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.capacity = capacity;
        this.booked = booked;
    }

    //get the number of minutes this flight takes (round to nearest whole number)
    public int getDuration() {
        double duration = Math.round(getDistance() / 720 * 60);
        return (int) duration;
    }

    //implement the ticket price formula
    public double getTicketPrice() {
        double price1 = 30 + 4 * (destinationLocation.getDemand() - sourceLocation.getDemand());
        double price2 = getDistance() / 100;
        double initialPrice = price1 * price2;
        double finalPrice = 0;
        if (getBooked() == 0) {
            return initialPrice;
        } else {
            if (getPercentageOfBookedSeats() > 0 && getPercentageOfBookedSeats() <= 0.5) {
                finalPrice = (-0.4 * getPercentageOfBookedSeats() + 1) * initialPrice;
            }
            if (getPercentageOfBookedSeats() > 0.5 && getPercentageOfBookedSeats() <= 0.7) {
                finalPrice = (getPercentageOfBookedSeats() + 0.3) * initialPrice;
            }
            if (getPercentageOfBookedSeats() > 0.7 && getPercentageOfBookedSeats() <= 1) {
                finalPrice = ((0.2 / Math.PI) * Math.atan(20 * getPercentageOfBookedSeats() - 14) + 1) * initialPrice;
            }
        }
        return finalPrice;
    }

    //book the given number of passengers onto this flight, returning the total cost
    public double book(int num) {
        double cost = 0;
        if (!isFull()) {
            for (int i = 0; i < num; i++) {
                cost = cost + getTicketPrice();
                setBooked(getBooked() + 1);
            }
        }
        return cost;
    }

    //return whether or not this flight is full
    public boolean isFull() {
        int cap = Integer.parseInt(getCapacity());
        if (cap == getBooked()) {
            return true;
        }
        return false;
    }

    //get the distance of this flight in km
    public double getDistance() {
        return Location.distance(getSourceLocation(),getDestinationLocation());
    }

    //get the layover time, in minutes, between two flights
    public static int layover(Flight x, Flight y) {
        MyDate departingOfY=y.getDepartureTime();
        MyDate arrivingOfY=y.getArrivingTime();
        MyDate departingOfX=x.getDepartureTime();
        MyDate arrivingOfX=x.getArrivingTime();
        if(departingOfY.isLessThan(arrivingOfX)){
            return Math.abs(10080+(departingOfY.weekday-arrivingOfX.weekday)*1440+(departingOfY.hour-arrivingOfX.hour)*60+(departingOfY.minute-arrivingOfX.minute));
        }
        return Math.abs((departingOfY.weekday-arrivingOfX.weekday)*1440+(departingOfY.hour-arrivingOfX.hour)*60+(departingOfY.minute-arrivingOfX.minute));
    }


    //get proportion of booked seats(booked/capacity)
    public double getPercentageOfBookedSeats() {
        return ((double) booked) / Double.parseDouble(capacity);
    }

    public int getFlightID() {
        return flightID;
    }

    public MyDate getDepartureTime() {
        return departureTime;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    public String getCapacity() {
        return capacity;
    }

    public int getBooked() {
        return booked;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public MyDate getScheduleTime(String name) {
        if (getDestinationLocation().getName().equalsIgnoreCase(name)) {
            return getArrivingTime();
        } else {
            return getDepartureTime();
        }
    }

    public MyDate getArrivingTime() {
        return getDepartureTime().generateNewDate(departureTime, getDuration());
    }
}
