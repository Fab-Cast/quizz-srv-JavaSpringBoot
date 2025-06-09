package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.QcmDifficulty;
import com.checkskills.qcm.model.Sector;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QcmDetail {
    private Long id;
    String title;
    String description;
    String detail;
    Float note;
    Float popularity;
    Long credits;
    boolean visible;
    UserLite user;
    List<Sector> sectorList;
    QcmDifficulty difficulty;
    Integer questionLength;
    Integer usedLength;
    Integer durationAverage;
    List <QcmLite> qcmLiteList;
}
