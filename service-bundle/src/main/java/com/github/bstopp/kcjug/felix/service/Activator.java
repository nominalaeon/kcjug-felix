
package com.github.bstopp.kcjug.felix.service;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import com.github.bstopp.kcjug.felix.twitter.service.TwitterService;
import com.github.bstopp.kcjug.felix.twitter.service.impl.Twitter4jService;

public class Activator implements BundleActivator, ServiceListener {

    private BundleContext bundleContext;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Twitter Service Bundle.");
        bundleContext = context;
        bundleContext.addServiceListener(this);

        Hashtable<String, String> props = new Hashtable<String, String>();
        context.registerService(TwitterService.class.getName(), new Twitter4jService(), props);

    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Stopping Twitter Service Bundle.");
    }

    @Override
    public void serviceChanged(ServiceEvent event) {

        if (event.getType() == ServiceEvent.REGISTERED) {
            ServiceReference<?> ref = event.getServiceReference();
            TwitterService service = (TwitterService) bundleContext.getService(ref);
            System.out.println(service.getAccount().getScreenName());
        }

    }

}
