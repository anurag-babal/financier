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

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.CREATED,
                        "Finance Details Created Successfully",
                        response
                ), HttpStatus.CREATED
        );
    }

    @PostMapping("/getFinanceDetails")
    public ResponseEntity<ResponseDto> getFinanceDetails(@RequestBody FinanceDetailsRequestDto financeDetailsRequestDto) {
        Finance fin = finService.getFinanceDetails(finMapper.mapGetReqToFinance(financeDetailsRequestDto));
        FinanceDetailsResponseDto response = finMapper.mapToFinanceDetailsResponse(fin);

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Finance Details Fetched Successfully",
                        response
                ), HttpStatus.OK
        );
    }

    @PostMapping("/updateFinanceDetails/{id}")
    public ResponseEntity<ResponseDto> updateFinanceDetails(
            @PathVariable int id,
            @RequestBody FinanceUpdateRequestDto financeUpdateRequestDto
    ) {
        Finance fin = finService.updateFinanceDetails(id, finMapper.mapUpdateReqToFinance(financeUpdateRequestDto));
        FinanceCreateResponseDto response = finMapper.mapToFinanceCreateResponseDto(fin);

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Finance Details Updated Successfully",
                        response
                ), HttpStatus.OK
        );
    }

    @DeleteMapping("/deleteFinanceDetails/{userId}")
    public ResponseEntity<ResponseDto> deleteFinanceDetails(@PathVariable int userId) {
        boolean deleted = finService.deleteFinanceDetails(userId);

        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Finance Details Deleted Successfully",
                        deleted
                ), HttpStatus.OK
        );
    }
}
