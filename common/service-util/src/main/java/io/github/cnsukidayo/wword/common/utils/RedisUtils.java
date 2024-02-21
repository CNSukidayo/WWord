package io.github.cnsukidayo.wword.common.utils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author sukidayo
 * @date 2024/2/9 14:16
 */
public class RedisUtils {
    public static final TimeUnit COMMON_TIME_UTIL = TimeUnit.SECONDS;
    public static final long ONE_SECOND = 1;
    public static final long TEN_SECOND = 10 * ONE_SECOND;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long FIVE_MINUTE = ONE_MINUTE * 5;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final long TOW_HOUR = ONE_HOUR * 2;
    public static final long ONE_DAY = ONE_HOUR * 24;
    public static final long ONE_MONTH = ONE_DAY * 30;


    private static final Random random = new Random();

    /**
     * 根据给定的过期时间返回一个在30秒内波动的时间返回
     *
     * @param millSeconds 给定的过期时间
     * @return 返回的时间只可能会增长
     */
    public static long randomSecond(long millSeconds) {
        long randomTime = random.nextLong(TEN_SECOND * 3);
        return millSeconds + randomTime;
    }

}
