package org.tiralab.particles.main;

import org.tiralab.particles.storages.ArrayStorage;
import org.tiralab.particles.storages.DirectStorage;
import org.tiralab.particles.models.Fluid;
import org.tiralab.particles.models.Model;
import org.tiralab.particles.models.Stars;
import org.tiralab.particles.storages.Storage;
import org.tiralab.particles.storages.TreeStorage;

/**
 * The commandline interface for the program.
 */
public class Testing {
	private Model model;
	private Storage storage;
	private long frameEnd, count;

	/**
	 * Constructor for the command line tool.
	 *
	 * @param args The command line arguments.
	 */
	public Testing(String[] args) {
		int i;
		for(i = 0; i < args.length;) {
			i += this.commandExecute(args, i);
		}

		this.model = new Stars();
		this.storage = new DirectStorage();
		this.frameEnd = 50;
		this.count = 100;

		this.storage.setModel(this.model);
	}

	private int commandExecute(String[] args, int i) {
		String command = args[i];
		if(command.charAt(0) != '-' ||
		   command.charAt(1) != '-') {
			System.out.println("The command line flag must start with two dashes.");
			this.printHelp();
			return -1;
		}

		command = command.substring(2);
		if(command.equals("help")) {
			this.printHelp();
			return -1;
		} else if(command.equals("model")) {
			this.modelSwitch(args[i + 1]);
		} else if(command.equals("storage")) {
			this.storageSwitch(args[i + 1]);
		} else if(command.equals("iterations")) {
			this.frameEnd = Long.parseLong(args[i + 1], 10);
		} else if(command.equals("count")) {
			this.count = Long.parseLong(args[i + 1], 10);
		} else {
			System.out.println("Unknown command line flag.");
			System.exit(1);
			return -1;
		}
		return 2;
	}

	private void printHelp() {
		System.out.println("USAGE: ./testing [OPTIONS]");
		System.out.println("");
		System.out.println("Arguments:");
		System.out.println(" --model MODEL\t\tThe model (default is Stars)");
		System.out.println(" --storage STORAGE\tThe storage (default is Direct)");
		System.out.println(" --iterations NUMBER\tThe number of iterations to be");
		System.out.println("\t\t\texecuted (default is 250)");
		System.out.println(" --count NUMBER\tThe particle count runned");
		System.out.println("");
		System.out.println("Model:");
		System.out.println(" Stars\tNbody simulator");
		System.out.println(" Fluid\tFluid simulator");
		System.out.println("");
		System.out.println("Storages:");
		System.out.println(" Direct\tNon-accelerated storage");
		System.out.println(" Array\t2D array as storage");
		System.out.println(" Tree\tQuadtree based storage");
		System.exit(1);
	}

	private void modelSwitch(String model) {
		model = model.toLowerCase();
		if(model.equals("stars")) {
			this.model = new Stars();
		} else if(model.equals("fluid")){
			this.model = new Fluid();
		}
	}

	private void storageSwitch(String storage) {
		storage = storage.toLowerCase();
		if(storage.equals("direct")) {
			this.storage = new DirectStorage();
		} else if(storage.equals("array")){
			this.storage = new ArrayStorage();
		} else if(storage.equals("tree")){
			this.storage = new TreeStorage();
		}
	}

	private long runOnce() {
		long startTime, endTime;
		int i;

		this.model.reset(this.count);

		startTime = System.currentTimeMillis();
		for(i = 0; i < this.frameEnd; i++) {
			this.model.simulate();
		}
		endTime = System.currentTimeMillis();

		return (endTime - startTime);
	}

	private long runCompute() {
		long timeSum, time;
		int i, tries;

		timeSum = 0;
		tries = 20;

		for(i = 0; i < tries; i++) {
			timeSum += this.runOnce();
		}
		time = timeSum / tries;

		return time;
	}

	/**
	 * Run the performance tests.
	 */
	public void run() {
		while(true) {
			long time = this.runCompute();
			System.out.println("Time of " + this.frameEnd + " iterations and " + this.count + " particles is " + time + "ms");

			this.count += 100;
		}
	}

	/**
	 * The main function for the command line tool.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		Testing test;

		test = new Testing(args);
		test.run();
	}
}
