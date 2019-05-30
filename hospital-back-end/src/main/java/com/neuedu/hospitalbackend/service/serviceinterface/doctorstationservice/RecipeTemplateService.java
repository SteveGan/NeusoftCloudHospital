package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.neuedu.hospitalbackend.model.poback.RecipeTemplate;

import java.util.List;


/**
 * 提供了关于处方模版的服务
 * @author Steve
 */
public interface RecipeTemplateService {


    /**
     * get all available recipe template by doctor's id
     * @param doctorId of the doctor
     * @return list of available recipes
     */
    List<RecipeTemplate> listRecipeTemplate(int doctorId);


}
