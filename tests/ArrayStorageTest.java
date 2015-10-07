package org.tiralab.particles;

public class ArrayStorageTest extends StorageTest {
	@Override
	public Storage createInstance() {
		return new ArrayStorage();
	}
}