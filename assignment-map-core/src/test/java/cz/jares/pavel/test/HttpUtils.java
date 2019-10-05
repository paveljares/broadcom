package cz.jares.pavel.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;

/**
 * 
 * @author jaresp
 *
 */
public final class HttpUtils {
	
	private HttpUtils() {
	}

	public static Map<String, String> convertParameters(String url) {
		Assert.assertTrue(url.contains("?"));
		String params=url.substring(url.indexOf("?")+1);
		
		final Map<String, String> out=new HashMap<String, String>();
		for (String pair : params.split("&")) {
			Assert.assertTrue(pair.contains("="));
			String[] parts=pair.split("=");
			out.put(parts[0], parts.length>1?parts[1]:"");
		}
		return out;
	}
	
	public static void assertUrl(String expected, String actual) throws MalformedURLException {
		// check if url is the same
		URL url1=new URL(expected);
		URL url2=new URL(actual);
		Assert.assertEquals(url1.getProtocol(), url2.getProtocol());
		Assert.assertEquals(url1.getHost(), url2.getHost());
		Assert.assertEquals(url1.getPort(), url2.getPort());
		Assert.assertEquals(url1.getPath(), url2.getPath());
	}
	
	public static void assertUri(String expectedUrl, Map<String, String> expectedParams, String actualUri) throws MalformedURLException {
		assertUrl(expectedUrl, actualUri);
		
		Map<String, String> params=convertParameters(actualUri);
		
		for (Entry<String, String> entry : expectedParams.entrySet()) {
			Assert.assertEquals(entry.getValue(), params.remove(entry.getKey()));
		}
		
		Assert.assertTrue(params.isEmpty());
	}
	
}
