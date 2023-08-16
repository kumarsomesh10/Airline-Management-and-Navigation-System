public abstract class FlightDistance {
    public abstract String toString(int i);

    public abstract String[] calculateDistance(double lat1, double lon1, double lat2, double lon2);

    public void displayMeasurementInstructions(){
        String symbols = "+---------------------------+";
        User.addToMessage(symbols);
        User.addToMessage("| SOME IMPORTANT GUIDELINES |");
        User.addToMessage(symbols);
        User.addToMessage("1. Distance between the destinations are based upon the Airports Coordinates(Latitudes && Longitudes) based in those cities");
        User.addToMessage("2. Actual Distance of the flight may vary from this approximation as Airlines may define their on Travel Policy that may restrict the planes to fly through specific regions...");
        User.addToMessage("3. Flight Time depends upon several factors such as Ground Speed(GS), AirCraft Design, Flight Altitude and Weather. Ground Speed for these calculations is 450 Knots...");
        User.addToMessage("4. Expect reaching your destination early or late from the Arrival Time. So, please keep a margin of ±1 hour...");
        User.addToMessage("5. The departure time is the moment that your plane pushes back from the gate, not the time it takes off. The arrival time is the moment that your plane pulls into the gate, not the time it touches down on the runway...");
    }

}
