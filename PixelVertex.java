import java.util.ArrayList;
import java.util.List;

public class PixelVertex{

    private int x; //x coord of the pixel in the image
    private int y; //y coord of the pixel in the image
    private List<PixelVertex> neighbours; //list of all adjacent same color pixels

    public PixelVertex(int x, int y) {
        this.x = x;
        this.y = y;
        this.neighbours = new ArrayList<>();
    }

    public int getX(){
        return x;
    }
    
    /* getY()
       Return the y-coordinate of the pixel corresponding to this vertex.
    */
    public int getY(){
        return y;
    }
    
    /* getNeighbours()
       Return an array containing references to all neighbours of this vertex.
       The size of the array must be equal to the degree of this vertex (and
       the array may therefore contain no duplicates).
    */
    public PixelVertex[] getNeighbours(){
        return neighbours.toArray(new PixelVertex[0]);
    }
    
    /* addNeighbour(newNeighbour)
       Add the provided vertex as a neighbour of this vertex.
    */
    public void addNeighbour(PixelVertex newNeighbour){
          if (!neighbours.contains(newNeighbour)) {
            neighbours.add(newNeighbour);
            newNeighbour.neighbours.add(this);
        }
    }
    
    /* removeNeighbour(neighbour)
       If the provided vertex object is a neighbour of this vertex,
       remove it from the list of neighbours.
    */
    public void removeNeighbour(PixelVertex neighbour){
         if (neighbours.contains(neighbour)) {
            neighbours.remove(neighbour);
            neighbour.neighbours.remove(this);
        }
    }
    
    /* getDegree()
       Return the degree of this vertex. Since the graph is simple, 
       the degree is equal to the number of distinct neighbours of this vertex.
    */
    public int getDegree(){
        return neighbours.size();
    }
    
    /* isNeighbour(otherVertex)
       Return true if the provided PixelVertex object is a neighbour of this
       vertex and false otherwise.
    */
    public boolean isNeighbour(PixelVertex otherVertex){
        return neighbours.contains(otherVertex);
    }
    
}