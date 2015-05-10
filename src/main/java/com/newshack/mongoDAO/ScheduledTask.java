import java.net.UnknownHostException;
import java.util.TimerTask;
import java.util.Date;
/**
 * This class represents the thread which is created every 30  minutes
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