package edu.rit.cs.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Methods generally useful for the graph classes
 * @author RIT CS
 */
public class Utilities {
    public static final int CMD_LINE_ERROR = 1;
    public static final int INSTANTIATION_ERROR = 2;

    /**
     * The constructor loads an <em>undirected</em>graph from file.
     * <br>
     * Each line in the input file is assumed to contain the names of
     * two nodes at opposite ends of an undirected edge.
     * To fill in the graph, this method uses the file contents to create
     * nodes as necessary as well as edges in both directions between the nodes.
     *
     * @param fileName name of the input graph specification file
     * @param graph    a pre-constructed, empty graph
     * @return the fully initialized graph
     * @throws IOException if there is a problem with the graph implementation
     *                     class file or the input file
     */
    public static < NodeType > void
                    loadFromFile( String fileName, Graph< NodeType > graph )
                            throws IOException {
        try (
                BufferedReader in = new BufferedReader(
                        new FileReader( fileName ) )
        ) {
            // Parse each line in the input file.
            String line = in.readLine();
            while ( line != null ) {
                NodeType n1, n2;

                // Split the line into an array of strings
                // where each string is separated by a space.
                String[] fields = line.split( "\\s+" );

                // Create new nodes as necessary.
                if ( !graph.hasNode( fields[ 0 ] ) ) {
                    n1 = graph.makeNode( fields[ 0 ] );
                }
                else {
                    n1 = graph.getNode( fields[ 0 ] );
                }
                if ( !graph.hasNode( fields[ 1 ] ) ) {
                    n2 = graph.makeNode( fields[ 1 ] );
                }
                else {
                    n2 = graph.getNode( fields[ 1 ] );
                }

                // Create new edges.
                graph.addNeighbor( n1, n2 );
                graph.addNeighbor( n2, n1 );

                line = in.readLine();
            }
        }
    }
}
