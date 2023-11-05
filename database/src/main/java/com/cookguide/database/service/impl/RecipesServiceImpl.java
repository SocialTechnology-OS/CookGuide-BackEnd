package com.cookguide.database.service.impl;

import com.cookguide.database.model.Recipes;
import com.cookguide.database.repository.RecipesRepository;
import com.cookguide.database.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipesServiceImpl implements RecipesService {
    @Autowired
    private RecipesRepository recipesRepository;

    @Override
    public Recipes createRecipes(Recipes recipes){
        return recipesRepository.save(recipes);
    }

    @Override
    public Recipes updateRecipes(Recipes recipes){
        return recipesRepository.save(recipes);
    }

    @Override
    public List<Recipes> getAllRecipes(){
        return (List<Recipes>) recipesRepository.findAll();
    }

    @Override
    public void deleteRecipes(int id){
        recipesRepository.deleteById(id);
    }

    @Override
    public boolean isRecipesExist(int id){
        return recipesRepository.existsById(id);
    }

}
