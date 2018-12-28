package com.favccxx.amp.admin.config;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.favccxx.amp.admin.constants.SysConstants;
import com.favccxx.amp.db.model.AmpShop;
import com.favccxx.amp.db.model.AmpUser;
import com.favccxx.amp.db.repository.ShopRepository;
import com.favccxx.amp.db.repository.UserRepository;

@Component
public class InitConfig implements ApplicationRunner {
	
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	UserRepository userRepository;
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		initShopInfo();
//		initUserInfo();
	}

	private void initShopInfo() {
		List<AmpShop> list = shopRepository.findAll();
		if(list == null || list.size() == 0) {
			AmpShop shop = new AmpShop();
			shop.setId(1);
			shop.setShopCode(SysConstants.DEFAULT_SHOP);
			shop.setShopName("易商城");
			shop.setCharger("陈先生");
			shop.setChargeTel("1871xxxxxxx");
			shop.setStatus("200");
			shop.setDescription("<p><strong>易商城</strong>是陈先生基于Panjiachen的VUE-ELEMENT-ADMIN开发的一套简易WEB商城项目。前端基于VUE2+Element，支持国际化，动态路由和权限验证，后台采用SpringBoot微服务+Shiro进行身份认证与鉴权，同时又集成了Swagger方便进行接口测试。</p>");
			shop.setCreateTime(new Date());
			shop.setCreateUserName("admin");
			shop.setUpdateTime(new Date());
			shopRepository.save(shop);
		}
	}
	
	private void initUserInfo() {
		AmpUser user = new AmpUser();
		user.setUsername("admin");
		user.setPassword("admin");
		user.setNickName("管理员");
		user.setStatus("NORMAL");
		user.setCreateTime(new Date());
		user.setCreateUserName("admin");
		user.setUpdateTime(new Date());
		user.setUpdateUserName("admin");
		userRepository.save(user);
	}
}
