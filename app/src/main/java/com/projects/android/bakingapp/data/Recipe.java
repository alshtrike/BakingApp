package com.projects.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable{
    private String name;
    private Ingredient[] ingredients;
    private Step[] steps;

    public Recipe(){

    }

    public Recipe(Parcel in) {
        name = in.readString();
        ingredients = new Ingredient[in.readInt()];
        steps = new Step[in.readInt()];
        in.readTypedArray(ingredients, Ingredient.CREATOR );
        in.readTypedArray(steps, Step.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(name);
        parcel.writeInt(ingredients.length);
        parcel.writeInt(steps.length);
        parcel.writeTypedArray(ingredients,i);
        parcel.writeTypedArray(steps, i);
    }

}
