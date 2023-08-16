import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Node implements Comparator<Node>
{
    private Integer v;
    private Integer weight;
    
    Node(int _v, int _w) { v = _v; weight = _w; }
    
    Node() {}
    
    Integer getV() { return v; }
    Integer getWeight() { return weight; }
    
    @Override
    public int compare(Node node1, Node node2) 
    { 
        if (node1.weight < node2.weight) 
            return -1; 
        if (node1.weight > node2.weight) 
            return 1; 
        return 0; 
    } 
}
public class Flight extends FlightDistance {

    //        ************************************************************ Fields ************************************************************

    private final String flightSchedule;
    private final String flightNumber;
    private final String fromWhichCity;
    private final String gate;
    private final String toWhichCity;
    private final Double cost;
    private double distanceInMiles;
    private double distanceInKm;
    private String flightTime;
    private int numOfSeatsInTheFlight;
    private List<Customer> listOfRegisteredCustomersInAFlight;
    private int customerIndex;
    private static int nextFlightDay = 0;
    private static final List<Flight> flightList = new ArrayList<>();
    ArrayList<ArrayList<Integer>> edges= new ArrayList<ArrayList<Integer>>();
    Map<String,Integer> mp= new HashMap<>();
    Map<Integer,String> mp2= new HashMap<>();
    ArrayList<ArrayList<Node>> adj=new ArrayList<ArrayList<Node>>();
    //        ************************************************************ Behaviours/Methods ************************************************************
   //----------------------------------------------------------------------------------------
    class Solution {
        public  void check(int V,
                                  ArrayList<ArrayList<Integer>> edges) {
            for(int S=0;S<V;S++){                        
            int[] dist = new int[V];
            for (int i = 0; i < V; i++) dist[i] = (int)(1e8);
            dist[S] = 0;
            // V x E
            for (int i = 0; i < V - 1; i++) {
                for (ArrayList<Integer> it : edges) {
                    int u = it.get(0);
                    int v = it.get(1);
                    int wt = it.get(2);
                    if (dist[u] != 1e8 && dist[u] + wt < dist[v]) {
                        dist[v] = dist[u] + wt;
                    }
                }
            }
            for(int i=0;i<V;i++){
                if(mp2.get(S)==mp2.get(i)){
                    continue;
                }
                if(dist[i]>=1e8){
                    User.addToMessage(mp2.get(S)+" ------> "+mp2.get(i)+" : "+"No route");
                }
                User.addToMessage(mp2.get(S)+" ------> "+mp2.get(i)+" : "+dist[i]);
            }
            
        }
        }
    }
    //---------------------------------------------------
    Flight() {
        this.flightSchedule = null;
        this.flightNumber = null;
        this.numOfSeatsInTheFlight = 0;
        toWhichCity = null;
        fromWhichCity = null;
        this.gate = null;
        this.cost=0.0;
    }

    /**
     * Creates new random flight from the specified arguments.
     *
     * @param flightSchedule           includes departure date and time of flight
     * @param flightNumber             unique identifier of each flight
     * @param numOfSeatsInTheFlight    available seats in the flight
     * @param chosenDestinations       consists of origin and destination airports(cities)
     * @param distanceBetweenTheCities gives the distance between the airports both in miles and kilometers
     * @param gate                     from where passengers will board to the aircraft
     */
    
    Flight(String flightSchedule, String flightNumber, int numOfSeatsInTheFlight, String[][] chosenDestinations, String[] distanceBetweenTheCities, String gate,Double cost) {
        this.flightSchedule = flightSchedule;
        this.flightNumber = flightNumber;
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
        this.fromWhichCity = chosenDestinations[0][0];
        this.toWhichCity = chosenDestinations[1][0];
        this.distanceInMiles = Double.parseDouble(distanceBetweenTheCities[0]);
        this.distanceInKm = Double.parseDouble(distanceBetweenTheCities[1]);
        this.flightTime = calculateFlightTime(distanceInMiles);
        this.listOfRegisteredCustomersInAFlight = new ArrayList<>();
        this.gate = gate;
        this.cost=cost;
        
    }

