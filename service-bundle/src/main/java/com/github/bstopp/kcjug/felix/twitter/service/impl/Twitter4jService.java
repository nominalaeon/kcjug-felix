
package com.github.bstopp.kcjug.felix.twitter.service.impl;


import com.github.bstopp.kcjug.felix.twitter.Account;
import com.github.bstopp.kcjug.felix.twitter.service.TwitterService;

public class Twitter4jService implements TwitterService {

	
	@Override
	public Account getAccount() {
		Account acct = new Account();
		acct.setScreenName("@StoppThinking");
		acct.setDescription("AEM Platform Architect @VML, avid reader, book collector");
		acct.setFollowers(45);
		acct.setFriends(44);
		acct.setProfileUrl(TwitterService.TWITTER_URL + "/StoppThinking");
		
		return acct;
	}

}
