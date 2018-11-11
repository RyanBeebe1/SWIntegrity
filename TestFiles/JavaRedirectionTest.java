
public class JavaRedirectionTest {
	
	URL obj = new URL(url);
	HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
	conn.setInstanceFollowRedirects(true);  //you still need to handle redirect manully.
	HttpURLConnection.setFollowRedirects(true);

}
