package com.cookguide.api.service;

import com.cookguide.api.model.Recipes;

import java.util.List;

public interface RecipesService {
    public Recipes createRecipes(Recipes recipes);

    Recipes updateRecipes(Recipes recipes);

    List<Recipes> getAllRecipes();

    void deleteRecipes(int id);

    boolean isRecipesExist(int id);
}
