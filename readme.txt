Android CommonLibaray Structure:

1.package structure
���ṹ
com.android.library

2.resource name regulation
��Դ�������� 
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
    
4.ģ���б�
A.PullToRefresh:����ˢ��
B.CheckList����ѡ�б�
C.Sidebar:A-Z��ĸ����
D.Thirdpay:������֧��
E.Zxing:����ɨ��
F.BaseActivity:����UI
G.Net:��������,WebService
H.XUtils:ͼƬ����
