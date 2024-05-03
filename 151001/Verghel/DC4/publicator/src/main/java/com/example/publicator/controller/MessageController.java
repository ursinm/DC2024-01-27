package com.example.publicator.controller;

import com.example.publicator.KafkaSender;
import com.example.publicator.dto.MessageRequestTo;
import com.example.publicator.dto.MessageResponseTo;
import com.example.publicator.exception.NotFoundException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class MessageController {

    @Autowired
    private KafkaConsumer<String, MessageResponseTo> kafkaConsumer;
    @Autowired
    private KafkaSender kafkaSender;
    private String inTopic = "InTopic";
    private String outTopic = "OutTopic";

    WebClient client = WebClient.create();
    private String uriBase = "http://localhost:24130/api/v1.0/";

    private MessageResponseTo listenKafka() throws NotFoundException{
        ConsumerRecords<String, MessageResponseTo> records = kafkaConsumer.poll(Duration.ofMillis(500000));
        for(ConsumerRecord<String, MessageResponseTo> record: records){
            String key = record.key();
            MessageResponseTo value = record.value();
            if(value == null){
                throw new NotFoundException("Message not found", 404);
            }
            System.out.println(value.toString());
            return value;
        }
        return null;
    }



    @PostMapping(value = "messages")
    public ResponseEntity<?> create(@RequestHeader(value = "Accept-Language", defaultValue = "EN") String acceptLanguageHeader, @RequestBody MessageRequestTo messageRequestTo) {
        messageRequestTo.setCountry(acceptLanguageHeader);
        messageRequestTo.setMethod("POST");
        kafkaSender.sendMessage(messageRequestTo,inTopic);
        System.out.println(messageRequestTo.toString());

        // MessageResponseTo message = client.post().uri(uriBase + "messages").header(HttpHeaders.ACCEPT_LANGUAGE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(messageRequestTo), MessageRequestTo.class).retrieve().bodyToMono(MessageResponseTo.class).onErrorMap(NotFoundException.class, ex -> new NotFoundException(ex.getMessage(), ex.getStatus())).block();
        return new ResponseEntity<>(listenKafka(), HttpStatus.CREATED);
    }

    @GetMapping(value = "messages", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read(
            @RequestParam(required = false, defaultValue = "0") Integer pageInd,
            @RequestParam(required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam(required = false, defaultValue = "id") String sortedBy,
            @RequestParam(required = false, defaultValue = "desc") String direction)
    {
        final List<MessageResponseTo> list = client.get().uri(uriBase + "messages").retrieve().bodyToFlux(MessageResponseTo.class).collectList().block();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "messages/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        MessageRequestTo messageRequestTo = new MessageRequestTo();
        messageRequestTo.setMethod("GET");
        messageRequestTo.setId(id);
        kafkaSender.sendMessage(messageRequestTo,inTopic);
        System.out.println(messageRequestTo.toString());

        /*MessageResponseTo message = client.get().uri(uriBase + "messages/" + id).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                {
                    throw new NotFoundException("Message not found", 404);
                }
                )
                .bodyToMono(MessageResponseTo.class).block();
        */
        return new ResponseEntity<>(listenKafka(), HttpStatus.OK);
    }

    @PutMapping(value = "messages")
    public ResponseEntity<?> update(@RequestHeader(value = "Accept-Language", defaultValue = "EN") String acceptLanguageHeader, @RequestBody MessageRequestTo messageRequestTo) {
        messageRequestTo.setCountry(acceptLanguageHeader);
        messageRequestTo.setMethod("PUT");
        kafkaSender.sendMessage(messageRequestTo,inTopic);
        System.out.println(messageRequestTo.toString());
        /*MessageResponseTo message = client.put().uri(uriBase + "messages").header(HttpHeaders.ACCEPT_LANGUAGE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(messageRequestTo), MessageRequestTo.class).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        {
                            throw new NotFoundException("Message not found", 404);
                        }
                )
                .bodyToMono(MessageResponseTo.class).block();
        */
        return new ResponseEntity<>(listenKafka(), HttpStatus.OK);
    }

    @DeleteMapping(value = "messages/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        MessageRequestTo messageRequestTo = new MessageRequestTo();
        messageRequestTo.setMethod("DELETE");
        messageRequestTo.setId(id);
        kafkaSender.sendMessage(messageRequestTo,inTopic);
        System.out.println(messageRequestTo.toString());

        /*boolean isDeleted = Boolean.TRUE.equals(client.delete().uri(uriBase + "messages/" + id).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        {
                            throw new NotFoundException("Message not found", 404);
                        }
                )
                .bodyToMono(Boolean.class).block());
        */
        return new ResponseEntity<>(listenKafka(), HttpStatus.NO_CONTENT);
    }
}
