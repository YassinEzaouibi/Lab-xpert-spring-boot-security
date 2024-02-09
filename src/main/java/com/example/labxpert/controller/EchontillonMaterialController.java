package com.example.labxpert.controller;

import com.example.labxpert.dtos.EchontillonMaterialDto;
import com.example.labxpert.exception.message_error_exception.MessageError;
import com.example.labxpert.service.IEchontillonMaterialService;
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
@RequestMapping("/api/v1/echontillons_materials")
public class EchontillonMaterialController {

    private final IEchontillonMaterialService iEchontillonMaterialService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<List<EchontillonMaterialDto>> getAll()
    {
        return ResponseEntity.ok(iEchontillonMaterialService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'TECHNICIAN')")
    public ResponseEntity<EchontillonMaterialDto> save(@RequestBody @Valid EchontillonMaterialDto echontillonMaterialDto)
    {
        EchontillonMaterialDto echontillonMaterialSaved = iEchontillonMaterialService.add(echontillonMaterialDto);
        return new ResponseEntity<>(echontillonMaterialSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'TECHNICIAN')")
    public ResponseEntity<EchontillonMaterialDto> update(@PathVariable Long id, @RequestBody @Valid EchontillonMaterialDto echontillonMaterialDto)
    {
        EchontillonMaterialDto echontillonMaterialUpdated = iEchontillonMaterialService.update(id, echontillonMaterialDto);
        return new ResponseEntity<>(echontillonMaterialUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<EchontillonMaterialDto> getById(@PathVariable Long id)
    {
        try{
            EchontillonMaterialDto echontillonMaterial = iEchontillonMaterialService.getById(id);
            return new ResponseEntity<>(echontillonMaterial, HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'TECHNICIAN')")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Echontillon material deleted successfully.");
        iEchontillonMaterialService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }

    @GetMapping("/price-total")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<List<EchontillonMaterialDto>> getByPriceTotalBefore(@RequestParam(name = "price-total") double priceTotal)
    {
        try{
            List<EchontillonMaterialDto> echontillonMaterials = iEchontillonMaterialService.getByPriceTotalBefore(priceTotal);
            return new ResponseEntity<>(echontillonMaterials, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/quantity")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<List<EchontillonMaterialDto>> getByQuantityBefore(@RequestParam int quantity)
    {
        try{
            List<EchontillonMaterialDto> echontillonMaterials = iEchontillonMaterialService.getByQuantityBefore(quantity);
            return new ResponseEntity<>(echontillonMaterials, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
