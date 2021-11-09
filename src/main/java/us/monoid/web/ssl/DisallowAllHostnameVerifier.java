package us.monoid.web.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * This HostnameVerifier fails every hostname verification.
 *
 * Replaces sun.net.www.protocol.https.DefaultHostnameVerifier which is no longer available in the JDK.
 */
public final class DisallowAllHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify( String hostname, SSLSession session) { return false; }
}
