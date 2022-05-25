package org.github.roulette;

import org.github.palace.bot.core.plugin.Plugin;

/**
 * @author jihongyuan
 * @date 2022/5/19 14:13
 */
public class App extends Plugin {

    public App() {
        super("1.0-SNAPSHOT", "俄罗斯转盘", "俄罗斯转盘");
    }

    @Override
    public void onLoad() {
        register(new RouletteCommand());
    }

}