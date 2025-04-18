package com.panelitapi.service;

import com.panelitapi.model.Plan;
import com.panelitapi.model.User;
import com.panelitapi.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan findById(Integer id) {
        return planRepository.findById(id).orElseThrow();
    }

    public List<Plan> findAll() {return planRepository.findAll();}
}
