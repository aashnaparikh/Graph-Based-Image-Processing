# Graph-Based Image Processing Toolkit

A Java implementation of efficient image processing algorithms using graph theory and traversal techniques. This toolkit transforms images into pixel graphs and applies DFS/BFS algorithms to perform operations like flood fill, region outlining, and component detection.

## Overview

This project demonstrates the powerful intersection of graph theory and image processing. By representing images as graphs where each pixel is a vertex connected to adjacent pixels of the same color, we can leverage classic graph algorithms to solve common image manipulation problems efficiently.

## Features

### Core Algorithms

- **Flood Fill (DFS & BFS)** - Paint bucket tool implementation
- **Region Outlining (DFS & BFS)** - Automatic boundary detection
- **Component Counting** - Identify distinct color regions in images

### Key Capabilities

- Handles images up to 2000×2000 pixels
- Support for both depth-first and breadth-first traversal strategies
- Automatic neighbor detection based on color similarity
- Efficient boundary detection using vertex degree analysis

## How It Works

### 1. Graph Construction

The `PixelGraph` class converts a 2D image into a graph structure:

```java
// Image → Graph conversion
PixelGraph graph = new PixelGraph(imagePixels);
```

**Process:**
- Each pixel becomes a vertex with (x, y) coordinates
- Vertices are connected if adjacent pixels share the same color
- Maximum degree of 4 (up, down, left, right neighbors)

### 2. Graph Representation

The `PixelVertex` class represents each pixel:

```java
public class PixelVertex {
    private int x, y;                      // Pixel coordinates
    private List<PixelVertex> neighbours;  // Adjacent same-color pixels
}
```

**Operations:**
- `addNeighbour()` - Create bidirectional edge
- `getDegree()` - Returns number of neighbors (1-4)
- `isNeighbour()` - Check adjacency

### 3. Algorithm Applications

#### Flood Fill

