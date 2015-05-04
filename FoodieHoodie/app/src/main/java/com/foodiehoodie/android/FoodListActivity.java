package com.foodiehoodie.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.foodiehoodie.android.dto.Ingredient;
import com.foodiehoodie.android.dto.Recipe;
import com.foodiehoodie.android.dto.list.RecipeList;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.HashSet;


public class FoodListActivity extends ActionBarActivity {
    private TextView searchResultMsg = null;
    private ListView searchRecipeListView = null;
    private Recipe[] recipes = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        searchResultMsg = (TextView)findViewById(R.id.searchResultMsg);
        searchRecipeListView = (ListView)findViewById(R.id.searchRecipeListView);
        searchRecipeListView.setClickable(true);
        searchRecipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FoodListActivity.this,"Item Clicked",Toast.LENGTH_LONG).show();
                TextView recipeSelectedId = (TextView) view.findViewById(R.id.recipeId);
                int recipeId = Integer.parseInt((String) recipeSelectedId.getText());
                Recipe rec = null;
                for (Recipe obj : recipes) {
                    if (obj.getId() == recipeId) {
                        rec = obj;
                        break;
                    }
                }
                Intent intent = new Intent(FoodListActivity.this, SelectedRecipe.class);
                intent.putExtra("recipe", rec);
                startActivity(intent);
            }
        });
        HashSet<String> ingredientList = (HashSet<String>) getIntent().getExtras().get("ingredientList");
        String ingredientQuery = ingredientList.toString().toLowerCase();
        if(ingredientList.size()>0){
            ingredientQuery = ingredientQuery.substring(1,ingredientQuery.length()-1);
        }
        new CallSearchRecipeRestApi().execute(ingredientQuery);
       // searchResultMsg.setText(ingredientList.toString());
        searchResultMsg.setText(ingredientQuery);
        Log.i("Recipe Result", "Check Recipe here");
//        searchRecipeListView.setAdapter(new SearchRecipeResultView(FoodListActivity.this,recipes));
    }

    private class CallSearchRecipeRestApi extends AsyncTask<String,Void,Recipe[]>{
        private StringBuilder restUrl = new StringBuilder("http://172.16.188.23:8080/FHv1/rest/recipe/");
        private ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            progressDialog= ProgressDialog.show(FoodListActivity.this, "Searching FoodieHoodie Database","preparing your recipes", true);
        }

        @Override
        protected Recipe[] doInBackground(String... params) {
            Log.i("Enter : ", "Do In Background");

            try {

                restUrl.append(URLEncoder.encode(params[0],"UTF-8"));
                Log.i("Test",restUrl.toString());
                URL url = new URL(restUrl.toString());
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                Log.i("Test","Test");
                Recipe[] recipeList= new Gson().fromJson(reader, Recipe[].class);
                reader.close();
                in.close();
                urlConnection.disconnect();
                recipes = recipeList;
            }catch(MalformedURLException mue){
                Log.i("Error","Malformed URL Exception");
                mue.printStackTrace();
                return null;
            }catch(IOException ioe){
                Log.i("Error","IO Exception");
                ioe.printStackTrace();
                return null;
            }
            return recipes;
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
            super.onPostExecute(recipes);
            progressDialog.dismiss();
            if(recipes!=null)
                searchRecipeListView.setAdapter(new SearchRecipeResultView(FoodListActivity.this,recipes));

        }
    }

    private class SearchRecipeResultView extends ArrayAdapter<Recipe>{
        Context context=null;
        public SearchRecipeResultView(Context context, Recipe[] recipes) {
            super(FoodListActivity.this, R.layout.searchresultrecipe,R.id.recipeName, recipes);
            this.context=context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View recipeItem =super.getView(position, convertView, parent);
            TextView recipeId = (TextView)recipeItem.findViewById(R.id.recipeId);
            recipeId.setText(String.valueOf(recipes[position].getId()));
            TextView recipeName = (TextView)recipeItem.findViewById(R.id.recipeName);
            recipeName.setText(recipes[position].getRecipeName());
            TextView ingredientList = (TextView)recipeItem.findViewById(R.id.ingredientList);
            ingredientList.setText(recipes[position].getRecipeIngredients().toString());
            return recipeItem;
        }
    }

    private Recipe[] dummyRecipeSearch(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        ArrayList<Ingredient> ingredients=new ArrayList<>();

        ingredients.add(new Ingredient("potato"));
        ingredients.add(new Ingredient("Onion"));
        ingredients.add(new Ingredient("garlic"));
        ingredients.add(new Ingredient("Paneer"));
        Recipe recpe1=new Recipe();
        recpe1.setId(1);
        recpe1.setRecipeName("Sourabh Taletiya");
        recpe1.setRecipeIngredients(ingredients);

        Recipe recpe2=new Recipe();
        recpe2.setId(2);
        recpe2.setRecipeName("Jay Patel");
        recpe2.setRecipeIngredients(ingredients);

        Recipe recpe3=new Recipe();
        recpe3.setId(3);
        recpe3.setRecipeName("Mayank Purohit");
        recpe3.setRecipeIngredients(ingredients);
        recipes.add(recpe1);
        recipes.add(recpe2);
        recipes.add(recpe3);
        return (Recipe[]) recipes.toArray(new Recipe[0]);
    }
}
