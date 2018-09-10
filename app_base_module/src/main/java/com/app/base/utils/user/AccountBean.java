package com.app.base.utils.user;

import com.app.base.entity.BaseEntity;
import com.app.base.entity.ImageEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bryan on 2017/12/4 0004.
 */

public class AccountBean extends BaseEntity implements Serializable {

    private String id;
    private String pwd;
    private String phone;
    private String nickName;
    private String registerTime;
    private String lastLoginTime;
    private String cityId;
    private String wxUnionId;
    private String wxOpenId;
    private String phoneType;
    private String nationCode;
    private String agentId;
    private String bindExpiresTime;
    private String salt;

    private String name;
    private String headImg;
    private String token;
    private String pushToken;
    private String score;
    private double balance;//金额

    private List<Agent> agentList;
    private List<String> permissionList;//平台员工具有的权限
    private List<Employee> employeeList;

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getBindExpiresTime() {
        return bindExpiresTime;
    }

    public void setBindExpiresTime(String bindExpiresTime) {
        this.bindExpiresTime = bindExpiresTime;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public List<Agent> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<Agent> agentList) {
        this.agentList = agentList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public double getBalance() {
        return balance;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static class Agent implements Serializable {
        private String id;
        private String name;
        private String accountId;
        private String registerTime;
        private int lv; //1 - 一级（市），2 - 二级（县区），3 - 三级（乡镇）
        private String disable;
        private int type;// 0 – 代理商， 1 – 经销商
        private String parentId;
        private String address;
        private String cityId;
        private String idNumber;
        private String totalProfit;
        private String phone;
        private String parentOrganizationId;
        private String parentOrganizationName;
        private String parentAgentName;
        private String headImg;
        private int checkStatus;// 0 –未审核；1 – 审核通过； 2 – 审核不通过
        private List<Hall> hallList;
        private List<ImageEntity> imageList;

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getParentOrganizationName() {
            return parentOrganizationName;
        }

        public void setParentOrganizationName(String parentOrganizationName) {
            this.parentOrganizationName = parentOrganizationName;
        }

        public String getParentAgentName() {
            return parentAgentName;
        }

        public void setParentAgentName(String parentAgentName) {
            this.parentAgentName = parentAgentName;
        }

        public List<ImageEntity> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageEntity> imageList) {
            this.imageList = imageList;
        }

        public String getDisable() {
            return disable;
        }

        public void setDisable(String disable) {
            this.disable = disable;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public List<Hall> getHallList() {
            return hallList;
        }

        public void setHallList(List<Hall> hallList) {
            this.hallList = hallList;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getParentOrganizationId() {
            return parentOrganizationId;
        }

        public void setParentOrganizationId(String parentOrganizationId) {
            this.parentOrganizationId = parentOrganizationId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public int getLv() {
            return lv;
        }

        public void setLv(int lv) {
            this.lv = lv;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getTotalProfit() {
            return totalProfit;
        }

        public void setTotalProfit(String totalProfit) {
            this.totalProfit = totalProfit;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class Hall implements Serializable {
        private String id;
        private String name;
        private String address;
        private String cityId;
        private double longitude;
        private double latitude;
        private String phoneNumber;
        private String memo;
        private String disable;
        private String shortPy;

        public String getShortPy() {
            return shortPy;
        }

        public void setShortPy(String shortPy) {
            this.shortPy = shortPy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getDisable() {
            return disable;
        }

        public void setDisable(String disable) {
            this.disable = disable;
        }
    }

    /**
     * 员工信息
     */
    public static class Employee implements Serializable {

        private String id;
        private String accountId;
        private String phone;
        private String name;
        private String boss;
        private String disable;
        private String organizationId;
        private String duty;
        private String role;
        private String hallId;//0是平台，其余的都是展厅
        private String hallName;
        private List<String> permissionList;


        public String getHallName() {
            return hallName;
        }

        public void setHallName(String hallName) {
            this.hallName = hallName;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBoss() {
            return boss;
        }

        public void setBoss(String boss) {
            this.boss = boss;
        }

        public String getDisable() {
            return disable;
        }

        public void setDisable(String disable) {
            this.disable = disable;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
        }

        public String getHallId() {
            return hallId;
        }

        public void setHallId(String hallId) {
            this.hallId = hallId;
        }

        public List<String> getPermissionList() {
            return permissionList;
        }

        public void setPermissionList(List<String> permissionList) {
            this.permissionList = permissionList;
        }
    }

    public enum Identify {

        CommonUser(0, "普通用户"),
        Agency(1, "特约经销商"),
        Hall(2, "战略合作"),
        Center(3, "车同享");

        private int status;
        private String Desc;

        private Identify(int status, String desc) {
            this.status = status;
            this.Desc = desc;
        }

        public String getDesc() {
            return Desc;
        }
    }

    /*获取当前账号的身份*/
    public Identify getCurUserIdentify() {
        Identify identify = Identify.CommonUser;
        if (this.getAgentList() != null && this.getAgentList().size() > 0) {
            Agent agent = this.getAgentList().get(0);
            if (agent.getType() == 1) {//经销商身份
                identify = Identify.Agency;
            } else {
                identify = Identify.Hall;//展厅身份
            }
        } else if (this.getEmployeeList() != null && this.getEmployeeList().size() > 0) {
            Employee employee = getEmployeeList().get(0);
            if (employee.getHallId().equals("0")) {
                identify = Identify.Center;//平台
            } else {
                identify = Identify.Hall;//展厅
            }
        }
        return identify;
    }

}