Fill a connected region with a target color (like MS Paint's bucket tool):

```java
FloodFillDFS(startVertex, writer, Color.RED);
FloodFillBFS(startVertex, writer, Color.BLUE);
```

**Algorithm:**
1. Start at clicked pixel
2. Visit all connected same-color pixels
3. Paint each visited pixel with target color
4. Use visited array to prevent cycles

**Time Complexity:** O(V + E) where V = pixels in region, E = connections

#### Region Outlining

Automatically detect and draw boundaries around color regions:

```java
OutlineRegionDFS(startVertex, writer, Color.BLACK);
OutlineRegionBFS(startVertex, writer, Color.WHITE);
```

**Algorithm:**
1. Traverse all pixels in the region
2. Identify boundary pixels (degree < 4)
3. Paint only boundary pixels with outline color

**Key Insight:** Interior pixels have 4 neighbors, boundary pixels have fewer

#### Component Counting

Count the number of distinct color regions in an image:

```java
int numRegions = CountComponents(graph);
```

**Algorithm:**
1. Scan entire image pixel-by-pixel
2. For each unvisited pixel, launch DFS to mark entire component
3. Increment counter for each new component found

**Application:** Object detection, image segmentation

## Implementation Details

### DFS vs BFS Comparison

| Aspect | DFS (Depth-First) | BFS (Breadth-First) |
|--------|-------------------|---------------------|
| Data Structure | Recursion (stack) | Queue |
| Memory | O(h) where h = depth | O(w) where w = width |
| Fill Pattern | Deep "tendrils" | Radial "ripple" |
| Use Case | Deep regions | Level-by-level processing |

### Visited Array Strategy

```java
boolean[][] visited = new boolean[height][width];
```

**Why it's needed:**
- Prevents infinite loops in cyclic graphs
- Ensures O(V + E) time complexity
- Tracks processed pixels efficiently

### Boundary Detection Logic

```java
if (vertex.getDegree() < 4) {
    writer.setPixel(x, y, outlineColour);
}
```

**Reasoning:**
- Interior pixels have exactly 4 neighbors (N, S, E, W)
- Edge pixels have 2-3 neighbors
- Corner pixels have 2 neighbors
- Degree < 4 indicates pixel touches image boundary or different color

## File Structure

```
.
├── A3Algorithms.java      # Main algorithm implementations
├── PixelGraph.java        # Graph construction from image
├── PixelVertex.java       # Vertex representation with neighbors
└── README.md             # This file
```

## Usage Example

```java
// Load image into 2D color array
Color[][] imagePixels = loadImage("photo.jpg");

// Build pixel graph
PixelGraph graph = new PixelGraph(imagePixels);

// Perform flood fill from pixel (100, 150)
PixelVertex start = graph.getPixelVertex(100, 150);
FloodFillBFS(start, writer, Color.GREEN);

// Outline all regions in the image
for (int y = 0; y < graph.getHeight(); y++) {
    for (int x = 0; x < graph.getWidth(); x++) {
        PixelVertex v = graph.getPixelVertex(x, y);
        OutlineRegionDFS(v, writer, Color.BLACK);
    }
}

// Count distinct regions
int regions = CountComponents(graph);
System.out.println("Found " + regions + " distinct regions");
```

## Algorithm Complexity Analysis

### Flood Fill
- **Time:** O(V + E) where V = pixels in region, E = edges between them
- **Space:** O(W × H) for visited array + O(V) for recursion/queue
- **Best Case:** Single pixel region - O(1)
- **Worst Case:** Entire image same color - O(W × H)

### Region Outlining
- **Time:** O(V + E) - must visit all pixels in component
- **Space:** O(W × H) for visited array
- **Optimization:** Only paints boundary pixels

### Component Counting
- **Time:** O(W × H) - scans entire image once
- **Space:** O(W × H) for visited array
- **Visits:** Each pixel visited exactly once

## Technical Highlights

### Graph Theory Concepts Applied

1. **Connected Components** - Regions of same-color pixels
2. **Graph Traversal** - DFS and BFS for region exploration
3. **Degree Analysis** - Boundary detection via vertex degree
4. **Adjacency Lists** - Efficient neighbor storage

### Design Patterns

- **Separation of Concerns** - Graph construction separate from algorithms
- **Strategy Pattern** - Interchangeable DFS/BFS implementations
- **Bidirectional Edges** - Automatic reciprocal neighbor addition

### Optimizations

- **Single Pass Construction** - Build all vertices before adding edges
- **In-place Coloring** - Modify image directly via PixelWriter
- **Early Termination** - Skip null vertices immediately
- **Bounds Checking** - Prevent array index errors

## Real-World Applications

- **Image Editing Software** - Paint bucket, magic wand tools
- **Computer Vision** - Object segmentation, blob detection
- **Medical Imaging** - Tumor boundary detection
- **Geographic Information Systems** - Land region identification
- **Photo Manipulation** - Background removal, selective coloring
- **Game Development** - Terrain mapping, collision detection

## Potential Extensions

### Algorithm Enhancements
- [ ] Color tolerance threshold for fuzzy matching
- [ ] Multi-threaded processing for large images
- [ ] Pattern fill (textures instead of solid colors)
- [ ] Gradient fills along component paths

### Additional Operations
- [ ] Erosion and dilation morphology
- [ ] Skeleton extraction from regions
- [ ] Convex hull computation around components
- [ ] Distance transform from boundaries

### Performance Improvements
- [ ] Sparse graph representation for large uniform regions
- [ ] Incremental updates (modify small regions without rebuilding)
- [ ] GPU acceleration for parallel pixel processing

## Limitations

- Maximum image size: 2000×2000 pixels (configurable)
- Color matching is exact (no tolerance threshold)
- Undirected graphs only (bidirectional edges)
- No support for diagonal neighbors (4-connectivity only)

## Dependencies

- Java 8 or higher
- `java.awt.Color` - Color representation
- `java.util.LinkedList` - BFS queue implementation
- Custom `PixelWriter` interface - Image output abstraction

## Author

Aashna Parikh (V01057821)

## Acknowledgments

- Graph algorithms based on classic computer science literature
- Flood fill concept from early graphics editors
- Component counting adapted from connected components algorithm

## References

- *Introduction to Algorithms* (CLRS) - Graph traversal algorithms
- *Computer Graphics: Principles and Practice* - Image processing techniques
- Robert Sedgewick's *Algorithms* - Graph data structures

*This project demonstrates the practical application of graph theory to solve real-world image processing problems, bridging theoretical computer science with visual computing.*
