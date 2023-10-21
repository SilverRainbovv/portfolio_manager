package com.didenko.service;

import com.didenko.dto.PortfolioCreateEditDto;
import com.didenko.dto.PortfolioReadDto;
import com.didenko.mapper.PortfolioCreateEditDtoMapper;
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
    private final PortfolioCreateEditDtoMapper portfolioCreateEditDtoMapper;

    @Transactional
    public void createPortfolio(PortfolioCreateEditDto portfolioCreateEditDto){

        portfolioRepository.save(portfolioCreateEditDtoMapper.mapFrom(portfolioCreateEditDto));
    }

    public List<PortfolioReadDto> getByUserId(Long userId){
        return portfolioRepository.findByUserId(userId).stream()
                .map(portfolioReadDtoMapper::mapFrom).toList();
    }

    public boolean verifyPortfolioBelongsToUser(Long portfolioId, Long userId){
        return portfolioRepository.verifyPortfolioBelongsToUserById(portfolioId, userId).isPresent();
    }

}
