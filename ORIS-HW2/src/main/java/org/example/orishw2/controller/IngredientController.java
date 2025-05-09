package org.example.orishw2.controller;

import lombok.RequiredArgsConstructor;
import org.example.orishw2.entity.Ingredient;

import org.example.orishw2.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/create")
    public ResponseEntity<?> createIngredient(@RequestBody Ingredient ingredient) {
        try {
            Ingredient createdIngredient = ingredientService.createIngredient(ingredient);
            return ResponseEntity.ok(createdIngredient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Внутренняя ошибка сервера"));
        }
    }
}
class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
