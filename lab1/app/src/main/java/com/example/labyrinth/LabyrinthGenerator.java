package com.example.labyrinth;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.function.Consumer;

enum Direction {
    UP(point -> point.y--),
    LEFT(point -> point.x--),
    RIGHT(point -> point.x++),
    DOWN(point -> point.y++);

    private final Consumer<Point> direction;

    Direction(Consumer<Point> direction) {
        this.direction = direction;
    }

    public Point updatePoint(Point point) {
        direction.accept(point);
        return point;
    }

}

enum Cell{
    CAN_MOVE_TO(true),
    WALL(true),
    NOT_VISITED_YET(false),
    NOT_PERMANENT_WALL(false);

    private final boolean isPermanent;

    Cell(boolean isPermanent) {
        this.isPermanent = isPermanent;
    }

    public boolean isPermanent(){
        return isPermanent;
    }
}

public class LabyrinthGenerator {
    private Cell[][] matrix = null;
    private final int xLength;
    private final int yLength;
    private int xExit, yExit;
    private final Random random;

    public static Labyrinth generate(int xLength, int yLength,  long seed){
        return new LabyrinthGenerator(xLength, yLength, seed).getLabyrinth();
    }


    private LabyrinthGenerator(int xLength, int yLength,  long seed){
        this.xLength = xLength;
        this.yLength = yLength;
        random = new Random(seed);
        this.generate();
    }

    public Labyrinth getLabyrinth() { return new Labyrinth(matrix,new Point(xExit,yExit) );}


    public Point getExitPoint(){
        return new Point(xExit,yExit);
    }

    public void generate() {
        matrix = new Cell[yLength][xLength];
        generateWalls();
        generatePasses();
        generateExit();
    }

    private void generateExit() {
        Integer[][] subMatrix = new Integer[yLength][xLength];
        for (int i = 0; i < yLength; i++)
            for (int j = 0; j < xLength; j++)
                subMatrix[i][j] = matrix[i][j].equals(Cell.WALL) ? 1 : 0;
        subMatrix[0][0] = subMatrix[1][0] = subMatrix[0][1] = 1;
        matrix[0][0] = matrix[1][0] = matrix[0][1] = Cell.WALL;
        dfsPass(subMatrix);

        int maximumValue = subMatrix[1][1];
        for (int i = 1; i < yLength - 1; i++) {
            if (subMatrix[i][1] > maximumValue) {
                maximumValue = subMatrix[i][1];
                yExit = i;
                xExit = 0;
            }
            if (subMatrix[i][xLength - 2] > maximumValue) {
                maximumValue = subMatrix[i][xLength - 2];
                yExit = i;
                xExit = xLength - 1;
            }
        }

        for (int i = 1; i < xLength - 1; i++) {
            if (subMatrix[1][i] > maximumValue) {
                maximumValue = subMatrix[1][i];
                yExit = 0;
                xExit = i;
            }
            if (subMatrix[yLength - 2][i] > maximumValue) {
                maximumValue = subMatrix[yLength - 2][i];
                yExit = yLength - 1;
                xExit = i;
            }
        }

        matrix[yExit][xExit] = Cell.CAN_MOVE_TO;
    }

    private void dfsPass(Integer[][] subMatrix) {
        Queue<Triple> queue = new LinkedList<>();
        queue.add(new Triple(1, 1, 100));

        List<Direction> availableMoves;
        while (!queue.isEmpty()) {
            Triple cur = queue.remove();
            availableMoves = getReadyPasses(cur.a, cur.b, subMatrix);
            subMatrix[cur.b][cur.a] = cur.c;

            if (availableMoves.contains(Direction.UP))
                queue.add(new Triple(cur.a, cur.b + 1, cur.c + 1));
            if (availableMoves.contains(Direction.RIGHT))
                queue.add(new Triple(cur.a + 1, cur.b, cur.c + 1));
            if (availableMoves.contains(Direction.LEFT))
                queue.add(new Triple(cur.a - 1, cur.b, cur.c + 1));
            if (availableMoves.contains(Direction.DOWN))
                queue.add(new Triple(cur.a, cur.b - 1, cur.c + 1));
        }
    }

