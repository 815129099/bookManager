
package com.smart.shiro;

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

    public UserRealm() {
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        System.out.println(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (this.userService == null) {
            this.userService = (UserService)SpringBeanFactoryUtils.getBean("userService");
        } else {
            System.out.println("UserRealm is not NULL");
        }

        authorizationInfo.setRoles(this.userService.findRoles(username));
        authorizationInfo.setStringPermissions(this.userService.findPermissions(username));
        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        System.out.println(username);
        if (this.userService == null) {
            this.userService = (UserService)SpringBeanFactoryUtils.getBean("userService");
        } else {
            System.out.println("UserRealmsss is not NULL");
        }

        System.out.println(username);
        User user = this.userService.findByGeNumber(username);
        if (user == null) {
            throw new UnknownAccountException();
        } else {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getGeNumber(), user.getPassword(), this.getName());
            return authenticationInfo;
        }
    }
}
