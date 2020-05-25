package com.sm.api.facebook;

import com.restfb.*;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.exception.FacebookGraphException;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.sm.api.utils.Utils;

import java.util.HashMap;
import java.util.List;


public class FacebookConnect {

    FacebookClient fbClient = null;
    HashMap<String, String> facebookProp = new HashMap<String, String>();
    AccessToken accessToken = null;

    // constructor
    public FacebookConnect() {
        Utils utils = new Utils();
        facebookProp = utils.readPropertyFile("src/main/java/com/sm/api/facebook/fb.properties");
        fbClient = new DefaultFacebookClient(facebookProp.get("accessToken"), Version.LATEST);
        //getting long lived access token
        accessToken = this.getLongLivedAccessToken(facebookProp.get("appID"), facebookProp.get("appSecret"));
        //re initializing the Facebook Client with Long Lived access token
        fbClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
        System.out.println("Your Long lived access token will expire on " + accessToken.getExpires());
    }

    //This method will exchange short lived access with long lived access token
    public AccessToken getLongLivedAccessToken(String appID, String appSecret) {
        AccessToken token = fbClient.obtainExtendedAccessToken(appID, appSecret);
        System.out.println("[INFO] successfully obtained the Long Lived access token");
        return token;
    }

    //This method will display the user timeline feeds
    public void getUserTimeline() {
        int cnt = 0;
        try {
            Connection<Post> timeline = fbClient.fetchConnection("me/feed", Post.class);

            for (List<Post> timelineRes : timeline) {
                for (Post post : timelineRes) {
                    System.out.println(post.getMessage());
                    System.out.println("fb.com/" + post.getId());
                    cnt++;
                }
            }
        } catch(FacebookGraphException e) {
            System.out.println("Total post displayed = " + cnt);
            e.printStackTrace();
        }
    }

    //This method will display the pages liked by user
    public void getLikedPages(){
        int cnt=0;
        try{
            Connection<Page> pageLikes = fbClient.fetchConnection("me/likes", Page.class);

            for (List<Page> results : pageLikes) {
                for (Page likes : results) {
                    System.out.println(likes.getName());
                    cnt++;
                }
            }
            System.out.println("You've liked total " + cnt + " pages");
        } catch(FacebookGraphException e){
        e.printStackTrace();
    }
    }

    //This method will return the data from facebook pages
    public void getPageData(String pageName){
        try {
            Page page = fbClient.fetchObject(pageName, Page.class);

            Connection<Post> pageData = fbClient.fetchConnectionPage(page.getId()+"/feed", Post.class);

            for(List<Post> pdata : pageData){
                for(Post data:pdata){
                    System.out.println(data.getFrom().getName());
                    System.out.println(data.getMessage());
                    System.out.println("fb.com/" + data.getId());
                }
            }
        } catch (FacebookGraphException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FacebookConnect fbConnect = new FacebookConnect();
        //fbConnect.getUserTimeline();
        fbConnect.getLikedPages();
        //fbConnect.getPageData("satarainfo");

    }

}
