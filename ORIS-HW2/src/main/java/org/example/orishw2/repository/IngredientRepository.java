package org.example.orishw2.repository;

import org.example.orishw2.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    boolean existsByName(String name);

}
