/**
 * 
 */
package lighting;

import primitives.*;
/**The interface represents common operations on types of light sources
 * @author Menucha and Yael
 *
 */
public interface LightSource {
	
	/**gets intensity light on point (IL)
	 * @param p - point on body geometry
	 * @return color of pixel in this point*/
	public Color getIntensity(Point p);
	
	/**The method calculates a unit vector from the light source to a point in a geometric body
	 * not support to zero vector!
	 * @param p - point on body geometry
	 * @return  a unit vector from the light source to a point in a geometric body */
	public Vector getL(Point p);


}
