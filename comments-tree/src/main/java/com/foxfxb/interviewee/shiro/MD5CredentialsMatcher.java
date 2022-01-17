
package com.foxfxb.interviewee.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class MD5CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Object accountCredentials = getCredentials(info);
        String pwdType = String.valueOf(token.getPassword());
        if (pwdType.length() == 32) {
            return equals(pwdType, accountCredentials);
        }
        String pwdUser = "";
        return equals(pwdUser, accountCredentials);
    }
}
