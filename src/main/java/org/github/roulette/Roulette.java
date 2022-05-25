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
    private final Random random;

    private final Integer max;

    private Integer currentCount;

    private boolean shot;

    private final List<User> participants;

    public Roulette(Integer max) {
        this.random = new Random();
        this.max = max;
        this.currentCount = 0;
        this.participants = new ArrayList<>(max);
    }

    private void add(User user) {
        currentCount = currentCount + 1;
        participants.add(user);
    }

    public boolean shot(User user) {
        add(user);
        shot = random.nextInt(max) == 0;
        return shot;
    }

}
