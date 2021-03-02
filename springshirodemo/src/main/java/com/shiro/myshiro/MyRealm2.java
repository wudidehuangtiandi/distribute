package com.shiro.myshiro;

import com.dao.UserSearchDao;
import com.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 仅仅是认证
 *
 * @author GC
 * @date 2020年 01月15日 20:16:42
 */
@Component
public class MyRealm2 extends AuthenticatingRealm {
    @Autowired
    private UserSearchDao userSearchDao;
    //Authentication认证的意思
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.把AuthenticationToken强转为UsernamePasswordToken
        UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
        //2.从UsernamePasswordToken中获取用户名
        String username = uptoken.getUsername();
        //3.调用数据库方法查询username对应的用户记录
        List<User> user;
        try {
            user = userSearchDao.findUser(username);
        } catch (Exception e) {
            //4.若用户不存在则抛出UnknownAccountException
            throw new UnknownAccountException("用户不存在");
        }
        //5.根据用户情况决定要不要抛出其他异常(比如数据表user有是否锁定啊啥的，在这边获取了可以判断下如果锁定就抛出LockedAccountException异常)
        //这边应为数据库木有这字段偷个懒不写了

        //6.构建AuthenticationInfo对象并返回
        //principal:认证的实体信息，也可以是实体类对象
        Object principal=user.get(0);
        //credentials:密码
        Object credentials=user.get(0).getPassword();
        //realmName:当前realm对象的name,调用父类的getname即可
        String realmName=getName();

        SimpleAuthenticationInfo Info = new SimpleAuthenticationInfo( principal,  credentials,  realmName);//AuthenticationInfo的实现类

        return Info;

    }
}
