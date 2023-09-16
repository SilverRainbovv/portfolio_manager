package com.didenko.service;

import com.didenko.dto.PortfolioReadDto;
import com.didenko.mapper.PortfolioReadDtoMapper;
import com.didenko.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioReadDtoMapper portfolioReadDtoMapper;

    public List<PortfolioReadDto> getByUserId(Long userId){
        return portfolioRepository.findByUserId(userId).stream()
                .map(portfolioReadDtoMapper::mapFrom).toList();
    }

}
