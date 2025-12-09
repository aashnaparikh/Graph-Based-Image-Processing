import java.awt.Color;

public class PixelGraph{

    //2D grid of vertices
    private PixelVertex[][] pixels;
    private int width; //number of columns
    private int height; //number of rows

    public PixelGraph(Color[][] imagePixels){
        this.width = imagePixels.length; //number of columns
        this.height = imagePixels[0].length; //number of rows
        this.pixels = new PixelVertex[height][width]; //allocating vertex grid in row order

        // Create all vertices first so the neighbour can reference
        for (int y = 0; y < height; y++){
            for(int x = 0; x < width; x++) {
                pixels[y][x] = new PixelVertex(x, y);
            }
        }
        
        // Then add edges between vertices with same color
        for (int y = 0; y < height; y++){
            for(int x = 0; x < width; x++) {
                Color current = imagePixels[x][y];

                // Check left neighbor
                if (x > 0 && current.equals(imagePixels[x - 1][y])) {
                    pixels[y][x].addNeighbour(pixels[y][x - 1]);
                }
                // Check right neighbor  
                if (x < width - 1 && current.equals(imagePixels[x + 1][y])) {
                    pixels[y][x].addNeighbour(pixels[y][x + 1]);
                }
                // Check top neighbor
                if (y > 0 && current.equals(imagePixels[x][y - 1])) {
                    pixels[y][x].addNeighbour(pixels[y - 1][x]);
                }
                // Check bottom neighbor
                if (y < height - 1 && current.equals(imagePixels[x][y + 1])) {
                    pixels[y][x].addNeighbour(pixels[y + 1][x]);
                }
            }
        }
    }
    
    public PixelVertex getPixelVertex(int x, int y){
        return pixels[y][x];
    }
   
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
}