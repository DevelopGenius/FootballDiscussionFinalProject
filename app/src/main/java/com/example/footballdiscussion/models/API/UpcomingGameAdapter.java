package com.example.footballdiscussion.models.API;

import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.OffsetDateTime;

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
        OffsetDateTime gameDate = OffsetDateTime
                .parse(gamesJsonObject.get("fixture").getAsJsonObject()
                        .get("date").getAsString().replace(" ", "T"));
        currGame.setGameDate(gameDate.toLocalDate().toString());
        currGame.setId(gamesJsonObject.get("fixture").getAsJsonObject().get("id").toString());
        currGame.setFirstTeamName(gamesJsonObject.get("teams").getAsJsonObject()
                .get("home").getAsJsonObject().get("name").getAsString());
        currGame.setSecondTeamName(gamesJsonObject.get("teams").getAsJsonObject()
                .get("away").getAsJsonObject().get("name").getAsString());
        currGame.setLeagueName(gamesJsonObject.get("league").getAsJsonObject().get("name").getAsString());

        return currGame;
    }
}
