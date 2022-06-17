package javaFXSettings;

import accepter.GameAccepter;
import dotaBuff.DotaBuffChecker;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static com.sun.jna.platform.win32.Advapi32Util.registryCreateKey;
import static com.sun.jna.platform.win32.Advapi32Util.registrySetStringValue;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static globalVariables.GlobalVariables.*;
import static java.awt.Desktop.getDesktop;

public class Controller {
    @FXML
    private MenuItem chatId;
    @FXML
    public MenuItem configLog;
    @FXML
    private Group root;
    @FXML
    private MenuItem aboutButton;
    @FXML
    private TextField textField;
    @FXML
    private Button enterText;
    @FXML
    private Button saveChatId;
    @FXML
    private Button acceptButton;
    @FXML
    private TextArea infoAboutProgram;
    @FXML
    private Button dotaBuff;
    @FXML
    private Hyperlink hyperLink;
    @FXML
    private Hyperlink hyperLink2;
    @FXML
    private Hyperlink hyperLink3;
    @FXML
    private Hyperlink hyperLink4;
    @FXML
    private Hyperlink hyperLink5;
    @FXML
    private Hyperlink hyperLink6;
    @FXML
    private Hyperlink hyperLink7;
    @FXML
    private Hyperlink hyperLink8;
    @FXML
    private Hyperlink hyperLink9;
    @FXML
    private Hyperlink hyperLink10;
    @FXML
    private Hyperlink radiantTeam;
    @FXML
    private Hyperlink direTeam;

    @FXML
    void openChatIdTextArea(ActionEvent ignoredEvent) {
        textField.setDisable(false);
        textField.setPromptText("Enter your Telegram ChatId");
        saveChatId.setVisible(true);
        enterText.setVisible(false);
    }

    @FXML
    void openTextArea(ActionEvent ignoredEvent) {
        textField.setDisable(false);
        textField.setPromptText("Enter file path. Read \"Help\" before");
        enterText.setVisible(true);
        saveChatId.setVisible(false);
    }

    @FXML
    void putChatId(ActionEvent ignoredEvent) {
        String getUserTelegramChatId = textField.getText();
        registryCreateKey(HKEY_CURRENT_USER, pathToUserSettings);
        registrySetStringValue(HKEY_CURRENT_USER, pathToUserSettings, pathToTelegramChatVariable, getUserTelegramChatId);

        saveChatId.setVisible(false);
        textField.setText("");
        textField.setDisable(true);
        infoAboutProgram.setVisible(false);
    }

    @FXML
    void putText(ActionEvent ignoredEvent) {
        String getUserPathToSteam = textField.getText();
        registryCreateKey(HKEY_CURRENT_USER, pathToUserSettings);
        registrySetStringValue(HKEY_CURRENT_USER, pathToUserSettings, pathToSteamVariable, getUserPathToSteam);

        enterText.setVisible(false);
        textField.setDisable(true);
        infoAboutProgram.setVisible(false);
    }

    @FXML
    void openLink(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player1")));
    }

    @FXML
    void openLink2(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player2")));
    }

    @FXML
    void openLink3(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player3")));
    }

    @FXML
    void openLink4(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player4")));
    }

    @FXML
    void openLink5(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player5")));
    }

    @FXML
    void openLink6(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player6")));
    }

    @FXML
    void openLink7(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player7")));
    }

    @FXML
    void openLink8(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player8")));
    }

    @FXML
    void openLink9(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player9")));
    }

    @FXML
    void openLink10(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        getDesktop().browse(new URI(dotaBuffLinkMap.get("Player10")));
    }

    @FXML
    void openLinkYourTeam(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        openLinkTeam("Player1", "Player2", "Player3", "Player4", "Player5");
    }

    @FXML
    void openLinkEnemyTeam(ActionEvent ignoredEvent) throws URISyntaxException, IOException {
        openLinkTeam("Player6", "Player7", "Player8", "Player9", "Player10");
    }

