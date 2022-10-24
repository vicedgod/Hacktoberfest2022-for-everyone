//This code is Contributed by Aman Kumar Behera in the month of October.
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class AirlineProblem {

    public static void main(String[] args){
        Scanner scannerToReadAirlines = null;
        try{
            scannerToReadAirlines = new Scanner(new File("airlines.txt"));
        }
        catch(IOException e){
            System.out.println("Could not connect to file airlines.txt.");
            System.exit(0);
        }
        if(scannerToReadAirlines != null){
            ArrayList<Airline> airlinesPartnersNetwork = new ArrayList<Airline>();
            Airline newAirline;
            String lineFromFile;
            String[] airlineNames;
            
            while( scannerToReadAirlines.hasNext() ){
                lineFromFile = scannerToReadAirlines.nextLine();
                airlineNames = lineFromFile.split(",");
                newAirline = new Airline(airlineNames);
                airlinesPartnersNetwork.add( newAirline );
            }
            System.out.println(airlinesPartnersNetwork);
            Scanner keyboard = new Scanner(System.in);
            System.out.print("Enter airline miles are on: ");
            String start = keyboard.nextLine();
            System.out.print("Enter goal airline: ");
            String goal = keyboard.nextLine();
            ArrayList<String> pathForMiles = new ArrayList<String>();
            ArrayList<String> airlinesVisited = new ArrayList<String>();
            if( canRedeem(start, goal, pathForMiles, airlinesVisited, airlinesPartnersNetwork))
                System.out.println("Path to redeem miles: " + pathForMiles);
            else
                System.out.println("Cannot convert miles from " + start + " to " + goal + ".");
        }
    }
    
    private static boolean canRedeem(String current, String goal,
            ArrayList<String> pathForMiles, ArrayList<String> airlinesVisited,
            ArrayList<Airline> network){
        if(current.equals(goal)){
            
            pathForMiles.add(current);
            return true;
        }
        else if(airlinesVisited.contains(current))
            
            return false;
        else{
            
            airlinesVisited.add(current);
            
            
            pathForMiles.add(current);
            
            
            int pos = -1;
            int index = 0;
            while(pos == -1 && index < network.size()){
                if(network.get(index).getName().equals(current))
                    pos = index;
                index++;
            }
            
            if( pos == - 1)
                return false;
            
            
            index = 0;
            String[] partners = network.get(pos).getPartners();
            boolean foundPath = false;
            while( !foundPath && index < partners.length){
                foundPath = canRedeem(partners[index], goal, pathForMiles, airlinesVisited, network);
                index++;
            }
            if( !foundPath )
                pathForMiles.remove( pathForMiles.size() - 1);
            return foundPath;
        }
    }

    private static class Airline{
        private String name;
        private ArrayList<String> partners;
        
        
        public Airline(String[] data){
            assert data != null && data.length > 0 : "Failed precondition";
            name = data[0];
            partners = new ArrayList<String>();
            for(int i = 1; i < data.length; i++)
                partners.add( data[i] );
        }
        
        public String[] getPartners(){
            return partners.toArray(new String[partners.size()]);
        }
        
        public boolean isPartner(String name){
            return partners.contains(name);
        }
        
        public String getName(){
            return name;
        }
        
        public String toString(){
            return name + ", partners: " + partners;
        }
    }
}
