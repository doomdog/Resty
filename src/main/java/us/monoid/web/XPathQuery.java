package us.monoid.web;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** PathQuery for XPath.
 * Use this to access XMLResource objects.
 * Many times you might want to use Resty.xpath(...) to create instances of this class.
 * 
 * @author beders
 *
 */
public class XPathQuery extends PathQuery<XMLResource,NodeList> {
	protected XPathExpression xPathExpression;

	public XPathQuery(String anXPath) throws XPathException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		xPathExpression = xPath.compile(anXPath);
	}

	private <T> QName getConstant(Class<T> aReturnType) {
		if (aReturnType == String.class)
			return XPathConstants.STRING;
		if (aReturnType == Boolean.class)
			return XPathConstants.BOOLEAN;
		if (aReturnType == Double.class)
			return XPathConstants.NUMBER;
		if (aReturnType == NodeList.class)
			return XPathConstants.NODESET;
		if (aReturnType == Node.class)
			return XPathConstants.NODE;

		throw new IllegalArgumentException("'" + aReturnType + "' is not supported as result of an XPath expression");
	}

	/** Eval to a NodeList */
	@Override
	NodeList eval(XMLResource resource) throws Exception {
		return (NodeList) xPathExpression.evaluate(resource.doc(), XPathConstants.NODESET);
	}
	
	/** Evaluate the XPath on an XMLResource and convert the result into aReturnType.
	 * 
	 * @param <T> the expected type of the result
	 * @param resource
	 * @param aReturnType 
	 * @return
	 * @throws Exception
	 */
	public <T> T eval(XMLResource resource, Class<T>aReturnType) throws Exception {
		return (T) xPathExpression.evaluate(resource.doc(), getConstant(aReturnType));
	}
}
