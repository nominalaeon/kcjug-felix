
package com.github.bstopp.kcjug.felix;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	public void start(BundleContext context) {
		System.out.println("Starting Twitter Service Bundle.");
	}

	public void stop(BundleContext context) {
		System.out.println("Stopping Twitter Service Bundle.");
	}

}