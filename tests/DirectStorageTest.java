package org.tiralab.particles;

public class DirectStorageTest extends StorageTest {
	@Override
	public Storage createInstance() {
		return new DirectStorage();
	}
}