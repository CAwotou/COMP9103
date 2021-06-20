package Assignment1;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class FlightScheduler {

    int flightId;
    ArrayList<Flight> flights;
    ArrayList<Location> locations;
    DecimalFormat df = new DecimalFormat("######0.00");
    boolean run = true;
    String errorMessage = null;
    String successMessage = null;

    private static FlightScheduler instance;

    public static void main(String[] args) {
        instance = new FlightScheduler(args);
        instance.run();
    }

    public static FlightScheduler getInstance() {
        return instance;
    }

    public FlightScheduler(String[] args) {
        this.flightId = 0;
        this.flights = new ArrayList<>();
        this.locations = new ArrayList<>();
    }

    public void run() {
        Scanner keyboard = new Scanner(System.in);
        while (run) {
            System.out.print("User: ");
            if (keyboard.hasNext()) {
                String commandLine = keyboard.nextLine();
                String[] commands = commandLine.split(" ");
                if (commands[0].equalsIgnoreCase("exit")) {
                    System.out.print("Application closed.");
                    run = false;
                } else if (commands[0].equalsIgnoreCase("flight") && commands.length > 1) {
                    if (commands[1].equalsIgnoreCase("add")) {
                        if (commands.length >= 7) {
                            messageOfAdd(addFlight(commands[2], commands[3], commands[4], commands[5], commands[6], 0));
                        } else if (commands.length < 7) {
                            System.out.println("Usage:   FLIGHT ADD <departure time> <from> <to> <capacity>\nExample: FLIGHT ADD Monday 18:00 Sydney Melbourne 120");
                        }
                    } else if (commands.length >= 3 && commands[2].equalsIgnoreCase("book")) {
                        if (commands.length == 3) {
                            bookFlight(commands[1], "1");
                        }
                        if (commands.length >= 4) {
                            bookFlight(commands[1], commands[3]);
                        }
                    } else if (commands.length >= 3 && commands[2].equalsIgnoreCase("remove")) {
                        removeFlight(commands[1]);
                    } else if (commands.length >= 3 && commands[2].equalsIgnoreCase("reset")) {
                        resetFlight(commands[1]);
                    } else if (commands.length >= 2 && !commands[1].equalsIgnoreCase("export") && !commands[1].equalsIgnoreCase("import")) {
                        showFlightDetail(commands[1]);
                    } else if (commands[1].equalsIgnoreCase("import")) {
                        if (commands.length == 2) {
                            System.out.println("Error reading file.");
                        } else if (commands[2] != null) {
                            importFlights(commands);
                        }
                    } else if (commands[1].equalsIgnoreCase("export")) {
                        if (commands.length == 2) {
                            System.out.println("Error writing file.");
                        } else if (commands[2] != null) {
                            exportFlights(commands[2]);
                        }
                    }
                } else if (commands[0].equalsIgnoreCase("flight") && commands.length == 1) {
                    System.out.println("Usage:\nFLIGHT <id> [BOOK/REMOVE/RESET] [num]\nFLIGHT ADD <departure time> <from> <to> <capacity>\nFLIGHT IMPORT/EXPORT <filename>");

                } else if (commands[0].equalsIgnoreCase("flights")) {
                    listFlights();
                } else if (commands[0].equalsIgnoreCase("locations")) {
                    listLocation();
                } else if (commands[0].equalsIgnoreCase("location") && commands.length > 1) {
                    if (commands[1].equalsIgnoreCase("add")) {
                        if (commands.length >= 6) {
                            messageOfAdd(addLocation(commands[2], commands[3], commands[4], commands[5]));
                        } else {
                            System.out.println("Usage:   LOCATION ADD <name> <lat> <long> <demand_coefficient>\nExample: LOCATION ADD Sydney -33.847927 150.651786 0.2");
                        }
                    } else if (commands.length == 2 && !commands[1].equalsIgnoreCase("export") && !commands[1].equalsIgnoreCase("import")) {
                        showLocationDetail(commands[1]);
                    } else if (commands[1].equalsIgnoreCase("import")) {
                        if (commands.length == 2) {
                            System.out.println("Error reading file.");
                        } else if (commands[2] != null) {
                            importLocations(commands);
                        }
                    } else if (commands[1].equalsIgnoreCase("export")) {
                        if (commands.length == 2) {
                            System.out.println("Error writing file.");
                        } else if (commands[2] != null) {
                            exportLocations(commands[2]);
                        }
                    } else {
                        System.out.println("Usage:   LOCATION ADD <name> <lat> <long> <demand_coefficient>\nExample: LOCATION ADD Sydney -33.847927 150.651786 0.2");
                    }
                } else if (commands.length == 1 && commands[0].equalsIgnoreCase("location")) {
                    System.out.println("Usage:\nLOCATION <name>\nLOCATION ADD <name> <latitude> <longitude> <demand_coefficient>\nLOCATION IMPORT/EXPORT <filename>");
                } else if (commands[0].equalsIgnoreCase("schedule")) {
                    if (commands.length == 1) {
                        System.out.println("This location does not exist in the system.");
                    } else if (commands[1] != null) {
                        showTimetable(commands[1], "schedule");
                    }
                } else if (commands[0].equalsIgnoreCase("departures")) {
                    if (commands.length == 1) {
                        System.out.println("This location does not exist in the system.");
                    } else if (commands[1] != null) {
                        showTimetable(commands[1], "departures");
                    }
                } else if (commands[0].equalsIgnoreCase("arrivals")) {
                    if (commands.length == 1) {
                        System.out.println("This location does not exist in the system.");
                    } else if (commands[1] != null) {
                        showTimetable(commands[1], "arrivals");
                    }
                } else if (commands[0].equalsIgnoreCase("travel")) {
                    if (commands.length == 3) {
                        travelInOrder(findRoute(commands[1], commands[2]), "0", "duration");
                    }
                    if (commands.length <= 2) {
                        System.out.println("Usage: TRAVEL <from> <to> [cost/duration/stopovers/layover/flight_time]");
                    } else if (commands.length == 4) {
                        if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("cost")) {
                            travelInOrder(findRoute(commands[1], commands[2]), "0", "cost");
                        } else if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("duration")) {
                            travelInOrder(findRoute(commands[1], commands[2]), "0", "duration");
                        } else if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("stopovers")) {
                            travelInOrder(findRoute(commands[1], commands[2]), "0", "stopovers");
                        } else if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("layover")) {
                            travelInOrder(findRoute(commands[1], commands[2]), "0", "layover");
                        } else if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("flight_time")) {
                            travelInOrder(findRoute(commands[1], commands[2]), "0", "flightTime");
                        } else {
                            System.out.println("Invalid sorting property: must be either cost, duration, stopovers, layover, or flight_time.");
                        }
                    } else if (commands.length >= 5) {
                        if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("cost")) {
                            travelInOrder(findRoute(commands[1], commands[2]), commands[4], "cost");
                        } else if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("duration")) {
                            travelInOrder(findRoute(commands[1], commands[2]), commands[4], "duration");
                        } else if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("stopovers")) {
                            travelInOrder(findRoute(commands[1], commands[2]), commands[4], "stopovers");
                        } else if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("layover")) {
                            travelInOrder(findRoute(commands[1], commands[2]), commands[4], "layover");
                        } else if (commands[0].equalsIgnoreCase("travel") && commands[3].equalsIgnoreCase("flight_time")) {
                            travelInOrder(findRoute(commands[1], commands[2]), commands[4], "flighttime");
                        } else {
                            System.out.println("Invalid sorting property: must be either cost, duration, stopovers, layover, or flight_time.");
                        }
                    }
                } else if (commands[0].equalsIgnoreCase("help")) {
                    System.out.println("FLIGHTS - list all available flights ordered by departure time, then departure location name");
                    System.out.println("FLIGHT ADD <departure time> <from> <to> <capacity> - add a flight");
                    System.out.println("FLIGHT IMPORT/EXPORT <filename> - import/export flights to csv file");
                    System.out.println("FLIGHT <id> - view information about a flight (from->to, departure arrival times, current ticket price, capacity, passengers booked)");
                    System.out.println("FLIGHT <id> BOOK <num> - book a certain number of passengers for the flight at the current ticket price, and then adjust the ticket price to reflect the reduced capacity remaining. If no number is given, book 1 passenger. If the given number of bookings is more than the remaining capacity, only accept bookings until the capacity is full.");
                    System.out.println("FLIGHT <id> REMOVE - remove a flight from the schedule");
                    System.out.println("FLIGHT <id> RESET - reset the number of passengers booked to 0, and the ticket price to its original state.");
                    System.out.println();
                    System.out.println("LOCATIONS - list all available locations in alphabetical order");
                    System.out.println("LOCATION ADD <name> <lat> <long> <demand_coefficient> - add a location");
                    System.out.println("LOCATION <name> - view details about a location (it's name, coordinates, demand coefficient)");
                    System.out.println("LOCATION IMPORT/EXPORT <filename> - import/export locations to csv file");
                    System.out.println("SCHEDULE <location_name> - list all departing and arriving flights, in order of the time they arrive/depart");
                    System.out.println("DEPARTURES <location_name> - list all departing flights, in order of departure time");
                    System.out.println("ARRIVALS <location_name> - list all arriving flights, in order of arrival time");
                    System.out.println();
                    System.out.println("TRAVEL <from> <to> [sort] [n] - list the nth possible flight route between a starting location and destination, with a maximum of 3 stopovers. Default ordering is for shortest overall duration. If n is not provided, display the first one in the order. If n is larger than the number of flights available, display the last one in the ordering.");
                    System.out.println();
                    System.out.println("can have other orderings:");
                    System.out.println("TRAVEL <from> <to> cost - minimum current cost");
                    System.out.println("TRAVEL <from> <to> duration - minimum total duration");
                    System.out.println("TRAVEL <from> <to> stopovers - minimum stopovers");
                    System.out.println("TRAVEL <from> <to> layover - minimum layover time");
                    System.out.println("TRAVEL <from> <to> flight_time - minimum flight time");
                    System.out.println();
                    System.out.println("HELP - outputs this help string.");
                    System.out.println("EXIT - end the program.");
                } else {
                    System.out.println("Invalid command. Type 'help' for a list of commands.");
                }
            }
            System.out.println();
        }
        // Do not use System.exit() anywhere in your code,
        // otherwise it will also exit the auto test suite.
        // Also, do not use static attributes otherwise
        // they will maintain the same values between testcases.

        // START YOUR CODE HERE
    }

    /**
     * export flights into csv
     *
     * @param name file name
     */
    public void exportFlights(String name) {
        Collections.sort(flights, new Comparator<Flight>() {
            @Override
            public int compare(Flight f1, Flight f2) {
                if (f1.getFlightID() > f2.getFlightID()) {
                    return 1;
                }
                return -1;
            }
        });
        int count = 0;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(name));
            for (Flight flight : flights) {
                String s = flight.getDepartureTime().toString1() + "," + flight.getSourceLocation().getName() + "," + flight.getDestinationLocation().getName() + "," + flight.getCapacity() + "," + flight.getBooked() + "\n";
                out.write(s);
                count++;
            }
            out.close();
            System.out.println("Exported " + count + " flights.");
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }


    /**
     * export locations into csv
     *
     * @param name file name
     */
    public void exportLocations(String name) {
        int count = 0;
        Collections.sort(locations, new Comparator<Location>() {
            @Override
            public int compare(Location l1, Location l2) {
                String s1 = l1.getName();
                String s2 = l2.getName();
                return s1.compareTo(s2);
            }
        });
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(name));
            for (Location location : locations) {
                String s = location.getName() + "," + location.getLatitude() + "," + location.getLongitude() + "," + location.getDemand() + "\n";
                out.write(s);
                count++;
            }
            out.close();
            System.out.println("Exported " + count + " locations.");
        } catch (IOException e) {
            System.out.println("Error writing file.");
            ;
        }
    }

    /**
     * command "flights"
     * show all flights
     */
    public void listFlights() {
        Collections.sort(flights, new Comparator<Flight>() {
            @Override
            public int compare(Flight f1, Flight f2) {
                if (f1.getDepartureTime().isGreaterThan(f2.getDepartureTime())) {
                    return 1;
                }
                if (f1.getDepartureTime().isEqualTo(f2.getDepartureTime())) {
                    return f1.getSourceLocation().getName().compareTo(f2.getSourceLocation().getName());
                } else return -1;
            }
        });
        System.out.println("Flights");
        System.out.println("-------------------------------------------------------");
        System.out.println("ID   Departure   Arrival     Source --> Destination");
        System.out.println("-------------------------------------------------------");
        if (flights.size() == 0) {
            System.out.println("(None)");
            return;
        }
        for (Flight f : flights) {
            System.out.printf("%4d", f.getFlightID());
            System.out.print(" ");
            System.out.print(f.getDepartureTime().toString() + "   ");
            System.out.print(f.getArrivingTime().toString() + "   ");
            System.out.print(f.getSourceLocation().getName() + " --> ");
            System.out.println(f.getDestinationLocation().getName());
        }
    }

    /**
     * command "locations"
     * show all locations
     */
    public void listLocation() {
        Collections.sort(locations, new Comparator<Location>() {
            @Override
            public int compare(Location l1, Location l2) {
                String s1 = l1.getName();
                String s2 = l2.getName();
                return s1.compareTo(s2);
            }
        });
        int i = 1;
        System.out.println("Locations " + "(" + locations.size() + "):");
        if (locations.size() == 0) {
            System.out.println("(None)");
        } else {
            for (Location l : locations) {
                if (i < locations.size()) {
                    System.out.print(l.getName() + ", ");
                    i++;
                } else {
                    System.out.println(l.getName());
                }
            }
        }
    }

    /**
     * remove a flight according to ID
     *
     * @param ID ID of a Flight in String type
     */
    public void removeFlight(String ID) {
        int id = -1;
        try {
            id = Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Flight ID.");
            return;
        }
        if (id < 0) {
            System.out.println("Invalid Flight ID.");
            ;
            return;
        }
        Iterator it = flights.iterator();
        while (it.hasNext()) {
            Flight f = (Flight) it.next();
            f.getSourceLocation().getDeparting().remove(f);
            f.getSourceLocation().getSchedule().remove(f);
            f.getDestinationLocation().getArriving().remove(f);
            f.getDestinationLocation().getSchedule().remove(f);
            if (f.getFlightID() == id) {
                System.out.println("Removed Flight " + f.getFlightID() + ", " + f.getDepartureTime().toString() + " " + f.getSourceLocation().getName() + " --> " + f.getDestinationLocation().getName() + ", from the flight schedule.");
                it.remove();
                return;
            }
        }
        System.out.println("Invalid Flight ID.");
    }

    /**
     * show all information of a certain flight
     *
     * @param ID ID of a Flight in String type
     */
    public void showFlightDetail(String ID) {
        int id = -1;
        try {
            id = Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Flight ID.");
            return;
        }
        for (Flight f : flights) {
            if (f.getFlightID() == id) {
                System.out.println("Flight " + f.getFlightID());
                System.out.println("Departure:    " + f.getDepartureTime().toString() + " " + f.getSourceLocation().getName());
                System.out.println("Arrival:      " + f.getDepartureTime().generateNewDate(f.getDepartureTime(), f.getDuration()) + " " + f.getDestinationLocation().getName());
                System.out.print("Distance:     ");
                System.out.printf("%,d", Math.round(f.getDistance()));
                System.out.print("km");
                System.out.println();
                System.out.println("Duration:     " + f.getDuration() / 60 + "h " + f.getDuration() % 60 + "m");
                System.out.println("Ticket Cost:  " + "$" + df.format(f.getTicketPrice()));
                System.out.println("Passengers:   " + f.getBooked() + "/" + f.getCapacity());
                return;
            }
        }
        System.out.println("Invalid Flight ID.");
    }

    /**
     * set a flight's booked seats to 0
     *
     * @param ID ID of a Flight in String type
     */
    public void resetFlight(String ID) {
        int id;
        try {
            id = Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Flight ID.");
            return;
        }
        Iterator it = flights.iterator();
        while (it.hasNext()) {
            Flight f = (Flight) it.next();
            if (f.getFlightID() == id) {
                System.out.println("Reset passengers booked to 0 for Flight " + f.getFlightID() + ", " + f.getDepartureTime().toString() + " " + f.getSourceLocation().getName() + " --> " + f.getDestinationLocation().getName() + ".");
                f.setBooked(0);
                return;
            }
        }
        System.out.println("Invalid Flight ID.");
    }

    /**
     * Book a certain number of seats
     *
     * @param ID     ID of a Flight in String type
     * @param number The number of tickets to be booked
     * @return cost
     */
    public double bookFlight(String ID, String number) {
        int id = -1, num = -1;
        try {
            id = Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Flight ID.");
            return -2;
        }
        try {
            num = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of passengers to book.");
            return -2;
        }
        if (id < 0) {
            System.out.println("Invalid Flight ID.");
            return -1;
        }
        if (num <= 0) {
            System.out.println("Invalid number of passengers to book.");
        }
        Iterator it = flights.iterator();
        while (it.hasNext()) {
            Flight f = (Flight) it.next();
            if (f.getFlightID() == id) {
                int booked = f.getBooked();
                if (booked == Integer.parseInt(f.getCapacity())) {
                    System.out.println("Booked 0 passengers on flight 1 for a total cost of $0.00");
                    System.out.println("Flight is now full.");
                    return -1;//full
                }
                if (booked < Integer.parseInt(f.getCapacity()) && (booked + num) >= Integer.parseInt(f.getCapacity())) {
                    num = Integer.parseInt(f.getCapacity()) - booked;
                    double cost = f.book(num);
                    System.out.println("Booked " + num + " passengers on flight " + f.getFlightID() + " for a total cost of $" + df.format(cost));
                    if (f.isFull()) {
                        System.out.println("Flight is now full.");
                    }
                    return 1;
                } else {
                    double cost = f.book(num);
                    System.out.println("Booked " + num + " passengers on flight " + f.getFlightID() + " for a total cost of $" + df.format(cost));
                    if (f.isFull()) {
                        System.out.println("Flight is now full.");
                    }
                    return 1;
                }
            }
        }
        System.out.println("Invalid Flight ID.");
        return -2;//error
    }

    /**
     * show all information of a certain location
     *
     * @param name the name of a location
     */
    public void showLocationDetail(String name) {
        Iterator it = locations.iterator();
        while ((it.hasNext())) {
            Location l = (Location) it.next();
            if (l.getName().equalsIgnoreCase(name)) {
                String lat = String.format("%.6f", l.getLatitude());
                String lon = String.format("%.6f", l.getLongitude());
                String demand = String.format("%.4f", l.getDemand());
                System.out.println("Location:    " + l.getName());
                if (l.getLatitude() < 0) {
                    System.out.println("Latitude:    " + lat);
                }
                if (l.getLatitude() >= 0) {
                    System.out.println("Latitude:    " + lat);
                }
                if (l.getLongitude() < 0) {
                    System.out.println("Longitude:   " + lon);
                }
                if (l.getLongitude() >= 0) {
                    System.out.println("Longitude:   " + lon);
                }
                if (l.getDemand() >= 0) {
                    System.out.println("Demand:      +" + demand);
                }
                if (l.getDemand() < 0) {
                    System.out.println("Demand:      " + demand);
                }
                return;
            }
        }
        System.out.println("Invalid location name.");
    }

    /**
     * command "schedule", "departures", "arrivals" + location name
     *
     * @param name name of a certain location
     * @param type "schedule", "departures", "arrivals"
     */
    public void showTimetable(String name, String type) {
        if (findLocation(name) == null) {
            System.out.println("This location does not exist in the system.");
        }
        Iterator it = locations.iterator();
        while ((it.hasNext())) {
            Location l = (Location) it.next();
            if (l.getName().equalsIgnoreCase(name)) {
                System.out.println(l.getName());
                System.out.println("-------------------------------------------------------");
                System.out.println("ID   Time        Departure/Arrival to/from Location");
                System.out.println("-------------------------------------------------------");
                Iterator iterator = null;
                if (type.equalsIgnoreCase("schedule")) {
                    iterator = l.getSchedule().iterator();
                }
                if (type.equalsIgnoreCase("arrivals")) {
                    iterator = l.getArriving().iterator();
                }
                if (type.equalsIgnoreCase("departures")) {
                    iterator = l.getDeparting().iterator();
                }
                while (iterator.hasNext()) {
                    Flight f = (Flight) iterator.next();
                    System.out.printf("%4d", f.getFlightID());
                    System.out.print(" ");
                    if (f.getSourceLocation().getName().equalsIgnoreCase(name)) {
                        System.out.print(f.getDepartureTime().toString());
                        System.out.print("   ");
                        System.out.print("Departure to ");
                        System.out.println(f.getDestinationLocation().getName());
                    } else {
                        System.out.print(f.getArrivingTime().toString());
                        System.out.print("   ");
                        System.out.print("Arrival from ");
                        System.out.println(f.getSourceLocation().getName());
                    }
                }
            }
        }
    }

    /**
     * Output routes according to different conditions and order
     *
     * @param possibleRoute All possible routes
     * @param No            which route
     * @param type          "cost", "duration", "layover", "stopovers", "flight_time"
     */
    public void travelInOrder(ArrayList<Route> possibleRoute, String No, String type) {
        if (possibleRoute == null || possibleRoute.size() == 0) {
            return;
        }
        int id = 0;
        try {
            id = Integer.parseInt(No);
        } catch (NumberFormatException e) {
            id = 0;
        }
        if (id >= possibleRoute.size()) {
            id = possibleRoute.size() - 1;
        }
        if (id < 0) {
            id = 0;
        }
        if (type.equalsIgnoreCase("cost")) {
            Collections.sort(possibleRoute, new Comparator<Route>() {
                public int compare(Route r1, Route r2) {
                    if (r1.getCost() > r2.getCost()) {
                        return 1;
                    }
                    if (r1.getCost() == r2.getCost()) {
                        if (r1.getDuration() > r2.getDuration()) {
                            return 1;
                        }
                    }
                    return -1;
                }
            });
        }
        if (type.equalsIgnoreCase("duration")) {
            Collections.sort(possibleRoute, new Comparator<Route>() {
                public int compare(Route r1, Route r2) {
                    if (r1.getDuration() > r2.getDuration()) {
                        return 1;
                    }
                    if (r1.getDuration() == r2.getDuration()) {
                        if (r1.getCost() > r2.getCost()) {
                            return 1;
                        }
                    }
                    return -1;
                }
            });
        }
        if (type.equalsIgnoreCase("stopovers")) {
            Collections.sort(possibleRoute, new Comparator<Route>() {
                public int compare(Route r1, Route r2) {
                    if (r1.getStopovers() > r2.getStopovers()) {
                        return 1;
                    }
                    if (r1.getStopovers() == r2.getStopovers()) {
                        if (r1.getDuration() > r2.getDuration()) {
                            return 1;
                        }
                        if (r1.getDuration() == r2.getDuration()) {
                            if (r1.getCost() > r2.getCost()) {
                                return 1;
                            }
                        }
                    }
                    return -1;
                }
            });
        }
        if (type.equalsIgnoreCase("layover")) {
            Collections.sort(possibleRoute, new Comparator<Route>() {
                public int compare(Route r1, Route r2) {
                    if (r1.getLayover() > r2.getLayover()) {
                        return 1;
                    }
                    if (r1.getLayover() == r2.getLayover()) {
                        if (r1.getDuration() > r2.getDuration()) {
                            return 1;
                        }
                        if (r1.getDuration() == r2.getDuration()) {
                            if (r1.getCost() > r2.getCost()) {
                                return 1;
                            }
                        }
                    }
                    return -1;
                }
            });
        }
        if (type.equalsIgnoreCase("flightTime")) {
            Collections.sort(possibleRoute, new Comparator<Route>() {
                public int compare(Route r1, Route r2) {
                    if (r1.getFlightTime() > r2.getFlightTime()) {
                        return 1;
                    }
                    if (r1.getFlightTime() == r2.getFlightTime()) {
                        if (r1.getDuration() > r2.getDuration()) {
                            return 1;
                        }
                        if (r1.getDuration() == r2.getDuration()) {
                            if (r1.getCost() > r2.getCost()) {
                                return 1;
                            }
                        }
                    }
                    return -1;
                }
            });
        }
        Route r = possibleRoute.get(id);
        System.out.println("Legs:             " + (int) (r.getStopovers() + 1));
        System.out.println("Total Duration:   " + (int) r.getDuration() / 60 + "h " + (int) r.getDuration() % 60 + "m");
        System.out.println("Total Cost:       $" + df.format(r.getCost()));
        System.out.println("-------------------------------------------------------------");
        System.out.println("ID   Cost      Departure   Arrival     Source --> Destination");
        System.out.println("-------------------------------------------------------------");
        for (int i = 0; i < r.flightList.size(); i++) {
            Flight f = r.flightList.get(i);
            System.out.printf("%4d", f.getFlightID());
            System.out.print(" $ ");
            System.out.printf("%8s", df.format(f.getTicketPrice()) + " ");
            System.out.print(f.getDepartureTime().toString() + "   ");
            System.out.print(f.getArrivingTime().toString() + "   ");
            System.out.print(f.getSourceLocation().getName() + " --> ");
            System.out.println(f.getDestinationLocation().getName());
            if (i < r.flightList.size() - 1) {
                System.out.println("LAYOVER " + (int) (Flight.layover(r.flightList.get(i), r.flightList.get(i + 1))) / 60 + "h " + (int) (Flight.layover(r.flightList.get(i), r.flightList.get(i + 1))) % 60 + "m" + " at " + f.getDestinationLocation().getName());
            }
        }
    }


    /**
     * find all possible route between two locations
     *
     * @param start the name of start location
     * @param end   the name of end location
     * @return all possible route
     */
    public ArrayList<Route> findRoute(String start, String end) {
        if (findLocation(start) == null) {
            System.out.println("Starting location not found.");
            return null;
        }
        if (findLocation(end) == null) {
            System.out.println("Ending location not found.");
            return null;
        }
        ArrayList<Route> allRoute = new ArrayList<>();
        ArrayList<ArrayList<Flight>> possibleRoute = new ArrayList<>();
        helperForFind(possibleRoute, new ArrayList<>(), start, end, 0);
        for (ArrayList<Flight> route : possibleRoute) {
            allRoute.add(new Route(route));
        }
        if (allRoute == null || allRoute.size() == 0) {
            start = start.substring(0, 1).toUpperCase() + start.substring(1);
            end = end.substring(0, 1).toUpperCase() + end.substring(1);
            System.out.println("Sorry, no flights with 3 or less stopovers are available from " + findLocation(start).getName() + " to " + findLocation(end).getName() + ".");
        }
        return allRoute;
    }

    /**
     * Look for potential routes
     *
     * @param possibleRoute store all possible routes
     * @param route         Stores flight information for a route
     * @param start         name of start location
     * @param end           name of end location
     * @param count         stores the number of stopovers
     * @return
     */
    public void helperForFind(ArrayList<ArrayList<Flight>> possibleRoute, ArrayList<Flight> route, String start, String end, int count) {
        if (route.size() > 0) {
            if (route.get(route.size() - 1).getDestinationLocation().getName().equalsIgnoreCase(end)) {
                possibleRoute.add(new ArrayList<>(route));
                return;
            }
        }
        Iterator it = flights.iterator();
        while ((it.hasNext()) && route.size() < 4) {
            Flight f = (Flight) it.next();
            if (f.getSourceLocation().getName().equalsIgnoreCase(start)) {
                count++;
                route.add(f);
                helperForFind(possibleRoute, route, f.getDestinationLocation().getName(), end, count);
                route.remove(route.size() - 1);
                count--;
            }
        }
    }

    /**
     * Look for a exist location
     *
     * @param name the name of location which need to be found in the list
     * @return a location
     */
    public Location findLocation(String name) {
        for (Location l : locations) {
            if (name.equalsIgnoreCase(l.getName())) {
                return l;
            }
        }
        return null;
    }

    /**
     * Output some error or success messages
     *
     * @param code codes with different meaning
     */
    public void messageOfAdd(int code) {
        if (code == -1) {
            System.out.println("Invalid departure time. Use the format <day_of_week> <hour:minute>, with 24h time.");
        }
        if (code == -2) {
            System.out.println("Invalid starting location.");
        }
        if (code == -3) {
            System.out.println("Invalid ending location.");
        }
        if (code == -4) {
            System.out.println("Invalid positive integer capacity.");
        }
        if (code == -5) {
            System.out.println("Source and destination cannot be the same place.");
        }
        if (code == -6 || code == -7) {
            System.out.println(errorMessage);
        }
        if (code == -8) {
            System.out.println("This location already exists.");
        }
        if (code == -9) {
            System.out.println("Invalid latitude. It must be a number of degrees between -85 and +85.");
        }
        if (code == -10) {
            System.out.println("Invalid longitude. It must be a number of degrees between -180 and +180.");
        }
        if (code == -11) {
            System.out.println("Invalid demand coefficient. It must be a number between -1 and +1.");
        }
        if (code == 0) {
            System.out.println(successMessage);
        }

    }

    // Add a flight to the database
    // handle error cases and return status negative if error
    // (different status codes for different messages)
    // do not print out anything in this function
    public int addFlight(String date1, String date2, String start, String end, String capacity, int booked) {
        int hour = -1, minute = -1;
        try {
            hour = Integer.parseInt(date2.split(":")[0]);
            minute = Integer.parseInt(date2.split(":")[1]);
        } catch (Exception e) {
            //System.out.println("Invalid departure time. Use the format <day_of_week> <hour:minute>, with 24h time.");
            return -1;
        }
        int weekday = -1;
        for (int i = 1; i <= 7; i++) {
            if (date1.equalsIgnoreCase("Monday")) {
                weekday = 1;
            }
            if (date1.equalsIgnoreCase("Tuesday")) {
                weekday = 2;
            }
            if (date1.equalsIgnoreCase("Wednesday")) {
                weekday = 3;
            }
            if (date1.equalsIgnoreCase("Thursday")) {
                weekday = 4;
            }
            if (date1.equalsIgnoreCase("Friday")) {
                weekday = 5;
            }
            if (date1.equalsIgnoreCase("Saturday")) {
                weekday = 6;
            }
            if (date1.equalsIgnoreCase("Sunday")) {
                weekday = 7;
            }
        }
        if (weekday == -1) {
            //System.out.println("Invalid departure time. Use the format <day_of_week> <hour:minute>, with 24h time.");
            return -1;//time error
        }
        MyDate date = new MyDate(weekday, hour, minute);
        if (!checkTimeOfAddFlight(date1)) {
            //System.out.println("Invalid departure time. Use the format <day_of_week> <hour:minute>, with 24h time.");
            return -1;//time error
        }
        if (!checkExistLocation(start)) {
            //System.out.println("Invalid starting location.");
            return -2;//start error
        }
        if (!checkExistLocation(end)) {
            //System.out.println("Invalid ending location.");
            return -3;//end error
        }
        try {
            if (Integer.parseInt(capacity) <= 0) {
                //System.out.println("Invalid positive integer capacity.");
                return -4;//capacity error
            }
        } catch (Exception e) {
            //System.out.println("Invalid positive integer capacity.");
            return -4;//capacity error
        }
        if (start.equalsIgnoreCase(end)) {
            //System.out.println("Source and destination cannot be the same place.");
            return -5;//same place
        }
        Flight flight = new Flight(-1, date, this.findLocation(start), this.findLocation(end), capacity, booked);
        if (flight.getSourceLocation().hasRunwayDepartureSpace(flight) != null) {
            errorMessage = flight.getSourceLocation().hasRunwayDepartureSpace(flight);
            return -6;
        }
        if (flight.getDestinationLocation().hasRunwayArrivalSpace(flight) != null) {
            errorMessage = flight.getDestinationLocation().hasRunwayArrivalSpace(flight);
            return -7;//no runway when arriving
        }
        flight.setFlightID(flightId++);
        flights.add(flight);
        flight.getSourceLocation().addDeparture(flight);
        flight.getDestinationLocation().addArrival(flight);
        successMessage = "Successfully added Flight " + flight.getFlightID() + ".";
        return 0;//success
    }


    /**
     * Check time format
     *
     * @param date1 the input time from users
     * @return true of false, whether the format is correct
     */
    public boolean checkTimeOfAddFlight(String date1) {
        if (date1.equalsIgnoreCase("monday") ||
                date1.equalsIgnoreCase("tuesday") ||
                date1.equalsIgnoreCase("wednesday") ||
                date1.equalsIgnoreCase("thursday") ||
                date1.equalsIgnoreCase("friday") ||
                date1.equalsIgnoreCase("sunday") ||
                date1.equalsIgnoreCase("saturday")
        ) {
            return true;
        }
        return false;
    }

    /**
     * Check if a location exists
     *
     * @param location the input name of a location from users
     * @return true of false, whether the location exists
     */
    public boolean checkExistLocation(String location) {
        for (Location existLocation : locations) {
            if (location.equalsIgnoreCase(existLocation.getName())) {
                return true;
            }
        }
        return false;
    }

    // Add a location to the database
    // do not print out anything in this function
    // return negative numbers for error cases
    public int addLocation(String name, String lat, String lon, String demand) {
        double latitude, longitude, demandCoefficient;
        if (checkExistLocation(name)) {
            //System.out.println("This location already exists.");
            return -8;//location already exist
        }
        try {
            latitude = Double.parseDouble(lat);
        } catch (Exception e) {
            //System.out.println("Invalid latitude. It must be a number of degrees between -85 and +85.");
            return -9;//lat error
        }
        if (latitude < -85 || latitude > 85) {
            //System.out.println("Invalid latitude. It must be a number of degrees between -85 and +85.");
            return -9;//lat error
        }
        try {
            longitude = Double.parseDouble(lon);
        } catch (Exception e) {
            //System.out.println("Invalid longitude. It must be a number of degrees between -180 and +180.");
            return -10;//lon error
        }
        if (longitude < -180 || longitude > 180) {
            //System.out.println("Invalid longitude. It must be a number of degrees between -180 and +180.");
            return -10;//lon error
        }
        try {
            demandCoefficient = Double.parseDouble(demand);
        } catch (Exception e) {
            //System.out.println("Invalid demand coefficient. It must be a number between -1 and +1.");
            return -11;//demand error
        }
        if (demandCoefficient < -1 || demandCoefficient > 1) {
            //System.out.println("Invalid demand coefficient. It must be a number between -1 and +1.");
            return -11;//demand error
        }
        Location location = new Location(name, latitude, longitude, demandCoefficient);
        locations.add(location);
        successMessage = "Successfully added location " + location.getName() + ".";
        return 0;//success
    }


    //flight import <filename>
    public void importFlights(String[] command) {
        try {
            if (command.length < 3) throw new FileNotFoundException();
            BufferedReader br = new BufferedReader(new FileReader(new File(command[2])));
            String line;
            int count = 0;
            int err = 0;

            while ((line = br.readLine()) != null) {
                String[] lparts = line.split(",");
                if (lparts.length < 5) continue;
                String[] dparts = lparts[0].split(" ");
                if (dparts.length < 2) continue;
                int booked = 0;

                try {
                    booked = Integer.parseInt(lparts[4]);

                } catch (NumberFormatException e) {
                    continue;
                }

                int status = addFlight(dparts[0], dparts[1], lparts[1], lparts[2], lparts[3], booked);
                if (status < 0) {
                    err++;
                    continue;
                }
                count++;
            }
            br.close();
            System.out.println("Imported " + count + " flight" + (count != 1 ? "s" : "") + ".");
            if (err > 0) {
                if (err == 1) System.out.println("1 line was invalid.");
                else System.out.println(err + " lines were invalid.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }
    }

    //location import <filename>
    public void importLocations(String[] command) {
        try {
            if (command.length < 3) throw new FileNotFoundException();
            BufferedReader br = new BufferedReader(new FileReader(new File(command[2])));
            String line;
            int count = 0;
            int err = 0;

            while ((line = br.readLine()) != null) {
                String[] lparts = line.split(",");
                if (lparts.length < 4) continue;
                int status = addLocation(lparts[0], lparts[1], lparts[2], lparts[3]);
                if (status < 0) {
                    err++;
                    continue;

                }
                count++;
            }
            br.close();
            System.out.println("Imported " + count + " location" + (count != 1 ? "s" : "") + ".");
            if (err > 0) {
                if (err == 1) System.out.println("1 line was invalid.");
                else System.out.println(err + " lines were invalid.");
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }
    }
}