package org.example.orishw2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.orishw2.entity.Category;
import org.example.orishw2.entity.Ingredient;
import org.example.orishw2.entity.Recipe;
import org.example.orishw2.security.details.UserDetailsImpl;
import org.example.orishw2.service.CategoryService;
import org.example.orishw2.service.IngredientService;
import org.example.orishw2.service.RecipeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final CategoryService categoryService;

    @GetMapping("/recipes")
    public String showAllRecipes(Model model) {
        List<Recipe> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "recipes";
    }

    @GetMapping("/recipes/create")
    public String showCreateRecipeForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("ingredients", ingredients);
        return "create_recipe";
    }

    @PostMapping("/recipes/create")
    public String createRecipe(@Valid @ModelAttribute("recipe") Recipe recipe,
                               @RequestParam("ingredientIds") List<Long> ingredientIds,
                               @RequestParam("categoryIds") List<Long> categoryIds,
                               BindingResult result,
                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.getAllIngredients());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "create_recipe";
        }

        try {
            if (userDetails != null) {
                recipe.setAuthor(userDetails.getUser());
            }

            // Установка ингредиентов
            if (ingredientIds != null && !ingredientIds.isEmpty()) {
                Set<Ingredient> ingredients = ingredientIds.stream()
                        .map(id -> ingredientService.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Ингредиент с ID " + id + " не найден")))
                        .collect(Collectors.toSet());
                recipe.setIngredients(ingredients);
            } else {
                recipe.setIngredients(new HashSet<>()); // Пустое множество, если ингредиенты не выбраны
            }

            // Установка категорий
            if (categoryIds != null && !categoryIds.isEmpty()) {
                Set<Category> categories = categoryIds.stream()
                        .map(id -> categoryService.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Категория с ID " + id + " не найдена")))
                        .collect(Collectors.toSet());
                recipe.setCategories(categories);
            } else {
                recipe.setCategories(new HashSet<>()); // Пустое множество, если категории не выбраны
            }

            // Сохранение рецепта
            Recipe savedRecipe = recipeService.createRecipe(recipe);
            return "redirect:/recipes";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при создании рецепта: " + e.getMessage());
            model.addAttribute("ingredients", ingredientService.getAllIngredients());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "create_recipe";
        }
    }

}
