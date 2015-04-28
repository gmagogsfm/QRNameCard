package ezvcard.io.xml;

import java.util.Arrays;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;

import ezvcard.VCardVersion;

/**
 * Used for xCard XPath expressions.
 * @see javax.xml.xpath.XPath#setNamespaceContext(javax.xml.namespace.NamespaceContext)
 * @author Michael Angstadt
 */
public class XCardNamespaceContext implements NamespaceContext {
	private final String ns;
	private final String prefix;

	/**
	 * @param version the vCard version to use
	 * @param prefix the prefix to use
	 */
	public XCardNamespaceContext(VCardVersion version, String prefix) {
		this.ns = version.getXmlNamespace();
		this.prefix = prefix;
	}

	/**
	 * Gets the prefix to use in xpath expressions.
	 * @return the xpath prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	//@Override
	public String getNamespaceURI(String prefix) {
		if (this.prefix.equals(prefix)) {
			return ns;
		}
		return null;
	}

	//@Override
	public String getPrefix(String ns) {
		if (this.ns.equals(ns)) {
			return prefix;
		}
		return null;
	}

	//@Override
	public Iterator<String> getPrefixes(String ns) {
		if (this.ns.equals(ns)) {
			return Arrays.asList(prefix).iterator();
		}
		return null;
	}
}