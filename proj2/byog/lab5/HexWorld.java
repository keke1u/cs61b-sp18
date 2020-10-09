package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 50;

    private static final long SEED = 55555555;
    private static final Random RANDOM = new Random();

    public static class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position(Position p) {
            this.x = p.x;
            this.y = p.y;
        }

        public boolean isSamePosition(Position p) {
            return p.x == this.x && p.y == this.y;
        }
    }
    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return s + 2 * effectiveI;
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the leftmost at the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     *   xxxx
     *  xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     *  xxxxxx
     *   xxxx
     *
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /** Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexgon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2!");
        }
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);
        }
    }

/*    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }*/

    /**
     * Highlight after this line for spoilers about my abstractions and approach.
     * Solution code is not provided on this website.
     *
     * First: I wrote a couple of methods that compute the bottom-left Position of a current hexagon’s neighbors.
     * For example, I wrote topRightNeighbor, which computed the right thing to pass
     * to addHexagon so that I could get my topRight neighbor.
     * I did not write JUnit tests for this because I knew it’d be easy to visually test,
     * though JUnit tests would have been fine. I then wrote bottomRight,
     * and ended up with 3 whole hexagons out of the 19 I wanted.
     *
     * Second: I tried to figure out how I could use bottomRight and topRight in some clever way to get the whole grid,
     * but was a bit stymied. I considered trying to use recursion in some way,
     * but it didn’t feel like the right solution for symmetry reasons
     * (too much weird backtracking).
     * I then thought about whether I’d be stuck having to write six different neighbor methods,
     * and decided that seemed excessive. All of this was done without any coding.
     * If I’d have started coding all this, it would have been a huge waste of time.”
     *
     * Third: Stepping back, I decided to try to find the “nicest” way to try to lay out my hexagons
     * and was ready to throw away my bottomRight and topRight methods entirely.
     * Then the key AHA moment occurred when I noticed that the world consists of 5 columns of 3, 4, 5, 4,
     * and 3 hexagons, respectively.
     *
     * Fourth: I wrote a method called drawRandomVerticalHexes that draws a column of N hexes,
     * each one with a random biome.
     *
     * Fifth: I wrote a main method that is a little ugly,
     * but basically calls drawRandomVerticalHexes five times,
     * one for each of the five columns of the world, consisting of 3, 4, 5, 4,
     * and 3 hexagons. To figure out the starting position for the “top” hex of each column,
     * I used the topRightNeighbor or bottomRightNeighbor on the old top, as appropriate.
     *
     * Sixth: I added some code to allow for interactivity so you can press a single key
     * to get a new random world and enjoyed playing around with it.
     * But interactivity will have to wait until next week’s lab for you guys.
     *
     * Submission
     */
    private static void toTopRight(Position p, int size) {
        p.x = p.x + 2 * size - 1;
        p.y += size;
    }

    private static void toBottomRight(Position p, int size) {
        p.x = p.x + 2 * size - 1;
        p.y -= size;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            default: return Tileset.MOUNTAIN;
        }
    }

    private static void drawRandomVerticalHexes(TETile[][] world, int hexNum, Position p, int size) {
        Position temp = new Position(p);
        for (int i = 0; i < hexNum; i += 1) {
            TETile t = randomTile();
            addHexgon(world, temp, size, t);
            // update temp position
            temp.y -= 2 * size;
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH * HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int size = 3;
        Position p = new Position(10, 30);
        drawRandomVerticalHexes(world, 3, p, size);
        toTopRight(p, size);
        drawRandomVerticalHexes(world, 4, p, size);
        toTopRight(p, size);
        drawRandomVerticalHexes(world, 5, p, size);
        toBottomRight(p, size);
        drawRandomVerticalHexes(world, 4, p, size);
        toBottomRight(p, size);
        drawRandomVerticalHexes(world, 3, p, size);

        // draw the world to the screen
        ter.renderFrame(world);
    }
}
