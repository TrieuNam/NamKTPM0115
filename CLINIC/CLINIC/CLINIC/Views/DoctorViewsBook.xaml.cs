using CLINIC.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CLINIC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class DoctorViewsBook : ContentPage
    {
        BookModel2 _cldh = new BookModel2();
        public List<BookModel2> menuList2 = new List<BookModel2>();
        ExecuteData ex = new ExecuteData();
        string description_p, hoten_p, mabs_p, id_doctor_cli_p, id_doctor_fa_p;
        public static string id_cs_p, sex_cs_p, phone_cs_p, mail_cs_p, name_cus;
        public DoctorViewsBook()
        {
            InitializeComponent();
            var today = dp.Date.ToString("yyyy/MM/dd");
            LoadData(mabs_p, today);
            listtime.ItemsSource = null;
            listtime.ItemsSource = menuList2;
        }
        private static string sql;
        async Task LoadData(string mabs, string today)// hom nay
        {
            var id_dtor = Views.Login.EmPNo;
            var id_dtor2 = App.Empo;
            if (id_dtor != null)
            {
                sql = @"SELECT[BookNo_] ,[CusNo_] ,[NameCus] ,[Name] ,[Phone] as Phone_Cus ,[Adr] ,[DateBook],[TimeBook],[EmpNo_] ,[NameEmp] ,[Phone No_] as Phone_Emp ,[CliNo_],[NameCli],[SubCliNo_],[TimeShift],[TimeShift2],[NameTime],[TrangThai] FROM[BFOCLINIC].[dbo].[Book] where EmpNo_ = '" + id_dtor + "' and DateBook = '" + today + "'";
            }
            else
            {
                sql = @"SELECT[BookNo_] ,[CusNo_] ,[NameCus] ,[Name] ,[Phone] as Phone_Cus ,[Adr] ,[DateBook],[TimeBook],[EmpNo_] ,[NameEmp] ,[Phone No_] as Phone_Emp ,[CliNo_],[NameCli],[SubCliNo_],[TimeShift],[TimeShift2],[NameTime],[TrangThai] FROM[BFOCLINIC].[dbo].[Book] where EmpNo_ = '" + id_dtor2 + "' and DateBook = '" + today + "'";

            }
            var json = await ex.getDataBFO(sql);
            foreach (var item in json)
            {
                _cldh = new BookModel2();
                _cldh.TimeBook = DateTime.Parse(item["TimeBook"].ToString());
                _cldh.DateBook = DateTime.Parse(item["DateBook"].ToString());
                _cldh.date = _cldh.DateBook.ToString("yyyy/MM/dd");
                _cldh.time = _cldh.TimeBook.ToString("hh:mm:ss");
                _cldh.EmpNo_ = item["EmpNo_"].ToString();
                _cldh.TrangThai = item["TrangThai"].ToString();
                menuList2.Add(_cldh);
            }
        }
        public static string today;
        private void dp_DateSelected(object sender, DateChangedEventArgs e)
        {
            var s = dp.Date;
            today = s.ToString("yyyy/MM/dd");
            // chuyển ngày int 
            var ap = dp.Date.ToString("MM");
            var app = dp.Date.ToString("dd");
            var bp = DateTime.Now.ToString("MM");
            var bpp = DateTime.Now.ToString("dd");
            int a = Int32.Parse(ap);
            int b = Int32.Parse(app);
            int c = Int32.Parse(bp);
            int d = Int32.Parse(bpp);
            if (a < c || b < d)
            {
                menuList2.Clear();
                LoadData(this.mabs_p, today);
                listtime.ItemsSource = null;
                listtime.ItemsSource = menuList2;

            }
            else if (a > c || b > d)
            {

                menuList2.Clear();
                LoadData(this.mabs_p, today);
                listtime.ItemsSource = null;
                listtime.ItemsSource = menuList2;
            }
            else if (a == c && b == d)
            {

                LoadData(this.mabs_p, today);
                listtime.ItemsSource = null;
                listtime.ItemsSource = menuList2;
            }

        }

    }
}