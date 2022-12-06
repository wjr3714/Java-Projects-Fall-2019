package edu.rit.cs.graph;

import edu.rit.cs.graph.linkedgraph.LinkedGraph;
import edu.rit.cs.graph.linkedgraph.LinkedNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class attempts to verify the following claim: any actor in any movie can be linked to Kevin Bacon through a
 * series of actors that appeared in a movie together. More generally, this class can be used to find the degrees of
 * separation (the path) between two actors (provided an appropriate data set).
 *
 * @author William J. Reid (wjr3714)
 */
public class ThreeDegrees {

    /** Graph of Movies (with actor neighbors) &  Graph of Actors (with movie neighbors)*/
    private LinkedGraph graph;

    /** Graph of Movies (with actor neighbors) */
    private LinkedGraph graphMovies;

    /** Graph of Actors (with movie neighbors) */
    private LinkedGraph graphActors;

    /**
     * Prompts user for data set file, builds a graph from the data set, asks for the staring and ending nodes, and
     * outputs the path from the staring to the ending node (if one exists) to command line.
     * @param args Command Line Arguments
     */
    public static void main(String[] args) {

        //    Prompts the user for the name of an input file.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter movie data filename: ");
        String fileName = scanner.nextLine();

        // Reads the file contents into a graph data structure.
        ThreeDegrees dataGraph = new ThreeDegrees(fileName);

        while (true){

            //  Prompts the user for two names to try connecting.
            System.out.print("Enter first actor name (two case-sensitive words): ");
            String startingNode = scanner.nextLine();

            //  If either the first or second name is the empty string, then the program should end.
            if (startingNode.equals("")){
                System.exit(0);
            }

            //  If either name is not found in the graph, then the program should print a message to that effect, and request two
            //  new names for the next search.
            if (!dataGraph.graphActors.hasNode(startingNode)){
                System.out.println(startingNode + " is not known in the " + fileName + " dataset.");
                continue;
            }

            System.out.print("Enter second actor name (two case-sensitive words): ");
            String endingNode = scanner.nextLine();

            // If either the first or second name is the empty string (the user hit enter giving no name), then the program should end.
            if (endingNode.equals("")){
                System.exit(0);
            }

            // If either name is not found in the graph, then the program should print a message to that effect, and request two
            // new names for the next search.
            if (!dataGraph.graphActors.hasNode(endingNode)){
                System.out.println(endingNode + " is not known in the " + fileName + " dataset.");
                continue;
            }
            if (startingNode.equals(endingNode)){
                System.out.println("Silly! They are in their own movie!");
                continue;
            }

            List<LinkedNode> path;
            path = dataGraph.BreathFirstSearch(startingNode, endingNode);

            if (path.size() <= 7){
                if (path.size() == 0){
                    System.out.println("No path exists between " + startingNode + " and " + endingNode +".");
                }
                for (int i = 0; i < path.size(); i+=2){
                    if (i ==0){
                        System.out.println(path.get(i).getName() + " was in " + path.get(i+1).getName());
                    }
                    else if (i == path.size()-1){
                        System.out.println("with " + path.get(i).getName() + ".");
                    }
                    else{
                        System.out.println("with " + path.get(i).getName() + " who was in " + path.get(i+1).getName());
                    }
                }
            }
            else{
                System.out.println("No path less than or equal to a 3-hop distance exists between " + startingNode +
                        " and " + endingNode +".");
            }
        }
    }


    /**
     * Visits all neighbors of the initial searchKey (node) provided in a breadth-first search manner. The search stops
     * only when the target node (end node) is reached or if all possible routs have been exhausted and no path was found
     * connecting the start node and end node. The nodes the have been visited and the path taken to visit these nodes
     * is recorded in a HashMap.
     *
     * @param startingNode The starting location of the breadth-first search.
     * @param endingNode The target (end location) of the breadth-first search.
     * @return The path from the starting location to the ending location, provided that a path exists.
     */
    private List<LinkedNode> BreathFirstSearch(String startingNode, String endingNode){

        // Create Starting & Ending Node from String
        LinkedNode startNode = graph.getNode(startingNode);
        LinkedNode endNode = graph.getNode(endingNode);

        // Add Starting Node to Queue --> The queue holds the next node we will explore neighbors on.
        List<LinkedNode> queue = new LinkedList<>();
        queue.add(startNode);

        // This Hash Map keeps track of the nodes traversed after arriving
        Map<LinkedNode,LinkedNode> previousNodes = new HashMap<>();
        previousNodes.put(startNode, startNode);

        while (!queue.isEmpty()){
            LinkedNode currentNode = queue.remove(0);
            if (currentNode == endNode){
                break;
            }
            for (LinkedNode node : currentNode.getNeighbors()){
                if (!previousNodes.containsKey(node)){
                    previousNodes.put(node, currentNode);
                    queue.add(node);
                }
            }
        }

        return buildPath(previousNodes, startNode, endNode);
    }

