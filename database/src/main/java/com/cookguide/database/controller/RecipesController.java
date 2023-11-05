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
@RequestMapping("/api/recipes/v1")
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
    public ResponseEntity<Recipes> createRecipes(Recipes recipes){
        return new ResponseEntity<Recipes>(recipesService.createRecipes(recipes), HttpStatus.CREATED);
    }

    @PutMapping("/recipes")
    public ResponseEntity<Recipes> updateRecipes(Recipes recipes){
        return new ResponseEntity<Recipes>(recipesService.updateRecipes(recipes), HttpStatus.OK);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Void> deleteRecipes(@PathVariable int id){
        recipesService.deleteRecipes(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private void validateRecipes(Recipes recipes){
        if(recipes.getNumPortions() < 0){
            throw new ValidationException("El numero de porciones debe ser mayor a 0");
        }

        if(recipes.getDescription() == null || recipes.getDescription().isEmpty()){
            throw new ValidationException("La descripcion de la receta debe ser obligatoria");
        }
    }

    private void existRecipesByName(Recipes recipes){
        if(recipesRepository.existsById(recipes.getUid())){
            throw new ValidationException("Ya existe una receta con ese nombre");
        }
    }
}
