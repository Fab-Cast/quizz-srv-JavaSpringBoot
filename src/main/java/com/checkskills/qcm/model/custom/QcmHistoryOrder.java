package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.QcmHistoryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class QcmHistoryOrder {
    @Id
    private Long id;
    private Long qcm_id;
    private String code;
    private Date date_bought;
    private Date date_used;
    private Date date_invited;
    private String title;
    private String candidate_name;
    private String candidate_mail;
    private int success;
    private Integer duration;
    private QcmHistoryStatus status;
    public QcmHistoryOrder(Long id,Long qcm_id,String code,Date date_bought,Date date_used,Date date_invited,String title,String candidate_name,String candidate_mail,int success,Integer duration,QcmHistoryStatus status){
        this.id = id;
        this.qcm_id = qcm_id;
        this.code = code;
        this.date_bought = date_bought;
        this.date_used = date_used;
        this.date_invited = date_invited;
        this.title = title;
        this.candidate_name = candidate_name;
        this.candidate_mail = candidate_mail;
        this.success = success;
        this.duration = duration;
        this.status = status;
    }
}