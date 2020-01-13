package dao;
import java.util.List;

/**
 * @author ZhutianLin
 * @version 1.0.0
 * */
public interface DeveloperDao {
    //上架
    int addApp(App app);//加入新App(使用类内版本号)，注意状态应该是待审查

    //下架
    boolean deleteApp(String appId);//删除这个App，返回删除成功与否
    boolean deleteApp(String appId,String version);//删除对应版本的App，返回删除成功与否

    //修改
    int updateApp(App app);//更新App，传入App对象，返回影响的行号

    //查询
    List<App> getApp(String appId);//按照Id获取所有App
    App getApp(String appId, String version);//获取对应版本的App，按Id查找
    AppBasic getAppBasicInformation(String appId);//按Id获取基本信息
    AppVersion getAppVersionInformation(String appId);//按Id获取版本信息

}
