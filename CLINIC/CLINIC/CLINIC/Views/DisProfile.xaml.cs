using CLINIC.Models;
using Rg.Plugins.Popup.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CLINIC.Views;


using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CLINIC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class DisProfile : ContentPage
    {
        ExecuteData ex = new ExecuteData();
        private static string cusno;
        private static string cusno2;

        public List<DisProfileModels> menuList2 = new List<DisProfileModels>();
        public DisProfile()
        {
            InitializeComponent();
            DisProfileList.ItemsSource = menuList2;
            cusno2 = App.abc;
            cusno = Views.Login.CCode;




            LoadData();
        }


        async Task LoadData()
        {
            /*** docNo_ mã chuẩn đoán 
 symptoms chịu chứng
 DisNo_ mã bệnh 
 nameofDis tên bệnh
 kết luận của bác sĩ summary
 treatments phuong phap dieu chỉ
***/

            var json = await ex.getDataBFO(@"Select DisPro.ProNo_ as ProNo_ 
,Employee.[EmpNo_] as ID_Emp 
,Employee.Name as Name_Emp 
, CusNo_ ,Customer.Name as Name_Cus 
, Customer.[Date of Birth] as DateBirth_Cus 
,Customer.[Address] as Address
,Customer.[Phone No_] as Phone
, [StaringDate] 
,[EndingDate]
,Employee.[CliNo_] as CLiNo_
,Clinic.Name as CliName 
,Clinic.Adr as CliAdr 
,Clinic.Phone as CliPhone 
FROM [DisPro], Employee, Customer, Clinic where DisPro.EmpNo_=Employee.EmpNo_ and DisPro.CusNo_= '" + cusno + "' and DisPro.CliNo_= Clinic.CliNo_ and Customer.No_='" + cusno + "'or  DisPro.EmpNo_=Employee.EmpNo_ and DisPro.CusNo_= '" + cusno2 + "' and DisPro.CliNo_= Clinic.CliNo_ and Customer.No_='" + cusno2 + "'");

            foreach (var item in json)
            {
                DisProfileModels _cldh = new DisProfileModels();
                _cldh = new DisProfileModels();
                _cldh.ID_Emp = item["ID_Emp"].ToString();
                _cldh.Name_Emp = item["Name_Emp"].ToString();
                _cldh.CusNo_ = item["CusNo_"].ToString();
                _cldh.Name_Cus = item["Name_Cus"].ToString();

                _cldh.DateBirth_Cus = DateTime.Parse(item["DateBirth_Cus"].ToString());
                _cldh.dateofbirth = _cldh.DateBirth_Cus.ToString("yyyy/MM/dd");
                _cldh.StaringDate = DateTime.Parse(item["StaringDate"].ToString());
                _cldh.straidate = _cldh.StaringDate.ToString("yyyy/MM/dd");
                _cldh.EndingDate = DateTime.Parse(item["EndingDate"].ToString());
                _cldh.endingdate = _cldh.EndingDate.ToString("yyyy/MM/dd");
                _cldh.CliNo_ = item["CLiNo_"].ToString();
                _cldh.CliName = item["CliName"].ToString();
                _cldh.CliAdr = item["CliAdr"].ToString();
                _cldh.CliPhone = item["CliPhone"].ToString();
                _cldh.ProNo_ = item["ProNo_"].ToString();
                _cldh.Address = item["Address"].ToString();
                _cldh.Phone = item["Phone"].ToString();


                menuList2.Add(_cldh);
            }

        }
        // lay du lieu list
        private async void DisProfileList_ItemTapped(object sender, ItemTappedEventArgs e)
        {
            var item = e.Item as DisProfileModels;
            var prono_ = item.ProNo_;
            var cusNo_ = item.CusNo_;
            await PopupNavigation.PushAsync(new PupopViews(prono_, cusNo_));
        }

    }
}