package com.panelitapi.repository;

import com.panelitapi.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
}
