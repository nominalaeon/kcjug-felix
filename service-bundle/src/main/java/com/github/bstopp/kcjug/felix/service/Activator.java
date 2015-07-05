
package com.github.bstopp.kcjug.felix.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Twitter Service Bundle.");

    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Stopping Twitter Service Bundle.");
    }

}
