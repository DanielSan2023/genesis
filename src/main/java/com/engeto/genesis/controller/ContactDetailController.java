package com.engeto.genesis.controller;

import com.engeto.genesis.model.ContactDetailDTO;
import com.engeto.genesis.service.ContactDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ContactDetailController {

    private final ContactDetailService contactDetailService;

    @PostMapping("/contactDetail")
    public ResponseEntity<ContactDetailDTO> createContactDetail(@RequestBody ContactDetailDTO contactDetailDTO) {
        ContactDetailDTO createdContactDetail = contactDetailService.createContact(contactDetailDTO);
        return new ResponseEntity<>(createdContactDetail, HttpStatus.CREATED);
    }
     @GetMapping("/contactDetails")
    public ResponseEntity<List<ContactDetailDTO>> getAllContactDetail(){
        List<ContactDetailDTO> allContactDetailList = contactDetailService.findAllContactDetailsSortById();

         if (CollectionUtils.isEmpty(allContactDetailList)) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         } else {
             return new ResponseEntity<>(allContactDetailList, HttpStatus.OK);
         }
     }
}
