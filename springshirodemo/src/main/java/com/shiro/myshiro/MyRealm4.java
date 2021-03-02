package com.shiro.myshiro;

import com.dao.RolesDao;
import com.dao.UserSearchDao;
import com.pojo.User;
import com.pojo.UserRoles;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用于授权及认证
 *
 * @author GC
 * @date 2020年 01月17日 20:22:05
 */
public class MyRealm4 extends AuthorizingRealm {

    @Autowired
    private UserSearchDao userSearchDao;
    @Autowired
    private RolesDao rolesDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.从principalCollection获取登陆用户信息
        User user = (User)principalCollection.getPrimaryPrincipal();

        //2.利用登陆的用户的信息来获取当前用户的角色或者权限(查找数据库)
        String username = user.getUsername();
        List<UserRoles> roles1 = rolesDao.findRoles(username);
        Set<String> roles = new HashSet<>();
        for (UserRoles userRoles : roles1) {
            roles.add(userRoles.getRole_name());
        }

        //3.创建SimpleAuthorizationInfo并设置值
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);

        //4.返回AuthorizationInfo对象
        return simpleAuthorizationInfo;


    }

    //认证
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
        Object credentials =user.get(0).getPassword();
        String realmName = getName();
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo Info = new SimpleAuthenticationInfo(principal, credentials,credentialsSalt, realmName);

        return Info;

    }
}
