package dotaBuff;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import static globalVariables.GlobalVariables.playersNameMap;

public class NickNameChecker implements Runnable {
    private final String steamId;
    private final int playerId;

    NickNameChecker(String steamId, int playerId) {
        this.steamId = steamId;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        setPlayersNameMap();
    }

    private void setPlayersNameMap() {
        try {
            playersNameMap.put("Player" + playerId, getNickName(getJsonFormat(
                    "https://api.opendota.com/api/players/" + steamId)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            playersNameMap.put("Player" + playerId, "private");
        }
    }
    private String getNickName(String json) {
        JSONObject obj = new JSONObject(json);
        return obj.getJSONObject("profile").getString("personaname");
    }
    private String getJsonFormat(String urlAdress) throws IOException {
        StringBuilder content = new StringBuilder();

        URL url = new URL(urlAdress);
        URLConnection urlConn = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlConn.getInputStream(), StandardCharsets.UTF_8));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();
        return content.toString();
    }
}
