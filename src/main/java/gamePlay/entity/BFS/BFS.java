package gamePlay.entity.BFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    static int mapRow = 16;
    static int mapCol = 16;
    public static boolean visited[][] = new boolean[mapRow][mapCol];

    // Direction vectors
    static int dRow[] = { -1, 0, 1, 0 };
    static int dCol[] = { 0, 1, 0, -1 };

    // Function to check if a cell
    // be visited or not
    static boolean isValid(int[][] map, boolean[][] visited, int row, int col) {
        // If cell lies out of bounds
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) {
            return false;
        }
        // If cell is already visited
        if (visited[row][col])
            return false;
        // If cell is not grass
        if (map[row][col] != 1)
            return false;
        else return true;
    }

    // Function to perform the BFS traversal
    public static List<Point> findPath(int grid[][], boolean vis[][], int row, int col, int rowDes, int colDes) {

        // Stores indices of the matrix cells
        Queue<List<Point>> queue = new LinkedList<>();

        // Store current path
        List<Point> path = new ArrayList<>();
        // Mark the starting cell as visited
        path.add(new Point(row, col));
        // and push it into the queue
        queue.add(path);
        vis[row][col] = true;

        while (!queue.isEmpty()) {
            path = queue.poll();
            Point last = path.get(path.size() - 1);

            // fìnd
            if (last.x == rowDes && last.y == colDes) {
                return path;
            }

            int x = last.x;
            int y = last.y;
//             Go to the adjacent cells
            for (int i = 0; i < 4; i++) {
                int adjx = x + dRow[i];
                int adjy = y + dCol[i];

                if (isValid(grid, vis, adjx, adjy)) {
                    List<Point> newPath = new ArrayList<>(path);
                    newPath.add(new Point(adjx, adjy));
                    queue.add(newPath);
                    vis[adjx][adjy] = true;
                }
            }
        }
        return new ArrayList<Point>();
    }

    static void printPath(List<Point> path) {
        for(Point v : path)
        {
            System.out.print("(" + v.x + "," + v.y + ") ");
        }
        System.out.println();
    }
}
