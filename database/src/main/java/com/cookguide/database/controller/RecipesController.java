package com.cookguide.database.controller;

import com.cookguide.database.exception.ValidationException;
import com.cookguide.database.model.Recipes;
import com.cookguide.database.repository.RecipesRepository;
import com.cookguide.database.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cookguide/v1")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    private final RecipesRepository recipesRepository;

    public RecipesController(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    @Transactional(readOnly = true)
    @GetMapping("/recipes")
    public ResponseEntity<List<Recipes>> getAllRecipes(){
        return new ResponseEntity<List<Recipes>>(recipesService.getAllRecipes(), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/recipes")
    public ResponseEntity<Recipes> createRecipes(@RequestBody Recipes recipes){
        validateRecipes(recipes);
        return new ResponseEntity<Recipes>(recipesService.createRecipes(recipes), HttpStatus.CREATED);
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipes> updateRecipes(@RequestBody Recipes recipes){
        return new ResponseEntity<Recipes>(recipesService.updateRecipes(recipes), HttpStatus.OK);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Void> deleteRecipes(@PathVariable int id){
        recipesService.deleteRecipes(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private void validateRecipes(Recipes recipes){

        if(recipes.getServings() == null || recipes.getServings().isEmpty()){
            throw new ValidationException("El numero de porciones es obligatorio");
        }

        if(recipes.getPreparation() == null || recipes.getPreparation().isEmpty()){
            throw new ValidationException("La descripcion de la receta debe ser obligatoria");
        }

        if (recipes.getName() == null || recipes.getName().isEmpty()){
            throw new ValidationException("El nombre de la receta es obligatorio");
        }

        if (recipes.getPreparation() == null || recipes.getPreparation().isEmpty()){
            throw new ValidationException("La preparacion de la receta es obligatoria");
        }

        if (Integer.parseInt(recipes.getServings()) < 0){
            throw new ValidationException("El numero de porciones debe ser mayor a 0");
        }

        if(recipes.getIngredients() == null || recipes.getIngredients().isEmpty()){
            throw new ValidationException("La lista de ingredientes es obligatoria");
        }

        if (Integer.parseInt(recipes.getTime()) < 0){
            throw new ValidationException("El tiempo de preparacion debe ser mayor a 0");
        }

    }

    private void existRecipesByName(Recipes recipes){
        if(recipesRepository.existsById(recipes.getUid())){
            throw new ValidationException("Ya existe una receta con ese nombre");
        }
    }

}
