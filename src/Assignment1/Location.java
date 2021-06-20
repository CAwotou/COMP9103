package Assignment1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Location {
    private String name;
    private double longitude;
    private double latitude;
    private double demand;
    private ArrayList<Flight> arriving;
    private ArrayList<Flight> departing;
    private ArrayList<Flight> schedule;

    public Location(String name, double latitude, double longitude, double demand) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.demand = demand;
        this.arriving = new ArrayList<>();
        this.departing = new ArrayList<>();
        this.schedule=new ArrayList<>();
    }

    //Implement the Haversine formula - return value in kilometres
    public static double distance(Location l1, Location l2) {
        final double radiusOfEarth = 6371;
        double lat1 = l1.getLatitude();
        double lat2 = l2.getLatitude();
        double lon1 = l1.getLongitude();
        double lon2 = l2.getLongitude();
        double radiansOfLat = Math.toRadians(lat2 - lat1);
        double radiansOfLon = Math.toRadians(lon2 - lon1);
        double a = (Math.sin(radiansOfLat / 2) * Math.sin(radiansOfLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(radiansOfLon / 2) * Math.sin(radiansOfLon / 2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radiusOfEarth * c;
        return distance;
    }

    //Add the flights arriving at this airport to the list
    public void addArrival(Flight f) {
        arriving.add(f);
        schedule.add(f);
        Collections.sort(schedule, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())){
                    return 1;
                }
                else return -1;
            }
        });
        Collections.sort(arriving, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())){
                    return 1;
                }
                else return -1;
            }
        });
    }

    //Add the flights departing from this airport to the list
    public void addDeparture(Flight f) {
        Collections.sort(schedule, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())){
                    return 1;
                }
                else return -1;
            }
        });
        Collections.sort(arriving, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())){
                    return 1;
                }
                else return -1;
            }
        });
        departing.add(f);
        schedule.add(f);
    }

    /**
     * Check to see if Flight f can depart from this location.
     * If there is a clash, the clashing flight string is returned, otherwise null is returned.
     * A conflict is determined by if any other flights are arriving or departing at this location within an hour of this flight's departure time.
     *
     * @param f The flight to check.
     * @return "Flight <id> [departing/arriving] from <name> on <clashingFlightTime>". Return null if there is no clash.
     */
    public String hasRunwayDepartureSpace(Flight f) {
        ArrayList<Flight> departingOfSource=f.getSourceLocation().getDeparting();
        ArrayList<Flight> arrivingOfSource=f.getSourceLocation().getArriving();
        Collections.sort(departingOfSource, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())){
                    return -1;
                }
                else return 1;
            }
        });
        Collections.sort(arrivingOfSource, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())){
                    return -1;
                }
                else return 1;
            }
        });
        for(Flight flight:departingOfSource){
            MyDate departingTimeOfOther=flight.getDepartureTime();
            MyDate departingTimeOfF=f.getDepartureTime();
            MyDate smallRange=departingTimeOfOther.generateNewDate(departingTimeOfOther,-60);
            MyDate bigRange=departingTimeOfOther.generateNewDate(departingTimeOfOther,60);
            if(departingTimeOfF.weekday==7&&departingTimeOfOther.weekday==1){
                int minutes=MyDate.timeDifference(departingTimeOfF,departingTimeOfOther);
                if(minutes<60){
                    return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " departing from " + flight.getSourceLocation().getName() + " on " + flight.getDepartureTime().toString1() + ".";
                }
            }
            if(departingTimeOfF.weekday==1&&departingTimeOfOther.weekday==7){
                int minutes=MyDate.timeDifference(departingTimeOfOther,departingTimeOfF);
                if(minutes<60){
                    return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " departing from " + flight.getSourceLocation().getName() + " on " + flight.getDepartureTime().toString1() + ".";
                }
            }
            else if(departingTimeOfF.isGreaterThan(smallRange)&&departingTimeOfF.isLessThan(bigRange)){
                return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " departing from " + flight.getSourceLocation().getName() + " on " + flight.getDepartureTime().toString1() + ".";
            }
        }
        for(Flight flight:arrivingOfSource){
            MyDate arrivingTimeOfOther=flight.getArrivingTime();
            MyDate departingTimeOfF=f.getDepartureTime();
            MyDate smallRange=arrivingTimeOfOther.generateNewDate(arrivingTimeOfOther,-60);
            MyDate bigRange=arrivingTimeOfOther.generateNewDate(arrivingTimeOfOther,60);
            if(departingTimeOfF.weekday==7&arrivingTimeOfOther.weekday==1){
                int minutes=MyDate.timeDifference(departingTimeOfF,arrivingTimeOfOther);
                if(minutes<60){
                    return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " arriving at " + flight.getDestinationLocation().getName() + " on " + flight.getArrivingTime().toString1() + ".";
                }
            }
            if(departingTimeOfF.weekday==1&&arrivingTimeOfOther.weekday==7){
                int minutes=MyDate.timeDifference(arrivingTimeOfOther,departingTimeOfF);
                if(minutes<60){
                    return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " arriving at " + flight.getDestinationLocation().getName() + " on " + flight.getArrivingTime().toString1() + ".";
                }
            }
            else if(departingTimeOfF.isGreaterThan(smallRange)&&departingTimeOfF.isLessThan(bigRange)){
                return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " arriving at " + flight.getDestinationLocation().getName() + " on " + flight.getArrivingTime().toString1() + ".";
            }
        }
        return null;// no clash
    }

    /**
     * Check to see if Flight f can arrive at this location.
     * A conflict is determined by if any other flights are arriving or departing at this location within an hour of this flight's arrival time.
     *
     * @param f The flight to check.
     * @return String representing the clashing flight, or null if there is no clash. Eg. "Flight <id> [departing/arriving] from <name> on <clashingFlightTime>"
     */
    public String hasRunwayArrivalSpace(Flight f) {
        ArrayList<Flight> departingOfSource=f.getDestinationLocation().getDeparting();
        ArrayList<Flight> arrivingOfSource=f.getDestinationLocation().getArriving();
        Collections.sort(departingOfSource, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())){
                    return -1;
                }
                else return 1;
            }
        });
        Collections.sort(arrivingOfSource, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())){
                    return -1;
                }
                else return 1;
            }
        });
        for(Flight flight:departingOfSource){
            MyDate departingTimeOfOther=flight.getDepartureTime();
            MyDate arrivingTimeOfF=f.getArrivingTime();
            MyDate smallRange=departingTimeOfOther.generateNewDate(departingTimeOfOther,-60);
            MyDate bigRange=departingTimeOfOther.generateNewDate(departingTimeOfOther,60);
            if(departingTimeOfOther.weekday==7&&arrivingTimeOfF.weekday==1){
                int minutes=MyDate.timeDifference(departingTimeOfOther,arrivingTimeOfF);
                if(minutes<60){
                    return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " departing from " + flight.getSourceLocation().getName() + " on " + flight.getDepartureTime().toString1() + ".";
                }
            }
            if(departingTimeOfOther.weekday==1&&arrivingTimeOfF.weekday==7){
                int minutes=MyDate.timeDifference(arrivingTimeOfF,departingTimeOfOther);
                if(minutes<60){
                    return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " departing from " + flight.getSourceLocation().getName() + " on " + flight.getDepartureTime().toString1() + ".";
                }
            }
            else if(arrivingTimeOfF.isGreaterThan(smallRange)&&arrivingTimeOfF.isLessThan(bigRange)){
                return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " departing from " + flight.getSourceLocation().getName() + " on " + flight.getDepartureTime().toString1() + ".";
            }
        }
        for(Flight flight:arrivingOfSource){
            MyDate arrivingTimeOfOther=flight.getArrivingTime();
            MyDate arrivingTimeOfF=f.getArrivingTime();
            MyDate smallRange=arrivingTimeOfOther.generateNewDate(arrivingTimeOfOther,-60);
            MyDate bigRange=arrivingTimeOfOther.generateNewDate(arrivingTimeOfOther,60);
            if(arrivingTimeOfOther.weekday==7&&arrivingTimeOfF.weekday==1){
                int minutes=MyDate.timeDifference(arrivingTimeOfOther,arrivingTimeOfF);
                if(minutes<60){
                    return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " arriving at " + flight.getDestinationLocation().getName() + " on " + flight.getArrivingTime().toString1() + ".";
                }
            }
            if(arrivingTimeOfOther.weekday==1&&arrivingTimeOfF.weekday==7){
                int minutes=MyDate.timeDifference(arrivingTimeOfF,arrivingTimeOfOther);
                if(minutes<60){
                    return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " arriving at " + flight.getDestinationLocation().getName() + " on " + flight.getArrivingTime().toString1() + ".";
                }
            }
            else if(arrivingTimeOfF.isGreaterThan(smallRange)&&arrivingTimeOfF.isLessThan(bigRange)){
                return "Scheduling conflict! This flight clashes with Flight " + flight.getFlightID() + " arriving at " + flight.getDestinationLocation().getName() + " on " + flight.getArrivingTime().toString1() + ".";
            }
        }
        return null;// no clash
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getDemand() {
        return demand;
    }

    public ArrayList<Flight> getArriving() {
        Collections.sort(arriving, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getArrivingTime().isGreaterThan(f2.getArrivingTime())){
                    return 1;
                }
                else return -1;
            }
        });
        return arriving;
    }

    public ArrayList<Flight> getDeparting() {
        Collections.sort(departing, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())){
                    return 1;
                }
                else return -1;
            }
        });
        return departing;
    }

    public ArrayList<Flight> getSchedule() {
        Collections.sort(schedule, new Comparator<Flight>() {
            public int compare(Flight f1, Flight f2) {
                if(f1.getScheduleTime(getName()).isGreaterThan(f2.getScheduleTime(getName()))){
                    return 1;
                }
                else return -1;
            }
        });
        return schedule;
    }
}