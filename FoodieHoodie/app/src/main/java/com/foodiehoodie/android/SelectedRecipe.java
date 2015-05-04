package com.foodiehoodie.android;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.foodiehoodie.android.dto.Ingredient;
import com.foodiehoodie.android.dto.Recipe;

import java.util.ArrayList;


public class SelectedRecipe extends Activity {
    private TextView recipeName=null;
    private TextView preptime=null;
    private TextView cooktime=null;
    private TextView yield=null;
    private TextView ingredients=null;
    private TextView steps=null;
    private TextView author=null;
    private TextView url=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe);
        recipeName=(TextView)findViewById(R.id.recipeName);
        preptime=(TextView)findViewById(R.id.preptime);
        cooktime=(TextView)findViewById(R.id.cooktime);
        yield=(TextView)findViewById(R.id.yield);
        ingredients=(TextView)findViewById(R.id.ingredients);
        steps=(TextView)findViewById(R.id.steps);
        author=(TextView)findViewById(R.id.author);
        url=(TextView)findViewById(R.id.url);

        Recipe recipe = (Recipe) getIntent().getExtras().get("recipe");
        recipeName.setText(recipe.getRecipeName());
        preptime.setText("Prep Time: "+recipe.getRecipePrepTime());
        cooktime.setText("Cook Time: "+recipe.getRecipeCookTime());
        yield.setText("For "+recipe.getRecipeYield()+" people");
        ArrayList<Ingredient> recipeIngredients=recipe.getRecipeIngredients();
        StringBuilder ingredientList = new StringBuilder("Ingredients : \n");
        for (Ingredient ing:recipeIngredients){
            ingredientList.append(ing.getName()).append("\n");
        }
        ingredients.setText(ingredientList.toString());

        ArrayList<String> recipeSteps=recipe.getRecipeSteps();
        StringBuilder method = new StringBuilder("Method : \n");
        for (String str:recipeSteps){
            method.append(str).append("\n");
        }
        steps.setText(method);
        author.setText(recipe.getAuthor());
        url.setText(recipe.getURL());
    }

}