    /**
     * Creates a path from the start node to the end node provided by the user.
     *
     * @param previousNodes The nodes stroed in a Map used to construct the path.
     * @param startNode The node we are starting the path search from.
     * @param endNode The node we are trying to reach.
     * @return Path from starting node to ending node (if one exists)
     */
    private List<LinkedNode> buildPath(Map<LinkedNode, LinkedNode> previousNodes, LinkedNode startNode, LinkedNode endNode) {
        List<LinkedNode> path = new LinkedList<>();

        if (previousNodes.containsKey(endNode)){
            LinkedNode currentNode = endNode;
            while (currentNode != startNode){
                path.add(0, currentNode);
                currentNode = previousNodes.get(currentNode);
            }
            path.add(0,startNode);
        }
        return path;
    }


    /**
     * Reads the data file and builds an undirected graph from the data set fed.
     * If an actor or movie are in the same line in the data file, then an edge must be constructed to connect them.
     * @param fileName The data file from which to build the graph.
     */
    private ThreeDegrees(String fileName) {

        try {
            File dataFile = new File(fileName);// Requires absolute path to be provided.
            Scanner scanner = new Scanner(dataFile);

            // Create graphs
            graph = new LinkedGraph();
            graphMovies = new LinkedGraph();
            graphActors = new LinkedGraph();

            // Read the entire file
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                // Array contains an entry for each word in file.
                ArrayList movieANDactor = new ArrayList<>(Arrays.asList(line.split("\\s+")));

                // Join actor's FirstName & LastName into single index entry of Array.
                ArrayList<String> movieActor = new ArrayList<>();
                for (int i = 0; i < movieANDactor.size(); i += 2) {
                    if (i == 0) {
                        movieActor.add(i, (String) movieANDactor.get(i));
                    } else if (i % 2 == 0) {
                        movieActor.add(i - (i - 1), movieANDactor.get(i - 1) + " " + movieANDactor.get(i));
                    }
                }


                // Create Graph of Movies & Graph of Actors
                LinkedNode movieNode;
                LinkedNode actorNode;
                for (int i = 0; i < movieActor.size(); i++){

                    // Create graph of Movies (Each movie node has actor neighbors)
                    if (i == 0) {
                        movieNode = graphMovies.makeNode(movieActor.get(i));
                        for (int j = 1; j < movieActor.size(); j++) {
                            movieNode.addNeighbor(graphActors.makeNode(movieActor.get(j)));
                        }
                    }

                    // Create graph of Actors (Each actor node has movie neighbors)
                    else{
                        actorNode = graphActors.getNode(movieActor.get(i));
                        for (LinkedNode node : graphMovies.getNodes()){
                            if (graphMovies.getNeighbors(node).contains(actorNode)){
                                actorNode.addNeighbor(node);
                            }
                        }
                    }
                }

                // Combinination of movieGraph + actorGraph = graph
                for (String s : movieActor) {
                    if (!graph.hasNode(s)) {
                        graph.makeNode(s);
                    }
                }
                for (int k = 0; k < movieActor.size(); k++) {
                    LinkedNode node = graph.getNode(movieActor.get(k));

                    // Add actor neighbors to movie nodes
                    if (k == 0) {
                        for (int j = 1; j < movieActor.size(); j++) {
                            node.addNeighbor(graph.getNode(movieActor.get(j)));
                        }
                    }
                    else {
                        // Add movie neighbor to actor node.
                        node.addNeighbor(graph.getNode(movieActor.get(0)));
                    }
                }
            }

            // Ask user for type of graph display
            DisplayGraph();

            scanner.close();
        }
        catch (FileNotFoundException exception){
            System.err.println("File " + fileName + " does not exist. \nSuggestion: Provide the absolute path " +
                    "instead of the relative path to the data file.");
            System.exit(0);
        }
    }

    /**
     * Options for display the data set as a graph.
     */
    private void DisplayGraph(){

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nGraph Display Options: " +
                "\n" +
                "\n" +
                "(1) - Graph of Actors (with movie neighbors)\n" +
                "(2) - Graph of Movies (with actor neighbors)\n" +
                "(3) - Graph of Actors & Graph of Movies\n" +
                "\n" +
                "Please enter the integer corresponding to your choice to display the data set provided as a graph: ");

        int userInput = scanner.nextInt();

        if (userInput == 1){
            System.out.println("\n"+graphActors);
        }
        else if (userInput == 2){
            System.out.println("\n"+graphMovies);
        }
        else if (userInput == 3){
            System.out.println("\n"+graph);
        }
    }
}