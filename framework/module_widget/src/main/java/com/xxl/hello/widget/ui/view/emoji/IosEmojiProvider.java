package com.xxl.hello.widget.ui.view.emoji;

import androidx.annotation.NonNull;

import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.ios.category.ActivitiesCategory;
import com.vanniktech.emoji.ios.category.AnimalsAndNatureCategory;
import com.vanniktech.emoji.ios.category.FlagsCategory;
import com.vanniktech.emoji.ios.category.FoodAndDrinkCategory;
import com.vanniktech.emoji.ios.category.ObjectsCategory;
import com.vanniktech.emoji.ios.category.SmileysAndPeopleCategory;
import com.vanniktech.emoji.ios.category.SymbolsCategory;
import com.vanniktech.emoji.ios.category.TravelAndPlacesCategory;

public final class IosEmojiProvider implements EmojiProvider {
    @Override
    @NonNull
    public EmojiCategory[] getCategories() {
        return new EmojiCategory[]{
                //new CustomCategory(),//自定义emoji
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
