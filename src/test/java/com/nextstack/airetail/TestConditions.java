package com.nextstack.airetail;

import org.testcontainers.DockerClientFactory;

/**
 * Shared conditions for test execution.
 */
public final class TestConditions {

    private TestConditions() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Returns true when Docker is available for Testcontainers.
     *
     * @return docker availability flag
     */
    public static boolean isDockerAvailable() {
        try {
            return DockerClientFactory.instance().isDockerAvailable();
        } catch (Exception ex) {
            return false;
        }
    }
}
