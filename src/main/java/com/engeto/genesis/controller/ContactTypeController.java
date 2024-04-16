package com.engeto.genesis.controller;

import com.engeto.genesis.model.ContactTypeDTO;
import com.engeto.genesis.service.ContactTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class ContactTypeController {

    private final ContactTypeService contactTypeService;


    @PostMapping("/contactType")
    public ResponseEntity<ContactTypeDTO> createContactType(@RequestBody ContactTypeDTO contactTypeDTO) {
        contactTypeService.createContactType(contactTypeDTO);
        return new ResponseEntity<>(contactTypeDTO, HttpStatus.CREATED);
    }

    @GetMapping("/contactTypes")
    public ResponseEntity<List<ContactTypeDTO>> getAllContactTypeSortedById() {
        List<ContactTypeDTO> findAllContactType = contactTypeService.findAllContactTypesSortById();
        if (CollectionUtils.isEmpty(findAllContactType)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(findAllContactType, HttpStatus.OK);
        }
    }

    @GetMapping("/contactType/{id}")
    public ResponseEntity<ContactTypeDTO> getContactTypeById(@PathVariable("id") Long id) {
        ContactTypeDTO existContactType = contactTypeService.findContactTypeById(id);
        if (existContactType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(existContactType, HttpStatus.OK);
    }

    @DeleteMapping("/contactType/{id}")
    public ResponseEntity<ContactTypeDTO> deleteTypeById(@PathVariable("id") Long id) {
        contactTypeService.deleteContactType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
