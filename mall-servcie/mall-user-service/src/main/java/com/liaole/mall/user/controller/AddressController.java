package com.liaole.mall.user.controller;

import com.liaole.mall.user.model.Address;
import com.liaole.mall.user.service.AddressService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "list")
    public RespResult<List<Address>> list(){
        String username = "liaole" ;
        List<Address> list = addressService.list(username);
        return  RespResult.ok(list);
    }

}
