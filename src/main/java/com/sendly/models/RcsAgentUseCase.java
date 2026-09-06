package com.sendly.models;

/**
 * Values accepted for an RCS agent's {@code useCase}.
 */
public final class RcsAgentUseCase {
    public static final String MULTI_USE = "MULTI_USE";
    public static final String PROMOTIONAL = "PROMOTIONAL";
    public static final String TRANSACTIONAL = "TRANSACTIONAL";
    public static final String OTP = "OTP";

    private RcsAgentUseCase() {}
}
