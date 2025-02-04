package us.monoid.web;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/** Encapsulates form-data sent to web services.
 * Currently only application/x-www-form-urlencoded is supported.
 * 
 * @author beders
 *
 */
public class FormContent extends Content {
	protected String rawQuery;

	public FormContent(String query) {
		super("application/x-www-form-urlencoded", getBytes(query)); // strictly speaking US ASCII should be used
	}
	
	private static byte[] getBytes(String query) {
		return query.getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public String toString() {
		return rawQuery;
	}
}
