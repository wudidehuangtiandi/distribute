package com.shiro.myshiro;

import com.dao.UserSearchDao;
import com.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.ModelResultMatchers;

import java.util.List;

/**
 * 密码加密及比对
 * 通过AuthenticatingRealm的credentialsMatcher属性进行（配置中实现前台传入密码加密，本类中实现加盐）
 *
 * @author GC
 * @date 2020年 01月16日 15:35:04
 */
public class MyRealm3 extends AuthenticatingRealm {

    @Autowired
    private UserSearchDao userSearchDao;

    //Authentication认证的意思
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
        String username = uptoken.getUsername();
        List<User> user;
        try {
            user = userSearchDao.findUser(username);
        } catch (Exception e) {
            throw new UnknownAccountException("用户不存在");
        }



        Object principal = user.get(0);
        //数据库中的密码应该指定加密方法加盐加密过指定次数的值
        Object credentials =user.get(0).getPassword();
        String realmName = getName();
        //获取盐
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        //使用长构造方法传入盐
        SimpleAuthenticationInfo Info = new SimpleAuthenticationInfo(principal, credentials,credentialsSalt, realmName);

        return Info;

    }
    //a25094795b76d7d407404c52983f1694
    //计算加密值(测试用，本方法也是shiro加密前台密码的方法)
    public static void main(String[] args) {
        String algorithmName="MD5";
        Object source="1234ABcd";//需要加密的密码
        Object salt="li";//盐
        int hashIterations=1024;//加密次数
        SimpleHash simpleHash = new SimpleHash(algorithmName, source, salt, hashIterations);
        System.out.println(simpleHash);
    }
}
