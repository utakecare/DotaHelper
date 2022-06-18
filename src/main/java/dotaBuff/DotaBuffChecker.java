package dotaBuff;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.jna.platform.win32.Advapi32Util.registryGetStringValue;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static globalVariables.GlobalVariables.*;

public class DotaBuffChecker implements Runnable {
    @Override
    public void run() {
        try {
            setDotaBuffLinkMap(getSteamIdList(getGameLog()));
            setPlayersNameMap(getSteamIdList(getGameLog()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getGameLog() throws IOException {
        String pathToLogs = registryGetStringValue(HKEY_CURRENT_USER, pathToUserSettings, pathToSteamVariable);

        File file = new File(pathToLogs
                .replace('\\', '/')
                .replaceAll("\"", "")
                .replaceAll("ï»¿", "")
                .trim());
        return readLastLine(file);
    }
    private List<String> getSteamIdList(String gameLog) {
        List<String> steamIdList = new ArrayList<>();
        for (int i = 0; i < totalPlayers; i++) {
            steamIdList.add(getSteamId(gameLog, i));
        }
        return steamIdList;
    }
    private String getSteamId(String gameLog, int i) {
        String steamId;
        if (i != 9) {
            steamId = gameLog.substring(gameLog.indexOf(i + ":[U:1:") + 7, gameLog.indexOf("] " + (i + 1 + ":[U:1:")));
        } else {
            steamId = gameLog.substring(gameLog.indexOf("9:[U:1:") + 7, gameLog.indexOf("]) "));
        }
        return steamId;
    }
    private void setDotaBuffLinkMap(List<String> steamIdList) {
        for (int i = 0; i < totalPlayers; i++) {
            dotaBuffLinkMap.put("Player" + i, "https://www.dotabuff.com/players/" + steamIdList.get(i));
        }
    }
    private void setPlayersNameMap(List<String> steamIdList) {
        ExecutorService executorService = Executors.newFixedThreadPool(countCores);
        for (int i = 0; i < totalPlayers; i++) {
            executorService.execute(new NickNameChecker(steamIdList.get(i), i));
            if (i == 9) {
                flagDotaBuffButtons = false;
            }
        }
        executorService.shutdown();
    }
    private String readLastLine(File file) throws IOException {
        String result = null;
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long startIdx = file.length();
            while (startIdx >= 0 && (result == null || result.length() == 0)) {
                raf.seek(startIdx);
                if (startIdx > 0)
                    raf.readLine();
                result = raf.readLine();
                startIdx--;
            }
        }
        return result;
    }
}
