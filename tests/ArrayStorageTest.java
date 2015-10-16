package org.tiralab.particles;

import org.tiralab.particles.storages.Storage;
import org.tiralab.particles.storages.ArrayStorage;

public class ArrayStorageTest extends StorageTest {
	@Override
	public Storage createInstance() {
		return new ArrayStorage();
	}
}