package com.github.xmlparser.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommonUtilTest {

    File csv;

    @BeforeAll
    public void setup() {
        csv = new File(new File(TestConstants.LOCATION).getAbsolutePath() + TestConstants.CHAR_FORWARD_SLASH
                + TestConstants.XML_SAMPLE);
    }

    @Test
    void getFileNamePrefix() {
        System.out.println(CommonUtil.getFileNamePrefix(csv));
    }
}