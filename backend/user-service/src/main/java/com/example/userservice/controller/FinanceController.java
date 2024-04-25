package com.example.userservice.controller;

import com.example.userservice.domain.model.Finance;
import com.example.userservice.domain.service.FinanceService;
import com.example.userservice.dto.*;
import com.example.userservice.mapper.FinanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/finance")
public class FinanceController {
    private final FinanceService finService;
    private final FinanceMapper finMapper;

    @PostMapping("/addFinanceDetails")
    public ResponseEntity<ResponseDto> addFinanceDetails(@RequestBody FinanceCreateRequestDto financeCreateRequestDto) {
        Finance fin = finService.addFinanceDetails(finMapper.mapCreateReqToFinance(financeCreateRequestDto));
        FinanceCreateResponseDto response = finMapper.mapToFinanceCreateResponseDto(fin);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.CREATED)
                .message("Finance Details Added Successfully")
                .data(response)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/getFinanceDetails")
    public ResponseEntity<ResponseDto> getFinanceDetails(@RequestBody FinanceDetailsRequestDto financeDetailsRequestDto) {
        Finance fin = finService.getFinanceDetails(finMapper.mapGetReqToFinance(financeDetailsRequestDto));
        FinanceDetailsResponseDto response = finMapper.mapToFinanceDetailsResponse(fin);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("Finance Details Fetched Successfully")
                .data(response)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
