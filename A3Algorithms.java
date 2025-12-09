import java.awt.Color;
import java.util.Queue;
import java.util.LinkedList;

public class A3Algorithms{

    public static void FloodFillDFS(PixelVertex start, PixelWriter writer, Color targetColour) {
        //Implementing large bounds to make sure it runs into no bound issue
        int width = 2000;
        int height = 2000;
        
        //marking whether pixel (x,y) has been visited
        boolean[][] visited = new boolean[height][width];

        //run the recursive DFS from the start pixel
        dfsFill(start, visited, writer, targetColour);
    }

    private static void dfsFill(PixelVertex vertex, boolean[][] visited, PixelWriter writer, Color targetColour) {
        //ignoring null vertices
        if (vertex == null) return;
        
        int x = vertex.getX();
        int y = vertex.getY();

        //Checking the bounds
        if (y < 0 || y >= visited.length || x < 0 || x >= visited[0].length) return;
        if (visited[y][x]) return; //if pixel already processed, skip

        visited[y][x] = true; //visit and paint current pixel
        writer.setPixel(x, y, targetColour);

        //recurse on all graph neighbours
        for (PixelVertex neighbour : vertex.getNeighbours()) {
            dfsFill(neighbour, visited, writer, targetColour);
        }
    }

    public static void FloodFillBFS(PixelVertex start, PixelWriter writer, Color targetColour) {
        int width = 2000;
        int height = 2000;
        boolean[][] visited = new boolean[height][width];

        //BFS queue
        Queue<PixelVertex> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            PixelVertex current = queue.poll();
            if (current == null) continue;
            
            int cx = current.getX();
            int cy = current.getY();

            // Bounds checking
            if (cy < 0 || cy >= visited.length || cx < 0 || cx >= visited[0].length) continue;
            if (visited[cy][cx]) continue; //skip pixels already processed

            visited[cy][cx] = true;
            writer.setPixel(cx, cy, targetColour);

            //Enqueue all neighbours to continue the flood
            for (PixelVertex neighbour : current.getNeighbours()) {
                if (neighbour != null) {
                    queue.add(neighbour);
                }
            }
        }
    }

    public static void OutlineRegionDFS(PixelVertex start, PixelWriter writer, Color outlineColour) {
        int width = 2000;
        int height = 2000;
        boolean[][] visited = new boolean[height][width];
        dfsOutline(start, visited, writer, outlineColour); //DFS over the whole component color only boundary vertices
    }

    private static void dfsOutline(PixelVertex vertex, boolean[][] visited, PixelWriter writer, Color outlineColour) {
        if (vertex == null) return;
        
        int x = vertex.getX();
        int y = vertex.getY();

        // Checking bounds
        if (y < 0 || y >= visited.length || x < 0 || x >= visited[0].length) return;
        if (visited[y][x]) return; //avoid revisiting

        visited[y][x] = true;

        // testing boundary, if degree < 4, not all neighbours present
        if (vertex.getDegree() < 4) {
            writer.setPixel(x, y, outlineColour);
        }

        for (PixelVertex neighbour : vertex.getNeighbours()) {
            dfsOutline(neighbour, visited, writer, outlineColour);
        }
    }

    public static void OutlineRegionBFS(PixelVertex start, PixelWriter writer, Color outlineColour) {
        int width = 2000;
        int height = 2000;
        boolean[][] visited = new boolean[height][width];

        Queue<PixelVertex> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            PixelVertex current = queue.poll();
            if (current == null) continue;
            
            int cx = current.getX();
            int cy = current.getY();

            // Bounds checking
            if (cy < 0 || cy >= visited.length || cx < 0 || cx >= visited[0].length) continue;
            if (visited[cy][cx]) continue;

            visited[cy][cx] = true;

            // Only outline pixels that are on the boundary (degree < 4)
            if (current.getDegree() < 4) {
                writer.setPixel(cx, cy, outlineColour);
            }

            //explore through neighbours
            for (PixelVertex neighbour : current.getNeighbours()) {
                if (neighbour != null) {
                    queue.add(neighbour);
                }
            }
        }
    }

    public static int CountComponents(PixelGraph graph) {
        //use image dimensions from the graph
        int width = graph.getWidth();
        int height = graph.getHeight();
        boolean[][] visited = new boolean[height][width];

        int count = 0;

        //scan every coordinate whenever an univisited coordinate is found
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!visited[y][x]) {
                    PixelVertex vertex = graph.getPixelVertex(x, y);
                    if (vertex != null) {
                        //Mark the whole componenet reachable
                        dfsMark(vertex, visited);
                        count++; //one component accounted for
                    }
                }
            }
        }

        return count;
    }

    private static void dfsMark(PixelVertex vertex, boolean[][] visited) {
        if (vertex == null) return;
        
        int x = vertex.getX();
        int y = vertex.getY();

        // Bounds check
        if (y < 0 || y >= visited.length || x < 0 || x >= visited[0].length) return;
        if (visited[y][x]) return;

        visited[y][x] = true; //marking current

        for (PixelVertex neighbour : vertex.getNeighbours()) {
            dfsMark(neighbour, visited); //recursing through the entire component
        }
    }
}