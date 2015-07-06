
package com.github.bstopp.kcjug.felix.dao;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting DAO Bundle.");

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping DAO Bundle.");
    }

}
