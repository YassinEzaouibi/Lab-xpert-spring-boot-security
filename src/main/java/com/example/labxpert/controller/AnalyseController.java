package com.example.labxpert.controller;

import com.example.labxpert.dtos.AnalyseDto;
import com.example.labxpert.exception.message_error_exception.MessageError;
import com.example.labxpert.model.enums.TypeAnalyse;
import com.example.labxpert.service.IAnalyseService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/analyses")
public class AnalyseController {

    private final IAnalyseService iAnalyseService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<List<AnalyseDto>> getAll()
    {
        return ResponseEntity.ok(iAnalyseService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'TECHNICIAN')")
    public ResponseEntity<AnalyseDto> save(@RequestBody @Valid AnalyseDto analyseDto)
    {
        AnalyseDto analyseSaved = iAnalyseService.add(analyseDto);
        return new ResponseEntity<>(analyseSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'TECHNICIAN')")
    public ResponseEntity<AnalyseDto> update(@PathVariable Long id, @RequestBody @Valid AnalyseDto analyseDto)
    {
        AnalyseDto analyseUpdated = iAnalyseService.update(id, analyseDto);
        return new ResponseEntity<>(analyseUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<AnalyseDto> getById(@PathVariable Long id)
    {
        try{
            AnalyseDto analyse = iAnalyseService.getById(id);
            return new ResponseEntity<>(analyse, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'TECHNICIAN')")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Analyse deleted successfully.");
        iAnalyseService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }

    @GetMapping("/type-analyse")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<AnalyseDto> getByTypeAnalyse(@RequestParam(name = "type-analyse") TypeAnalyse typeAnalyse)
    {
        try{
            AnalyseDto analyse = iAnalyseService.getByTypeAnalyse(typeAnalyse);
            return new ResponseEntity<>(analyse, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/date")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<List<AnalyseDto>> getByDateBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "date-start") LocalDate dateStart, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "date-end") LocalDate dateEnd)
    {
        try{
            List<AnalyseDto> analyses = iAnalyseService.getByDateBetween(dateStart, dateEnd);
            return new ResponseEntity<>(analyses, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
