package com.neuedu.hospitalbackend.model.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Date;

public class Inspection {

    private Integer id;

    private Integer caseId;

    private Integer projectId;

    private Integer creatorId;

    private Integer inspectorId;

    private Boolean status;

    private String goal;

    private String requirement;

    private String resultImage;

    private String resultPicture;

    private String resultDescription;

    private String advice;

    private Date gmtCreate;

    private Date gmtModified;

}
