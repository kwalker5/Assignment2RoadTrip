//Kate Walker
import java.util.*;

public class PathPlanner
{

    private class Edge
    {
        public int v1,v2; // index of the vertices
        int distance; // distance in miles
    }


    private class Vertex
    {
        public String city;//string to store the city name
        public ArrayList<String> attractions; // list of attractions to visit

        public Vertex(String name)
        {
            city = name;//set city to the name
            attractions = new ArrayList<String>();//make a new arrayList withe the attractions
        }
    }

    private ArrayList<Vertex> vertex;//ArrayList of vertices
    private ArrayList<ArrayList<Edge>> edge;//ArrayList of edges

    private LinkedList<Integer> attraction;//list of vertices to visit on the way

    public boolean checkVertex(String name)//function to test if a vertex with the name exists
    {
        return (index(name) >= 0);//the name of the vertex
    }


    public int index(String name)//function to find the index in the vertex list
    {
        for(int vi = 0; vi < vertex.size(); vi++)
        {
            if(vertex.get(vi).city.equalsIgnoreCase(name))
                return vi;
        }

        return -1;
    }

    PathPlanner() //constructor for the pathplanner
    {
        vertex = new ArrayList<Vertex>();//create arraylist for vertex
        edge = new ArrayList<ArrayList<Edge>>();//create arraylist for edge
        attraction = new LinkedList<Integer>();//create arraylist for attraction
    }


    public void addEdge(String vName1, String vName2, int dist)//function to add edge
    {
        if(!checkVertex(vName1))//if not edge
        {

            Vertex v1 = new Vertex(vName1);//add vertex
            vertex.add(v1);//add vertex
            edge.add(new ArrayList<Edge>());//add an ArrayList of edges
        }
        int v1 = index(vName1);
        ArrayList<Edge> edges1 = edge.get(v1);


        if(!checkVertex(vName2))//if not edge
        {
            Vertex v2 = new Vertex(vName2);//add vertex
            vertex.add(v2);
            edge.add(new ArrayList<Edge>());//add the edge to an ArrayList
        }
        int v2 = index(vName2);
        ArrayList<Edge> edges2 = edge.get(v2);


        Edge e1 = new Edge();//create an edge
        e1.v1 = v1;//set the first vertex equal to vertex 1
        e1.v2 = v2;//set second vertex to vertex 2
        e1.distance = dist;//set the distance to the value of dist

        Edge e2 = new Edge();//create second edge
        e2.v1 = v2;//set the first vertex equal to vertex 2
        e2.v2 = v1;//set the first vertex equal to vertex 1
        e2.distance = dist;//set the distance to the value of dist

        edges1.add(e1);//add the new edge to the list of edges
        edges2.add(e2);//add the new edge to the list of edges
    }


    public boolean addPlace(String city, String place)//function to add the list of attractions of the city
    {
        if(!checkVertex(city))//if it isn't a vertex
            return false;//return false

        int vIndex = index(city);//set the vertex to the index of the city
        vertex.get(vIndex).attractions.add(place);//get the information and add it to the attractions list

        return true;//return true
    }


    public boolean placeRoute(String place)//function to add the name and index of the city with the attractions to add them to the list
    {
        for(int i = 0; i < vertex.size(); i++)//loop thorough form zero to the vertex size
        {
            Vertex v = vertex.get(i);//get the information
            if(v.attractions.contains(place))//if the attraction is in that place
            {
                attraction.add(i);//add it to the attractions list
                return true;//return true
            }
        }

        return false;//if not found return false
    }


