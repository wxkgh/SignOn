package org.skynet.web.Common;

import org.skynet.web.Model.LoginUser;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TokenManager {
    // 令牌有效期(秒)
    protected int tokenTimeOut = 1800; // 30 min
    private final Timer timer = new Timer(true);

    // 每分钟执行一次
    public TokenManager() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                verifyExpired();
            }
        }, 60*1000, 60*1000);
    }

    /**
     * 验证失效token
     */
    public abstract void verifyExpired();

    /**
     * 用户授权成功后将授权信息存入
     */
    public abstract void addToken(String token, LoginUser loginUser);

    /**
     * 验证令牌有效性,有效则延长session生命周期
     */
    public abstract LoginUser validate(String token);

    /**
     * 移除令牌
     */
    public abstract void remove(String token);
}
