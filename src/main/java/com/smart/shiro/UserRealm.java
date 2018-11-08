package com.smart.shiro;

import javax.annotation.Resource;

import com.smart.bean.User;
import com.smart.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String)principals.getPrimaryPrincipal();
        System.out.println(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if(userService==null){
                userService = SpringBeanFactoryUtils.getBean("userService");
        }else {
            System.out.println("UserRealm is not NULL");
        }
        authorizationInfo.setRoles(userService.findRoles(username));
        authorizationInfo.setStringPermissions(userService.findPermissions(username));

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();
        System.out.println(username);
        if(userService==null){
            userService = SpringBeanFactoryUtils.getBean("userService");
        }else {
            System.out.println("UserRealmsss is not NULL");
        }
        System.out.println(username);
        User user = userService.findByGeNumber(username);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getGeNumber(),//用户名
                user.getPassword(),
                getName()  //realm name
        );

        return authenticationInfo;
    }

}
