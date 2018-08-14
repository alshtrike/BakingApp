package com.projects.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recepie implements Parcelable{
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    public Recepie(){

    }

    public Recepie(Parcel in) {
        name = in.readString();
        ingredients = new ArrayList<Ingredient>();
        in.readList(ingredients, null);
        steps = new ArrayList<Step>();
        in.readList(steps, null);
    }

    public static final Creator<Recepie> CREATOR = new Creator<Recepie>() {
        @Override
        public Recepie createFromParcel(Parcel in) {
            return new Recepie(in);
        }

        @Override
        public Recepie[] newArray(int size) {
            return new Recepie[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
