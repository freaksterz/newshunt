package com.newshack.mongoDAO;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import com.mongodb.*;

/**
 * @author ganesh
 * This class is responsible for getting connection from MongoDb and then updating it through
 * a thread as well as returning a collection of documents when inquired for a particular keyword
 */
public class MongoLabConnection {

	public static void main(String[] args) throws UnknownHostException,
			InterruptedException {

		updateDocumentStore(); //load the database first time rest of the times it gets updated via thread
		System.out.println("store updted");
		Timer time = new Timer(); // Instantiate Timer Object
		ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask
												// class
		time.schedule(st, 1800000, 1800000); // Create Repetitively task for every 30
										// mins
		
		Map Collection = returnDocument("");

		System.out.println(Collection);

	}

	/**
	 * @throws java.net.UnknownHostException
	 */
	public static void updateDocumentStore() throws UnknownHostException {
		String[] urls = {
				"http://gadgets.ndtv.com/rss/news",
				"http://timesofindia.feedsportal.com/c/33039/f/533965/index.rss",
				"http://www.vogella.com/article.rss" };

		ArrayList<BasicDBObject> seedData = null;
		MongoClientURI uri = new MongoClientURI(
				"mongodb://newshuntuser:newshuntpassword2015@dbh04.mongolab.com:27047/newshuntdatabse");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection documentstore = db.getCollection("documentstore");
		documentstore.drop(); // remove the old entries in the collection

		for (int i = 0; i < urls.length; i++) {
			seedData = createSeedData(urls[i]);
			for (BasicDBObject temp : seedData)
				documentstore.insert(temp);
		}

		client.close();

	}

	public static Map returnDocument(String key) throws UnknownHostException {

		MongoClientURI uri = new MongoClientURI(
				"mongodb://newshuntuser:newshuntpassword2015@dbh04.mongolab.com:27047/newshuntdatabse");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection documentstore = db.getCollection("documentstore");
		DBCursor docs = documentstore.find();
		Map collection = new HashMap<String, String>();
		String[] temp = null;

		while (docs.hasNext()) {
			DBObject doc = docs.next();
			temp = ((String) doc.get("document")).split("!####!");
			collection.put(temp[0], temp[1]);
		}

		client.close();

		return collection;
	}
	
	// Extra helper code

		public static ArrayList<BasicDBObject> createSeedData(String url) {

			ReadFeeds parser = new ReadFeeds(url);
			Feed feed = parser.readFeed();
			ArrayList<BasicDBObject> seedData = new ArrayList<BasicDBObject>();

			BasicDBObject feeddata = null;

			for (FeedMessage message : feed.entries) {
				feeddata = new BasicDBObject();
				feeddata.put("document", message.title + "!####!" + message.link);
				seedData.add(feeddata);
			}

			return seedData;
		}

    public static CommandResult searchText (String textToSearchFor, String collectionName){

        MongoClient client = null;
        DB db = null;
        try {
            MongoClientURI uri = new MongoClientURI(
                    "mongodb://newshuntuser:newshuntpassword2015@dbh04.mongolab.com:27047/newshuntdatabse");

            client = new MongoClient(uri);
            db = client.getDB(uri.getDatabase());

        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        collectionName = "documentstore";
        textToSearchFor = "a";

        final DBObject textSearchCommand = new BasicDBObject();
        textSearchCommand.put("text", collectionName);
        textSearchCommand.put("search", textToSearchFor);
        final CommandResult commandResult = db.command(textSearchCommand);
        System.out.println("commandResult = " + commandResult);
        client.close();



        return null;

    }

}