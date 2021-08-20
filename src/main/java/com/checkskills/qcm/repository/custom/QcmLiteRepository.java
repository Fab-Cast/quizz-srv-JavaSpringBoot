package com.checkskills.qcm.repository.custom;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.model.custom.QcmLite;
import com.checkskills.qcm.model.custom.UserLite;
import com.checkskills.qcm.model.custom.UserSubscriptionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QcmLiteRepository extends JpaRepository<QcmLite, Long> {
    //@Query("SELECT new com.checkskills.qcm.model.custom.QcmLite(q.id, q.title, q.note )" + "FROM Qcm AS q GROUP BY q.id ORDER BY q.id ASC")
    //@Query("SELECT q.id, q.title, q.note, j.title as jobTitle FROM Qcm q LEFT JOIN Job j ON q.id = j.id GROUP BY q.id ORDER BY q.id ASC")
    //List<QcmLite> find();

    List<QcmLite> findByVisible(boolean visible);

    List<QcmLite> findByUser(UserLite user);

    //@Query("SELECT new com.checkskills.qcm.model.custom.QcmLite(q.id, q.title, q.description, q.note, q.credits, COUNT(h), q.visible, q.user, q.sectorList, q.difficulty)" + "from QcmLite as q LEFT JOIN QcmHistory as h ON q.id = h.qcm.id")
    //List<QcmLite> findAll();


}
