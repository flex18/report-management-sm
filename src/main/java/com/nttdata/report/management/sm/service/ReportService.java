package com.nttdata.report.management.sm.service;

import com.nttdata.report.management.sm.entity.Commission;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ReportService {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  @Autowired
  public ReportService(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  public Flux<Map<String, BigDecimal>> ReportOfAllCommissionsChargedByProductInTheCurrentMonth() {
    LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1);
    LocalDateTime toDate = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).minusDays(1);

    return reactiveMongoTemplate.find(
        Query.query(Criteria.where("date").gte(startDate).lte(toDate)),
        Commission.class
    ).collectList().map(commissions -> {
      Map<String, BigDecimal> report = new HashMap<>();
      for (Commission c : commissions) {
        report.put(c.getTransactionType(), report.getOrDefault(c.getTransactionType(), BigDecimal.ZERO).add(c.getAmount()));
      }
      return report;
    }).flux();
  }

}
