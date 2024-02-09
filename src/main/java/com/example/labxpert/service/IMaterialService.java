package com.example.labxpert.service;

import com.example.labxpert.dtos.MaterialDto;

import java.util.List;

public interface IMaterialService {

    MaterialDto add(MaterialDto materialDto);
    MaterialDto update(Long id, MaterialDto materialDto);
    void delete(Long id);
    List<MaterialDto> getAll();
    MaterialDto getById(Long id);
    MaterialDto getByLibelle(String libelle);
    List<MaterialDto> getByPriceBefore(double price);
    List<MaterialDto> getByAvailableQuantityBefore(int availableQuantity);
    void validation(MaterialDto materialDto);
}
