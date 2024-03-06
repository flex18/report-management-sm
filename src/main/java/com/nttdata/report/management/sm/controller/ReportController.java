package com.nttdata.report.management.sm.controller;

import com.nttdata.report.management.sm.service.ReportService;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reports")
public class ReportController {

  @Autowired
  ReportService reportService;

  @GetMapping("/commissions-charged-current-month")
  public Flux<Map<String, BigDecimal>> getReportOfAllCommissionsChargedByProductInTheCurrentMonth() {
    return reportService.ReportOfAllCommissionsChargedByProductInTheCurrentMonth();
  }
}
