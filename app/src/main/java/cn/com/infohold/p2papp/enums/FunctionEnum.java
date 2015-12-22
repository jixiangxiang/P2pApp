package cn.com.infohold.p2papp.enums;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.PInvestProjectActivity;
import cn.com.infohold.p2papp.activity.PTransferProjectActivity;

/**
 * Created by eric on 2015/5/11.
 */
public enum FunctionEnum {
    FUNCTION_INVEST("直投项目", "实物抵押安全可靠", R.mipmap.p_invest_icon, PInvestProjectActivity.class),
    FUNCTION_TRANSFER("债权转让", "流动性高", R.mipmap.p_transfer_icon, PTransferProjectActivity.class),
    FUNCTION_FINANCING("融资借款", "无抵押贷款", R.mipmap.p_invest_finaning, null),
    FUNCTION_SAFETY("安全保障", "银行级别保障", R.mipmap.p_safe_icon, null),
    FUNCTION_GUIDE("新手指引", "新手投资投资通道", R.mipmap.p_guide_icon, null),
    FUNCTION_MORE(null, null, R.mipmap.p_dot_more, null);

    private String functionDetailed;
    private String functionDescript;
    private int functionDetailedIcon;
    private Class functionClass;

    FunctionEnum(String functionDetailed, String functionDescript, int functionDetailedIcon, Class functionClass) {
        this.functionDetailed = functionDetailed;
        this.functionDetailedIcon = functionDetailedIcon;
        this.functionDescript = functionDescript;
        this.functionClass = functionClass;
    }

    FunctionEnum() {
    }

    public String getFunctionDetailed() {
        return functionDetailed;
    }

    public void setFunctionDetailed(String functionDetailed) {
        this.functionDetailed = functionDetailed;
    }

    public int getFunctionDetailedIcon() {
        return functionDetailedIcon;
    }

    public void setFunctionDetailedIcon(int functionDetailedIcon) {
        this.functionDetailedIcon = functionDetailedIcon;
    }

    public Class getFunctionClass() {
        return functionClass;
    }

    public void setFunctionClass(Class functionClass) {
        this.functionClass = functionClass;
    }

    public String getFunctionDescript() {
        return functionDescript;
    }

    public void setFunctionDescript(String functionDescript) {
        this.functionDescript = functionDescript;
    }
}
