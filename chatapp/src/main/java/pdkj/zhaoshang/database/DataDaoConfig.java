package pdkj.zhaoshang.database;

import org.xutils.DbManager;

import java.io.File;

/**
 * 创建时间： 2018/2/6
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class DataDaoConfig {

    private static DbManager.DaoConfig daoConfig;

    public static DbManager.DaoConfig getDaoConfig(){
        if(daoConfig==null){
            daoConfig = new DbManager.DaoConfig()
                    .setAllowTransaction(true)//设置允许开启事务
                    .setDbName("zhaoshang.db")//创建数据库的名称
                    // 不设置dbDir时, 默认存储在app的私有目录.
                    .setDbVersion(1)//数据库版本号
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            // 开启WAL, 对写入加速提升巨大
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    })
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            // TODO: ...
                        }
                    });
        }
        return daoConfig;
    }

}
