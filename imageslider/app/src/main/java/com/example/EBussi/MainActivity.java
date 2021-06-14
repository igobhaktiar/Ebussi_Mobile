package com.example.EBussi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg"," "));
        slideModels.add(new SlideModel("https://static.onecms.io/wp-content/uploads/sites/20/2021/03/01/fruits-veggies1-2000.jpg"," "));
        slideModels.add(new SlideModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg",""));
        imageSlider.setImageList(slideModels, true);
    }
}