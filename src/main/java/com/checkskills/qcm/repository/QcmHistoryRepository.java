package com.checkskills.qcm.repository;

import com.checkskills.qcm.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QcmHistoryRepository  extends JpaRepository<QcmHistory, Long> {

    List<QcmHistory> findByEmployer(User user);


    @Query(value = "SELECT qcm_history.qcm_id, qcm_history.code, qcm_history.date_bought, qcm.title FROM checkskills.qcm_history LEFT JOIN qcm ON qcm_history.qcm_id = qcm.id  WHERE employer_id = :employer_id ORDER BY qcm_history.date_bought DESC", nativeQuery = true)
    List<QcmHistoryOrder> findQcmHistoryOrder(@Param("employer_id") Long employer_id);

    public interface QcmHistoryOrder {
        Long getQcm_id();
        String getCode();
        Date getDate_bought();
        String getTitle();
    }

}
