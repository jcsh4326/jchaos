package me.jcis.chaos.core.service;

import java.util.List;

/**
 * 用户权限管理接口
 * 同一个方法，如果有带参数userId和不带参数userId两种情况，没有传入userId参数的默认指当前用户
 * @author <a href="mailto:jincheng.jcs@gmail.com">jincheng</a>
 * @version v1.0  2016/1/11
 */
public interface UserAuthorityService {
    /**
     * 判断指定id的用户是否是管理员
     * @param userId 用户id
     * @return
     */
    boolean isAdmin(String userId);

    /**
     * 判断当前用户是否是管理员
     * @return
     */
    boolean isAdmin();

    /**
     * 获取指定id的用户的所有角色
     * @param userId
     * @return
     */
    List getRoles(String userId);

    /**
     * 获取当前用户的所有角色
     * @return
     */
    List getRoles();

    /**
     * 指定用户是否拥有指定角色
     * @param userId
     * @param role 指定角色
     * @return
     */
    boolean ownRole(String userId, String role);

    /**
     * 当前用户是否拥有指定角色
     * @param role
     * @return
     */
    boolean ownRole(String role);

    /**
     * 获取指定id的用户的所有权限
     * @param userId
     * @return
     */
    List getAuthorities(String userId);

    /**
     * 获取当前用户的所有权限
     * @return
     */
    List getAuthorities();

    /**
     * 指定id的用户是否拥有指定权限
     * @param userId
     * @param authority 指定的权限
     * @return
     */
    boolean ownAuthority(String userId, String authority);

    /**
     * 当前用户是否拥有指定权限
     * @param authority 指定的权限
     * @return
     */
    boolean ownAuthority(String authority);
}
