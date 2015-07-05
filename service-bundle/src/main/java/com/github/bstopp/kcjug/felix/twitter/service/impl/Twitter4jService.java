
package com.github.bstopp.kcjug.felix.twitter.service.impl;

import java.util.Dictionary;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.framework.ServiceException;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.component.ComponentContext;

import com.github.bstopp.kcjug.felix.twitter.Account;
import com.github.bstopp.kcjug.felix.twitter.service.TwitterService;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

//@formatter:off
@Service
@Component(metatype = true, enabled= true, immediate = true,
        policy = ConfigurationPolicy.REQUIRE,
        label= "Twitter4J API Service",
        description= "Pull information from twitter using Twitter4J Library.")
//@formatter:on
public class Twitter4jService implements TwitterService {

    @Property(label = "Twitter Screen Name")
    private static final String SCREEN_NAME_PROP = "screen.name";

    @Property(label = "Twitter API Consumer Key")
    private static final String CONSUMER_KEY_PROP = "consumer.key";

    @Property(label = "Twitter API Consumer Secret")
    private static final String CONSUMER_SECRET_PROP = "consumer.secret";

    private String screenName;
    private String consumerKey;
    private String consumerSecret;

    private TwitterFactory twitterFactory;

    public Twitter4jService() {
        twitterFactory = new TwitterFactory(buildConfiguration());
    }

    @Override
    public Account getAccount() {

        Twitter twitter = null;
        try {
            twitter = getTwitter();
            User user = twitter.showUser(screenName);

            Account account = new Account();
            account.setScreenName(user.getScreenName());
            account.setUserId(user.getId());
            account.setProfileUrl(TwitterService.TWITTER_URL + user.getScreenName());
            account.setProfileImage(user.getBiggerProfileImageURLHttps());
            account.setDescription(user.getDescription());
            account.setFollowers(user.getFollowersCount());
            account.setFriends(user.getFriendsCount());

            return account;
        } catch (TwitterException ex) {
            System.out.println("An error occured while trying to retrieve the statuses. ");
            ex.printStackTrace();
            throw new ServiceException("An error occured while trying to load the account", ex);
        }

    }

    @Activate
    protected void activate(ComponentContext context) throws Exception {

        Dictionary<String, Object> props = context.getProperties();

        screenName = PropertiesUtil.toString(props.get(SCREEN_NAME_PROP), null);
        if (StringUtils.isBlank(screenName)) {
            throw new ConfigurationException(SCREEN_NAME_PROP, "Screen name is required.");
        }

        consumerKey = PropertiesUtil.toString(props.get(CONSUMER_KEY_PROP), null);
        if (StringUtils.isBlank(consumerKey)) {
            throw new ConfigurationException(CONSUMER_KEY_PROP, "Consumer Key is required.");
        }

        consumerSecret = PropertiesUtil.toString(props.get(CONSUMER_SECRET_PROP), null);
        if (StringUtils.isBlank(consumerSecret)) {
            throw new ConfigurationException(CONSUMER_SECRET_PROP, "Consumer Secret is required.");
        }

        try {
            // Test connection to Twitter.
            getTwitter();
        } catch (TwitterException e) {
            throw new Exception("Unable to connect to Twitter, Key/Securt invalid.", e);
        }
    }

    private Twitter getTwitter() throws TwitterException {
        Twitter twitter = twitterFactory.getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.getOAuth2Token();
        return twitter;
    }

    private Configuration buildConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setGZIPEnabled(true);
        cb.setApplicationOnlyAuthEnabled(true);
        return cb.build();
    }

}
