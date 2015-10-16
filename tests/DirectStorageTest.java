package org.tiralab.particles;

import org.tiralab.particles.storages.DirectStorage;
import org.tiralab.particles.storages.Storage;

public class DirectStorageTest extends StorageTest {
	@Override
	public Storage createInstance() {
		return new DirectStorage();
	}
}