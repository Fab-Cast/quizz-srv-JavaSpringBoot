package com.checkskills.qcm.repository;

import com.checkskills.qcm.model.Plan;
import com.checkskills.qcm.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
