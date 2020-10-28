package com.github.xmlparser.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xmlparser.exception.XmlParsingException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author ralapure
 */
public class ValidationUtilTest {

    @Test
    public void exceptionTest() {
        assertThrows(XmlParsingException.class, () -> ValidationUtil.rejectNull(null, "field"));
        assertThrows(XmlParsingException.class, () -> ValidationUtil.rejectNull(" ", "field"));
    }

}
