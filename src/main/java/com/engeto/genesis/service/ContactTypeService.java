package com.engeto.genesis.service;

import com.engeto.genesis.domain.ContactType;
import com.engeto.genesis.model.ContactTypeDTO;
import com.engeto.genesis.repository.ContactTypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;
    private ModelMapper modelMapper;

    public ContactTypeDTO createContactType(ContactTypeDTO contactTypeDTO) {
        validateNewContactType(contactTypeDTO);
        ContactType contactType = convertToDomain(contactTypeDTO);
        contactTypeRepository.save(contactType);
        return contactTypeDTO;
    }

    public List<ContactTypeDTO> findAllContactTypesSortById() {
        List<ContactType> allContactTypes = contactTypeRepository.findAll(Sort.by("id"));
        return allContactTypes.stream().map(this::convertToDTO).toList();
    }

    public ContactTypeDTO findContactTypeById(Long id) {
        ContactType existContactType = contactTypeRepository.findById(id).orElse(null);
        if (existContactType == null) {
            return null;
        } else {
            return convertToDTO(existContactType);
        }
    }

    public void deleteContactType(Long id) {
        contactTypeRepository.deleteById(id);
    }

    private ContactTypeDTO convertToDTO(ContactType contactType) {
        ContactTypeDTO contactTypeDTO = modelMapper.map(contactType, ContactTypeDTO.class);
        return contactTypeDTO;
    }

    private ContactType convertToDomain(ContactTypeDTO contactTypeDTO) {
        ContactType contactType = modelMapper.map(contactTypeDTO, ContactType.class);
        return contactType;
    }

    public void validateNewContactType(ContactTypeDTO contactTypeDTO) {
        String type = contactTypeDTO.getType();
        String errorMessage = "ContactType: " + contactTypeDTO.getType() + " already exist.";
        boolean test = contactTypeRepository.existsByType(contactTypeDTO.getType());
        String type2 = contactTypeDTO.getType();
        if (contactTypeRepository.existsByType(contactTypeDTO.getType())) {
            throw new RuntimeException(errorMessage);
        }
    }

}
