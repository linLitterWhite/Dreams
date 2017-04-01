package com.looklook.xinghongfei.looklook.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.looklook.xinghongfei.looklook.MainActivity;
import com.looklook.xinghongfei.looklook.MyApplication;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.LoginActivity;
import com.looklook.xinghongfei.looklook.bean.School.DSchool;
import com.looklook.xinghongfei.looklook.presenter.implView.ISchoolFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.looklook.xinghongfei.looklook.R.id.map;


public class SchoolSiftPopupWindow
        implements View.OnClickListener
{
    private static PopupWindow popupWindow;
    private String[][] cityList = { { "北京市" }, { "上海市" }, { "天津市" }, { "重庆市" },
            { "哈尔滨市", "齐齐哈尔市", "佳木斯市", "鹤岗市", "大庆市", "鸡西市", "双鸭山市", "伊春市", "牡丹江市", "黑河市", "七台河市", "绥化市", "大兴安岭地区" }, { "沈阳市", "大连市", "鞍山市", "抚顺市", "本溪市", "丹东市", "锦州市", "营口市", "阜新市", "辽阳市", "盘锦市", "铁岭市", "朝阳市", "葫芦岛市" }, { "长春市", "吉林市", "四平市", "辽源市", "通化市", "白山市", "白城市", "通化市", "松原市" }, { "石家庄市", "唐山市", "邯郸市", "秦皇岛市", "保定市", "张家口市", "承德市", "廊坊市", "沧州市", "衡水市和邢台市" }, { "郑州市", "开封市", "洛阳市", "平顶山市", "焦作市", "鹤壁市", "新乡市", "安阳市", "濮阳市", "许昌市", "漯河市", "三门峡市", "南阳市", "商丘市", "信阳市", "周口市", "驻马店市", "济源市" }, { "武汉市", "十堰市", "襄樊市", "随州市", "荆门市", "孝感市", "宜昌市", "黄冈市", "鄂州市", "荆州市", "黄石市", "咸宁市" }, { "长沙市", "岳阳市", "常德市", "株洲市", "湘潭市", "衡阳市", "益阳市", "张家界市", "郴州市", "娄底市", "邵阳市", "永州市", "怀化市和湘西土家族自治州" }, { "淄博市", "枣庄市", "东营市", "烟台市", "潍坊市", "济宁市", "泰安市", "威海市", "日照市", "滨州市", "德州市", "聊城市", "临沂市", "菏泽市", "莱芜市", "济南", "青岛" }, { "太原市", "大同市", "阳泉市", "长治市", "晋城市", "朔州市", "晋中市", "运城市", "忻州市", "临汾市", "吕梁市" }, { "西安市", "铜川市", "宝鸡市", "咸阳市", "渭南市", "汉中市", "安康市", "商洛市", "延安市", "榆林市" }, { "合肥市", "芜湖市", "蚌埠市", "淮南市", "马鞍山市", "淮北市", "铜陵市", "安庆市", "黄山市", "滁州市", "宣城市", "巢湖市", "阜阳市", "六安市", "宿州市", "亳州市", "池州市" }, { "杭州市", "宁波市", "温州市", "嘉兴市", "湖州市", "绍兴市", "金华市", "衢州市", "舟山市", "台州市", "丽水市" }, { "南京市", "无锡市", "徐州市", "常州市", "苏州市", "南通市", "连云港市", "淮安市", "盐城市", "扬州市", "镇江市", "泰州市", "宿迁市" }, { "厦门市", "地级市", "福州市", "泉州市", "漳州市", "南平市", "三明市", "龙岩市、莆田市、宁德市" }, { "珠海市", "汕头市", "佛山市", "韶关市", "湛江市", "肇庆市", "江门市", "茂名市", "惠州市", "梅州市", "汕尾市", "河源市", "阳江市", "清远市", "东莞市", "中山市", "潮州市", "揭阳市", "云浮市", " 广州市", " 深圳市" }, { "海口市", "三亚市", "琼海市", "万宁市", "儋州市", "东方市", "文昌市", "五指山市" }, { "成都市", "自贡市", "攀枝花市", "泸州市", "德阳市", "绵阳市", "广元市", "遂宁市", "内江市", "乐山市", "南充市", "宜宾市", "广安市", "达州市", "巴中市", "雅安市", "眉山市", "资阳市", "阿坝藏族羌族自治州", "甘孜藏族自治州", "凉山彝族自治州" }, { "昆明市", "曲靖市", "昭通市", "玉溪市", "楚雄州", "红河州", "文山州", "普洱市", "版纳州", "大理州", "保山市", "德宏州", "丽江市", "怒江州", "迪庆州", "临沧市" }, { "贵阳市", "遵义市", "六盘水市", "安顺市", "毕节地区", "铜仁地区", "黔西南州", "黔东南州", "黔南州" }, { "西宁市", "海东市", "海西蒙古族藏族自治州", "海南藏族自治州", "海北藏族自治州", "黄南藏族自治州", "果洛藏族自治州和玉树藏族自治州" }, { "呼伦贝尔市", "通辽市", "赤峰市", "乌兰察布", "包头市", "呼和浩特市", "巴彦淖尔市", "鄂尔多斯市", "乌海市", "兴安盟", "锡林郭勒盟", "阿拉善盟" }, { "石嘴山市", "银川市", "吴忠市", "中卫市", "固原市石嘴山市" }, { "乌鲁木齐市", "克拉玛依市", "吐鲁番市", "哈密地区", "阿克苏地区", "喀什地区", "和田地区", "塔城地区", "阿勒泰地区", "克孜勒苏柯尔克孜自治州", "昌吉回族自治州", "博尔塔拉蒙古自治州", "巴音郭楞蒙古自治州", "伊犁哈萨克自治州" }, { "市就拉萨市", "那曲地区", "日喀则地区", "昌都地区", "山南地区", "阿里地区", "林芝地区", "双湖特别行政区" }, { "南宁市", "柳州市", "桂林市", "梧州市", "玉林市", "贵港市", "贺州市", "河池市", "白色市", "崇左市", "防城港市", "钦州市", "北海市", "来宾市" }, { "兰州市", "嘉峪关市", "金昌市", "白银市", "天水市", "武威市", "张掖市", "酒泉市", "平凉市", "庆阳市", "定西市", "陇南市", "临夏回族自治州", "甘南藏族自治州" }, { "南昌", "九江", "赣州", "吉安", "萍乡", "鹰潭", "新余", "宜春", "上饶", "景德镇", "抚州" }, { "台北市", "新北市", "高雄市", "台中市", "台南市" }, { "香港岛", "九龙", "新界" }, { "澳门半岛", "离岛" } };
    private ListView cityLv;

    private TextView erben;
    private boolean listShow = false;
    private Context mContext;

    private Button ok_sift;

    private String type = "";
    String provicetext= "";
    String citytext=  "";
    private String scoretext=  "";
    private String schoolname = "";

    private String[] provinceList = { "北京市", "上海市", "天津市", "重庆市", "黑龙江省", "辽宁省", "吉林省", "河北省", "河南省", "湖北省", "湖南省", "山东省", "山西省", "陕西省", "安徽省", "浙江省", "江苏省", "福建省", "广东省", "海南省", "四川省", "云南省", "贵州省", "青海省", "内蒙古自治区", "宁夏回族自治区", "新疆维吾尔自治区", "西藏自治区", "广西壮族自治区 ", "甘肃省", "江西省", "台湾省", "香港特别行政区", "澳门特别行政区" };
    private ListView provinceLv;
    public Callback callback;
    private TextView sanben;
    private SchoolSiftPopupWindow schoolSiftPopupWindow;
    private EditText score;
    private Button sift_school_address;
    private EditText schoolName;
    private String[] ss = new String[0];
    private TextView yiben;

    ISchoolFragment mSchoolFragment;
    



    public SchoolSiftPopupWindow(Context paramContext)
    {
        this.mContext = paramContext;
    }

    private void initlisten()
    {
        this.yiben.setOnClickListener(this);
        this.erben.setOnClickListener(this);
        this.sanben.setOnClickListener(this);
        this.sift_school_address.setOnClickListener(this);
        this.ok_sift.setOnClickListener(this);
        this.provinceLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
            {
                SchoolSiftPopupWindow.this.provicetext = SchoolSiftPopupWindow.this.provinceList[paramAnonymousInt];
                SchoolSiftPopupWindow.this.initCityListView(SchoolSiftPopupWindow.this.cityList[paramAnonymousInt]);
                SchoolSiftPopupWindow.this.cityLv.setVisibility(View.VISIBLE);
            }
        });
        this.cityLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
            {
                TextView localTextView = (TextView)paramAnonymousView.findViewById(R.id.school_address_item);
                SchoolSiftPopupWindow.this.citytext = localTextView.getText().toString().trim();

                SchoolSiftPopupWindow.this.sift_school_address.setText(SchoolSiftPopupWindow.this.provicetext + "·" + SchoolSiftPopupWindow.this.citytext);
                SchoolSiftPopupWindow.this.cityLv.setVisibility(View.GONE);
                SchoolSiftPopupWindow.this.provinceLv.setVisibility(View.GONE);
                changlistshow(false);
                Toast.makeText(SchoolSiftPopupWindow.this.mContext, SchoolSiftPopupWindow.this.provicetext + SchoolSiftPopupWindow.this.citytext, Toast.LENGTH_SHORT).show();
                //SchoolSiftPopupWindow.access$602(SchoolSiftPopupWindow.this, false);
            }
        });
        recoverView();
    }

    private void changlistshow(boolean b) {
        this.listShow = b;
    }

    public List<Map<String, String>> getCityData(String[] paramArrayOfString)
    {
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < paramArrayOfString.length; i++)
        {
            HashMap localHashMap = new HashMap();
            localHashMap.put("city", paramArrayOfString[i]);
            localArrayList.add(localHashMap);
        }
        return localArrayList;
    }

    public PopupWindow getPopupWindow()
    {
        initPopwindow();
        return popupWindow;
    }

    public List<Map<String, String>> getProviceData()
    {
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < this.provinceList.length; i++)
        {
            HashMap localHashMap = new HashMap();
            localHashMap.put("provice", this.provinceList[i]);
            localArrayList.add(localHashMap);
        }
        return localArrayList;
    }

    public void initCityListView(String[] paramArrayOfString)
    {
        SimpleAdapter localSimpleAdapter = new SimpleAdapter(this.mContext, getCityData(paramArrayOfString), R.layout.school_address, new String[] { "city" }, new int[] { R.id.school_address_item });
        this.cityLv.setAdapter(localSimpleAdapter);
    }

    public void initPopwindow()
    {
        View localView = LayoutInflater.from(this.mContext).inflate(R.layout.school_sift, null, false);
        localView.getBackground().setAlpha(180);
        popupWindow = new PopupWindow(localView, -1, -2);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        LinearLayout localLinearLayout = (LinearLayout)localView.findViewById(R.id.school_sift_context);
        schoolName = (EditText) localView.findViewById(R.id.schoolnametext);
        this.score = ((EditText)localLinearLayout.findViewById(R.id.edit_fenshu));
        this.yiben = ((TextView)localLinearLayout.findViewById(R.id.yiben));
        this.erben = ((TextView)localLinearLayout.findViewById(R.id.erben));
        this.sanben = ((TextView)localLinearLayout.findViewById(R.id.sanben));
        this.sift_school_address = ((Button)localLinearLayout.findViewById(R.id.sift_school_address));
        this.provinceLv = ((ListView)localLinearLayout.findViewById(R.id.provinceList));
        this.cityLv = ((ListView)localLinearLayout.findViewById(R.id.cityList));
        this.ok_sift = ((Button)localLinearLayout.findViewById(R.id.ok_sift));
        SimpleAdapter localSimpleAdapter = new SimpleAdapter(this.mContext, getProviceData(), R.layout.school_address, new String[] { "provice" }, new int[] { R.id.school_address_item });
        this.provinceLv.setAdapter(localSimpleAdapter);
        initlisten();
    }

    public void onClick(View paramView)
    {
        switch (paramView.getId())
        {

            case R.id.yiben:
                this.type = "重点学校";
                recoverView();
                this.yiben.setTextColor(Color.BLACK);
                paramView.setBackgroundResource(R.drawable.school_sift_shap_click);
                return;
            case R.id.erben:
                this.type = "本科学校";
                recoverView();
                this.erben.setTextColor(Color.BLACK);
                paramView.setBackgroundResource(R.drawable.school_sift_shap_click);
                return;
            case R.id.sanben:
                this.type = "专科学校";
                recoverView();
                this.sanben.setTextColor(Color.BLACK);
                paramView.setBackgroundResource(R.drawable.school_sift_shap_click);
                return;
            case R.id.sift_school_address:
                if (!this.listShow)
                {
                    this.provinceLv.setVisibility(View.VISIBLE);
                    this.listShow = true;
                    return;
                }
                this.provinceLv.setVisibility(View.GONE);
                this.cityLv.setVisibility(View.GONE);
                this.listShow = false;
                return;
            case R.id.ok_sift:
                popupWindow.dismiss();
                scoretext = score.getText().toString().trim();
                schoolname = schoolName.getText().toString().trim();
                String URL = MyApplication.getURL()+"/SchoolNew/Andr_school_show_view";
    
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        Log.e("sift",result);
                        Gson gson = new Gson();
                        ArrayList<DSchool> schools = new Gson().fromJson(result, new TypeToken<ArrayList<DSchool>>(){}.getType());
                        //mSchoolFragment.updateSchoolData(schools);
                        callback.callback(schools);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
            
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError{
                        Map<String,String> map = new HashMap<>();
                        map.put("page",String.valueOf(0));
                        if(type != null || !type.equals(""))
                        map.put("type",type);
                        if(citytext != null || !citytext.equals(""))
                        map.put("diqu",citytext);
                        if(schoolname != null || !schoolname.equals(""))
                        map.put("schoolname",schoolname);
                        if(scoretext != null || !scoretext.equals(""))
                        map.put("fenshuxian",scoretext);
                        Log.e("siftschool", "getParams: "+map.toString());
                        return map;
                    }
                };
    
                MyApplication.getmQueue().add(request);
            
        }

    }

    public void recoverView()
    {
        this.yiben.setBackgroundResource(R.drawable.school_sift_shap);
        this.yiben.setTextColor(Color.parseColor("#3299CC"));
        this.erben.setBackgroundResource(R.drawable.school_sift_shap);
        this.sanben.setBackgroundResource(R.drawable.school_sift_shap);

        this.erben.setTextColor(Color.parseColor("#3299CC"));
        this.sanben.setTextColor(Color.parseColor("#3299CC"));

    }

    public void setCallback(Callback callback)
    {
        this.callback = callback;
    }

    public interface Callback
    {
        void callback(ArrayList<DSchool> schools);
    }


}
