
package com.github.bstopp.kcjug.felix.twitter.service.impl;

import org.osgi.framework.ServiceException;

import com.github.bstopp.kcjug.felix.twitter.Account;
import com.github.bstopp.kcjug.felix.twitter.service.TwitterService;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter4jService implements TwitterService {

    private static final String screenName     = "StoppThinking";
    private static final String consumerKey    = "Iyc3J6xPuDLgBoXyegJxdbesw";
    private static final String consumerSecret = "dIVdrtms7xBkEioeGMEoIKruP0Bxd3IM40MuYQJ8Z7tqn0dLY0";

    private TwitterFactory twitterFactory;

    public Twitter4jService() {
        twitterFactory = new TwitterFactory(buildConfiguration());
    }

    @Override
    public Account getAccount() {

        Twitter twitter = null;
        try {
            twitter = getTwitter();
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.getOAuth2Token();
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

    private Twitter getTwitter() throws TwitterException {
        return twitterFactory.getInstance();
    }

    private Configuration buildConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setGZIPEnabled(true);
        cb.setApplicationOnlyAuthEnabled(true);
        return cb.build();
    }

}
