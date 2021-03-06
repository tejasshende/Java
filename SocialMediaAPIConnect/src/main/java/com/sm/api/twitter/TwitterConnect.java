package com.sm.api.twitter;

import com.sm.api.utils.Utils;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.HashMap;
import java.util.List;

public class TwitterConnect {

    boolean isTwitterAuthorized = false;
    TwitterFactory tf = null;
    Twitter twitter = null;
    HashMap<String, String> twitterProp = new HashMap<String, String>();
    Utils utils = null;

    //constructor
    public TwitterConnect(){
        utils = new Utils();
        twitterProp = utils.readPropertyFile("src/main/java/com/sm/api/twitter/twitter.properties");
        twitter = this.getTwitterAuthObject();
    }

    // This method will authorise the user and will return the twitter object
    public Twitter getTwitterAuthObject(){
        try {
            if (!(isTwitterAuthorized)) {
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey(twitterProp.get("consumerKey"))
                        .setOAuthConsumerSecret(twitterProp.get("consumerKeySecret"))
                        .setOAuthAccessToken(twitterProp.get("accessTokenKey"))
                        .setOAuthAccessTokenSecret(twitterProp.get("accessTokenSecret"));
                tf = new TwitterFactory(cb.build());
                twitter = tf.getInstance();
                //twitter = tf.getSingleton();
                isTwitterAuthorized = true;
            } else {
                // returning the twitter authorised object
                return twitter;
            }
        } catch (Exception e){
            e.printStackTrace();
            isTwitterAuthorized = false;
        }

        // returning the twitter authorised object
        return twitter;
    }

    //This method will give the location wise / woeid wise trend
    public void getLocationWiseTrends(int locationID){
        try {
            //getting the authorised twitter object
            //twitter = this.getTwitterAuthObject();

            Trends trends = twitter.getPlaceTrends(locationID);
            Trend[] trend = trends.getTrends();

            for (int i = 0; i < trend.length; i++) {
                System.out.println("Current trends are " + trend[i].getName());
            }

        } catch(Exception e){
            e.printStackTrace();
        }



    }

    //This method will return top 20 tweets from specified user timeline
    public void getUserTweets(String username){

        try {
            //getting the authorised twitter object
            //twitter = this.getTwitterAuthObject();

            ResponseList<Status> status = twitter.getUserTimeline(username);

            //Iterating over the response
            for(int i=0; i<status.size(); i++){
                System.out.println(status.get(i).getText());
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //This method will show the retweets from specified user timeline
    public void getUserRetweets(){

        try {
            //getting the authorised twitter object
            //twitter = this.getTwitterAuthObject();

            //paging object
            Paging paging = new Paging(1, 100);

            ResponseList<Status> status = twitter.getRetweetsOfMe(paging);

            //Iterating over the response
            for(int i=0; i<status.size(); i++){
                System.out.println(status.get(i).getText());
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //This method will show the favorite tweets marked by the specified user
    public void getUserFavoriteTweets(String username){
        try {
            //getting the authorised twitter object
            //twitter = this.getTwitterAuthObject();

            //paging object
            Paging paging = new Paging(1, 100);

            ResponseList<Status> status = twitter.getFavorites(username, paging);

            //Iterating over the response
            for(int i=0; i<status.size(); i++){
                System.out.println(status.get(i).getText());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //This method will show the followers information for the specified user
    public void getFollowersInfo(String username) {

        int followerCnt = 0;
        long cursor = -1;

        try {
            //getting the authorised twitter object
            //twitter = this.getTwitterAuthObject();

            PagableResponseList<User> followers = twitter.getFollowersList(username, cursor);
            do {
                //Iterating over the response
                for (User follower : followers) {
                    System.out.println(follower.getName() + " has " + follower.getFollowersCount() + " follower(s)");
                }
            } while ((cursor = followers.getNextCursor()) != 0);

            System.out.println(username + " has " + followerCnt + " followers(s)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method will post a tweet
    public void postATweet(String post){
        try {
            Status status = twitter.updateStatus(post);
            System.out.println("Tweet posted successfully...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method will get the tweets for specified HashTag
    public void getTweetsForSpecifiedHashtag(String hashtag){
        try {
            Query query = new Query(hashtag);
            QueryResult result;
            int Count=0;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("@" + tweet.getUser().getScreenName() + ":" + tweet.getText());
                    Count++;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while ((query = result.nextQuery()) != null);
            System.out.println(Count);
            System.exit(0);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //This method will get the count of tweets for specified HashTag
    public void getCountOfTweetsForSpecifiedHashtag(String hashtag) {
        int count = 0;
        try {

            Query query = new Query(hashtag);
            QueryResult result;

            System.out.println("Getting the count of tweets for hashtag " + hashtag);

            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    count++;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while ((query = result.nextQuery()) != null);
            System.out.println(hashtag + " has " + utils + " tweets");
            System.exit(0);

        } catch (Exception e){
            System.out.println(hashtag + " has " + utils.formatNumber(count) + " tweets");
            e.printStackTrace();
        }

            // One more way to get this done
//            Query AA = new Query("%23" + hashtag);
//            AA.setCount(100);
//            //if (!statusid.equals("")) {
//            //long l = Long.parseLong(statusid);
//            //AA.setMaxId(l - 1);
//            //}
//            QueryResult result = twitter.search(AA);
//            List<Status> statuslist = result.getTweets();
//            long id = result.getMaxId();
//
//            System.out.println("count : " + result.getTweets().size());
//
//        } catch (TwitterException e) {
//            e.printStackTrace();
//        }
    }

    //Main
    public static void main(String[] args) {
    TwitterConnect analysis = new TwitterConnect();
//    analysis.getLocationWiseTrends(2295412);
//    analysis.getUserTweets("twitter");
//    analysis.getUserRetweets();
//    analysis.getUserFavoriteTweets("twitter");
//    analysis.getFollowersInfo("twitter");
//    analysis.postATweet("First tweet from API...");
//    analysis.getTweetsForSpecifiedHashtag("sambhajimaharaj");
      analysis.getCountOfTweetsForSpecifiedHashtag("sambhajimaharaj");
    }
}

