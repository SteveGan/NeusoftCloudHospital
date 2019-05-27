package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.neuedu.hospitalbackend.model.po.RecipeTemplate;
import com.sun.tools.javac.util.List;


public interface RecipeTemplateService {


    /**
     * get all available recipe template by doctor's id
     * @param doctorId of the doctor
     * @return list of available recipes
     */
    public List<RecipeTemplate> listRecipeTemplate(int doctorId);


}
