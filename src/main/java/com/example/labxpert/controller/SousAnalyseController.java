package com.example.labxpert.controller;

import com.example.labxpert.dtos.SousAnalyseDto;
import com.example.labxpert.exception.message_error_exception.MessageError;
import com.example.labxpert.service.ISousAnalyseService;
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
@RequestMapping("/api/v1/sous-analyses")
public class SousAnalyseController {

    private final ISousAnalyseService iSousAnalyseService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<List<SousAnalyseDto>> getAll()
    {
        return ResponseEntity.ok(iSousAnalyseService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'TECHNICIAN')")
    public ResponseEntity<SousAnalyseDto> save(@RequestBody @Valid SousAnalyseDto sousAnalyseDto)
    {
        SousAnalyseDto sousAnalyseSaved = iSousAnalyseService.add(sousAnalyseDto);
        return new ResponseEntity<>(sousAnalyseSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'TECHNICIAN')")
    public ResponseEntity<SousAnalyseDto> update(@PathVariable Long id, @RequestBody @Valid SousAnalyseDto sousAnalyseDto)
    {
        SousAnalyseDto sousAnalyseUpdated = iSousAnalyseService.update(id, sousAnalyseDto);
        return new ResponseEntity<>(sousAnalyseUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<SousAnalyseDto> getById(@PathVariable Long id)
    {
        try{
            SousAnalyseDto sousAnalyse = iSousAnalyseService.getById(id);
            return new ResponseEntity<>(sousAnalyse, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Sous analyse deleted successfully.");
        iSousAnalyseService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }

    @GetMapping("/title")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<SousAnalyseDto> getByTitle(@RequestParam String title)
    {
        try{
            SousAnalyseDto sousAnalyse = iSousAnalyseService.getByTitle(title);
            return new ResponseEntity<>(sousAnalyse, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
