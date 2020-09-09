package com.checkskills.qcm.repository;

import com.checkskills.qcm.model.Qcm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QcmRepository extends JpaRepository<Qcm, Long> {
    //@Query("SELECT new com.checkskills.qcm.model.custom.QcmLite(q.id, q.title, q.note )" + "FROM Qcm AS q GROUP BY q.id ORDER BY q.id ASC")
    //@Query("SELECT q.id, q.title, q.note, j.title as jobTitle FROM Qcm q LEFT JOIN Job j ON q.id = j.id GROUP BY q.id ORDER BY q.id ASC")
    //List<QcmLite> find();
}