    /**
     * Creates Flight Schedule. All methods of this class are collaborating with each other
     * to create flight schedule of the said length in this method.
     */
    public void flightScheduler() {
        int numOfFlights = 4;              // decides how many unique flights to be included/display in scheduler
        RandomGenerator r1 = new RandomGenerator();
        Integer idx=0;
        for (int i = 0; i < numOfFlights; i++) {
            String[][] chosenDestinations = r1.randomDestinations();
            String[] distanceBetweenTheCities = calculateDistance(Double.parseDouble(chosenDestinations[0][1]), Double.parseDouble(chosenDestinations[0][2]), Double.parseDouble(chosenDestinations[1][1]), Double.parseDouble(chosenDestinations[1][2]));
            String flightSchedule = createNewFlightsAndTime();
            String flightNumber = r1.randomFlightNumbGen(2, 1).toUpperCase();
            int numOfSeatsInTheFlight = r1.randomNumOfSeats();
            String gate = r1.randomFlightNumbGen(1, 30);
            Random r = new Random();
            Double cost = 5000 + (100000 - 5000) * r.nextDouble();
            String fromWhichCity = chosenDestinations[0][0];
        String toWhichCity = chosenDestinations[1][0];
            if(!mp.containsKey(fromWhichCity)){
                mp.put(fromWhichCity, idx);
                mp2.put(idx,fromWhichCity);
                idx=idx+1;
            }
            if(!mp.containsKey(toWhichCity)){
                mp.put(toWhichCity, idx);
                mp2.put(idx,toWhichCity);
                idx=idx+1;
            }
        edges.add(new ArrayList<Integer>(Arrays.asList(mp.get(fromWhichCity),mp.get(toWhichCity),cost.intValue())));
            flightList.add(new Flight(flightSchedule, flightNumber, numOfSeatsInTheFlight, chosenDestinations, distanceBetweenTheCities, gate.toUpperCase(),cost));
        }
    }

    /**
     * Registers new Customer in this Flight.
     *
     * @param customer customer to be registered
     */
    void addNewCustomerToFlight(Customer customer) {
        this.listOfRegisteredCustomersInAFlight.add(customer);
    }

    /**
     * Adds numOfTickets to existing customer's tickets for the this flight.
     *
     * @param customer     customer in which tickets are to be added
     * @param numOfTickets number of tickets to add
     */
    void addTicketsToExistingCustomer(Customer customer, int numOfTickets) {
        customer.addExistingFlightToCustomerList(customerIndex, numOfTickets);
    }

    /***
     * Checks if the specified customer is already registered in the FLight's array list
     * @param customersList of the flight
     * @param customer specified customer to be checked
     * @return true if the customer is already registered in the said flight, false otherwise
     */
    boolean isCustomerAlreadyAdded(List<Customer> customersList, Customer customer) {
        boolean isAdded = false;
        for (Customer customer1 : customersList) {
            if (customer1.getUserID().equals(customer.getUserID())) {
                isAdded = true;
                customerIndex = customersList.indexOf(customer1);
                break;
            }
        }
        return isAdded;
    }

