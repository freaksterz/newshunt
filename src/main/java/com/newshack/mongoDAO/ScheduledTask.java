package com.newshack.mongoDAO;

import java.net.UnknownHostException;
import java.util.TimerTask;
import java.util.Date;
/**
 * 
 * @author Ganesh.
 */

public class ScheduledTask extends TimerTask { 

	public void run() {
		try {
			MongoLabConnection.updateDocumentStore();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}