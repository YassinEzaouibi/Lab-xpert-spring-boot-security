package com.example.labxpert.controller;

import com.example.labxpert.dtos.PatientDto;
import com.example.labxpert.exception.message_error_exception.MessageError;
import com.example.labxpert.service.IPatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final IPatientService iPatientService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<List<PatientDto>> getAll()
    {
        return ResponseEntity.ok(iPatientService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<PatientDto> save(@RequestBody @Valid PatientDto patientDto)
    {
        PatientDto patientSaved = iPatientService.add(patientDto);
        return new ResponseEntity<>(patientSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<PatientDto> update(@RequestBody @Valid PatientDto patientDto, @PathVariable Long id)
    {
        PatientDto patientUpdated = iPatientService.update(id, patientDto);
        return new ResponseEntity<>(patientUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<PatientDto> getById(@PathVariable Long id)
    {
        try{
            PatientDto patient = iPatientService.getById(id);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/patient")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<List<PatientDto>> getByName(@RequestParam String name)
    {
        try{
            List<PatientDto> patients = iPatientService.getByName(name);
            return  new ResponseEntity<>(patients, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Patient deleted successfully.");
        iPatientService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }
}
