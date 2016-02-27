package mask;

/**
 * @author JonathanZwiebel
 * @version 1/23/16
 *
 * TODO: Differentiate this from the image mask
 */
interface BooleanMask {
    boolean[][] mask(float ... args);
}
