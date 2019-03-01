package com.restaurant.management.mapper;

import com.restaurant.management.domain.Ingredient;
import com.restaurant.management.domain.dto.IngredientDto;
import com.restaurant.management.web.response.IngredientResponse;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public Ingredient mapToIngredient(final IngredientDto ingredientDto) {
        return new Ingredient(
                ingredientDto.getName()
        );
    }

    public IngredientDto mapToIngredientDto(final Ingredient ingredient) {
        return new IngredientDto(
                ingredient.getName()
        );
    }

    public IngredientResponse mapToIngredientResponse(final IngredientDto ingredientDto) {
        return new IngredientResponse(
                ingredientDto.getName()
        );
    }
}