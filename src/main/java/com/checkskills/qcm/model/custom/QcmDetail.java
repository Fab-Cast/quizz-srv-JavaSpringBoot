package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.QcmDifficulty;
import com.checkskills.qcm.model.Sector;

import java.util.List;

public class QcmDetail {

    Long id;
    String title;
    String description;
    String detail;
    Float note;
    Long credits;
    boolean visible;
    UserLite user;
    List<Sector> sectorList;
    QcmDifficulty difficulty;
    Integer questionLength;
    Integer usedLength;
    Integer durationAverage;
    List <QcmLite> qcmLiteList;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getNote() {
        return note;
    }

    public void setNote(Float note) {
        this.note = note;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public UserLite getUser() {
        return user;
    }

    public void setUser(UserLite user) {
        this.user = user;
    }

    public List<Sector> getSectorList() {
        return sectorList;
    }

    public void setSectorList(List<Sector> sectorList) {
        this.sectorList = sectorList;
    }

    public QcmDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(QcmDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getQuestionLength() {
        return questionLength;
    }

    public void setQuestionLength(Integer questionLength) {
        this.questionLength = questionLength;
    }

    public Integer getUsedLength() {
        return usedLength;
    }

    public void setUsedLength(Integer usedLength) {
        this.usedLength = usedLength;
    }

    public Integer getDurationAverage() {
        return durationAverage;
    }

    public void setDurationAverage(Integer durationAverage) {
        this.durationAverage = durationAverage;
    }

    public List<QcmLite> getQcmLiteList() {
        return qcmLiteList;
    }

    public void setQcmLiteList(List<QcmLite> qcmLiteList) {
        this.qcmLiteList = qcmLiteList;
    }
}
