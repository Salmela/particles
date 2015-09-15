package org.tiralab.particles;

public interface Storage {
	Particle[] get_objects_at_area(float x, float y, float w, float h);
	Particle[] get_object_at_point(float x, float y);
	void add_particle(Particle particle);
	void remove_particle(Particle particle);
	void move_particle(Particle particle);
}
