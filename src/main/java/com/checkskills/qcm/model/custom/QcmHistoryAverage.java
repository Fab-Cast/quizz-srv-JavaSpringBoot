package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.Qcm;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
public class QcmHistoryAverage {
    private Integer successNote;
    private Integer percentage;
}
