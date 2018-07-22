package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle("");
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView mainNameTextView = (TextView) findViewById(R.id.main_name_tv);
        TextView descTextView = (TextView) findViewById(R.id.description_tv);
        TextView placeTextView = (TextView) findViewById(R.id.origin_tv);
        TextView alsoKnownTextView = (TextView) findViewById(R.id.also_known_tv);
        TextView ingredientTextView = (TextView) findViewById(R.id.ingredients_tv);

        mainNameTextView.setText(sandwich.getMainName());
        descTextView.setText(sandwich.getDescription());
        if ((sandwich.getPlaceOfOrigin()).equals("")) {
            placeTextView.setVisibility(View.GONE);
        } else {
            placeTextView.setText(sandwich.getPlaceOfOrigin());
        }

        ArrayList<String> alsoKnownAses = (ArrayList<String>) sandwich.getAlsoKnownAs();
        if (alsoKnownAses.size() != 0) {
            for (String alsoKnownAs : alsoKnownAses) {
                alsoKnownTextView.append(alsoKnownAs + "  •  ");
            }
        } else {
            alsoKnownTextView.setVisibility(View.GONE);
        }

        ArrayList<String> ingredients = (ArrayList<String>) sandwich.getIngredients();
        for (String ingredient : ingredients) {
            ingredientTextView.append(ingredient + "\n\n");
        }
    }
}
