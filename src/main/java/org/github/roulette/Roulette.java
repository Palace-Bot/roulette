package org.github.roulette;

import lombok.Getter;
import net.mamoe.mirai.contact.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author jihongyuan
 * @date 2022/5/19 14:17
 */
@Getter
public class Roulette {
    /**
     * 随机种子
     */
    private final Random random;

    /**
     * 最大次数
     */
    private final Integer maxCount;

    /**
     * 当前次数
     */
    private Integer currentCount;

    /**
     * 中枪
     */
    private boolean shot;

    /**
     * 参与者
     */
    private final List<User> participants;

    public Roulette(Integer maxCount) {
        this.random = new Random();
        this.maxCount = maxCount;
        this.currentCount = 0;
        this.participants = new ArrayList<>(maxCount);
    }

    private void add(User user) {
        currentCount = currentCount + 1;
        participants.add(user);
    }

    public boolean shot(User user) {
        add(user);
        return maxCount.equals(currentCount) || random.nextInt(maxCount) == 0;
    }

}
