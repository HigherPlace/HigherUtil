package com.higher.util.gallery.model;


import androidx.annotation.DrawableRes;

public class GalleryPhotoModel {

    public Object photoSource;

    public GalleryPhotoModel(@DrawableRes int drawableRes) {
        this.photoSource = drawableRes;
    }

    public GalleryPhotoModel(String path) {
        this.photoSource = path;
    }

}
