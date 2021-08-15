package com.example.dataentry;

import android.media.Image;
import android.net.Uri;

public class data_items {
    private String name;
    private String email;
    private String img_src;

    public data_items(String name, String email,String img_src) {
        this.name = name;
        this.email = email;
        this.img_src = img_src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getImg_src() {
        return Uri.parse(img_src)
                ;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }
}
