package com.sm.api.test;

import com.sm.api.twitter.TwitterConnect;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class SMAPIConnectTest
{
    TwitterConnect twitterCon;

    @Before
    public void initilizeClassObj(){
        twitterCon = new TwitterConnect();
    }

    @Test
    public void testGetLocationWiseTrends() throws Exception {
        //test code here...

    }

    @After
    public void tearDown(){
        twitterCon = null;
    }

    @Test
    public void getFollowersInfo() {
        twitterCon.getFollowersInfo("tejasshende");

    }
}
