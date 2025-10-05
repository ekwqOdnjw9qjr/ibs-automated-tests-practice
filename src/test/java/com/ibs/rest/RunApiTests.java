package com.ibs.rest;

import org.junit.jupiter.api.Tag;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        SandboxApiTest.class
})
@Tag("api")
public class RunApiTests {
}
