Android CommonLibaray Structure:

1.package structure
包结构
com.android.library

2.resource name regulation
资源命名规则： 
layout:lib_layout_module_name
string:lib_string_module_name
drawable:lib_drawable_module_name
id:lib_id_module_name

3.module list

a.checklist
package:com.android.library.ui.checklist
resource:
	layout:lib_layout_checklist_main_demo.xml 
		   lib_layout_checklist_item.xml 
	       lib_layout_checkedlist_item.xml
    drawable:
    values:lib_checklist_strings.xml
    
4.模块列表
A.PullToRefresh:下拉刷新
B.CheckList：多选列表
C.Sidebar:A-Z字母排序
D.Thirdpay:第三方支付
E.Zxing:条码扫描
F.BaseActivity:定制UI
G.Net:网络请求,WebService
H.XUtils:图片加载