    public void Dijkstra(int start, ArrayList<Integer> distance, ArrayList<Integer> previous)//function to perform Dijkstra's and find the shortest path
    {
        

        ArrayList<Integer> list = new ArrayList<Integer>();//intiate an ArrayList to store the information

        int ai,bi;//intialize variables
        int alternate,minDistance,curDistance;//intialize variables
        int a,b;//intialize variables
        for(b = 0; b < vertex.size(); b++)//loop through the vertex arraylist
        {
            distance.add(-1);//add -1 to the distance arraylist
            previous.add(-1);//aff -1 to the previous arraylist
            list.add(b);//add b to the arraylist
        }
        distance.set(start,0);//set the distance to the value of start and 0

        while(!list.isEmpty())//while the list is not empty
        {
            ai = minDistance = -1;//set ai to the min distance -1
            for(bi = 0; bi < list.size(); bi++)//loop through the list
            {
                b = list.get(bi);//get bi from the list and set it to b
                curDistance = distance.get(b);//set curdistance to the distance that you get from b
                if(curDistance >= 0 && (ai < 0 || curDistance < minDistance))//if the current disnace is <0 and ai < 0 or the current< min distance
                {
                    ai = bi;//set ai to equal bi
                    minDistance = curDistance;//and min distance euqal to current distance
                }
            }

            a = list.get(ai);//set a to list.get ai
            list.remove(ai);//remove ai fromt he list


            for(bi = 0; bi < edge.get(a).size(); bi++)//loop through the size of edge.get(a)
            {
                b = edge.get(a).get(bi).v2;//set b to edge.get(a)

                if(list.contains(b))//if the list contains b
                {
                    alternate = distance.get(a) + edge.get(a).get(bi).distance;//set alternate to distance at a + the distance at bi

                    if(distance.get(b) < 0 || alternate < distance.get(b))//if the distance at b is <0 or the alternate is < the distance of b
                    {
                        distance.set(b,alternate);//set the distance to b and alternate
                        previous.set(b,a);//set previous to a and b
                    }
                }
            }
        }
    }


    private List<Integer> path(int start, int end, ArrayList<Integer> previous)//function to pull the shortest path
    {
        LinkedList<Integer> p = new LinkedList<Integer>();//linkedlist to store the path

        int a = end;//a to the end
        if(a == start || previous.get(a) >= 0)//if a equals the start or previous.get(a) = 0
        {
            while(a >= 0)//while a > 0
            {
                p.addFirst(a);//add a to the path
                a = previous.get(a);//set a to previous.get(a)
            }
        }

        return p;//return the path
    }


    private static LinkedList<LinkedList<Integer>> var(LinkedList<Integer> l)//function that returns a list of all the variations
    {
        LinkedList<LinkedList<Integer>> list = new LinkedList<LinkedList<Integer>>();//make a linked list

        int s = l.size();//set int s to the l size


        if(s == 1)//if s is 1
        {
            // duplicate the array am to not affect it further
            LinkedList<Integer> l1 = new LinkedList<Integer>();//duplicate the array
            l1.addLast(l.get(0));//add the value at 0

            list.addLast(l1);//add l1 to the list
        }
        else
        {
            for(int i = 0; i < s; i++)//loop through from 0 to the value of s
            {
                LinkedList<Integer> l1 = new LinkedList<Integer>();//intiate l1

                for(int j = 0; j < s; j++)//add all values to li
                {
                    if(j != i)//if j is not equal to i
                        l1.add(l.get(j));//add the information from l.get(j) to l1
                }


                LinkedList<LinkedList<Integer>> list1 = var(l1);//recursivly call the function


                for(int j = 0; j < list1.size(); j++)//add the elements to the end of each variation
                {
                    LinkedList<Integer> array = list1.get(j);//intialize the array
                    array.add(0,l.get(i));//add the information from l.get(i) to the 0 position
                    list.add(array);//add the array to the list
                }
            }
        }

        return list;//return the list
    }


