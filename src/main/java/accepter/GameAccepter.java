package accepter;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.sun.jna.platform.win32.Advapi32Util.registryGetStringValue;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static globalVariables.GlobalVariables.*;

public class GameAccepter implements Runnable {
    private int MIN_RED_VALUE;
    private int MAX_RED_VALUE;
    private int MIN_GREEN_VALUE;
    private int MAX_GREEN_VALUE;
    private int MIN_BLUE_VALUE;
    private int MAX_BLUE_VALUE;
    private int width;
    private int height;

    @Override
    public void run() {
        getScreenResolution();
        Robot robot;
        try {
            robot = new Robot();
            getComparedRGB();
        } catch (AWTException | IOException e) {
            throw new RuntimeException(e);
        }

        new Rectangle(width, height);
        Rectangle area = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenshot = robot.createScreenCapture(area);

        while (!pixelMatch(getScreenColor(screenshot)) & accepterStatus) {

            screenshot = robot.createScreenCapture(area);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (pixelMatch(getScreenColor(screenshot))) {
            try {
                pressEnter();
                sendMessage();
            } catch (AWTException | InterruptedException | TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getScreenResolution() {
        Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenResolution.width;
        height = screenResolution.height;
    }

    public void getComparedRGB() throws IOException {
        BufferedImage acceptImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/staticImage.png")));
        Color color = new Color(acceptImage.getRGB(width / 2, height / 2), false);
        MAX_RED_VALUE = color.getRed() + range;
        MIN_RED_VALUE = color.getRed() - range;
        MAX_GREEN_VALUE = color.getGreen() + range;
        MIN_GREEN_VALUE = color.getGreen() - range;
        MAX_BLUE_VALUE = color.getBlue() + range;
        MIN_BLUE_VALUE = color.getBlue() - range;
    }

    public boolean pixelMatch(ArrayList<Integer> list) {
        return checkRedValue(list) && checkGreenValue(list) && checkBlueValue(list);
    }

    public boolean checkRedValue(ArrayList<Integer> list) {
        return list.get(red) > MIN_RED_VALUE && list.get(red) < MAX_RED_VALUE;
    }

    public boolean checkGreenValue(ArrayList<Integer> list) {
        return list.get(green) > MIN_GREEN_VALUE && list.get(green) < MAX_GREEN_VALUE;
    }

    public boolean checkBlueValue(ArrayList<Integer> list) {
        return list.get(blue) > MIN_BLUE_VALUE && list.get(blue) < MAX_BLUE_VALUE;
    }

    public ArrayList<Integer> getScreenColor(BufferedImage image) {
        Raster raster = image.getRaster();
        ColorModel model = image.getColorModel();
        int x = width / 2;
        int y = height / 2;
        Object obj = raster.getDataElements(x, y, null);
        int argb = model.getRGB(obj);
        Color color = new Color(argb, false);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(color.getRed());
        list.add(color.getGreen());
        list.add(color.getBlue());
        return list;
    }

    public void pressEnter() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        int i = 2;
        while (i > 0) {
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(3000);
            i--;
        }
    }

    public void sendMessage() throws TelegramApiException {
        MyTelegramBot bot = new MyTelegramBot(new DefaultBotOptions());
        String telegramChatId = registryGetStringValue(
                HKEY_CURRENT_USER, pathToUserSettings, pathToTelegramChatVariable);
        bot.execute(SendMessage
                .builder()
                .chatId(telegramChatId)
                .text("Your Game is Accepted")
                .build());
    }
}
