package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class URLReader {

	public static String download(String path) {
//		log.info("download(url="+path+")");
		ByteArrayOutputStream out = null;
		InputStream in = null;
		String res = "";
		try {
			URL url = new URL(path);
			URLConnection uc = url.openConnection();
			HttpURLConnection connection = (HttpURLConnection)uc;
			connection.setRequestMethod("GET");
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:18.0) Gecko/20100101 Firefox/18.0");
			connection.addRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			byte[] buffer = new byte[1024];
			int numRead = 0;
			in = connection.getInputStream();
			out = new ByteArrayOutputStream();
			while ( (numRead = in.read(buffer)) != -1 )
				out.write(buffer, 0, numRead);

			res = new String(out.toByteArray(), "UTF-8");
		} catch (Exception ex) {
			System.out.println("Exception while downloading=" + ex.getMessage());
			ex.printStackTrace();
			return "";
		} finally {
			try {
				if (out != null){
					out.close();
				}
			} catch (IOException ex) {
				System.out.println("Exception while downloading=" + ex.getMessage());
			}
			try {
				if (in != null){
					in.close();
				}
			} catch (IOException e) {
				System.out.println("Failed to close input stream! "+e.getMessage());
			}
		}
		System.out.println(" downloaded "+res.length()+" bytes");

		return res;
	}
}
