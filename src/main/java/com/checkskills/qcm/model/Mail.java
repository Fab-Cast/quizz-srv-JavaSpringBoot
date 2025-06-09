package com.checkskills.qcm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Mail {
    List<String> codeList;
    String email;
    String candidate_name;
    String body;
    String object;
}
