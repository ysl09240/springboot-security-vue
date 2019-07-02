package com.sykean.hmhc.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sykean.hmhc.common.*;
import com.sykean.hmhc.config.fastdfs.core.FastDFSTemplate;
import com.sykean.hmhc.config.fastdfs.properties.FastDFSProperties;
import com.sykean.hmhc.entity.Dept;
import com.sykean.hmhc.entity.Role;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.entity.UserRole;
import com.sykean.hmhc.enums.DateTypeEnum;
import com.sykean.hmhc.enums.UserGender;
import com.sykean.hmhc.enums.UserState;
import com.sykean.hmhc.exception.UserStopException;
import com.sykean.hmhc.mapper.DeptMapper;
import com.sykean.hmhc.mapper.RoleMapper;
import com.sykean.hmhc.mapper.UserMapper;
import com.sykean.hmhc.mapper.UserRoleMapper;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.user.*;
import com.sykean.hmhc.res.dept.DeptListRes;
import com.sykean.hmhc.res.role.UserRoleRes;
import com.sykean.hmhc.res.user.UserEditRes;
import com.sykean.hmhc.res.user.UserListRes;
import com.sykean.hmhc.service.UserService;
import com.sykean.hmhc.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FastDFSTemplate fastDFSTemplate;

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private FastDFSProperties fastDFSProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User param = new User();
        param.setUsername(username);
        User user = userMapper.selectOne(param);
        if(user==null){
            throw new UsernameNotFoundException(ResponseCode.LOGIN_FAILED.getMsg());
        }
        if (!UserState.NORMAL.getValue().equals(user.getState())) {
            throw new UserStopException("用户已经停用，无法登录");
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>(0));
    }

    @Override
    public RestResponse<PageRes<UserListRes>> selectList(UserListReq userSearch) {
        //获取当前用户
        List<User> users = generateConditions(userSearch);
        long total = new PageInfo<>(users).getTotal();
        if (total == 0) {
            return RestResponse.success(new PageRes<>(new ArrayList<>(0), 0));
        }
        List<UserListRes> userListRes = BeanUtils.copyListProperties(users, UserListRes.class);
        //对应的部门
        List<String> deptIds = users.stream().map(User::getDeptId).collect(Collectors.toList());
        List<Dept> depts = deptMapper.selectBatchIds(deptIds);
        //对应的角色
        List<String> userIds = userListRes.stream().map(UserListRes::getId).collect(Collectors.toList());
        List<UserRoleRes> userRoleResList = userRoleMapper.findByUserIds(userIds);
        List<DictDataReq> userStateTypes = DictDataUtil.getDict(DictConstants.USER_STATE_TYPE);
        for (UserListRes userRes : userListRes) {
            //部门名称
            if (deptIds.size() > 0) {
                for (Dept dept : depts) {
                    if (dept.getCode().equals(userRes.getDeptId())) {
                        userRes.setDeptLabel(dept.getCodeName());
                    }
                }
            }
            //角色名称
            if (userIds.size() > 0) {
                for (UserRoleRes userRoleRes : userRoleResList) {
                    if (userRes.getId().equals(userRoleRes.getUserId())) {
                        userRes.setRoleId(userRoleRes.getRoleId());
                        userRes.setRoleLabel(userRoleRes.getRoleName());
                    }
                }
            }
            //用户状态
            for (DictDataReq dictData : userStateTypes) {
                if (userRes.getState().equals(dictData.getValue())) {
                    userRes.setStateLabel(dictData.getName());
                }
            }
        }
        return RestResponse.success(new PageRes<>(userListRes, total));
    }

    /**
     * 构造查询条件
     *
     * @param userSearch
     * @return
     */
    private List<User> generateConditions(UserListReq userSearch) {
        Wrapper<User> wrapper = new EntityWrapper<>();
        //用户名
        wrapper.like(StringUtils.isNotBlank(userSearch.getRealName()), "real_name", userSearch.getRealName());
        //警务编号
        wrapper.eq(StringUtils.isNotBlank(userSearch.getJzCode()), "jz_code", userSearch.getJzCode());
        //身份证
        wrapper.like(StringUtils.isNotBlank(userSearch.getCerNo()), "cer_no", userSearch.getCerNo());
        //所属组织
        List<DeptListRes> deptListVOS = deptMapper.findChildren(userSearch.getDeptId() != null ? userSearch.getDeptId() : SecurityUtil.getUser().getDeptId());
        //未查询到区域
        if (deptListVOS.size() == 0) {
            return new ArrayList<>(0);
        }
        wrapper.in("dept_id", deptListVOS.stream().map(DeptListRes::getCode).collect(Collectors.toList()));
        //角色id
        if (StringUtils.isNotBlank(userSearch.getRoleId())) {
            List<UserRole> userRoles = userRoleMapper.selectList(new EntityWrapper<UserRole>().eq("role_id", userSearch.getRoleId()));
            if (userRoles.size() == 0) {
                return new ArrayList<>(0);
            }
            List<String> userIds = userRoles.stream().map(UserRole::getUserId).collect(Collectors.toList());
            if (userIds.size() == 0) {
                return new ArrayList<>(0);
            }
            wrapper.in("id", userIds);
        }
        //联系电话
        wrapper.like(StringUtils.isNotBlank(userSearch.getPhone()), "phone", userSearch.getPhone());
        //状态
        wrapper.like(StringUtils.isNotBlank(userSearch.getState()), "state", userSearch.getState());
        //开始时间
        wrapper.gt(userSearch.getStartCreateTime() != null, "create_time", userSearch.getStartCreateTime());
        //结束时间
        if (userSearch.getEndCreateTime() != null) {
            wrapper.le("create_time", DateUtil.getDayAftertDay(userSearch.getEndCreateTime(), 1, DateTypeEnum.DAY));
        }
        wrapper.orderBy("create_time", false);
        PageHelper.startPage(userSearch.getPageIndex(), userSearch.getPageSize());
        return userMapper.selectList(wrapper);
    }

    @Override
    public RestResponse<UserEditRes> findEditDeatilById(String id) {
        User user = userMapper.selectById(id);
        if(user == null){
            return RestResponse.error(ResponseCode.USER_NOT_EXISTS,null);
        }
        UserEditRes userEditRes = BeanUtils.copyPropertiesByClass(user, UserEditRes.class);
        UserRole param = new UserRole();
        param.setUserId(id);
        UserRole userRole = userRoleMapper.selectOne(param);
        if (userRole != null) {
            userEditRes.setRoleId(userRole.getRoleId());
        }
        //部门名称
        if (StringUtils.isNotBlank(user.getDeptId())) {
            final Dept dept = deptMapper.selectById(user.getDeptId());
            if (dept != null) {
                userEditRes.setDeptLabel(dept.getCodeName());
            }
        }
        //角色
        List<UserRoleRes> userRoleRes = userRoleMapper.findByUserIds(Collections.singletonList(user.getId()));
        if (userRoleRes.size() > 0) {
            userEditRes.setRoleName(userRoleRes.get(0).getRoleName());
        }
        //性别
        final String genderLabel = DictDataUtil.queryLabel(DictConstants.GENDER_TYPE, user.getGender());
        userEditRes.setGenderLabel(genderLabel);
        //状态
        final String stateLabel = DictDataUtil.queryLabel(DictConstants.USER_STATE_TYPE, user.getState());
        userEditRes.setStateLabel(stateLabel);
        return RestResponse.success(userEditRes);
    }

	@Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse save(UserSaveReq userSaveReq){
        User user = BeanUtils.copyPropertiesByClass(userSaveReq, User.class);
        user.setId(SysUtils.GetUUID());
        //密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //创建时间
        user.setCreateTime(new Date());
        //创建人
        user.setCreateUserId(SecurityUtil.getUser() != null ? SecurityUtil.getUser().getId():"");
        //保存用户
        userMapper.insert(user);
        //设置角色
        UserRole userRole = new UserRole(user.getId(), userSaveReq.getRoleId());
        userRole.setId(SysUtils.GetUUID());
        userRoleMapper.insert(userRole);
        return RestResponse.success();
    }

    @Override
    public boolean validateUsername(String username, String id) {
        Wrapper<User> wrapper = new EntityWrapper<User>().eq("username", username);
        if (StringUtils.isNotBlank(id)) {
            wrapper.ne("id", id);
        }
        Integer count = userMapper.selectCount(wrapper);
        return count != 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse update(UserUpdateReq userUpdateReq) {
        User user = userMapper.selectById(userUpdateReq.getId());
        if (user == null) {
            return RestResponse.error(ResponseCode.USER_NOT_EXISTS, userUpdateReq.getId());
        }
        BeanUtils.copyProperties(userUpdateReq, user);
        //修改人
        user.setUpdateUserId(SecurityUtil.getUser() != null ? SecurityUtil.getUser().getId() : "");
        //修改时间
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
        //删除当前用户所对应的的角色
        userRoleMapper.delete(new EntityWrapper<UserRole>().eq("user_id", userUpdateReq.getId()));
        //角色
        UserRole userRole = new UserRole(user.getId(), userUpdateReq.getRoleId());
        userRole.setId(SysUtils.GetUUID());
        userRoleMapper.insert(userRole);
        return RestResponse.success();
    }

    @Override
    @Cacheable(value = "user", key = "'one:username_'+#username")
    public User findByUsername(String username) {
        User param = new User();
        param.setUsername(username);
        return userMapper.selectOne(param);
    }

    @Override
    public boolean validateCerNo(String cerNo, String id) {
        Wrapper<User> wrapper = new EntityWrapper<User>().eq("cer_no", cerNo);
        if (StringUtils.isNotBlank(id)) {
            wrapper.ne("id", id);
        }
        Integer count = userMapper.selectCount(wrapper);
        return count != 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse delete(String id) {
        //删除对应的角色
        userRoleMapper.delete(new EntityWrapper<UserRole>().eq("user_id", id));
        //删除用户
        userMapper.deleteById(id);
        return RestResponse.success();
    }

    @Override
    public RestResponse importData(List<String[]> dataList, String ziptemp) {
        if (dataList.size() == 0) {
            return RestResponse.error("上传文件excel为空数据");
        }
        //结果信息
        String encodedPassword = passwordEncoder.encode(Constants.USER_DEFAULT_PASSWORD);
        Date now = new Date();
        int row = 1;
        for (String[] userArr : dataList) {
            User user = new User();
            user.setId(SysUtils.GetUUID());
            //用户姓名
            String realName = userArr[0];
            if (StringUtils.isBlank(realName)) {
                return RestResponse.error(String.format("第%s行用户姓名不能为空", row));
            }
            if (realName.length() > 20) {
                return RestResponse.error(String.format("第%s行用户姓名长度超过20", row));
            }
            user.setRealName(realName);
            //性别
            String gender = userArr[1];
            if (StringUtils.isBlank(gender)) {
                return RestResponse.error(String.format("第%s行性别不能为空", row));
            }
            if (UserGender.MALE.getName().equals(gender)) {
                user.setGender(UserGender.MALE.getValue());
            } else if (UserGender.FEMALE.getName().equals(gender)) {
                user.setGender(UserGender.FEMALE.getValue());
            } else {
                return RestResponse.error(String.format("第%s行性别填写不正确", row));
            }
            //组织
            String deptName = userArr[2];
            if (StringUtils.isBlank(deptName)) {
                return RestResponse.error(String.format("第%s行组织不能为空", row));
            }
            Dept deptParam = new Dept();
            deptParam.setCodeName(deptName);
            Dept dept = deptMapper.selectOne(deptParam);
            if (dept == null) {
                return RestResponse.error(String.format("第%s行组织不存在", row));
            }
            user.setDeptId(dept.getCode());
            //警务编号
            String jzCode = userArr[3];
            if (StringUtils.isBlank(jzCode)) {
                return RestResponse.error(String.format("第%s行警务编号不能为空", row));
            }
            if (jzCode.length() > 10) {
                return RestResponse.error(String.format("第%s行警务编号长度超过10", row));
            }
            if (!jzCode.matches("^[0-9]{0,10}$")) {
                return RestResponse.error(String.format("第%s行警务编号不是数字", row));
            }
            user.setJzCode(jzCode);
            //身份证号
            String cerNo = userArr[4];
            if (StringUtils.isBlank(cerNo)) {
                return RestResponse.error(String.format("第%s行身份证号不能为空", row));
            }
            if (cerNo.length() > 18) {
                return RestResponse.error(String.format("第%s行身份证号长度超过18", row));
            }
            if (!cerNo.matches("^[1-9][0-7]\\d{4}((19\\d{2}(0[13-9]|1[012])(0[1-9]|[12]\\d|30))|(19\\d{2}(0[13578]|1[02])31)|(19\\d{2}02(0[1-9]|1\\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\\d{3}(\\d|X|x)?$")) {
                return RestResponse.error(String.format("第%s行身份证号格式不正确", row));
            }
            int count = userMapper.selectCount(new EntityWrapper<User>().eq("cer_no", cerNo));
            if (count > 0) {
                return RestResponse.error(String.format("第%s行身份证号已存在", row));
            }
            user.setCerNo(cerNo);
            //联系电话
            String phone = userArr[5];
            if (StringUtils.isNotBlank(phone)) {
                if (phone.length() > 13) {
                    return RestResponse.error(String.format("第%s行联系电话长度超过13", row));
                }
                if (!phone.matches("^([0-9]{3,4}-[0-9]{7,8}|[0-9]{7,8}|[1][3,4,5,7,8,9][0-9]{9})$")) {
                    return RestResponse.error(String.format("第%s行联系电话格式不正确", row));
                }
                user.setPhone(phone);
            }
            //用户名
            String username = userArr[6];
            if (StringUtils.isBlank(username)) {
                return RestResponse.error(String.format("第%s行用户名不能为空", row));
            }
            if (username.length() > 20 || username.length() < 6) {
                return RestResponse.error(String.format("第%s行用户名长度大于20或者小于6", row));
            }
            if (!username.matches("^[a-zA-Z0-9]{6,20}$")) {
                return RestResponse.error(String.format("第%s行用户名应为6-20字母加数字", row));
            }
            count = userMapper.selectCount(new EntityWrapper<User>().eq("username", username));
            if (count > 0) {
                return RestResponse.error(String.format("第%s行用户名已存在", row));
            }
            user.setUsername(username);
            //登录密码
            user.setPassword(encodedPassword);
            //身份证人像面
            String cerFPath = userArr[7];
            if (StringUtils.isBlank(cerFPath)) {
                return RestResponse.error(String.format("第%s行身份证人像面不能为空", row));
            }
            File cerFPathFile = new File(ziptemp + "image" + File.separator + cerFPath);
            if (!cerFPathFile.exists()) {
                return RestResponse.error(String.format("第%s行身份证人像面不能为空", row));
            }
            cerFPath = fastDFSTemplate.uploadFile(cerFPathFile);
            user.setCerFPath(fastDFSProperties.getFilePrefix() + cerFPath);
            //身份证国徽面
            String cerBPath = userArr[8];
            if (StringUtils.isBlank(cerBPath)) {
                return RestResponse.error(String.format("第%s行身份证国徽面不能为空", row));
            }
            File cerBPathFile = new File(ziptemp + "image" + File.separator + cerBPath);
            if (!cerBPathFile.exists()) {
                return RestResponse.error(String.format("第%s行身份证国徽面不能为空", row));
            }
            cerBPath = fastDFSTemplate.uploadFile(cerBPathFile);
            user.setCerBPath(fastDFSProperties.getFilePrefix() + cerBPath);
            //警务证人像面
            String jwFPath = userArr[9];
            if (StringUtils.isBlank(jwFPath)) {
                return RestResponse.error(String.format("第%s行警务证人像面不能为空", row));
            }
            File jwFPathFile = new File(ziptemp + "image" + File.separator + jwFPath);
            if (!jwFPathFile.exists()) {
                return RestResponse.error(String.format("第%s行警务证人像面不能为空", row));
            }
            jwFPath = fastDFSTemplate.uploadFile(jwFPathFile);
            user.setJwFPath(fastDFSProperties.getFilePrefix() + jwFPath);
            //警务证国徽面
            String jwBPath = userArr[10];
            if (StringUtils.isBlank(jwBPath)) {
                return RestResponse.error(String.format("第%s行警务证国徽面不能为空", row));
            }
            File jwBPathFile = new File(ziptemp + "image" + File.separator + jwBPath);
            if (!jwBPathFile.exists()) {
                return RestResponse.error(String.format("第%s行警务证国徽面不能为空", row));
            }
            jwBPath = fastDFSTemplate.uploadFile(jwBPathFile);
            user.setJwBPath(fastDFSProperties.getFilePrefix() + jwBPath);
            //角色类型
            String roleName = userArr[11];
            if (StringUtils.isBlank(roleName)) {
                return RestResponse.error(String.format("第%s行角色类型不能为空", row));
            }
            Role roleParam = new Role();
            roleParam.setName(roleName);
            Role role = roleMapper.selectOne(roleParam);
            if (role == null) {
                return RestResponse.error(String.format("第%s行角色类型不存在", row));
            }
            //保存对应的角色
            UserRole userRole = new UserRole(user.getId(), role.getId());
            userRole.setId(SysUtils.GetUUID());
            userRoleMapper.insert(userRole);
            //保存用户
            user.setState(UserState.NORMAL.getValue());
            user.setCreateUserId(SecurityUtil.getUser().getId());
            user.setCreateTime(now);
            userMapper.insert(user);
            user = null;
            row++;
        }
        return RestResponse.success();
    }

    @Override
    public RestResponse updateUserState(UpdateUserStateReq updateUserState) {
        User user = new User();
        user.setId(updateUserState.getId());
        user.setState(updateUserState.getState());
        userMapper.updateById(user);
        return RestResponse.success();
    }

    @Override
    public RestResponse resetPassword(String id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return RestResponse.error(ResponseCode.TOEKN_INVALID, null);
        }
        String encodedPassword = passwordEncoder.encode(Constants.USER_DEFAULT_PASSWORD);
        user.setPassword(encodedPassword);
        userMapper.updateById(user);//清除缓存，重新登录
        final String token = stringRedisTemplate.opsForValue().get(Constants.USERNAME_TOKEN_PREFIX + user.getUsername());
        stringRedisTemplate.delete(Constants.USERNAME_TOKEN_PREFIX + user.getUsername());
        if (StringUtils.isNotBlank(token)) {
            stringRedisTemplate.delete(Constants.TOKEN_USERNAME_PREFIX + token);
            redisTemplate.delete(Constants.Security.AUTH_TOKEN_KEY + token);
        }
        return RestResponse.success();
    }

    @Override
    public RestResponse updatePwd(UpdatePwdReq updatePwdReq) {
        User user = SecurityUtil.getUser();
        if (user == null) {
            return RestResponse.error(ResponseCode.TOEKN_INVALID, null);
        }
        String encodedPassword = passwordEncoder.encode(updatePwdReq.getOldPassword());
        if (!encodedPassword.equals(user.getPassword())) {
            return RestResponse.error(ResponseCode.USER_PWD_ERROR, null);
        }
        String newPassword = passwordEncoder.encode(updatePwdReq.getNewPassword());
        user.setPassword(newPassword);
        userMapper.updateById(user);
        //清除缓存，重新登录
        final String token = stringRedisTemplate.opsForValue().get(Constants.USERNAME_TOKEN_PREFIX + user.getUsername());
        stringRedisTemplate.delete(Constants.USERNAME_TOKEN_PREFIX + user.getUsername());
        if (StringUtils.isNotBlank(token)) {
            stringRedisTemplate.delete(Constants.TOKEN_USERNAME_PREFIX + token);
            redisTemplate.delete(Constants.Security.AUTH_TOKEN_KEY + token);
        }
        return RestResponse.success();
    }
}