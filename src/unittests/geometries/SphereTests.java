package unittests.geometries;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;
import geometries.Intersectable.GeoPoint;

/** Unit tests for geometries.Sphere class
 * @author Menuha and Yael */
class SphereTests {

	/** Test method for {@link geometries.Sphere#getNormal(primitives.Point)}. */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		//TC01:
		Sphere sp = new Sphere(new Point(0, 0, 0), 1);
		try {
		assertEquals(new Vector(1, 0, 0), sp.getNormal(new Point(1, 0, 0)), "Normal calculation is incorrect");
		}
		catch(IllegalArgumentException e) {
		}
	}
	
	 /** Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}. */
    @Test
    public void testfindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
       
        // ============ Equivalence Partitions Tests ====================
        
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)	        
        result = sphere.findIntersections(new Ray(new Point(1, 0.5, 0), new Vector(-1, -1, -2)));
        assertEquals(1, result.size(), "Wrong number of points");
    
        
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(4, 10, 0), new Vector(1, 2, 0))), "The ray starts after the sphere");

        // =============== Boundary Values Tests ==================
        
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))), "The ray starts at sphere and goes outside");
        		

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(1, -2, 0), new Vector(0, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1, 1, 0), new Point(1, -1, 0)), result, "Ray crosses sphere");
        
        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(0, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        
        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(4, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points" );
        
        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(2.52,-5.02, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        
        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0)));
        assertNull( result, "Wrong number of points");
        assertNull( sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 1, 0))), "The ray starts at sphere and goes outside");
        // TC18: Ray starts after sphere (0 points)	        
        assertNull(sphere.findIntersections(new Ray(new Point(1, 2, 0), new Vector(0, 1, 0))), "The ray starts  after sphere");
        
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(-0.5,-0.5, 0), new Vector(-2, -1, 0))), "The ray starts before the tangent point");
        
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(-1,-1, 0))), "The ray starts at the tangent point");
        
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, -2, 1))), "The ray starts after the tangent point");
        
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-0.5, 0, 0), new Vector(0, -2, 1))), "The ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }
    

    /** Test method for {@link geometries.Sphere#findGeoIntersections(primitives.Ray, double)}. */
    @Test
    public void testfindGeoIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
       
        // ============ Equivalence Partitions Tests ====================
        
        // TC01: Ray starts before and crosses the sphere (2 points) but the distance from the ray head to the second point is bigger than maxDistance
        List<GeoPoint> result = sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)), 2);
        assertEquals(1, result.size(), "The ray end before the second point");

        // TC02: Ray starts before and crosses the sphere (2 points) but the distance from the ray head to the 2 points is bigger than maxDistance
        assertNull(sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)), 1), "The ray end before the 2 points");  
    }
}
