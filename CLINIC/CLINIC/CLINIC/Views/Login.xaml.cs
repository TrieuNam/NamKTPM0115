using CLINIC.Models;
using CLINIC.Service;
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
    public partial class Login : ContentPage
    {
        ExecuteData ex = new ExecuteData();
        LoginModels loginabc = new LoginModels();
        public List<LoginModels> menuList2 = new List<LoginModels>();
        public static string CCode;
        public static string Mailc;
        public static string Name_cus;
        public static string EmPNo;
        public NavigationService navigationService;
        public Login()
        {
            InitializeComponent();
            navigationService = new NavigationService();
            // login_p = login;


        }
        async Task Lodate()
        {
            var json = await ex.getDataBFO(@"select [AccountName],[Password],[No_] ,[E-Mail]as Mail,[Name],Customer.Role as Roles  from [Customer] where [AccountName]='" + Username.Text + "' and [Password]='" + Password.Text + "' and [Customer].[Role]='0' ");//where [EmpNo_]='" + mabs.ToString() + "'and [DateBook] ='" + today + "'
            foreach (var item in json)
            {

                loginabc.AccountName = item["AccountName"].ToString();
                loginabc.Password = item["Password"].ToString();
                loginabc.Mail = item["Mail"].ToString();
                loginabc.CusNo_ = item["No_"].ToString();
                loginabc.Name = item["Name"].ToString();
                loginabc.Role = item["Roles"].ToString();
                menuList2.Add(loginabc);

            }
            var json2 = await ex.getDataBFO(@" select Employee.AccountName as AccountName ,Employee.Password as Password,Employee.EmpNo_ as EmpNO, Employee.[E-Mail] as MaiEmp, Employee.Name as EmPName,Employee.Role as Roles from [Employee] where [AccountName]='" + Username.Text + "' and [Password]='" + Password.Text + "'and Employee.[Role]='1'");
            foreach (var item in json2)
            {
                loginabc.AccountName = item["AccountName"].ToString();
                loginabc.Password = item["Password"].ToString();
                loginabc.Name = item["EmPName"].ToString();
                loginabc.Role = item["Roles"].ToString();
                loginabc.EmpNo_ = item["EmpNO"].ToString();

                menuList2.Add(loginabc);
            }

            foreach (LoginModels dc in menuList2)
            {
                try
                {
                    if (dc.AccountName == Username.Text && dc.Password == Password.Text && dc.Role == "0")
                    {
                        await App.PersonRepo.AddNewPersonAsync(dc.AccountName, dc.Password, dc.CusNo_, dc.EmpNo_, dc.Role);
                        CCode = dc.CusNo_.ToString();
                        Mailc = dc.Mail.ToString();
                        Name_cus = dc.Name.ToString();
                        navigationService.setLogin();

                    }
                    else if (dc.AccountName == Username.Text && dc.Password == Password.Text && dc.Role == "1")
                    {
                        await App.PersonRepo.AddNewPersonAsync(dc.AccountName, dc.Password, dc.CusNo_, dc.EmpNo_, dc.Role);
                        EmPNo = dc.EmpNo_.ToString();
                        navigationService.setLoginbs();

                    }
                    else
                    {
                        await DisplayAlert("thong bao", "Tài khoản hoặc mật khẩu không đúng ?", "ok");
                    }

                }
                catch (Exception ex)
                {
                    await DisplayAlert("Thông báo", ex.Message, "ok");
                }
            }
        }
        private async Task Login_Clicked(object sender, EventArgs e)
        {
             await Lodate();

            if (string.IsNullOrEmpty(Username.Text))
            {

                await DisplayAlert("Thông báo", "Tài khoản rỗng", "ok");
                return;
            }
            if (string.IsNullOrEmpty(Password.Text))
            {

                await DisplayAlert("Thông báo", "Mật khoản rỗng", "ok");
                return;
            }


        }
        private void Register_Clicked(object sender, EventArgs e)
        {
            Navigation.PushAsync(new Register());
        }

    }
}