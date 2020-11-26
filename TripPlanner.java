//Kate Walker
import java.util.*;
import java.io.*;

public class TripPlanner
{
    public static ArrayList<Integer> dist= new ArrayList();
    public static List<String> route(String start, String end, List<String> attractions) throws IOException //function to read the files
    {
        PathPlanner planner = new PathPlanner();//make an object of the pathPlanner class

        String roadFile = "roads.csv",line;//set roadfile to roads.csv

        BufferedReader br = new BufferedReader(new FileReader(roadFile));//use bufferreader to read the file
        while ((line = br.readLine()) != null)//while line is not null
        {
            String[] info = line.split(",");//spilt the file at the commas
            planner.addEdge(info[0],info[1],Integer.parseInt(info[2]));//add the edges
            dist.add(Integer.parseInt(info[2]));
        }
        br.close();//close the bufferreader


        String attractionFile= "attractions.csv";//set thr attractionfile to attractions.csv
        br = new BufferedReader(new FileReader(attractionFile));//make new bufferreader with the attractions file
        while ((line = br.readLine()) != null)//while the line is not null
        {
            String[] data = line.split(",");//split at the comma
            planner.addPlace(data[1],data[0]);//add places as verticies
        }
        br.close();//close the bufferreader


        for(String place: attractions)//loop through the file
            planner.placeRoute(place);//add selected cities between the 2 main cities


        LinkedList<String> route = planner.findRoute(start,end);//find the route

        return route;//return the route

    }


    public static void main(String[] args) throws IOException
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the starting city:\n" );//get user input
        String start = sc.nextLine();
        System.out.print("Enter the ending city: \n");//get user input
        String end = sc.nextLine();

        ArrayList<String> attractions = new ArrayList<String>();//intialize the attractions list
        while(true)//collect user input while true
        {
            System.out.println("\nPlease enter an attraction name (done to finish): ");//enter the attractions until done
            String attraction = sc.nextLine();
            if(!attraction.equals("done"))//if user input is not done
                attractions.add(attraction);//add the attraction to the list
            else//if equals done
                break;//break
        }


        List<String> route = route(start,end,attractions);//find the route
        int finaldist = 0;
        for(int i=0; i<route.size(); i++){
            int d= dist.get(i);
            finaldist += d;
        }
        System.out.println("Final Route: " + route);//print the route
        System.out.println("Final Distance: " + finaldist);//print the final distance
    }
}
