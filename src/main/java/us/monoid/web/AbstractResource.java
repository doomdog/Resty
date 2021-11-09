/*
 * AbstractResource.java
 */
package us.monoid.web;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/**
 * Abstract base class for all resource handlers you want to use with Resty.
 * 
 * It gives access to the underlying URLConnection and the current inputStream
 * 
 * @author beders
 * 
 */
public abstract class AbstractResource extends Resty {
	static final Logger log = Logger.getLogger(AbstractResource.class.getName()); 
	protected URLConnection urlConnection;
	protected InputStream inputStream;

	public AbstractResource(Option... options) {
		super(options);
	}

	protected abstract String getAcceptedTypes();

	void fill(URLConnection anUrlConnection) throws IOException {
		urlConnection = anUrlConnection;
		try {
			if ("gzip".equals(anUrlConnection.getContentEncoding())) {
				inputStream = new GZIPInputStream(anUrlConnection.getInputStream());
			}
			else {
				inputStream = anUrlConnection.getInputStream();
			}
		} catch (IOException e) {
			// Per http://docs.oracle.com/javase/1.5.0/docs/guide/net/http-keepalive.html
			// (comparable documentation exists for later java versions)
			// if an HttpURLConnection was used clear the errorStream and close it
			// so that keep alive can keep doing its work
			if (anUrlConnection instanceof HttpURLConnection) {
				HttpURLConnection conn = (HttpURLConnection) anUrlConnection;
				InputStream es;
				if ("gzip".equals(conn.getContentEncoding())) {
					es = new BufferedInputStream(new GZIPInputStream(conn.getErrorStream()));
				}
				else {
					es = new BufferedInputStream(conn.getErrorStream());
				}

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					// read the response body
					byte[] buf = new byte[1024];
					int read = -1;
					while ((read = es.read(buf)) > 0) {
						baos.write(buf, 0, read);
					}
				} catch (IOException e1) {
					log.warning("IOException when reading the error stream. Ignored");
				}

				// close the errorstream
				es.close();

				inputStream = new ByteArrayInputStream(baos.toByteArray());

			} else {
				throw e;
			}
		}
	}

	public URLConnection getUrlConnection() {
		return urlConnection;
	}

	public HttpURLConnection http() {
		return (HttpURLConnection) urlConnection;
	}

	public InputStream stream() {
		return inputStream;
	}

	/**
	 * Get the responseCode for the URLConnection.
	 *
	 * @return responseCode
	 */
	public int status() {
		try {
			return ((HttpURLConnection)urlConnection).getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * Check if the URLConnection has returned the specified responseCode
	 * 
	 * @param responseCode the code to check
	 * @return true if the URLConnection returned the requested responseCcode
	 */
	public boolean status(int responseCode) {
        return status() == responseCode;
	}

	/**
	 * Get the location header as URI. Returns null if there is no location header.
	 * 
	 */
	public URI location() {
		String loc = http().getHeaderField("Location");
		if (loc != null) {
			return URI.create(loc);
		}
		return null;
	}

	/**
	 * Returns all values for the given header.
	 *
	 * @param name The header name
	 * @return The list of values for header 'name'
	 */
	public List<String> header( String name ) {
		List list = new ArrayList();
		HttpURLConnection http = http();
		if (http != null) {
			Map<String, List<String>> header = http.getHeaderFields();
			for (String val : header.get(name))
				list.add(val);
		}

		return list;
	}
	/** Print out the response headers for this resource.
	 * 
	 * @return
	 */
	public String printResponseHeaders() {
		StringBuilder sb = new StringBuilder();
		HttpURLConnection http = http();
		if (http != null) {
			Map<String, List<String>> header = http.getHeaderFields();
			for (String key : header.keySet()) {
				for (String val : header.get(key)) {
					sb.append(key).append(": ").append(val).append("\n");
				}
			}
		}
		return sb.toString();
	}
}
