package com.example.labxpert.service.implementation;

import com.example.labxpert.dtos.AnalyseDto;
import com.example.labxpert.dtos.ReactifDto;
import com.example.labxpert.dtos.SousAnalyseDto;
import com.example.labxpert.exception.NotFoundException;
import com.example.labxpert.model.Analyse;
import com.example.labxpert.model.Reactif;
import com.example.labxpert.model.SousAnalyse;
import com.example.labxpert.repository.IAnalyseRepository;
import com.example.labxpert.repository.IReactifRepository;
import com.example.labxpert.repository.ISousAnalyseRepository;
import com.example.labxpert.service.ISousAnalyseService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SousAnalyseServiceImpl implements ISousAnalyseService {

    private final ISousAnalyseRepository iSousAnalyseRepository;
    private final IReactifRepository iReactifRepository;
    private final IAnalyseRepository iAnalyseRepository;
    private final ModelMapper modelMapper;

    @Override
    public SousAnalyseDto add(SousAnalyseDto sousAnalyseDto)
    {
        validation(sousAnalyseDto);

        Analyse analyseExist = iAnalyseRepository.findByIdAndDeletedFalse(sousAnalyseDto.getAnalyse().getId()).orElseThrow(() -> new NotFoundException("Analyse not found with this id : " + sousAnalyseDto.getAnalyse().getId()));
        Reactif reactifExist = iReactifRepository.findByIdAndDeletedFalse(sousAnalyseDto.getReactif().getId()).orElseThrow(() -> new NotFoundException("Reactif not found with this id : " + sousAnalyseDto.getReactif().getId()));

        //TODO: CHANGE QUANTITY OF REACTIF SELECTED
        changeQuantityStockOfReactif(reactifExist);

        sousAnalyseDto.setAnalyse(modelMapper.map(analyseExist, AnalyseDto.class));
        sousAnalyseDto.setReactif(modelMapper.map(reactifExist, ReactifDto.class));

        SousAnalyse sousAnalyse = iSousAnalyseRepository.save(modelMapper.map(sousAnalyseDto, SousAnalyse.class));
        return modelMapper.map(sousAnalyse, SousAnalyseDto.class);
    }

    @Override
    public SousAnalyseDto update(Long id, SousAnalyseDto sousAnalyseDto)
    {
        validation(sousAnalyseDto);

        SousAnalyse sousAnalyseExist = iSousAnalyseRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Sous analyse not found with this id : " + id));
        Analyse analyseExist = iAnalyseRepository.findByIdAndDeletedFalse(sousAnalyseDto.getAnalyse().getId()).orElseThrow(() -> new NotFoundException("Analyse not found with this id : " + sousAnalyseDto.getAnalyse().getId()));
        Reactif reactifExist = iReactifRepository.findByIdAndDeletedFalse(sousAnalyseDto.getReactif().getId()).orElseThrow(() -> new NotFoundException("Reactif not found with this id : " + sousAnalyseDto.getReactif().getId()));

        changeQuantityStockOfReactif(reactifExist);

        sousAnalyseExist.setTitle(sousAnalyseDto.getTitle());
        sousAnalyseExist.setEtatNormalMin(sousAnalyseDto.getEtatNormalMin());
        sousAnalyseExist.setEtatNormalMax(sousAnalyseDto.getEtatNormalMax());
        sousAnalyseExist.setAnalyse(analyseExist);
        sousAnalyseExist.setReactif(reactifExist);

        SousAnalyse sousAnalyseUpdated = iSousAnalyseRepository.save(sousAnalyseExist);
        return modelMapper.map(sousAnalyseUpdated, SousAnalyseDto.class);
    }

    @Override
    public void delete(Long id)
    {
        SousAnalyse sousAnalyseExist = iSousAnalyseRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Sous analyse not found with this id : " + id));
        sousAnalyseExist.setDeleted(true);
        iSousAnalyseRepository.save(sousAnalyseExist);
    }

    @Override
    public List<SousAnalyseDto> getAll()
    {
        List<SousAnalyse> sousAnalyses = iSousAnalyseRepository.findByDeletedFalse();
        return sousAnalyses.stream()
                .map(sousAnalyse -> modelMapper.map(sousAnalyse, SousAnalyseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SousAnalyseDto getById(Long id)
    {
        SousAnalyse sousAnalyse = iSousAnalyseRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Sous analyse not found with this id : " + id));
        return modelMapper.map(sousAnalyse, SousAnalyseDto.class);
    }

    @Override
    public SousAnalyseDto getByTitle(String title)
    {
        SousAnalyse sousAnalyse = iSousAnalyseRepository.findByTitleAndDeletedFalse(title).orElseThrow(() -> new NotFoundException("Sous analyse not found with this title : " + title));
        return modelMapper.map(sousAnalyse, SousAnalyseDto.class);
    }

    @Override
    public void changeQuantityStockOfReactif(Reactif reactifExist)
    {
        if(reactifExist.getQuantityStock() != 0)
        {
            reactifExist.setQuantityStock(reactifExist.getQuantityStock() - 1);
            iReactifRepository.save(reactifExist);
        }else{
            throw new ValidationException("Ce material est n'est pas disponible dans le stock.");
        }
    }

    @Override
    public void validation(SousAnalyseDto sousAnalyseDto)
    {
        if (sousAnalyseDto == null)
        {
            throw new ValidationException("Le sous analyse est requise.");
        }

        if (StringUtils.isBlank(sousAnalyseDto.getTitle())) {
            throw new ValidationException("Le titre est requise.");
        }

        if (sousAnalyseDto.getEtatNormalMax() == 0) {
            throw new ValidationException("L'état normal maximum est requise.");
        }

        if (sousAnalyseDto.getEtatNormalMin() == 0) {
            throw new ValidationException("L'état normal minimum est requise.");
        }

        if (sousAnalyseDto.getAnalyse() == null) {
            throw new ValidationException("L'analyse est requise.");
        }

        if (sousAnalyseDto.getReactif() == null) {
            throw new ValidationException("Le réactif est requise.");
        }
    }
}
