package com.example.userservice.controller;

import com.example.userservice.domain.model.Finance;
import com.example.userservice.domain.service.FinanceService;
import com.example.userservice.dto.*;
import com.example.userservice.mapper.FinanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/updateFinanceDetails")
    public ResponseEntity<ResponseDto> updateFinanceDetails(@RequestBody FinanceUpdateRequestDto financeUpdateRequestDto) {
        Finance fin = finService.updateFinanceDetails(finMapper.mapUpdateReqToFinance(financeUpdateRequestDto));
        FinanceCreateResponseDto response = finMapper.mapToFinanceCreateResponseDto(fin);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("Finance Details Updated Successfully")
                .data(response)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteFinanceDetails/{userId}")
    public ResponseEntity<ResponseDto> deleteFinanceDetails(@PathVariable int userId) {
        boolean deleted = finService.deleteFinanceDetails(userId);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("Finance Details Deleted Successfully")
                .data(deleted)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