    private <T> List<Direction> getPassesTemplate(int x, int y, T equals, T[][] matrix) {
        List<Direction> res = new ArrayList<>();
        if (matrix[y - 1][x] == equals)
            res.add(Direction.DOWN);
        if (matrix[y + 1][x] == equals)
            res.add(Direction.UP);
        if (matrix[y][x - 1] == equals)
            res.add(Direction.LEFT);
        if (matrix[y][x + 1] == equals)
            res.add(Direction.RIGHT);
        return res;
    }


    private List<Direction> getPossiblePasses(int x, int y) {
        return getPassesTemplate(x, y, Cell.NOT_VISITED_YET, this.matrix);
    }

    private List<Direction> getTemporaryWalls(int x, int y) {
        return getPassesTemplate(x, y, Cell.NOT_PERMANENT_WALL, this.matrix);
    }

    private List<Direction> getReadyPasses(int x, int y, Integer[][] subMatrix) {
        return getPassesTemplate(x, y, 0, subMatrix);
    }

    private void placeTemporaryWalls(int x, int y) {
        placeTemporaryWall(x, y-1);
        placeTemporaryWall(x, y+1);
        placeTemporaryWall(x - 1, y);
        placeTemporaryWall(x + 1, y);
    }

    private void placeTemporaryWall(int x, int y){
        if (!matrix[y][x].isPermanent())
            matrix[y][x] = Cell.NOT_PERMANENT_WALL;
    }

    private void replaceWalls() {
        for (int i = 1; i < yLength - 1; i++)
            for (int j = 1; j < xLength - 1; j++)
                if (matrix[i][j] == Cell.NOT_PERMANENT_WALL)
                    matrix[i][j] = Cell.WALL;
    }

    private List<Integer> getRandomPass(List<Direction> passes, int x, int y) {
        List<Integer> res = new ArrayList<>();
        if (!passes.isEmpty()) {
            int rand = random.nextInt(passes.size());
            int value = rand % passes.size();
            Direction pass = passes.get(value);
            if (pass == Direction.UP) {
                res.add(x);
                res.add(y + 1);
            }
            if (pass == Direction.DOWN) {
                res.add(x);
                res.add(y - 1);
            }
            if (pass == Direction.RIGHT) {
                res.add(x + 1);
                res.add(y);
            }
            if (pass == Direction.LEFT) {
                res.add(x - 1);
                res.add(y);
            }
        }
        return res;
    }

    private void passCell(int x, int y) {
        matrix[y][x] = Cell.CAN_MOVE_TO;
        List<Direction> possiblePasses = getPossiblePasses(x, y);
        if (possiblePasses.size() > 0) {
            List<Integer> randomPass = getRandomPass(possiblePasses, x, y);
            placeTemporaryWalls(x, y);
            passCell(randomPass.get(0), randomPass.get(1));
        }

        // If temporary wall has unvisited cells
        List<Direction> possibleWalls = getTemporaryWalls(x, y);
        if (!possibleWalls.isEmpty()) {

            List<Integer> randomWall = getRandomPass(possibleWalls, x, y);
            List<Direction> possiblePassesWall =
                    getPossiblePasses(randomWall.get(0), randomWall.get(1));

            if (!possiblePassesWall.isEmpty()) {
                List<Integer> randomWallPass =
                        getRandomPass(possiblePassesWall, randomWall.get(0), randomWall.get(1));
                placeTemporaryWalls(randomWall.get(0), randomWall.get(1));
                matrix[randomWall.get(1)][randomWall.get(0)] = Cell.CAN_MOVE_TO;
                passCell(randomWallPass.get(0), randomWallPass.get(1));
            }
        }

    }

    private void generatePasses() {
        passCell(1, 1);
        replaceWalls();
    }

    private void generateWalls() {
        for (int i = 0; i < yLength; i++)
            matrix[i][0] = matrix[i][xLength - 1] = Cell.WALL;
        for (int i = 0; i < xLength; i++)
            matrix[0][i] = matrix[yLength - 1][i] = Cell.WALL;

        // Fill labyrinth with unvisited tag
        for (int i = 1; i < yLength - 1; i++)
            for (int j = 1; j < xLength - 1; j++)
                matrix[i][j] = Cell.NOT_VISITED_YET;

        // Let character to updatePoint into labyrinth
        matrix[0][1] = matrix[1][0] = matrix[0][0] = Cell.CAN_MOVE_TO;
    }
}

class Triple {
    public int a;
    public int b;
    public int c;
    Triple(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}