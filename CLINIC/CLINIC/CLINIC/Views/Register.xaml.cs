using CLINIC.Models;
using CLINIC.Service;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.ObjectModel;
using System.Text.RegularExpressions;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CLINIC.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Register : ContentPage
    {
        Models.ExecuteData ex = new Models.ExecuteData();
        ObservableCollection<GetDB> data = new ObservableCollection<GetDB>();
        string registerabc;
        string nametext;
        string addrtext;
        string phonetext;
        string account;
        string password;
        public NavigationService navigationService;
        public Register()
        {
            InitializeComponent();
            navigationService = new NavigationService();
        }
        public static string idname;
        public static string idadd;
        public static string idphone;
        public static string idacount;
        public static string idcirtificate;
        public static string idpass;
        public static string strRegex;
        public static string re_password;

        private async void Button_Clicked(object sender, EventArgs e)
        {

            idname = NameEntry.Text;
            idadd = AddressEntry.Text;
            idphone = PhoneEntry.Text;
            idacount = AccountEntry.Text;
            idcirtificate = CirtificateEntry.Text;
            idpass = PasswordEntry.Text;
            re_password = CPasswordEntry.Text;
            // xài không dc kham kh?o bên Bán Rau
            strRegex = @"^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}" +
                 @"\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\" +
                 @".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$";
            //
            if (string.IsNullOrEmpty(NameEntry.Text))
            {
                await DisplayAlert("ThÔng báo", "tên không du?c r?ng", "ok", "cancel");
            }
            if (string.IsNullOrEmpty(AddressEntry.Text))
            {
                await DisplayAlert("ThÔng báo", "Ð?a ch? không du?c r?ng", "ok", "cancel");
            }
            if (string.IsNullOrEmpty(PhoneEntry.Text))
            {
                await DisplayAlert("ThÔng báo", "S? di?n tho?i không du?c r?ng", "ok", "cancel");
            }
            if (string.IsNullOrEmpty(AccountEntry.Text))
            {
                await DisplayAlert("ThÔng báo", "Tài kho?n không du?c r?ng", "ok", "cancel");
            }
            if (string.IsNullOrEmpty(CirtificateEntry.Text))
            {
                await DisplayAlert("ThÔng báo", "CMND không du?c r?ng", "ok", "cancel");
            }
            if (string.IsNullOrEmpty(PasswordEntry.Text))
            {
                await DisplayAlert("ThÔng báo", "M?t kh?u không du?c r?ng", "ok", "cancel");
            }
            LoadData(idname, idadd, idphone, idacount, idcirtificate, idpass);
            bool b = true;
            Regex re = new Regex(strRegex);
            if (idacount != null && idpass != null && idacount != "" && idpass != "" && re_password == idpass)
            {
                while (b)
                {
                    //random cs_id

                    Random ra = new Random();

                    string cus1 = ra.Next(9).ToString();
                    string cus2 = ra.Next(9).ToString();
                    string cus3 = ra.Next(9).ToString();
                    string cus4 = ra.Next(9).ToString();
                    cs_id = "Cus-" + cus1 + cus2 + cus3 + cus4;
                    JArray json = await ex.getDataBFO(@"select [No_]  from [Customer] where [No_]='" + cs_id + "' ");
                    if (json.Count == 0)
                    {
                        bool mess;
                        mess = await ex.setDataBFO2(@"INSERT INTO [Customer] ([No_],[Name],[Address],[Phone No_],[AccountName],[Cirtificate No_],[Password],[Role]) VALUES ('" + cs_id + "','" + idname + "','" + idadd + "','" + idphone + "','" + idacount + "','" + idcirtificate + "','" + idpass + "','0')");
                        if (mess)
                        {
                            await DisplayAlert("Succes", "Create is succes", "OK");
                            //cs_id id customer   

                            navigationService.setLogin();
                        }
                    }
                    break;
                }
            }
        }

        public static string cs_id;
        public async void LoadData(string idname, string idadd, string idphone, string idacount, string idcirtificate, string idpass)
        {
            JArray json = await ex.getDataBFO(@"select [Cirtificate No_],Name,Address,[Phone No_],[AccountName],[Password] from [Customer] where [Cirtificate No_]='" + CirtificateEntry.Text + "' and Name ='" + NameEntry.Text + "' and Address='" + AddressEntry.Text + "'and [Phone No_]='" + PhoneEntry.Text + "' or [Cirtificate No_]='" + CirtificateEntry.Text + "' or Name ='" + NameEntry.Text + "' or Address='" + AddressEntry.Text + "'or [Phone No_]='" + PhoneEntry.Text + "'");

            foreach (var item in json)
            {

                registerabc = item["Cirtificate No_"].ToString();
                nametext = item["Name"].ToString();
                addrtext = item["Address"].ToString();
                phonetext = item["Phone No_"].ToString();
                account = item["AccountName"].ToString();
                password = item["Password"].ToString();
                try
                {// 1 ngu?i có th? tao nhi?u tài kho?n nhung CMND ph?i khác n?u mu?n t?o thì ph?i h?y tài kho?n  thì m?i t?o dc CMND dó

                    if (registerabc == CirtificateEntry.Text)
                    {
                        await DisplayAlert("thong bao", "L?i trùng ch?ng MND", "ok");
                        break;
                    }
                }
                catch (Exception ex)
                {
                    await DisplayAlert("Loi", "roi", "ok");
                }

            }
        }
    }
}