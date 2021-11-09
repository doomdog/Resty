package us.monoid.web.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class TrustAllX509SocketFactory {

    /** This utility class has all static methods */
    private TrustAllX509SocketFactory() {
        throw new UnsupportedOperationException("this class is static");
    }

    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, TrustAllX509Certificates.TRUST_MANAGER, null);
        return sc.getSocketFactory();
    }

}
