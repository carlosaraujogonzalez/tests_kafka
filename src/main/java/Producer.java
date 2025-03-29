import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Producer {


    public static void main(String[] args) {

        var props = new Properties();

        // serializers, used to send/receive data. Before sending data this serializer transforms Strings into bytes"
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        props.put("acks", "0"); // producer doesn't wait confirmation, faster, less reliable
        props.put("acks", "1"); // more reliable, but if broker fails, we can lost messages cause we have only one replica
        props.put("acks", "all"); // the most reliable, but we have more latency, more replicas

        // broker initial connections
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");

        // In case of we get a fail, number of retries to send a message again
        props.put("retries", 0);

        // buffer size that store events of the same partition to send them.
        props.put("batch.size", 16384);

        // total available memory for a producer's buffer
        props.put("buffer.memory", 33554432);

        // how many time producer waits before sending messages, improve performance
        props.put("linger.ms", 1);

        var producer = new KafkaProducer<String, String>(props);
        var topic = "testtopic";
        int partition = 0;
        var key = "testKey";
        var value = "testValue";
        producer.send(new ProducerRecord<>(topic, partition, key, value));
        producer.close();
    }

}
