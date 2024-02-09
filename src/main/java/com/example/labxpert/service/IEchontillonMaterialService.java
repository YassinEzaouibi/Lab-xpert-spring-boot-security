package com.example.labxpert.service;

import com.example.labxpert.dtos.EchontillonMaterialDto;
import com.example.labxpert.model.EchontillonMaterial;
import com.example.labxpert.model.Material;
import com.example.labxpert.model.StockRecuperer;

import java.util.List;

public interface IEchontillonMaterialService {
    EchontillonMaterialDto add(EchontillonMaterialDto echontillonMaterialDto);
    EchontillonMaterialDto update(Long id, EchontillonMaterialDto echontillonMaterialDto);
    void delete(Long id);
    List<EchontillonMaterialDto> getAll();
    EchontillonMaterialDto getById(Long id);
    void saveStockConsomer(Material material, EchontillonMaterial echontillonMaterial);
    StockRecuperer recuperationStockQuantity(Long MaterialId, Long EchontillonMaterialId);
    void RecupererQuantityConsomerAvailableQuantity(Material material, StockRecuperer stockRecuperer);
    void substractionNewQuantityToAvailableQuantity(Material material, EchontillonMaterialDto echontillonMaterial);
    void deleteByEchontillonMaterialId(Long echontillonMaterialId);
    List<EchontillonMaterialDto> getByPriceTotalBefore(double priceTotal);
    List<EchontillonMaterialDto> getByQuantityBefore(int quantity);
    void validationQuantity(int availableQuantity, int quantity);
    void validation(EchontillonMaterialDto echontillonMaterialDto);
}
