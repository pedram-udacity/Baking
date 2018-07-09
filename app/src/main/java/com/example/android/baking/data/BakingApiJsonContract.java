package com.example.android.baking.data;

public class BakingApiJsonContract {

    public static final String RECIPE_ID = "id";
    public static final String NAME = "name";
    public static final String INGREDIENTS = "ingredients";
    public static final String STEPS = "steps";
    public static final String SERVINGS = "servings";
    public static final String IMAGE = "image";

    public static class Ingredients {
        public static final String QUANTITY = "quantity";
        public static final String MEASURE = "measure";
        public static final String INGREDIENT = "ingredient";
    }

    public static class Step {
        public static final String STEP_ID = "id";
        public static final String SHORT_DESCRIPTION = "shortDescription";
        public static final String DESCRIPTION = "description";
        public static final String VIDEO_URL = "videoURL";
        public static final String THUMBNAIL_URL = "thumbnailURL";
    }

}
