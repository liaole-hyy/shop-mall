package com.liaole.mall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liaole.mall.user.mapper.AddressMapper;
import com.liaole.mall.user.model.Address;
import com.liaole.mall.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     *  根据用户姓名查询地址
      * @param userName
     * @return
     */
    @Override
    public List<Address> list(String userName) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper();

        queryWrapper.eq("userName",userName);

        return addressMapper.selectList(queryWrapper);
    }
}
