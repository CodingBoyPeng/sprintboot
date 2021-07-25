package com.pf.springboot.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pf.springboot.annotation.TargetDataSource;
import com.pf.springboot.entity.Role;
import com.pf.springboot.enums.DataSourceKey;
import com.pf.springboot.mapper.RoleMapper;
import com.pf.springboot.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service

public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Resource(name = "threadPool")
    ThreadPoolTaskExecutor executor;

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    // 当前任务数
    private final CountDownLatch latch = new CountDownLatch(8);

    @Transactional
    @Override
    public Integer insertRoleByThreadPool() {
        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Role role = new Role();
            role.setRoleCode("code" + i);
            role.setRoleName("name" + i);
            role.setDesc("desc" + i);
            roles.add(role);
        }

        long startTime = System.currentTimeMillis();
//        roles.forEach(i->roleMapper.insert(i));
        roleMapper.insert(roles.get(0));
        roles.forEach(i-> {
            executor.execute(() -> {
                System.out.println("当前线程"+ Thread.currentThread().getName());
                roleMapper.insert(i);
                latch.countDown();
            });
        });
        try {
            // 阻塞，等所有子线程完毕后唤醒主线程
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("多线程经历所需时间" + (endTime - startTime));
        return roles.size();
    }

    @TargetDataSource(dataSourceKey=DataSourceKey.DB_SLAVE)
    @Override
    public String insertRole() {
        Role role = new Role();
        role.setRoleCode("code");
        role.setRoleName("name");
        role.setDesc("desc");
        roleMapper.insert(role);

        return "犯贱";
    }

    @TargetDataSource(dataSourceKey=DataSourceKey.DB_SLAVE)
    @Override
    public IPage<Role> rolePage() {
        Page<Role> userPage = new Page<>(1 , 10);
        return roleMapper.selectPage(userPage, null);
    }


}
