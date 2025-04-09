package com.movie.utils;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class RandomNameUtil {

    private static final String[] names = {
            "探险家", "旅行者", "美食家", "音乐人", "艺术家", "读者", "作家", "摄影师",
            "运动员", "游戏玩家", "科技迷", "电影爱好者", "书虫", "收藏家", "历史学家",
            "天文学家", "梦想家", "艺术家", "编程者", "极客", "咖啡爱好者", "侦探", "博士"
    };

    private final Random random = new Random();

    /**
     * 生成随机昵称
     * @return 随机昵称
     */
    public String generateRandomName() {
        int index = random.nextInt(names.length);
        return names[index];
    }
}