import javafx.scene.shape.Rectangle;

/**
 * @author Clara
 * @version 1.0 12/10/19
 *
 * This class can make a square
 * It also makes and can set the value of a boolean off and of a boolean was
 * Finally, it makes and can get and set the value of int neigh
 */
class Square {
    private boolean off;
    private boolean was;
    private int neigh;
    private Rectangle sqr;

    /**
     * Square constructor
     * Makes a rectangle of the  correct size
     * Sets off to true, was to false, and neigh to 0
     */
    Square() {
        this.off = true;
        this.was = false;
        this.neigh = 0;
        this.sqr = new Rectangle(10, 10);
    }

    /**
     * @param off_B boolean from an outside class
     *
     * Changes the value of boolean off
     */
    void setOff (Boolean off_B) {
        off = off_B;
    }

    /**
     * @param was_B boolean from an outside class
     *
     * Changes the value of boolean was
     */
    void setWas (Boolean was_B) {
        was = was_B;
    }

    /**
     * @param neigh_I integer from an outside class
     *
     * Changes the value of int neigh
     */
    void setNeigh (int neigh_I) {neigh = neigh_I; }

    /**
     * @return the value of boolean off
     */
    Boolean getOff () {
        return off;
    }

    /**
     * @return the boolean was
     */
    Boolean getWas () {
        return was;
    }

    /**
     * @return the integer neigh
     */
    int getNeigh () { return neigh; }

    /**
     * @return the rectangle sqr
     */
    Rectangle getSqr () { return sqr; }
}
