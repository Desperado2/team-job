package com.desperado.teamjob.service;

import com.desperado.teamjob.dao.UserDao;
import com.desperado.teamjob.dao.UserRepository;
import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.utils.IdGenerator;
import com.desperado.teamjob.utils.PwdUtil;
import com.desperado.teamjob.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("userService")
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Override
    public Result addOrUpdateUser(HttpServletRequest request,User user) {
        Result result = new Result();
        Date date = new Date();
        if(!StringUtils.isEmpty(user.getId())){
            user.setDateUpdate(date);
            userDao.update(user);
            result.setData(user);
        }else{
            try {
                if(isExist(user.getEmail())){
                    result.setSuccess(false);
                    result.setMsg("email已存在");
                }else{
                    String password = user.getPassword();
                    String id = getId();
                    user.setId(id);
                    user.setPassword(PwdUtil.encoder(password));
                    user.setDateCreate(date);
                    user.setDateUpdate(date);
                    userDao.addUser(user);
                    result.setData(user);

                    ExecutorService service = Executors.newFixedThreadPool(5);
                    service.execute(() -> {
                        System.out.println(request.getRequestURI()+request.getServletPath());;
                        String url = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("尊敬的").append(user.getName()).append(":").append("\n\n");
                        stringBuilder.append("恭喜注册成功").append("\n\n");
                        stringBuilder.append("登录账户:").append(user.getEmail()).append("\n\n");
                        stringBuilder.append("登录密码:").append(password).append("\n\n");
                        stringBuilder.append("登录地址:").append(url);
                        mailService.sendSimpleMail(user.getEmail(),"注册成功",stringBuilder.toString());});
                }
            }catch (Exception e){
                result.setSuccess(false);
                result.setMsg("异常，添加失败");
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Result selectAllUser(boolean isSplit) {
        Result result = new Result();
        try {
            List<UserDto> users = userDao.selectAllUser();
            if(isSplit){
                List<List<UserDto>> list = splitList(users);
                result.setData(list);
            }else {
                result.setData(users);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("异常，查询失败");
        }
        return result;
    }

    @Override
    public Result updatePassword(User user, String oldPassword, String newPassword) {
        Result result = new Result();
        if(PwdUtil.match(oldPassword,user.getPassword())){
            user.setPassword(PwdUtil.encoder(newPassword));
            user.setDateUpdate(new Date());
            userDao.updatePassword(user);
            result.setData(user);
        }else{
            result.setSuccess(false);
            result.setMsg("原密码错误");
        }
        return result;
    }

    @Override
    public Result selectUserById(String id) {
        Result result = new Result();
        try {
            UserDto user = userDao.selectUserById(id);
            result.setData(user);
        }catch (Exception e){
            result.setMsg("查询失败");
            result.setSuccess(false);
        }
        return result;
    }


    /**
     * 用户是否存在
     * @param email  注册邮箱
     * @return
     */
    public boolean isExist(String email){
        User user = userRepository.selectUserByEmail(email);
        return user != null?true:false;
    }

    /**
     * 获取id
     * @return
     */
    private String getId(){
        String id = IdGenerator.generate(8);
        UserDto userDto = userDao.selectUserById(id);
        while (userDto != null){
            id = IdGenerator.generate(8);
            userDto = userDao.selectUserById(id);
        }
        return id;
    }

    private  List<List<UserDto>> splitList(List<UserDto> userDtos){
        List<List<UserDto>> list = new ArrayList<>();
        List<UserDto> dtoList = new ArrayList<>();
            for (int i= 0; i< userDtos.size(); i++){
                dtoList.add(userDtos.get(i));
                if((i+1) % 4 == 0){
                    list.add(dtoList);
                    dtoList = new ArrayList<>();
                }
            }
            if(dtoList.size() > 0){
                list.add(dtoList);
            }
        return list;
    }
}
