package com.checkskills.qcm.repository;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.QcmHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QcmHistoryRepository  extends JpaRepository<QcmHistory, Long> {
}
