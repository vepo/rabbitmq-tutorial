package io.vepo.rabbitmq.producer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

public class Producer {
    private static final String QUEUE_NAME = "testing-queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("OK");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(System.getenv("RABBITMQ_USR"));
        factory.setPassword(System.getenv("RABBITMQ_PWD"));
        factory.setHost(System.getenv("RABBITMQ_HOST"));
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}