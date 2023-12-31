package com.example.section6.controller;

import com.example.section6.model.Contact;
import com.example.section6.repository.ContactRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Random;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/contacts")
public class ContactController {

    ContactRepository contactRepository;

    @PostMapping("/contact")
    public Contact saveContactInquiryDetails(@RequestBody Contact contact) {
        contact.setId(getServiceReqNumber());
        contact.setCreatedAt(new Date(System.currentTimeMillis()));
        return contactRepository.save(contact);
    }

    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + ranNum;
    }

}
