package com.pinyougou.shop.service;

import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class UserDetailsServiceImpl implements UserDetailsService {
    //注入商家服务接口代理对象

    private SellerService sellerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //创建List集合封装角色
        ArrayList<GrantedAuthority> grandedAuths = new ArrayList<>();
        //添加角色
        grandedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        //根据登录名查询商家
        Seller seller = sellerService.findOne(username);
        //判断商家是否为空  并且   商家已经审核
        if (seller != null && seller.getStatus().equals("1")) {
            //返回用户信息对象
            return new User(username,seller.getPassword(),grandedAuths);
        }
        return null;
    }
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }
}
