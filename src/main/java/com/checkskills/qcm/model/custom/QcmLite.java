package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.QcmDifficulty;
import com.checkskills.qcm.model.Sector;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "qcm")
public class QcmLite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Float note;
    private Float popularity;
    private Long credits;
    private boolean visible;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserLite user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "qcm_sector",
            joinColumns = @JoinColumn(name = "qcm_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id"))
    private List<Sector> sectorList;
    @Enumerated(EnumType.STRING)
    private QcmDifficulty difficulty;
}
