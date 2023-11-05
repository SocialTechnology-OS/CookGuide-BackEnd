package com.cookguide.database.service;

import com.cookguide.database.model.Recipes;

import java.util.List;

public interface RecipesService {
    public Recipes createRecipes(Recipes recipes);

    Recipes updateRecipes(Recipes recipes);

    List<Recipes> getAllRecipes();

    void deleteRecipes(int id);

    boolean isRecipesExist(int id);
}