    /**
     * Calculates the flight time, using avg. ground speed of 450 knots.
     *
     * @param distanceBetweenTheCities distance between the cities/airports in miles
     * @return formatted flight time
     */
    public String calculateFlightTime(double distanceBetweenTheCities) {
        double groundSpeed = 450;
        double time = (distanceBetweenTheCities / groundSpeed);
        String timeInString = String.format("%.4s", time);
        String[] timeArray = timeInString.replace('.', ':').split(":");
        int hours = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);
        int modulus = minutes % 5;
        // Changing flight time to make minutes near/divisible to 5.
        if (modulus < 3) {
            minutes -= modulus;
        } else {
            minutes += 5 - modulus;
        }
        if (minutes >= 60) {
            minutes -= 60;
            hours++;
        }
        if (hours <= 9 && Integer.toString(minutes).length() == 1) {
            return String.format("0%s:%s0", hours, minutes);
        } else if (hours <= 9 && Integer.toString(minutes).length() > 1) {
            return String.format("0%s:%s", hours, minutes);
        } else if (hours > 9 && Integer.toString(minutes).length() == 1) {
            return String.format("%s:%s0", hours, minutes);
        } else {
            return String.format("%s:%s", hours, minutes);
        }
    }

    /**
     * Creates flight arrival time by adding flight time to flight departure time
     *
     * @return flight arrival time
     */
    public String fetchArrivalTime() {
        /*These lines convert the String of flightSchedule to LocalDateTIme and add the arrivalTime to it....*/
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a ");
        LocalDateTime departureDateTime = LocalDateTime.parse(flightSchedule, formatter);

        /*Getting the Flight Time, plane was in air*/
        String[] flightTime = getFlightTime().split(":");
        int hours = Integer.parseInt(flightTime[0]);
        int minutes = Integer.parseInt(flightTime[1]);


        LocalDateTime arrivalTime;

        arrivalTime = departureDateTime.plusHours(hours).plusMinutes(minutes);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EE, dd-MM-yyyy HH:mm a");
        return arrivalTime.format(formatter1);

    }

    void deleteFlight(String flightNumber) {
        boolean isFound = false;
        Iterator<Flight> list = flightList.iterator();
        while (list.hasNext()) {
            Flight flight = list.next();
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                isFound = true;
                break;
            }
        }
        if (isFound) {
            list.remove();
        } else {
            User.addToMessage("Flight with given Number not found...");
        }
        displayFlightSchedule();
        
    }

    /**
     * Calculates the distance between the cities/airports based on their lat longs.
     *
     * @param lat1 origin city/airport latitude
     * @param lon1 origin city/airport longitude
     * @param lat2 destination city/airport latitude
     * @param lon2 destination city/airport longitude
     * @return distance both in miles and km between the cities/airports
     */
    @Override
    public String[] calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double distance = Math.sin(degreeToRadian(lat1)) * Math.sin(degreeToRadian(lat2)) + Math.cos(degreeToRadian(lat1)) * Math.cos(degreeToRadian(lat2)) * Math.cos(degreeToRadian(theta));
        distance = Math.acos(distance);
        distance = radianToDegree(distance);
        distance = distance * 60 * 1.1515;
        /* On the Zero-Index, distance will be in Miles, on 1st-index, distance will be in KM and on the 2nd index distance will be in KNOTS*/
        String[] distanceString = new String[3];
        distanceString[0] = String.format("%.2f", distance * 0.8684);
        distanceString[1] = String.format("%.2f", distance * 1.609344);
        distanceString[2] = Double.toString(Math.round(distance * 100.0) / 100.0);
        return distanceString;
    }

    private double degreeToRadian(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double radianToDegree(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void displayFlightSchedule() {
        User.addToMessage("the edges size is:"+edges.size());
        Iterator<Flight> flightIterator = flightList.iterator();
        User.addToMessage();
        //System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        User.addToMessage("| Num  | FLIGHT NO | Available Seats | FROM ====>> | ====>> TO | ARRIVAL TIME | FLIGHT TIME |  GATE  | DISTANCE(MILES/KMS) | COST ");
        //System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        int i = 0;
        while (flightIterator.hasNext()) {
            i++;
            Flight f1 = flightIterator.next();
            User.addToMessage(f1.toString(i));
            //System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        }
        User.addToMessage("edges:"+edges);
        Solution s= new Solution();
        s.check(mp.size(), edges);
        User.showFlights(User.message);
        for(int k=0;k<mp.size();k++){
          adj.add(new ArrayList<Node>());
        }
        for (ArrayList<Integer> it : edges) {
            int u = it.get(0);
            int v = it.get(1);
            int wt = it.get(2);
            adj.get(u).add(new Node(v,wt));
        }
        
    }
//    public void recursion(Integer src,Integer dst,ArrayList<Integer> vis,ArrayList<Integer> temp,Integer price){
//          if(src.equals(dst)){
//             String ok="";
//            for(int i=0;i<temp.size();i++){
//             ok=ok+mp2.get(temp.get(i))+"->";
//            }
//            ok=ok+"pirce:"+price.toString();
//            User.raiseError(ok);
//            return;
//          }
//          vis.set(src,1);
//          for(Node it:adj.get(src)){
//               if(vis.get(it.getV())==0){
//                 temp.add(it.getV());
//                 recursion(it.getV(), dst, vis, temp, price+it.getWeight());
//                 temp.remove(temp.size()-1);
//               }
//          }
//          vis.set(src, 0);
//          return;
//     }
    //---------------------------------------//
    public class Edge {
        Integer src, dest, weight;

        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public void printAllPaths(List<Edge>[] graph, int src, int dest) {
        Set<Integer> visited = new HashSet<>();
        List<String> pathList = new ArrayList<>();

        pathList.add(mp2.get(src));
        Integer price=0;
        printAllPathsUtil(graph, src, dest, visited, pathList,price);
    }

    public void printAllPathsUtil(List<Edge>[] graph, int src, int dest, Set<Integer> visited, List<String> pathList,Integer price) {
        if (src == dest) {
            // System.out.println(pathList);
            String fin="";
            for(String it:pathList){
                fin=fin+it+"->";
            }
            fin=fin+"price "+price.toString();
            User.addToMessage(fin);
            return;
        }

        visited.add(src);

        for (Edge edge : graph[src]) {
            if (!visited.contains(edge.dest)) {
                pathList.add(mp2.get(edge.dest));
                printAllPathsUtil(graph, edge.dest, dest, visited, pathList,price+edge.weight);
                pathList.remove(pathList.size() - 1);
            }
        }

        visited.remove(src);
    }
//-----------------------//

    public void dst_src(String src ,String dst){
        if(!mp.containsKey(src)){
           User.raiseError("!!NO SUCH SOURCE");
        }
        if(!mp.containsKey(dst)){
            User.raiseError("!!NO SUCH Destination");
         }
         List<Edge>[] graph = new ArrayList[mp.size()];
         for (int i = 0; i < mp.size(); i++) {
            graph[i] = new ArrayList<>();
        }
        for(ArrayList<Integer> it:edges){
            graph[it.get(0)].add(new Edge(it.get(0), it.get(1), it.get(2)));
        }
         printAllPaths(graph, mp.get(src), mp.get(dst));
    }
    @Override
    public String toString(int i) {
        return String.format("| %-5d|  %-9s | \t%-9s | %-21s | %-22s |   %-6sHrs |  %-4s  |  %-8s / %-11s| %.2f", i,  flightNumber, numOfSeatsInTheFlight, fromWhichCity, toWhichCity, flightTime, gate, distanceInMiles, distanceInKm,cost);
    }

    /**
     * Creates new random flight schedule
     *
     * @return newly created flight schedule
     */
    public String createNewFlightsAndTime() {

        Calendar c = Calendar.getInstance();
        // Incrementing nextFlightDay, so that next scheduled flight would be in the future, not in the present
        nextFlightDay += Math.random() * 7;
        c.add(Calendar.DATE, nextFlightDay);
        c.add(Calendar.HOUR, nextFlightDay);
        c.set(Calendar.MINUTE, ((c.get(Calendar.MINUTE) * 3) - (int) (Math.random() * 45)));
        Date myDateObj = c.getTime();
        LocalDateTime date = Instant.ofEpochMilli(myDateObj.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        date = getNearestHourQuarter(date);
        return date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a "));
    }

    /**
     * Formats flight schedule, so that the minutes would be to the nearest quarter.
     *
     * @param datetime to be formatting
     * @return formatted LocalDateTime with minutes close to the nearest hour quarter
     */
    public LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {
        int minutes = datetime.getMinute();
        int mod = minutes % 15;
        LocalDateTime newDatetime;
        if (mod < 8) {
            newDatetime = datetime.minusMinutes(mod);
        } else {
            newDatetime = datetime.plusMinutes(15 - mod);
        }
        newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES);
        return newDatetime;
    }
   

    //        ************************************************************ Setters & Getters ************************************************************

    public int getNoOfSeats() {
        return numOfSeatsInTheFlight;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setNoOfSeatsInTheFlight(int numOfSeatsInTheFlight) {
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public List<Customer> getListOfRegisteredCustomersInAFlight() {
        return listOfRegisteredCustomersInAFlight;
    }

    public String getFlightSchedule() {
        return flightSchedule;
    }

    public String getFromWhichCity() {
        return fromWhichCity;
    }

    public String getGate() {
        return gate;
    }

    public String getToWhichCity() {
        return toWhichCity;
    }

}