    @FXML
    void initialize() {
        infoAboutProgram.setText(" HotKeys: \n F10 - Run/Stop Accepter \n F11 - DotaBuff \n F12 - Quit \n \n " +
                "DotaBuff checker: \n Open \"Help\" -> \"Config\"  and enter file path\n Sample: \n " +
                "F:\\takecare\\games\\steam\\steamapps\\common\\ dota 2 beta\\game\\dota\\server_log.txt\n\n" +
                " Don't worry, u do it only once");

        acceptButton.setOnMouseClicked(mouseEvent -> setAccepterSettings());
        dotaBuff.setOnMouseClicked(mouseEvent -> setDotaBuffSettings());
        aboutButton.addEventHandler(EventType.ROOT, event -> infoAboutProgram.setVisible(true));
        setMenuItemSetting(configLog);
        setMenuItemSetting(chatId);

        root.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case F10:
                    setAccepterSettings();
                    break;
                case F11:
                    setDotaBuffSettings();
                    break;
                case F12:
                    System.exit(0);
                    break;
            }
        });
    }

    private void openLinkTeam(String key1, String key2, String key3, String key4, String key5)
            throws URISyntaxException, IOException {
        for (String s : Arrays.asList(key1, key2, key3, key4, key5)) {
            getDesktop().browse(new URI(dotaBuffLinkMap.get(s)));
        }
    }

    private void hyperLinkVisible(boolean visibility) {
        for (Hyperlink hyperlink : Arrays.asList(
                hyperLink, hyperLink2, hyperLink3, hyperLink4, hyperLink5, hyperLink6,
                hyperLink7, hyperLink8, hyperLink9, hyperLink10, direTeam, radiantTeam)) {
            hyperlink.setVisible(visibility);
        }
    }

    private void setHyperLinkText() {
        hyperLink.setText(playersNameMap.get("Player1"));
        hyperLink2.setText(playersNameMap.get("Player2"));
        hyperLink3.setText(playersNameMap.get("Player3"));
        hyperLink4.setText(playersNameMap.get("Player4"));
        hyperLink5.setText(playersNameMap.get("Player5"));
        hyperLink6.setText(playersNameMap.get("Player6"));
        hyperLink7.setText(playersNameMap.get("Player7"));
        hyperLink8.setText(playersNameMap.get("Player8"));
        hyperLink9.setText(playersNameMap.get("Player9"));
        hyperLink10.setText(playersNameMap.get("Player0"));
    }

    private void setAccepterSettings() {
        if (flagAccepterButtons) {
            accepterStatus = true;
            textField.setText("Accepter running...");
            GameAccepter gameAccepter = new GameAccepter();
            Thread accepter = new Thread(gameAccepter);
            accepter.start();
            flagAccepterButtons = false;
            acceptButton.setText("Stop");
        } else {
            textField.setText("Accepter interrupt.");
            accepterStatus = false;
            flagAccepterButtons = true;
            acceptButton.setText("Auto Accept");
        }
        hyperLinkVisible(false);
        enterText.setVisible(false);
        saveChatId.setVisible(false);
        textField.setDisable(true);
        infoAboutProgram.setVisible(false);
    }

    private void setDotaBuffSettings() {
        if (flagDotaBuffButtons) {
            DotaBuffChecker dotaBuffChecker = new DotaBuffChecker();
            Thread dotaChecker = new Thread(dotaBuffChecker);
            dotaChecker.start();
            hyperLinkVisible(true);
            enterText.setVisible(false);
            saveChatId.setVisible(false);
            textField.setDisable(true);
            infoAboutProgram.setVisible(false);
            dotaBuff.setText("NickNames");
        } else {
            setHyperLinkText();
            dotaBuff.setText("DotaBuff");
            flagDotaBuffButtons = true;
        }
    }

    private void setMenuItemSetting(MenuItem item) {
        item.addEventHandler(EventType.ROOT, event -> {
            infoAboutProgram.setVisible(false);
            textField.setText("");
        });
    }
}
