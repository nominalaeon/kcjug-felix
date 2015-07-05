
package com.github.bstopp.kcjug.felix.service;

import java.io.IOException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.bstopp.kcjug.felix.twitter.service.TwitterService;

public class Activator implements BundleActivator, ServiceListener {

    private BundleContext bundleContext;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Twitter Service Bundle.");
        bundleContext = context;
        bundleContext.addServiceListener(this);

    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Stopping Twitter Service Bundle.");
    }

    @Override
    public void serviceChanged(ServiceEvent event) {

        System.out.println("Event occured, type: " + event.getType());

        if (event.getType() == ServiceEvent.REGISTERED) {

            ServiceReference<?> ref = event.getServiceReference();
            System.out.println("Got Twitter Service Reference.");
            TwitterService service = (TwitterService) bundleContext.getService(ref);

            if (service != null) {
                System.out.println("Got Twitter Service.");
                ObjectMapper om = new ObjectMapper();
                om.configure(SerializationFeature.INDENT_OUTPUT, true);
                om.configure(Feature.AUTO_CLOSE_TARGET, false);
                try {
                    om.writeValue(System.out, service.getAccount());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
