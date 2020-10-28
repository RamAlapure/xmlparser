package com.github.xmlparser.util;

import com.github.xmlparser.exception.XmlParsingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author ralapure
 */
public class ErrorUtilTest {

    @Test
    public void fileSystemExceptionTest() {
        assertThrows(XmlParsingException.class, () -> ErrorUtil.fileParsingException(ExceptionConstants.STR_IO_EXCEPTION,
                new IOException("IO exception occurred")));
    }

}
