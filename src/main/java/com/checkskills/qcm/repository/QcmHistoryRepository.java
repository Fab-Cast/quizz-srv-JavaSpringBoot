package com.checkskills.qcm.repository;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.model.custom.QcmHistoryLite;
import com.checkskills.qcm.model.custom.QcmHistoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QcmHistoryRepository  extends JpaRepository<QcmHistory, Long> {

    QcmHistory findOneByCode(String code);

    List<QcmHistory> findAllByQcmId(Long id);

    List<QcmHistory> findByEmployer(User user);


    //@Query(value = "SELECT qcm_history.qcm_id, qcm_history.id, qcm_history.code, qcm_history.date_bought, qcm_history.date_used, qcm.title, qcm_history.candidate_name, qcm_history.success, qcm_history.status FROM checkskills.qcm_history LEFT JOIN qcm ON qcm_history.qcm_id = qcm.id  WHERE employer_id = :employer_id ORDER BY qcm_history.date_used DESC, qcm_history.date_bought DESC", nativeQuery = true)

    @Query("SELECT new com.checkskills.qcm.model.custom.QcmHistoryOrder(qcm_history.id, qcm.id, qcm_history.code, qcm_history.dateBought, qcm_history.dateUsed, qcm.title, qcm_history.candidate_name, qcm_history.success, qcm_history.status)" + "from QcmHistory as qcm_history LEFT JOIN Qcm as qcm ON qcm_history.qcm.id = qcm.id WHERE qcm_history.employer.id = :employer_id ORDER BY qcm_history.dateUsed DESC, qcm_history.dateBought DESC")
    List<QcmHistoryOrder> findQcmHistoryOrder(@Param("employer_id") Long employer_id);


}
