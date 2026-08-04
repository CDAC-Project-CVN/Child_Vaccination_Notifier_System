package com.cvn.user.config;

public final class RabbitMQConstants {

    private RabbitMQConstants() {}

    /*
     * Exchange
     */
    public static final String CHILD_EXCHANGE =
            "child.exchange";

    /*
     * Queue
     */
    public static final String CHILD_REGISTERED_QUEUE =
            "child.registered.queue";

    /*
     * Routing Key
     */
    public static final String CHILD_REGISTERED_ROUTING_KEY =
            "child.registered";
}