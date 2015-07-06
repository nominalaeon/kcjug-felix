
package com.github.bstopp.kcjug.felix.twitter.job;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;

import com.github.bstopp.kcjug.felix.twitter.dao.TwitterDao;
import com.github.bstopp.kcjug.felix.twitter.service.TwitterService;

@Component(immediate = true, enabled = true)
public class TwitterSyncJob {

    @Reference
    private TwitterService service;

    @Reference
    private TwitterDao dao;

    protected void activate(ComponentContext context) throws Exception {

        System.out.println("Using Twitter Sync Job");

        if (!dao.save(service.getAccount())) {
            System.out.println("Something went wrong and could not save Account.");
        }

    }

}
