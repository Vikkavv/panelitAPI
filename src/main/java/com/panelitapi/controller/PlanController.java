package com.panelitapi.controller;

import com.panelitapi.model.Plan;
import com.panelitapi.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Plan")
public class PlanController {

    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/findAll")
    public List<Plan> findAll() {
        return planService.findAll();
    }

    @GetMapping("/findAllNoUsers")
    public List<Plan> findAllNoUsers() {
        List<Plan> plans = planService.findAll();
        for (Plan plan : plans) {
            plan.setUsers(null);
        }
        return plans;
    }
}
