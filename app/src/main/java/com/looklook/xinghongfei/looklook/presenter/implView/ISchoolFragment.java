package com.looklook.xinghongfei.looklook.presenter.implView;

import com.looklook.xinghongfei.looklook.bean.School.DSchool;
import com.looklook.xinghongfei.looklook.bean.School.School;
import java.util.List;

/**
 * Created by xinghongfei on 16/8/20.
 */
public interface ISchoolFragment extends IBaseFragment {
     void updateSchoolData(List<DSchool> Schools);
}
