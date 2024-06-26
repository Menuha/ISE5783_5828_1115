package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/** Sphere class represents sphere in 3D Cartesian coordinate system 
 * @author Menuha and Yael*/
public class Sphere extends RadialGeometry {
	// the sphere's center point
	private final Point center;
	
	/** Sphere constructor based on center point and radius
	 * @param center Point
	 * @param radius value */
	public Sphere(Point center, double radius) {
		super(radius);
		this.center = center;
	}
	
	/** @return the sphere's center point */
	public Point getCenter() { 
		return center; 
	}
	
	@Override
	public Vector getNormal(Point point) {
		Vector v = point.subtract(center);
		return v.normalize();
	}

	@Override
	public String toString() { 
		return "Sphere [center=" + center.toString() + ", radius=" + radius + "]"; 
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		double d0 = alignZero(radius - maxDistance);
		if (ray.getP0().equals(center) && d0 <= 0) // if the head of the ray is the center, the point p0 is on the radius
			return List.of(new GeoPoint(this, ray.getPoint(radius)));
		Vector u = center.subtract(ray.getP0());
		double tM = alignZero(ray.getDir().dotProduct(u));
		double d = alignZero(Math.sqrt(u.lengthSquared()- tM * tM));
		double tH = alignZero(Math.sqrt(radius * radius - d * d));
		double t1 = alignZero(tM + tH);
		double t2 = alignZero(tM - tH);
		
		if (d > radius)
			return null; // there are no instruction points

		double d1 = alignZero(t1 - maxDistance);
		double d2 = alignZero(t2 - maxDistance);
		if (t1 > 0 && t2 > 0 && d1 <= 0 && d2 <= 0)
			return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
		if (t1 > 0 && d1 <= 0)
			return List.of(new GeoPoint(this, ray.getPoint(t1)));
		if (t2 > 0 && d2 <= 0)
			return List.of(new GeoPoint(this, ray.getPoint(t2)));
		else
			return null;
	}

	/*	@Override
	public List<Point> findIntersections(Ray ray) {
		if (ray.getP0().equals(center)) // if the head of the ray is the center, the point p0 is on the radius
			return List.of(ray.getPoint(radius));
		Vector u = center.subtract(ray.getP0());
		double tM = alignZero(ray.getDir().dotProduct(u));
		double d = alignZero(Math.sqrt(u.lengthSquared()- tM * tM));
		double tH = alignZero(Math.sqrt(radius * radius - d * d));
		double t1 = alignZero(tM + tH);
		double t2 = alignZero(tM - tH);
		
		if (d > radius)
			return null; // there are no instruction points
		if (t1 <= 0 && t2 <= 0)
			return null;
		
		if (t1 > 0 && t2 >0)
			return List.of(ray.getPoint(t1),ray.getPoint(t2));
		if (t1 > 0)
			return List.of(ray.getPoint(t1));
		else
			return List.of(ray.getPoint(t2));
	}*/
	
}