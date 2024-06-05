package com.xxl.hello.widget.ui.view.emoji;

import androidx.annotation.NonNull;

import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.googlecompat.category.ActivitiesCategory;
import com.vanniktech.emoji.googlecompat.category.AnimalsAndNatureCategory;
import com.vanniktech.emoji.googlecompat.category.FlagsCategory;
import com.vanniktech.emoji.googlecompat.category.FoodAndDrinkCategory;
import com.vanniktech.emoji.googlecompat.category.ObjectsCategory;
import com.vanniktech.emoji.googlecompat.category.SmileysAndPeopleCategory;
import com.vanniktech.emoji.googlecompat.category.SymbolsCategory;
import com.vanniktech.emoji.googlecompat.category.TravelAndPlacesCategory;

public final class GoogleCompatEmojiProvider implements EmojiProvider {
    @Override
    @NonNull
    public EmojiCategory[] getCategories() {
        return new EmojiCategory[]{
                new SmileysAndPeopleCategory(),
                new AnimalsAndNatureCategory(),
                new FoodAndDrinkCategory(),
                new ActivitiesCategory(),
                new TravelAndPlacesCategory(),
                new ObjectsCategory(),
                new SymbolsCategory(),
                new FlagsCategory()
        };
    }
}
