package com.money.moneymanager.controller;

import com.money.moneymanager.dto.ExpenseDTO;
import com.money.moneymanager.dto.FilterDTO;
import com.money.moneymanager.dto.IncomeDTO;
import com.money.moneymanager.service.ExpenseService;
import com.money.moneymanager.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> filterTransections(@RequestBody FilterDTO filter){
        LocalDate startDate = filter.getStartDate() !=null ? filter.getStartDate() : LocalDate.MIN;
        LocalDate endDate = filter.getEndDate() !=null ? filter.getEndDate() : LocalDate.now();
        String keyword = filter.getKeyword()!=null ? filter.getKeyword() : "";
        String sortFiled = filter.getSortField()!=null ? filter.getSortField() : "date";
        Sort.Direction direction = "desc".equalsIgnoreCase(filter.getSortOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction,sortFiled);

        if("income".equalsIgnoreCase(filter.getType())){
            List<IncomeDTO> incomes = incomeService.filterIncomes(startDate,endDate,keyword,sort);
            return ResponseEntity.ok(incomes);
        } else if ("expense".equalsIgnoreCase(filter.getType())) {
            List<ExpenseDTO> expenses = expenseService.filterExpenses(startDate,endDate,keyword,sort);
            return ResponseEntity.ok(expenses);

        }else {
            return ResponseEntity.badRequest().body("Invalid type. Must be income or expense");
        }

    }
}
