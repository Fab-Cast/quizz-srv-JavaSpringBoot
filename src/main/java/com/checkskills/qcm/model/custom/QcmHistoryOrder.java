package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.QcmHistoryStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

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
    private int success;
    private QcmHistoryStatus status;


    public QcmHistoryOrder(Long id,Long qcm_id,String code,Date date_bought,Date date_used,Date date_invited,String title,String candidate_name,int success,QcmHistoryStatus status){
        this.id = id;
        this.qcm_id = qcm_id;
        this.code = code;
        this.date_bought = date_bought;
        this.date_used = date_used;
        this.date_invited = date_invited;
        this.title = title;
        this.candidate_name = candidate_name;
        this.success = success;
        this.status = status;
    }

    public Date getDate_invited() {
        return date_invited;
    }

    public void setDate_invited(Date date_invited) {
        this.date_invited = date_invited;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQcm_id() {
        return qcm_id;
    }

    public void setQcm_id(Long qcm_id) {
        this.qcm_id = qcm_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate_bought() {
        return date_bought;
    }

    public void setDate_bought(Date date_bought) {
        this.date_bought = date_bought;
    }

    public Date getDate_used() {
        return date_used;
    }

    public void setDate_used(Date date_used) {
        this.date_used = date_used;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCandidate_name() {
        return candidate_name;
    }

    public void setCandidate_name(String candidate_name) {
        this.candidate_name = candidate_name;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public QcmHistoryStatus getStatus() {
        return status;
    }

    public void setStatus(QcmHistoryStatus status) {
        this.status = status;
    }
}