package com.checkskills.qcm.repository.custom;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.custom.QcmLite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QcmLiteRepository extends JpaRepository<QcmLite, Long> {
    //@Query("SELECT new com.checkskills.qcm.model.custom.QcmLite(q.id, q.title, q.note )" + "FROM Qcm AS q GROUP BY q.id ORDER BY q.id ASC")
    //@Query("SELECT q.id, q.title, q.note, j.title as jobTitle FROM Qcm q LEFT JOIN Job j ON q.id = j.id GROUP BY q.id ORDER BY q.id ASC")
    //List<QcmLite> find();
}
