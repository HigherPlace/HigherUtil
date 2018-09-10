package com.app.base.utils.user;


import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * User的工具类，用于初始化数据到本地
 * <p/>
 * 实现Serializable，使之序列化自身到本地
 * <p/>
 * 因为一个序列化的对象在每次反序列化的时候，都会创建一个新的对象，而不仅仅是一个对原有对象的引用。 为了防止这种情况，需要在单例类中加入
 * readResolve方法和readObject方法，并实现Cloneable接口。
 * <p>
 * <p>
 * // 调用方 public void 使用的地方() {
 * // 读取 User user =
 * UserUtil.getInstance().getUser();
 * // 设置
 * UserUtil.getInstance().setUser(new User()); }
 * <p>
 * <p>
 * 修改单利模式写法，2017-04-23  bryan
 * //user被instance持有，只要有instance就不存在说user会被回收。
 * // NP的情况有可能是因为JVM指令集乱序的问题导致user指向null。
 * // 需修改单利模式写法,在此修改成双重检验法
 */

public class UserUtil implements Serializable, Cloneable {

    public static final String TAG = "UserUtil";
    private static final long serialVersionUID = 5569628389557818532L;
    private static volatile UserUtil instance;
    private AccountBean user;

    private static final String GLOBAL_PATH = Utils.getExternalFolder("User")
            + File.separator;

    private UserUtil() {
    }

    public static UserUtil getInstance() {
        if (instance == null) {
            synchronized (UserUtil.class) {
                if (instance == null) {
                    // 被回收的情况
                    // 先从文件中读取
                    Object object = Utils.restoreObject(GLOBAL_PATH + TAG);
                    if (object == null) {
                        // 如果没有就是第一次启动，需要创建
                        object = new UserUtil();
                        Utils.saveObject(GLOBAL_PATH, TAG, object);
                    }
                    instance = (UserUtil) object;
                }
            }
        }
        return instance;
    }

    public AccountBean getUser() {
        return user;
    }

    public void setUser(AccountBean user) {
        if (user != null) {
            this.user = user;
            Utils.saveObject(GLOBAL_PATH, TAG, this);
        }
    }

    /**
     * 防止放序列化的问题
     *
     * @return
     * @throws CloneNotSupportedException
     */
    public UserUtil readResolver() throws CloneNotSupportedException {
        instance = (UserUtil) this.clone();
        return instance;
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
    }

    public Object Clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void reset() {
        user = null;
        Utils.saveObject(GLOBAL_PATH, TAG, this);
    }
}
