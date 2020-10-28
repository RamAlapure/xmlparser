package com.github.xmlparser.util;


import com.github.xmlparser.exception.XmlParsingException;

import java.util.logging.Logger;

/**
 * @author Ram Alapure
 * @version 1.0
 * @since 17/02/2020
 */
public final class ErrorUtil {

    public static final Logger log = Logger.getLogger(ErrorUtil.class.getName());

    private ErrorUtil() {
    }

    public static void fileParsingException(String message, Exception e) throws XmlParsingException {
        log.severe(String.format("%sCause: %s%s", message, AppConstants.STR_BRACES, e.getCause()));
        throw new XmlParsingException(message);
    }

    public static void fileParsingException(String message) throws XmlParsingException {
        log.severe(message);
        throw new XmlParsingException(message);
    }

}
