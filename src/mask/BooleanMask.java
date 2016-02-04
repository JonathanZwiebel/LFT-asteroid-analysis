package mask;

/**
 * @author JonathanZwiebel
 * @version 1/23/16
 *
 * TODO: Differentiate this from the image mask
 */
public interface BooleanMask {
    boolean[][] mask(float ... args);
}
