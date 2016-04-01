package robocooper.twitbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Component
public class ParrotComponent implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory();
		TwitterStream twitterStream = twitterStreamFactory.getInstance();
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.track(new String[]{"@Robo_Cooper"});
		twitterStream.addListener(new TweetListener());
		twitterStream.filter(filterQuery);
	}

}

class TweetListener implements StatusListener {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onException(Exception arg0) {
		/* do nothing */
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		/* do nothing */
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		/* do nothing */
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		/* do nothing */
	}

	@Override
	public void onStatus(Status status) {
		log.info("Found a status: " + status.getText());
		Twitter twitter = TwitterFactory.getSingleton();
		try {
			if (twitter.getId() != status.getUser().getId()) {
				Status replyStatus = twitter.updateStatus("b'kaw @" + status.getUser().getScreenName() + "!! '" + status.getText() + "'");
				log.info("Replied with status: '" + replyStatus.getText() + "'");
			}
		} catch (Exception e) {
			log.error("Exception building the reply to status: '" + status.getText() + "'", e);
		}
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		/* do nothing */
	}
	
}
