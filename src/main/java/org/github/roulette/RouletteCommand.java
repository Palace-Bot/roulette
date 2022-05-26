package org.github.roulette;

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.data.At;
import org.github.palace.bot.core.CommandScope;
import org.github.palace.bot.core.annotation.ChildCommandHandler;
import org.github.palace.bot.core.annotation.CommandHandler;
import org.github.palace.bot.core.cli.CommandSender;
import org.github.palace.bot.core.cli.CommandSession;
import org.github.palace.bot.core.plugin.SimpleCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jihongyuan
 * @date 2022/5/19 14:14
 */
public class RouletteCommand extends SimpleCommand {

    private static final Map<Long, Roulette> groupRouletteMap = new HashMap<>(16);

    public RouletteCommand() {
        super("转盘", MemberPermission.ADMINISTRATOR, CommandScope.GROUP, false, "转盘");
    }

    @CommandHandler
    public void handler(CommandSender commandSender, CommandSession session) {
        if (!exist(commandSender.getSubject().getId())) {
            rouletteHandler(commandSender, session);
        }
    }

    @ChildCommandHandler(primaryName = "开枪")
    public void childHandler(CommandSender commandSender, CommandSession session) {
        rouletteHandler(commandSender, session);
    }

    public void rouletteHandler(CommandSender commandSender, CommandSession session) {
        Long groupId = commandSender.getSubject().getId();

        // (1) 判断群聊已经开启转盘
        Roulette roulette = groupRouletteMap.get(groupId);
        if (roulette == null) {
            roulette = new Roulette(6);
            groupRouletteMap.put(groupId, roulette);
        }

        // (2) 开枪
        boolean isShot = roulette.shot(commandSender.getUser());

        // (3) 转换消息
        StringBuilder sb = tansform(new StringBuilder(), isShot);
        sb.append("（").append(roulette.getCurrentCount()).append(" / ").append(roulette.getMax()).append("）");

        if (roulette.getMax().equals(roulette.getCurrentCount()) || isShot) {
            commandSender.sendMessage(new At(commandSender.getUser().getId()).plus(" ").plus(sb.toString()));
            commandSender.getBot().getGroup(groupId).get(commandSender.getUser().getId()).mute(5);
            groupRouletteMap.remove(groupId);
            session.finish();
        } else {
            commandSender.sendMessage(sb.toString());
        }

    }

    private boolean exist(Long groupId) {
        return groupRouletteMap.containsKey(groupId);
    }

    private StringBuilder tansform(StringBuilder sb, boolean shot) {
        if (shot) {
            sb.append("枪声响起，");
        } else {
            sb.append("没有枪声，");
        }
        return sb;
    }

}
