package us.monoid.web.ssl;

import org.junit.Test;
import org.mockito.Mockito;

import javax.net.ssl.SSLSession;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AllowAllHostnameVerifierTest {
    @Test
    public void shouldAllowAllHostnames() {
        assertThat("Should always verify true, but was false",
                AllowAllHostnameVerifier.ALLOW_ALL_HOSTNAMES.verify("AnyString", Mockito.mock(SSLSession.class)),
                equalTo(true));
    }
}
