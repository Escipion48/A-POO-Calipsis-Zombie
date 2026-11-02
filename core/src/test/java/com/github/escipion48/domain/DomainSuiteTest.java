package com.github.escipion48.domain;

import org.junit.jupiter.api.Test;

class DomainSuiteTest {

    @Test
    void runsAllDomainChecks() {
        DomainTests.main(new String[0]);
    }
}
