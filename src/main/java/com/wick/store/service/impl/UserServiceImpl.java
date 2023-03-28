package com.wick.store.service.impl;

import com.wick.store.domain.entiey.UserEntity;
import com.wick.store.eums.AccountType;
import com.wick.store.repository.UserMapper;
import com.wick.store.service.UserService;
import com.wick.store.service.ex.*;
import com.wick.store.service.ex.fileEx.*;
import com.wick.store.util.GetPassWord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



@Service
@Slf4j
public class UserServiceImpl implements UserService {


    //设置文件上传的最大值
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    //限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("images/jpeg");
        AVATAR_TYPE.add("images/png");
        AVATAR_TYPE.add("images/bmp");
        AVATAR_TYPE.add("images/gif");
    }


    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(UserEntity userEntity) {

        //首先先调用findByUsername，来判断用户是否被注册过。
        UserEntity result = userMapper.findByUserName(userEntity.getUsername());
        if (result != null) {
            throw new UserNameDuplicatedException("用户已经被注册了");
        }
        //加密操作
        String oldPassword = userEntity.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = GetPassWord.getmd5PassWord(oldPassword, salt);
        userEntity.setPassword(md5Password);
        userEntity.setSalt(salt);
        userEntity.setIsDelete(0);
        userEntity.setCreatedUser(userEntity.getUsername());
        userEntity.setCreatedDate(new Date());
        userEntity.setUpdateDate(new Date());
        userEntity.setUpdateUser(userEntity.getUsername());
        Integer row = userMapper.insert(userEntity);
        if (row != 1) {
            throw new InsertException("用户在注册过程中产生为止异常");
        }

    }

    @Override
    public UserEntity login(String username, String password) {
        UserEntity result = userMapper.findByUserName(username);
        if (result != null) {
            throw new UserNameDuplicatedException("用户数据不存在");
        }
        /**
         * 检验用户的密码是否匹配
         * 1：先获取数据库中加密后的密码
         * 2：和用户传递过来的密码进行比较
         * 2.1：先获取盐值
         * 2.2：将获取的用户的密码按照相同的md5算法进行加密
         */
        String oldPassword = result.getPassword();
        String salt = result.getSalt();
        String newMd5Password = GetPassWord.getmd5PassWord(password, salt);
        if (!newMd5Password.equals(oldPassword)) {
            throw new PasswordNotMatchException("用户密码错误");
        }
        if (result.getIsDelete() == 1) {
            throw new UserNameDuplicatedException("用户数据不存在");

        }
        UserEntity user = new UserEntity();
        user.setUsername(result.getUsername());
        user.setUid(result.getUid());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(String uid, String username, String oldPassword, String newPassword) {
        UserEntity result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNameDuplicatedException("用户数据不存在");
        }
        String oldMd5Password = GetPassWord.getmd5PassWord(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldPassword)) {
            throw new PasswordNotMatchException("密码错误");
        }
        String newMd5Password = GetPassWord.getmd5PassWord(newPassword, result.getSalt());
        Integer row = userMapper.updatePasswordByUid(uid, username, newPassword);
        if (row != 1) {
            throw new UpdateException("更新密码错误");

        }

    }

    @Override
    public UserEntity findByUid(String uid) {
        UserEntity result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNameDuplicatedException("用户数据不存在");
        }
        UserEntity user = new UserEntity();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfoUser(String uid, UserEntity user) {
        UserEntity result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNameDuplicatedException("用户数据不存在");
        }
        user.setUid(uid);
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新数据时产生异常");
        }
    }

    @Override
    public void updateAvatar(HttpSession session,String uid, String username, MultipartFile file) {
        //判断文件是否为null
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        String contentType=file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        String parent=session.getServletContext().getRealPath("/upload");
        log.info("测试调用====",parent);
        //File对象指向这个路径，通过判断file是否存在得到该路径是否存在
        File dir=new File(parent);
        if (!dir.exists()){
            //检测目录是否存在，创建当前目录
            dir.mkdirs();
        }
        //获取这个文件的名称（文件名+后缀，不包含父目录的结构）用UUID工具生成一个字符串作为文件名
        //好处就是避免因文件名重复发生的覆盖
        String originalFilename=file.getOriginalFilename();
        log.info("originalFilename======"+originalFilename);
        int index=originalFilename.indexOf(".");
        String suffix=originalFilename.substring(index);
        String filename=UUID.randomUUID().toString().toUpperCase()+suffix;
        //到时候图片上传的的格式就是UUID.png
        File dest=new File(dir,filename);
        try{
            file.transferTo(dest);
        }catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        }catch (IOException e){
            throw new FileUploadIOException("文件读写异常");
        }
        String avatar="/upload/"+filename;
        UserEntity result=userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        Integer row=userMapper.updateAvatarByUid(uid,avatar,username);
        if (row!=1){
            throw new UpdateException("更新用户头像时产生未知异常");
        }

    }


}
