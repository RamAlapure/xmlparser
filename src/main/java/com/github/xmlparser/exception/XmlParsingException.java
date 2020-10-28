package com.github.xmlparser.exception;

/**
 * Signals that a xml parsing exception of some sort has occurred.
 *
 * @author Ram Alapure
 * @version 1.0
 * @since 17/02/2020
 */
public class XmlParsingException extends Exception {

    private static final long serialVersionUID = 3009370493293321348L;

    /**
     * Constructs an {@code XmlParsingException} with the specified detail
     * message.
     *
     * @param message The detail message (which is saved for later retrieval by the
     *                {@link #getMessage()} method)
     */
    public XmlParsingException(String message) {
        super(message);
    }

}
