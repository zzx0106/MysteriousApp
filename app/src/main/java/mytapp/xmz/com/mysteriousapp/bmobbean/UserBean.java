package mytapp.xmz.com.mysteriousapp.bmobbean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/11/15.
 */
public class UserBean extends BmobUser {
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
