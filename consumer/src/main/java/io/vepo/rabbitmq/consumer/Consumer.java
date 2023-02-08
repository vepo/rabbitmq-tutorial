package io.vepo.rabbitmq.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {
    private static final String QUEUE_NAME = "testing-queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("OK");
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername(System.getenv("RABBITMQ_USR"));
        factory.setPassword(System.getenv("RABBITMQ_PWD"));
        factory.setHost(System.getenv("RABBITMQ_HOST"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
