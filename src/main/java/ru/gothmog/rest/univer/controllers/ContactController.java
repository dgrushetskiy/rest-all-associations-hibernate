package ru.gothmog.rest.univer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gothmog.rest.univer.exceptions.ResourceNotFoundException;
import ru.gothmog.rest.univer.model.Contact;
import ru.gothmog.rest.univer.repositories.ContactRepository;
import ru.gothmog.rest.univer.repositories.StudentRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/contacts")
    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    @GetMapping("/students/{studentId}/contacts")
    public Contact getContactByStudentId(@PathVariable Long studentId) {

        if(!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found!",studentId);
        }

        List<Contact> contacts = contactRepository.findByStudentId(studentId);
        if(contacts.size() > 0) {
            return contacts.get(0);
        }else {
            throw new ResourceNotFoundException("Contact not found!",studentId);
        }
    }

    @PostMapping("/students/{studentId}/contacts")
    public Contact addContact(@PathVariable Long studentId,
                              @Valid @RequestBody Contact contact) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    contact.setStudent(student);
                    return contactRepository.save(contact);
                }).orElseThrow(() -> new ResourceNotFoundException("Student not found!",studentId));
    }

    @PutMapping("/contacts/{contactId}")
    public Contact updateContact(@PathVariable Long contactId,
                                 @Valid @RequestBody Contact contactUpdated) {
        return contactRepository.findById(contactId)
                .map(contact -> {
                    contact.setCity(contactUpdated.getCity());
                    contact.setPhone(contactUpdated.getPhone());
                    return contactRepository.save(contact);
                }).orElseThrow(() -> new ResourceNotFoundException("Contact not found!",contactId));
    }

    @DeleteMapping("/contacts/{contactId}")
    public String deleteContact(@PathVariable Long contactId) {
        return contactRepository.findById(contactId)
                .map(contact -> {
                    contactRepository.delete(contact);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new ResourceNotFoundException("Contact not found!",contactId));
    }
}
