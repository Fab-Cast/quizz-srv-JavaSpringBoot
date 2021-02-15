package com.checkskills.qcm.repository.custom;

import com.checkskills.qcm.model.custom.QuestionLauncher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionLauncherRepository extends JpaRepository<QuestionLauncher, Long> {

    Optional<QuestionLauncher> findById(Long questionId);

}
