package com.checkskills.qcm.repository.custom;

import com.checkskills.qcm.model.QcmHistory;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.model.custom.QcmHistoryLite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QcmHistoryLiteRepository extends JpaRepository<QcmHistoryLite, Long> {

    List<QcmHistoryLite> findAllByQcmId(Long id);

}
