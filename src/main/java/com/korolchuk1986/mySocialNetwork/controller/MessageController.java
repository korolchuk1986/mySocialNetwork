package com.korolchuk1986.mySocialNetwork.controller;

import com.korolchuk1986.mySocialNetwork.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{put("id", "1"); put("text", "First message");}});
        add(new HashMap<String, String>() {{put("id", "2"); put("text", "Second message");}});
    }};

    @GetMapping
    public List<Map<String, String>> listMessage() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getMessage(@PathVariable String id) {
        return getMessageById(id);
    }

    private Map<String, String> getMessageById(String id) {
        return messages.stream().
                filter(message -> message.get("id").equals(id)).
                findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> addMessage(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(messages.size() + 1));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> addMessage(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDB = getMessageById(id);
        messageFromDB.putAll(message);
        messageFromDB.put("id", id);
        return messageFromDB;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable String id) {
        Map<String, String> message = getMessageById(id);
        messages.remove(message);
    }
}
