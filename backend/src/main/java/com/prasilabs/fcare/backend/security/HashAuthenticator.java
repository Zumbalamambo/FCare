package com.prasilabs.fcare.backend.security;

import com.prasilabs.constants.CommonConstant;
import com.prasilabs.fcare.backend.dbPojos.UserEntity;
import com.prasilabs.fcare.backend.logicEngines.UserLogicEngine;
import com.prasilabs.utils.DataUtil;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by prasi on 3/6/16.
 */
public class HashAuthenticator implements Authenticator
{
    @Override
    public User authenticate(HttpServletRequest httpServletRequest)
    {
        String hash = httpServletRequest.getHeader(CommonConstant.HASH_AUTH_STR);

        if(!DataUtil.isEmpty(hash))
        {
            UserEntity userEntity = UserLogicEngine.getInstance().getUserEntity(hash);

            if(userEntity != null) {
                return new User(String.valueOf(userEntity.getId()), userEntity.getPhone()+"@fcare.com");
            }
        }

        return null;
    }


}
