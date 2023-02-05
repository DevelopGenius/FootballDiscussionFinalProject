package com.example.footballdiscussion.models.API;

import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpcomingGameAdapter extends TypeAdapter<UpcomingGame> {

    @Override
    public void write(JsonWriter out, UpcomingGame value) {
    }

    @Override
    public UpcomingGame read(JsonReader in) throws IOException {
        JsonObject json = new Gson().getAdapter(JsonObject.class).read(in);
        UpcomingGame upcomingGame = parseGame(json);

        return upcomingGame;
    }

    private static UpcomingGame parseGame(JsonObject gamesJsonObject) {
        UpcomingGame currGame = new UpcomingGame();
        try {
            Date gameDate = new SimpleDateFormat("dd.MM")
                    .parse(gamesJsonObject.get("fixture").getAsJsonObject().get("date").toString());
            currGame.setGameDate(gameDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        currGame.setId(gamesJsonObject.get("fixture").getAsJsonObject().get("id").toString());
        currGame.setFirstTeamName(gamesJsonObject.get("teams").getAsJsonObject()
                .get("home").getAsJsonObject().get("name").toString());
        currGame.setFirstTeamName(gamesJsonObject.get("teams").getAsJsonObject()
                .get("away").getAsJsonObject().get("name").toString());
        currGame.setLeagueName(gamesJsonObject.get("league").getAsJsonObject().get("name").toString());

        return currGame;
    }
}
