
package com.github.bstopp.kcjug.felix.twitter.dao.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.bstopp.kcjug.felix.twitter.Account;
import com.github.bstopp.kcjug.felix.twitter.dao.TwitterDao;

//@formatter:off
@Service
@Component(enabled= true, immediate = true,
      label= "Twitter DAO Service")
@Properties({
    @Property(  name = Constants.SERVICE_DESCRIPTION,
                value = "Saves account information to a file with the name of the screen name."),
})
//@formatter:on
public class FileTwitterDao implements TwitterDao {

    @Override
    public boolean save(Account account) {

        if (StringUtils.isBlank(account.getScreenName())) {
            throw new IllegalArgumentException("Account screen name must be specified.");
        }

        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.INDENT_OUTPUT, true);
        om.configure(Feature.AUTO_CLOSE_TARGET, true);
        try {
            om.writeValue(new File(account.getScreenName() + ".json"), account);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Activate
    protected void activate(ComponentContext context) {
        System.out.println("Starting DAO Service");
    }

}
