package geometries;

import java.util.List;
import primitives.*;

/** The interface implements a function for intersection between a ray and shape.
 * @author Menuha and Yael */
public interface Intersectable {
	/** Function for finding intersection points.
	 * @param ray.
	 * @return list of intersections points. */
	List<Point> findIntersections(Ray ray);
}
