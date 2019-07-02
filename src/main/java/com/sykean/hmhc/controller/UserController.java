package com.sykean.hmhc.controller;

import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.req.user.*;
import com.sykean.hmhc.res.user.UserEditRes;
import com.sykean.hmhc.res.user.UserListRes;
import com.sykean.hmhc.service.UserService;
import com.sykean.hmhc.util.ExcelUtils;
import com.sykean.hmhc.util.FileUtil;
import com.sykean.hmhc.util.IOUtils;
import com.sykean.hmhc.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user")
@Validated
@Api(value = "用户接口", description = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "用户列表")
    @PostMapping("list")
    public RestResponse<PageRes<UserListRes>> list(@RequestBody @Valid UserListReq userListReq) {
        return userService.selectList(userListReq);
    }

    @ApiOperation("根据用户id获取编辑信息")
    @GetMapping("detail")
    public RestResponse<UserEditRes> findEditDeatilById(@RequestParam("id") String id) {
        return userService.findEditDeatilById(id);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("save")
    public RestResponse save(@RequestBody @Valid UserSaveReq userSaveReq) {
        return userService.save(userSaveReq);
    }

    @ApiOperation(value = "校验用户名是否重复")
    @GetMapping("validate/username")
    public RestResponse validateUserName(@RequestParam(value = "username") String username, @RequestParam(value = "id", required = false) String id) {
        boolean exists = userService.validateUsername(username, id);
        return RestResponse.success(exists);
    }

    @ApiOperation(value = "校验身份证号码是否重复")
    @GetMapping("validate/cerNo")
    public RestResponse validateCerNo(@RequestParam(value = "cerNo") String cerNo, @RequestParam(value = "id", required = false) String id) {
        boolean exists = userService.validateCerNo(cerNo, id);
        return RestResponse.success(exists);
    }

    @ApiOperation(value = "修改用户")
    @PostMapping("update")
    public RestResponse update(@RequestBody @Valid UserUpdateReq userUpdateReq) {
        return userService.update(userUpdateReq);
    }

    @ApiOperation(value = "删除用户")
    @GetMapping("del")
    public RestResponse delete(@RequestParam("id") String id) {
        return userService.delete(id);
    }

    @ApiOperation(value = "批量删除用户")
    @GetMapping("del/batch")
    public RestResponse batchDel(@RequestParam("ids") String[] userIds) {
        userService.deleteBatchIds(Arrays.asList(userIds));
        return RestResponse.success();
    }

    @PostMapping("import")
    @ApiOperation(value = "批量导入用户")
    public RestResponse importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            //校验文件类型
            if (file.getOriginalFilename() != null && !file.getOriginalFilename().endsWith(".zip")) {
                return RestResponse.error("上传文件必须为zip格式的文件");
            }
            //构建临时目录文件夹名称
            String ziptemp = System.getProperty("user.dir") + File.separator + "zipFiles"
                    + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + File.separator;
            //解压zip到临时文件夹里
            FileUtil.unZip(file, ziptemp);
            File[] imgFiles = new File(ziptemp + "image").listFiles((dir, name) ->
                    name.endsWith("jpg") || name.endsWith("jpeg") || name.endsWith("png") || name.endsWith("bmp"));
            if (imgFiles == null || imgFiles.length < 4) {
                return RestResponse.error("上传文件必须为4张jpg、jpeg、png、bmp格式的图片");
            }
            //获取解压后文件夹
            File[] excel = new File(ziptemp).listFiles((dir, name) -> name.endsWith("xls") || name.endsWith("xlsx"));
            if (excel == null || excel.length == 0) {
                return RestResponse.error("excel不存在");
            }
            //获取excel表格数据
            List<String[]> dataList;
            try {
                dataList = ExcelUtils.getExcelData(FileUtil.file2MulFile(excel[0]));
            } catch (Exception e) {

                return RestResponse.error("读取excel出错，请检查excel!");
            }
            //导入db
            return userService.importData(dataList, ziptemp);
        } finally {
            //最后删除文件夹
            FileUtil.deleteAllFileByDir(new File(System.getProperty("user.dir") + File.separator + "zipFiles"));
        }

    }

    @ApiOperation(value = "下载批量导入模板")
    @GetMapping("download/template")
    public void downFlie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] data;
        try (InputStream inputStream = this.getClass().getResourceAsStream("/files/user_import_template.zip")) {
            data = IOUtils.toByteArray(inputStream);
        }
        String fileName = "用户批量导入模板.zip";
        IOUtils.downloadFile(request, response, data, fileName);
    }

    @ApiOperation("停用/启动用户")
    @PostMapping("update/state")
    public RestResponse updateUserState(@RequestBody @Valid UpdateUserStateReq updateUserState) {
        return userService.updateUserState(updateUserState);
    }

    @ApiOperation("重置密码")
    @GetMapping("pwd/reset")
    public RestResponse resetPassword(@RequestParam String id) {
        return userService.resetPassword(id);
    }

    @ApiOperation("修改密码")
    @PostMapping("pwd/update")
    public RestResponse updatePwd(@RequestBody @Valid UpdatePwdReq updatePwdReq) {
        return userService.updatePwd(updatePwdReq);
    }

    @ApiOperation(("校验原密码是否正确"))
    @PostMapping("validate/pwd")
    public RestResponse validatePwd(@RequestBody ValidatePwdReq validatePwdReq) {
        String password = SecurityUtil.getUser().getPassword();
        boolean valid = passwordEncoder.matches(validatePwdReq.getPassword(), password);
        return RestResponse.success(valid);
    }
}