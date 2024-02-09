package com.example.labxpert.Controller;

import com.example.labxpert.Dtos.PlanificationDto;
import com.example.labxpert.Dtos.ResultDto;
import com.example.labxpert.Exception.MessageErrorException.MessageError;
import com.example.labxpert.Service.IPlanificationService;
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
@RequestMapping("/api/v1/planifications")
public class PlanificationController {

    private final IPlanificationService iPlanificationService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<List<PlanificationDto>> getAll()
    {
        return ResponseEntity.ok(iPlanificationService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<PlanificationDto> save(@RequestBody @Valid PlanificationDto planificationDto)
    {
        PlanificationDto planificationSaved = iPlanificationService.add(planificationDto);
        return new ResponseEntity<>(planificationSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<PlanificationDto> update(@PathVariable Long id, @RequestBody @Valid PlanificationDto planificationDto)
    {
        PlanificationDto planificationSaved = iPlanificationService.update(id, planificationDto);
        return new ResponseEntity<>(planificationSaved, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'TECHNICIAN')")
    public ResponseEntity<PlanificationDto> getById(@PathVariable Long id)
    {
        try{
            PlanificationDto planification = iPlanificationService.getById(id);
            return new ResponseEntity<>(planification, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Planification deleted successfully.");
        iPlanificationService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }
}
