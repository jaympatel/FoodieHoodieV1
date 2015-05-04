package com.foodiehoodie.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FillIngredientsActivity extends Activity {
    private BootstrapButton mSearchRecipeButton = null;
    private Button mSignupButton = null;
    private EditText mIngredientBox = null;
    private GridView mGridView = null;
    private HashSet<String> userIngredients = null;
    private ArrayList<String> essentialIngredients = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_ingredients);
        userIngredients = new HashSet<>();
        userIngredients.clear();
        essentialIngredients = new ArrayList<>();
        essentialIngredients.clear();
        /*userIngredients.add("Potato");
        userIngredients.add("Onion");
        userIngredients.add("Paneer");
        userIngredients.add("Rajma");
        userIngredients.add("Rice");
        userIngredients.add("Noodles");*/

//      mSignupButton = (Button)findViewById(R.id.button2);
        mSearchRecipeButton = (BootstrapButton)findViewById(R.id.searchRecipe);
        mIngredientBox = (EditText) findViewById(R.id.ingredientBox);
        mGridView = (GridView)findViewById(R.id.ingredientGridView);

        mIngredientBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                String str=((EditText)findViewById(v.getId())).getText().toString();
                    ((EditText)findViewById(v.getId())).setText("");
//                    mIngredientList.setText(str);
                    userIngredients.add(str);
                    mGridView.setAdapter(new IngredientTag(FillIngredientsActivity.this, new ArrayList<String>(userIngredients)));
                    ((EditText)findViewById(v.getId())).requestFocus();

                    return true;
                }
                return false;
            }
        });

       /* mIngredientBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIngredientList.setText(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                //TODO - set ingredients here
               *//* StringBuilder tempIngrdiendtList = new StringBuilder();
                tempIngrdiendtList.append(mIngredientList.getText());
                char[] chars = new char[s.length()];
                s.getChars(0,s.length()-1,chars,s.length()-1);
                tempIngrdiendtList.append(chars);*//*
//                mIngredientList.setText(s.toString());

            }
        });*/

        mSearchRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userIngredients.size()>0){
                Intent intent = new Intent(FillIngredientsActivity.this, FoodListActivity.class);
                intent.putExtra("ingredientList",userIngredients);
                startActivity(intent);}
                else
                    Toast.makeText(FillIngredientsActivity.this,"You need to enter atleast one Ingredient",Toast.LENGTH_LONG).show();
            }
        });

    /*    mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FillIngredientsActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });*/
    }


    class IngredientTag extends ArrayAdapter<String>{
        //Todo- Not sure to use context or not, but let it be here right now
        Context context=null;
        public IngredientTag(Context context, ArrayList<String> ingredientList) {
            super(FillIngredientsActivity.this, R.layout.ingredientonly, R.id.ingredientName,ingredientList);
            this.context=context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View tag = super.getView(position, convertView, parent);
            //Todo - if ingredient is not populated then use this method
            return tag;
        }
    }
}

