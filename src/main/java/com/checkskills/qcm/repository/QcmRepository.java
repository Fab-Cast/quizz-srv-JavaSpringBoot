package com.checkskills.qcm.repository;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.QcmHistory;
import com.checkskills.qcm.model.custom.QcmLite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QcmRepository extends JpaRepository<Qcm, Long> {

    //@Query("SELECT new com.checkskills.qcm.model.custom.QcmLite(q.id, q.title, q.note )" + "FROM Qcm AS q GROUP BY q.id ORDER BY q.id ASC")
    //@Query("SELECT q.id, q.title, q.note, j.title as jobTitle FROM Qcm q LEFT JOIN Job j ON q.id = j.id GROUP BY q.id ORDER BY q.id ASC")
    //List<QcmLite> find();


    @Query(value = "SELECT * FROM qcm q WHERE q.user_id = ?1", nativeQuery = true)
    List<Qcm> findAllByUser(Long userId);

    @Query(value = "SELECT qcm FROM qcm q WHERE q.id = ?1 LIMIT 1", nativeQuery = true)
    QcmLite findQcmLiteById(Long qcmId);

    Qcm findOneById(Long id);

}