    public LinkedList<String> findRoute(String scity, String ecity)//function to find the shortest route from the given information
    {
        int start = index(scity);//set the start to the index of the starting city
        int end = index(ecity);//set the end to the index of the ending city



        boolean places = !attraction.isEmpty();//if there are no attraction in the list
        if(!places)//if no attractions
            attraction.add(start);//add the start
        List<LinkedList<Integer>> lp = var(attraction);//call var on attractions
        int nump = attraction.size();//set the number of places to attractions size


        if(places)//if places
            attraction.add(start);//add the start to the attractions list
            attraction.add(end);//add the end to the attractions list

        int startnum = nump;//set start num to nump
        int endnum = nump + 1;//set end num to nump +1
        if(!places)//if no places
        {
            startnum = 0;//set start num to 0
            endnum = 1;//set end num to 1
        }


        ArrayList<ArrayList<Integer>> dist1 = new ArrayList<ArrayList<Integer>>();//intialze arraylist
        ArrayList<ArrayList<Integer>> prev1 = new ArrayList<ArrayList<Integer>>();//intialze arraylist
        for(int pi = 0; pi < attraction.size(); pi++)//loop through the attractions
        {
            int a = attraction.get(pi);//set a to the attarction.get(pi)

            ArrayList<Integer> dist = new ArrayList<Integer>();//intialize arraylist
            ArrayList<Integer> prev = new ArrayList<Integer>();//intialize arraylist
            Dijkstra(a,dist,prev);//call Dijkstra on a dist and prev
            dist1.add(dist);//add dist to dist1
            prev1.add(prev);//add prev to prev1
        }


        int dist = -1;//set dist to -1
        int min = -1;//set min to -1
        for(int n = 0; n < lp.size(); n++)// loop through lp
        {
            LinkedList<Integer> var = lp.get(n);//linked list to store the variations

            int curr_dist = 0;//set current distance to 0


            int end2 = var.get(0);//set end2 to the variation.get(0) the first path
            curr_dist += dist1.get(startnum).get(end2);//set curr_dist to dist1.get the startnum and the end2

            //List<Integer> pathS = path(start,end2,prev1.get(endnum));


            for(int i = 0; i < var.size() - 1; i++)//loop through the variations (the next paths)
            {
                int p1 = var.get(i);//set p1 to var.get(i)
                int p2 = var.get(i+1);//set p2 to the var +1
                int p1i = attraction.indexOf(p1);//set p1 index to the index of p1
                int p2i = attraction.indexOf(p2);//set p2 index to the index of p2

                curr_dist += dist1.get(p1i).get(p2);//set current distance to current distance + the distance of p1i.get(p2)
                List<Integer> path = path(p1,p2,prev1.get(p1i));

            }


            end2 = var.get(var.size()-1);//get the latest path
            curr_dist += dist1.get(startnum).get(end2);//update the distance

            List<Integer> bestpath = path(end,end2,prev1.get(endnum));//intialize the best path
            Collections.reverse(bestpath);//reverse it to be in the right order



            if(n == 0 || dist > curr_dist)//if n is 0 or distance is > current distance
            {
                dist = curr_dist;//set distance to current
                min = n;//set min to end
            }

        }


        LinkedList<String> finalPath = new LinkedList<String>();//intialize final path
        LinkedList<Integer> varMin = lp.get(min);//intialize the min variation
        varMin.addFirst(start);//add the start
        varMin.addLast(end);//add the end

        for(int i = 0; i < varMin.size()-1; i++)//loop through varMin
        {
            int p1 = varMin.get(i);//set p1 to varMin at i
            int p2 = varMin.get(i+1);//set p2 to varMin at i +1
            int p1i = attraction.indexOf(p1);//set p1i to the index of p1
            int p2i = attraction.indexOf(p2);//set p2i to the index of p2
            List<Integer> pathfinal = path(p1,p2,prev1.get(p1i));//set path final to path of p1,p1,prev1.get(p1i)

            for(int j = 0; j < pathfinal.size()-1; j++)//loop through the final path
                finalPath.addLast(vertex.get(pathfinal.get(j)).city);//add the information to the path final
        }
        finalPath.addLast(vertex.get(end).city);//add the ending city


        return finalPath;//return the final path
    }
    
}

