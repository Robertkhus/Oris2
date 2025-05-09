package org.example.orishw2.service;


import lombok.RequiredArgsConstructor;
import org.example.orishw2.entity.Ingredient;
import org.example.orishw2.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        if (ingredient.getName() == null || ingredient.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Название ингредиента не может быть пустым");
        }
        if (ingredientRepository.existsByName(ingredient.getName())) {
            throw new IllegalArgumentException("Ингредиент с таким названием уже существует");
        }
        return ingredientRepository.save(ingredient);
    }

    public Optional<Ingredient> findById(Long id) {
        return ingredientRepository.findById(id);
    }

}
