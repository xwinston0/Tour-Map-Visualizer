import java.util.StringJoiner;
/**
 * This class creates a Tour of Points using a 
 * Linked List implementation.  The points can
 * be inserted into the list using two heuristics.
 *
 * @author Xiomara Winston & Stella Thompson
 * @author Eric Alexander, modified code 01-12-2018
 * @author Layla Oesper, modified code 09-22-2017
 */


public class Tour {

    /** 
     * A helper class that defines a single node for use in a tour.
     * A node consists of a Point, representing the location of that
     * city in the tour, and a pointer to the next Node in the tour.
     */
    private class Node {
        private Point p;
        private Node next;

    
        /** 
         * Constructor creates a new Node at the given Point newP
         * with an initial next value of null.
         * 
         * @param newP the point to associate with the Node.
         */
        public Node(Point newP) {
            p = newP;
            next = null;
        }

        /** 
         * Constructor creates a new Node at the given Point newP
         * with the specified next node.
         *
         * @param newP the point to associate with the Node.
         * @param nextNode the nextNode this node should point to.
         */
        public Node(Point newP, Node nextNode) {
            p = newP;
            next = nextNode;
        }
          
    } // End Node class
    

    // Tour class Instance variables
    private Node head;
    private int size; //number of nodes
    private double distance; //distance of the tour
    
    
    /**
     * Constructor for the Tour class.  By default sets head to null.
     */
    public Tour() {
        head = null;
        size = 0;
        distance = 0;

    }
    /**
     * Method for returning the size of the tour
     * @return number of nodes
     */
    public int size() {
        return size;
    }

    /**
     * Method for turning points into Strings that can be printed neatly
     */
    public String toString() {
        String tour = "";
        StringJoiner sjTour = new StringJoiner("\n");
        Node temp = head;
        while (temp.next != null) {
            String pointString = temp.p.toString();
            sjTour.add(pointString);
            temp = temp.next;
        }
        tour = sjTour.toString();
        return tour;
    }

    /**
     * Method for returning the total distance of the tour
     * @return distance of tour
     */
    public double distance() {
        Node temp = head;
        while (temp.next != null) {
            double distanceOfLeg = temp.p.distanceTo(temp.next.p);
            distance += distanceOfLeg;
            temp = temp.next;
        }
        distance += temp.p.distanceTo(head.p);
        return distance;
    }

    /**
     * Method for drawing all of the nodes in the tour and connecting them with lines
     */
    public void draw() {
        Node temp = head;
        while (temp.next != null) {
            temp.p.draw();
            temp.p.drawTo(temp.next.p);
            temp = temp.next;
        }
        //special case for the last node to connect to the first node
        temp.p.draw();
        temp.p.drawTo(head.p);

    }


    /**
     * Method for inserting a point after the point it's closest to in the tour
     * @param p
     */
    public void insertNearest(Point p) {
        Double distanceLowest = Double.MAX_VALUE;
        Double distanceCurrent = 0.0;
        Node temp;
        Node closest = null;
        // Checks for the existence of a head
        if (head == null) {
            head = new Node(p, head);

        } else {
            temp = head; // Starts loop at head

            // Loops through nodes & checks the distance between each node and the new point
            while (temp != null) {
                distanceCurrent = temp.p.distanceTo(p);

                // Checks to see if it's closer than the previous closest point
                if (distanceCurrent < distanceLowest) {
                    distanceLowest = distanceCurrent;
                    closest = temp;  // Updates a node that will tell us where to insert the point
                }
                temp = temp.next;
            }
            // Creates a new node
            closest.next = new Node(p, closest.next);
        }
        size++;
    }

    // Method for inserting a point into the linked-list where it affects the distance the least
    public void insertSmallest(Point p) {
        Double distanceLowest = Double.MAX_VALUE;
        Double newDistance;
        Double originalDistance;
        Node temp;
        Node closest = null;

        if (head == null) {
            head = new Node(p, head);

        } else {
            temp = head;

            while (temp != null) {
                // Checks if there is only a head in the list and adds the second node
                if (temp == head && temp.next == null) {
                    temp.next = new Node(p, temp.next);

                // If it's the last point in the list, checks distance with the head    
                } else if (temp.next == null) {
                    temp.next = head;
                    // Original distance from last point to head
                    Double firstToLast = temp.next.p.distanceTo(temp.p);

                    // Distance from head to new point
                    Double firstLeg = temp.next.p.distanceTo(p);

                    // Distance from new point to tail
                    Double secondLeg = p.distanceTo(temp.p);

                    newDistance = firstLeg + secondLeg;

                    // Finds the distance added by inserting the point in the proposed position
                    Double differenceInDistance = newDistance - firstToLast;

                    // Checks to seee if it's the smallest change in distance
                    if (differenceInDistance < distanceLowest) {
                        distanceLowest = differenceInDistance;
                        closest = temp;
                    }
                    temp.next = null;

                // Compares distances from points in the middle of the list using the same logic as stated above
                } else {
                    originalDistance = temp.p.distanceTo(temp.next.p);
                    Double firstLeg = temp.p.distanceTo(p);
                    Double secondLeg = p.distanceTo(temp.next.p);
                    newDistance = firstLeg + secondLeg;
                    Double differenceInDistance = newDistance - originalDistance;

                    if (differenceInDistance < distanceLowest) {
                        distanceLowest = differenceInDistance;
                        closest = temp;
                    }
                }
            temp = temp.next;
            }
            // Creates a new node at the location saved within the while loop as the point that affects the distance the least
            closest.next = new Node(p, closest.next);
        }
        size++;
    }


    
    public static void main(String[] args) {
        /* Use your main() function to test your code as you write it. 
         * This main() will not actually be run once you have the entire
         * Tour class complete, instead you will run the NearestInsertion
         * and SmallestInsertion programs which call the functions in this 
         * class. 
         */
        
        //One example test could be the follow (uncomment to run):
        /*
        Tour tour = new Tour();
        Point p = new Point(0,0);
        tour.insertNearest(p);
        p = new Point(0,100);
        tour.insertNearest(p);
        p = new Point(100, 100);
        tour.insertNearest(p);
        System.out.println("Tour distance =  "+tour.distance());
        System.out.println("Number of points = "+tour.size());
        System.out.println(tour);
        */
         

        // the tour size should be 3 and the distance 341.42 (don't forget to include the trip back
        // to the original point)
    
        // uncomment the following section to draw the tour, setting w and h to the max x and y 
        // values that occur in your tour points
    
        /*
        int w = 100 ; //Set this value to the max that x can take on
        int h = 100 ; //Set this value to the max that y can take on
        StdDraw.setCanvasSize(w, h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);
        StdDraw.setPenRadius(.005);
        tour.draw(); 
        */
    }
   
}