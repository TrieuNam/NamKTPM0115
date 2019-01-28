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
	public partial class BookView2 : ContentPage
	{
		BookModel2 _cldh = new BookModel2();
		public List<BookModel2> menuList2 = new List<BookModel2>();
		ExecuteData ex = new ExecuteData();
        public static string datee;
        public static string EmpNo_1;
        string description_p, hoten_p, mabs_p, id_doctor_cli_p, id_doctor_fa_p;
		public static string id_cs_p, sex_cs_p, phone_cs_p, mail_cs_p, name_cus;
		public BookView2()
		{
			InitializeComponent();
			hoten_p = Views.ViewDoctor.name_doctorname_doctor;
			this.mabs_p = Views.ViewDoctor.id_doctor; //this.mabc la bien toan cuc
			id_doctor_cli_p = Views.ViewDoctor.id_doctor_cli;
			id_doctor_fa_p = Views.ViewDoctor.id_doctor_fa;
			id_cs_p = Views.Login.CCode;
			mail_cs_p = Views.Login.Mailc;
			name_cus = Views.Login.Name_cus;
            // 
			var today = dp.Date.ToString("yyyy/MM/dd");
			LoadData(mabs_p, today);
			listtime.ItemsSource = null;
			listtime.ItemsSource = menuList2;

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
			string tinnhan = "Đã Qua ngày,Bạn không được đặt";
			if (a < c || b < d)
			{
				var yes = DisplayAlert("Thông báo", tinnhan, "ok");

				Navigation.PushAsync(new BookView2());

			}
			else if (a > c || b > d)
			{
                //xoa va 
				menuList2.Clear();
				LoadData(this.mabs_p, today);
				listtime.ItemsSource = null;
				listtime.ItemsSource = menuList2;
			}
			else if (a == c && b == d)
			{
                //lam rong va load lai
                LoadData(this.mabs_p, today);
				listtime.ItemsSource = null;
				listtime.ItemsSource = menuList2;
			}

		}

		async Task LoadData(string mabs, string today)// hom nay
		{
            
			var id_dtor = Views.ViewDoctor.id_doctor;
			var json = await ex.getDataBFO(@"SELECT[BookNo_] ,[CusNo_] ,[NameCus] ,[Name] ,[Phone] as Phone_Cus ,[Adr] ,[DateBook],[TimeBook],[EmpNo_] ,[NameEmp] ,[Phone No_] as Phone_Emp ,[CliNo_],[NameCli],[SubCliNo_],[TimeShift],[TimeShift2],[NameTime],[TrangThai] FROM[BFOCLINIC].[dbo].[Book] where EmpNo_='" + id_dtor + "' and DateBook='" + today + "'");
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


	
		private async void listtime_ItemTapped(object sender, ItemTappedEventArgs e)
		{
            // lay du lieu cua list
			var varDate = e.Item as BookModel2;
			var varDay = dp.Date.ToString("yyyy/MM/dd");
			datee = varDate.time;
			EmpNo_1 = _cldh.EmpNo_.ToString();
			var varTime = varDate.time;
			var Thongbao = await DisplayAlert("thông báo", "Bạn muốn đặt đăng ký khám hay hủy đăng ký", "Hủy lịch", "Đăng ký");
			if (Thongbao)
			{
				getInfoCs(id_cs_p);
				string mess = "Bạn có hủy lịch hẹn  vào lúc:\n" +
							   varTime + "\nNgay: " + varDay;
				var varstatus = await DisplayAlert("Thông Báo", mess, "OK", "Cancel");
				if (varstatus)
				{
					string id_shift = await getIdShift(mabs_p, id_doctor_fa_p, varDay, varTime, id_doctor_cli_p);
					//insert description
					if (id_shift != null)
					{
						//get phone, and get name of customer
						string time_i = varDay + " " + varTime;
						var s = await SetNoBook(id_cs_p, id_shift, phone_cs_p);
						//dat lich thanh cong thong bao ve cho nguoi dung
						if (s)
						{
							//sent email test
							EmailService emais = new EmailService();
							emais.sentNoEmail(mail_cs_p, "Thông Báo DEC", name_cus, varDay, varTime, id_doctor_cli_p, id_doctor_fa_p);
							await DisplayAlert("Thông Báo", "Hủy lịch thành Công", "Ok");
							//load new list
							var var_date_t = dp.Date;
							var today = var_date_t.ToString("yyyy/MM/dd");
							LoadData(this.mabs_p, today);
						}
					}
				}
				else
				{
					await DisplayAlert("thong bao", "Cancel", "OK");
				}
			}
			else
			{
				getInfoCs(id_cs_p);
				string mess = "Bạn có muốn đặt lịch vào lúc :\n" +
							   varTime + "\nNgày: " + varDay;
				var varstatus = await DisplayAlert("Thông Báo", mess, "OK", "Cancel");
				if (varstatus)
				{
					string id_shift = await getIdShift(mabs_p, id_doctor_fa_p, varDay, varTime, id_doctor_cli_p);
					//insert description
					if (id_shift != null)
					{
						//get phone, and get name of customer
						string time_i = varDay + " " + varTime;
						var s = await setBook(id_cs_p, id_shift, phone_cs_p);
						//dat lich thanh cong thong bao ve cho nguoi dung
						if (s)
						{
							//sent email test
							EmailService emais = new EmailService();
							emais.sentEmail(mail_cs_p, "Thông Báo DEC", name_cus, varDay, varTime, id_doctor_cli_p, id_doctor_fa_p);
							await DisplayAlert("Thông Báo", "Đặt lịch thành Công", "Ok");
							//load new list
							var var_date_t = dp.Date;
							var today = var_date_t.ToString("yyyy/MM/dd");
							LoadData(this.mabs_p, today);
						}
					}
				}
				else
				{
					await DisplayAlert("thong bao", "Cancel", "OK");
				}
			}
		}
        // tim thong tin cus
		public async void getInfoCs(string id_cs)
		{
			string sql = @"SELECT[RowID]
                              ,[No_]
                              ,[Name]
                              ,[Phone No_]
                              ,[Date of Birth]
                              ,[Sex]
							  ,[E-Mail]
                          FROM [BFOCLINIC].[dbo].[Customer]
                          WHERE No_='" + id_cs_p + "'";
			var json = await ex.getDataBFO(sql);
			string name_cs = "";
			string sex_cs = "";
			foreach (var item in json)
			{
				sex_cs_p = item["Sex"].ToString();
				phone_cs_p = item["Phone No_"].ToString();
				mail_cs_p = item["E-Mail"].ToString();
				name_cus = item["Name"].ToString();

			}
		}

        // tinh phong truc cua emp
		public async Task<string> getIdShift(string id_em, string id_fa, string day, string time, string id_cli)
		{
			string time_day = day + " " + time;
			string sql = @"SELECT [ShiftNo_]
                          FROM [BFOCLINIC].[dbo].[SessionShift]
                          WHERE [EmpNo_]='" + id_em + "'" +
								"AND [FalNo_]='" + id_fa + "'" +
								"AND [DateShift]='" + day + "'" +
								"AND [TimeShift]='" + time_day + "'" +
								"AND [CliNo_]='" + id_cli + "'";
			var json = await ex.getDataBFO(sql);
			//lay ma lich truc
			string id_shift = "";
			foreach (var item in json)
			{
				id_shift = item["ShiftNo_"].ToString();
			}
			//kiem tra neu ma lich truc khong co
			//if (id_shift == "")
			//{
			//   await DisplayAlert("Thong Bao", "Chi dat truoc toi da 6 Ngay", "OK");
			//}
			return id_shift;
		}
        // huy dat lich
		public async Task<bool> SetNoBook(string CusNo_, string ShiftNo_, string Phone)
		{
			try
			{
				foreach (BookModel2 lay in menuList2)
				{
					if (lay.time == datee)
					{
						string sql = @"update Book set CusNo_='', ShiftNo_ ='" + ShiftNo_ + "',Phone='',TrangThai='' where Book.EmpNo_='" + EmpNo_1 + "' and  TimeBook='" + lay.TimeBook + "'";
						int result = await ex.setDataBFO(sql);
						break;
					}

				}


				return true;
			}
			catch (Exception ex)
			{
				await DisplayAlert("error", ex.ToString(), "OK");
				return false;
			}
		}
        // dat lich
		public async Task<bool> setBook(string CusNo_, string ShiftNo_, string Phone)
		{
			try
			{
				foreach (BookModel2 lay in menuList2)
				{
					if (lay.time == datee)
					{
						string sql = @"update Book set CusNo_='" + CusNo_ + "', ShiftNo_ ='" + ShiftNo_ + "',Phone='" + Phone + "',TrangThai='" + CusNo_ + " đã đăng ký' where Book.EmpNo_='" + EmpNo_1 + "' and  TimeBook='" + lay.TimeBook + "'";
						int result = await ex.setDataBFO(sql);
						break;
					}

				}


				return true;
			}
			catch (Exception ex)
			{
				await DisplayAlert("error", ex.ToString(), "OK");
				return false;
			}

		}
	}
}