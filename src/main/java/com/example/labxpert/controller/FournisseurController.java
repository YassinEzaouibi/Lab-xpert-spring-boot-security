package com.example.labxpert.controller;

import com.example.labxpert.dtos.FournisseurDto;
import com.example.labxpert.exception.message_error_exception.MessageError;
import com.example.labxpert.service.IFournisseurService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fournisseurs")
public class FournisseurController {

    private final IFournisseurService iFournisseurService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<List<FournisseurDto>> getAll()
    {
        return ResponseEntity.ok(iFournisseurService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FournisseurDto> save(@RequestBody @Valid FournisseurDto fournisseurDto)
    {
        FournisseurDto fournisseurSaved = iFournisseurService.add(fournisseurDto);
        return new ResponseEntity<>(fournisseurSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FournisseurDto> update(@PathVariable Long id, @RequestBody @Valid FournisseurDto fournisseurDto)
    {
        FournisseurDto fournisseurUpdated = iFournisseurService.update(id, fournisseurDto);
        return new ResponseEntity<>(fournisseurUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<FournisseurDto> getById(@PathVariable Long id)
    {
        try{
            FournisseurDto fournisseur = iFournisseurService.getById(id);
            return new ResponseEntity<>(fournisseur, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Fournisseur deleted successfully.");
        iFournisseurService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }

    @GetMapping("/name")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<FournisseurDto> getByName(@RequestParam String name)
    {
        try{
            FournisseurDto fournisseur = iFournisseurService.getByName(name);
            return new ResponseEntity<>(fournisseur, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
