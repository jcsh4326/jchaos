package me.jcis.chaos.service.impl;

import me.jcis.chaos.core.service.UserAuthorityService;

import java.util.List;

/**
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/11
 */
public class UserAuthorityServiceImpl implements UserAuthorityService {
    /**
     * 判断指定id的用户是否是管理员
     *
     * @param userId 用户id
     * @return
     */
    public boolean isAdmin(String userId) {
        return false;
    }

    /**
     * 判断当前用户是否是管理员
     *
     * @return
     */
    public boolean isAdmin() {
        return false;
    }

    /**
     * 获取指定id的用户的所有角色
     *
     * @param userId
     * @return
     */
    public List getRoles(String userId) {
        return null;
    }

    /**
     * 获取当前用户的所有角色
     *
     * @return
     */
    public List getRoles() {
        return null;
    }

    /**
     * 指定用户是否拥有指定角色
     *
     * @param userId
     * @param role   指定角色
     * @return
     */
    public boolean ownRole(String userId, String role) {
        return false;
    }

    /**
     * 当前用户是否拥有指定角色
     *
     * @param role
     * @return
     */
    public boolean ownRole(String role) {
        return false;
    }

    /**
     * 获取指定id的用户的所有权限
     *
     * @param userId
     * @return
     */
    public List getAuthorities(String userId) {
        return null;
    }

    /**
     * 获取当前用户的所有权限
     *
     * @return
     */
    public List getAuthorities() {
        return null;
    }

    /**
     * 指定id的用户是否拥有指定权限
     *
     * @param userId
     * @param authority 指定的权限
     * @return
     */
    public boolean ownAuthority(String userId, String authority) {
        return false;
    }

    /**
     * 当前用户是否拥有指定权限
     *
     * @param authority 指定的权限
     * @return
     */
    public boolean ownAuthority(String authority) {
        return false;
    }
}
