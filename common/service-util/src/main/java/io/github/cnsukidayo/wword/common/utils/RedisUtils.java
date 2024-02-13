package io.github.cnsukidayo.wword.common.utils;

import java.util.Random;

/**
 * @author sukidayo
 * @date 2024/2/9 14:16
 */
public class RedisUtils {
    public static final long ONE_SECOND = 1000;
    public static final long TEN_SECOND = 10 * ONE_SECOND;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long Five_MINUTE = ONE_MINUTE * 5;
    private static final Random random = new Random();

    /**
     * 根据给定的过期时间返回一个在10 * 1000 毫秒内波动的时间返回
     *
     * @param millSeconds 给定的过期时间
     * @return 返回的时间只可能会增长
     */
    public static long randomSecond(long millSeconds) {
        long randomTime = random.nextLong(TEN_SECOND);
        return millSeconds + randomTime;
    }

}
