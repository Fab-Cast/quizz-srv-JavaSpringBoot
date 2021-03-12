package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.Qcm;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

public class QcmHistoryAverage {

    private Integer successNote;
    private Integer percentage;

    public Integer getSuccessNote() {
        return successNote;
    }

    public void setSuccessNote(Integer successNote) {
        this.successNote = successNote;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
