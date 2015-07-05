
package com.github.bstopp.kcjug.felix.twitter.job;

import java.io.IOException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.bstopp.kcjug.felix.twitter.service.TwitterService;

@Component(immediate = true, enabled = true)
public class TwitterSyncJob {

    @Reference
    private TwitterService service;

    protected void activate(ComponentContext context) throws Exception {

        System.out.println("Using Twitter Sync Job");
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
