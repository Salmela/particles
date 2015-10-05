package org.tiralab.particles;

public class Testing {
	private Model model;
	private Storage storage;
	private int frameEnd;

	public Testing(String[] args) {
		//this.models[0] = new Stars();
		//this.models[1] = new Fluid();
		//this.storages[0] = new DirectStorage();
		//this.storages[1] = new ArrayStorage();
		//this.storages[2] = new TreeStorage();

		this.model = new Stars();
		this.storage = new DirectStorage();
		this.frameEnd = 250;

		this.storage.setModel(this.model);
	}

	public long runOnce() {
		long startTime, endTime;
		int i;

		this.model.reset();

		startTime = System.currentTimeMillis();
		for(i = 0; i < this.frameEnd; i++) {
			this.model.simulate();
		}
		endTime = System.currentTimeMillis();

		return (endTime - startTime);
	}

	public void run() {
		long timeSum, time;
		int i, tries;

		timeSum = 0;
		tries = 20;

		for(i = 0; i < tries; i++) {
			timeSum += this.runOnce();
		}
		time = timeSum / tries;

		System.out.println("Time of " + this.frameEnd + " iterations is " + time);
	}

	public static void main(String[] args) {
		Testing test;

		test = new Testing(args);
		test.run();
	}
}
