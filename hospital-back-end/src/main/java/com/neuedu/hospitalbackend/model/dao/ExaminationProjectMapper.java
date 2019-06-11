package com.neuedu.hospitalbackend.model.dao;


import com.neuedu.hospitalbackend.model.po.ExaminationProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExaminationProjectMapper {

    int insert(ExaminationProject examinationProject);

    List<String> listItemIdsByCollectionIdAndProjectId(@Param("collectionId") Integer collectionId,
                                                       @Param("projectId") Integer projectId);

    int updateAmount(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId,
                     @Param("itemId") String itemId, @Param("amount") Short amount);

    int delete(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId,
               @Param("itemId") String itemId);

    int deleteItemsByCidAndPid(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);

}


