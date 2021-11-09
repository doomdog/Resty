Changes
-------

New in 0.4.0:

- Added method to AbstractResource to return the response code for the URLConnection.
- Created a replacement for DefaultHostnameVerifier because it is being removed from the JDK.
- Cleaned up some out of date tests.
- Minor code cleanup
- Changed minimum Java version to 8.  It works best with Java 8, but might work with 11.
- This release has issues with Java 17, which will be addressed in a separate release.
- Note that this project was essentially abandoned many years ago and needs a lot of modernization.


New in 0.3.2:

- revert to Maven. Gradle came in through a pull request. Not intending to move to gradle just yet
- fix for open input streams
- support for arrays being returned as JSON
- support for security realms (Resty.authenticateForRealm()) in case the regular authentication is not working because java is not able to determine the URL
- deprecated alwaysSend in favor of withHeader
- Resty will not set a CookieHandler if one is already set. 

Since 0.3.0: 

- Option to ignore SSL certificate errors: Resty.ignoreAllCerts (global switch for now)
- New constructor to specify options: new Resty(Option.timeout(3000)); (sets the socket connect timeout)
- Create your own Options (see Resty.Option.Timeout or Resty.Option.Proxy for example)
- Fixed scala naming issue
- enhanced syntax for JSON queries
- bugfixes from my contributors. Thank you!
- Proxy support. Thank you, Gabriel. r.setProxy(...) for object r or new Resty(Option.proxy(...)) to carry proxy settings over when traversing paths
- convenient location header:  new Resty().bytes(url, put(someContent)).location(); // gets Location header as URI

Since 0.2.0: 

- Support for PUT, DELETE, Support for application/multipart-formdata

