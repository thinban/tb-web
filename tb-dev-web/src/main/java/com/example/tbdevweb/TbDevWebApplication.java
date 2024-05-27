package com.example.tbdevweb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.tbdevweb.domain.Users;
import com.example.tbdevweb.service.UsersService;
import com.tb.R;
import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController
public class TbDevWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbDevWebApplication.class, args);
    }

    @Resource
    UsersService usersService;

    @GetMapping("foo")
    public Object foo() {
        return "bar";
    }

    @PostMapping("db/{op}")
    public Object db(@PathVariable int op, Page pageReq, @RequestBody(required = false) Users req) {
        if (op == 1) {
            return R.ok(usersService.save(req));
        } else if (op == 2) {
            return R.ok(usersService.removeById(req));
        } else if (op == 3) {
            return R.ok(usersService.updateById(req));
        } else if (op == 4) {
//            POST http://localhost:8080/db/4?current=1&size=10&orders[0].column=email&orders[0].asc=false&orders[1].column=username
//            Content-Type: application/json
            QueryWrapper query = MybatisPlusConfig.search(Users.class, req);
            return R.ok(usersService.page(pageReq, query));
        } else {
            return R.fail();
        }
    }
}
