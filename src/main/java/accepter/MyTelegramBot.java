package accepter;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

public class MyTelegramBot extends DefaultAbsSender {
    public MyTelegramBot(DefaultBotOptions options) {
        super(options);
    }
    @Override
    public String getBotToken() {
        return "5483917791:AAHtEg-rcSnYn0MYW-mT8gyqdzvVGPXSzDE";
    }

}
