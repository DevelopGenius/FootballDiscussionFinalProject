package com.example.footballdiscussion.models.room;

import androidx.room.TypeConverter;

import com.example.footballdiscussion.models.entities.UserPostComment;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class UserPostCommentConverter {
    @TypeConverter
    public static List<UserPostComment> fromString(String value) {
        Type listType = new TypeToken<List<UserPostComment>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<UserPostComment> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
