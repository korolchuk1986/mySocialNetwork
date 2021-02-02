package com.korolchuk1986.mySocialNetwork.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.korolchuk1986.mySocialNetwork.domain.Message;
import com.korolchuk1986.mySocialNetwork.domain.Views;
import com.korolchuk1986.mySocialNetwork.exception.NotFoundException;
import com.korolchuk1986.mySocialNetwork.repo.MessageRepo;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> listMessage() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getMessage(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message addMessage(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        messageRepo.save(message);
        return message;
    }

    @PutMapping("{id}")
    public Message addMessage(@PathVariable("id") Message messageFromDB, @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDB, "id");
        return messageRepo.save(messageFromDB);
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }
}
