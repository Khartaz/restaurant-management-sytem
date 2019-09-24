package com.restaurant.management.web.controller;

import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.facade.DailyOrderListFacade;
import com.restaurant.management.web.response.company.StatisticsReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private DailyOrderListFacade dailyOrderListFacade;

    @Autowired
    public StatisticsController(DailyOrderListFacade dailyOrderListFacade) {
        this.dailyOrderListFacade = dailyOrderListFacade;
    }

    @GetMapping(value = "/orders/daily/count", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    StatisticsReportResponse todayStatsReport(@CurrentUser UserPrincipal currentUser) {
        return dailyOrderListFacade.countDailyOrders(currentUser);
    }
}
