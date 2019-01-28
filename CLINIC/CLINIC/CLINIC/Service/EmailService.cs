using CLINIC.Models;
using System;
using System.Net.Mail;
using System.Collections.Generic;
using System.Text;

namespace CLINIC.Service
{
   public class EmailService
    {
		ExecuteData ex = new ExecuteData();
		string name_cli_p, name_fal_p, address_cli_p;
		public async void sentEmail(string toEmail, string subject, string name_cs
									, string day, string time, string id_cli
									, string id_fal)
		{
			//gan thong tin vao bien cuc bo
			getInfoCs(id_cli, id_fal);
			try
			{
				string username = "dinhit123@gmail.com";
				string password = "Dinhvandai123";
				System.Net.NetworkCredential nc = new
				System.Net.NetworkCredential(username, password);
				MailMessage MailMessage = new MailMessage();

				MailMessage.From = new System.Net.Mail.MailAddress("dinhit123@gmail.com");
				MailMessage.To.Add(toEmail);
				//subject email
				MailMessage.Subject = subject;
				MailMessage.IsBodyHtml = true;


				//read file
				//var assembly = typeof(App).GetTypeInfo().Assembly;
				//Stream stream = assembly.GetManifestResourceStream("CLINIC.Droid.index.txt");
				//write stream
				//string text = "";
				//using (var reader = new System.IO.StreamReader(stream))
				//{
				//    text = reader.ReadToEnd();
				//}
				//string html = text;
				//MailMessage.Body = html;
				MailMessage.Body = @"<html>
                      <body> 
                      <p>Chào " + name_cs + @",</p> 
                      <p>Bạn đã đặt lịch hẹn tại phong khám " + name_cli_p + @"</p> 
                      <p>Chuyên khoa " + name_fal_p + @"</p> 
                      <p>Vào lúc " + time + @"</p> 
                      <p>Ngày " + day + @"</p> 
                      <p>Trân Trọng</p> 
                      </body> 
                      </html> 
                     ";
				SmtpClient SmtpClient = new SmtpClient("smtp.gmail.com");

				SmtpClient.EnableSsl = true;
				SmtpClient.Credentials = nc;
				SmtpClient.Port = 587;
				SmtpClient.Send(MailMessage);
			}
			catch (Exception ex)
			{
				Console.WriteLine(ex.ToString());
			}
		}
		//sent hủy đăng ký
		public async void sentNoEmail(string toEmail, string subject, string name_cs
									, string day, string time, string id_cli
									, string id_fal)
		{
			//gan thong tin vao bien cuc bo
			getInfoCs(id_cli, id_fal);
			try
			{
				string username = "dinhit123@gmail.com";
				string password = "Dinhvandai123";
				System.Net.NetworkCredential nc = new
				System.Net.NetworkCredential(username, password);
				MailMessage MailMessage = new MailMessage();

				MailMessage.From = new System.Net.Mail.MailAddress("dinhit123@gmail.com");
				MailMessage.To.Add(toEmail);
				//subject email
				MailMessage.Subject = subject;
				MailMessage.IsBodyHtml = true;


				//read file
				//var assembly = typeof(App).GetTypeInfo().Assembly;
				//Stream stream = assembly.GetManifestResourceStream("CLINIC.Droid.index.txt");
				//write stream
				//string text = "";
				//using (var reader = new System.IO.StreamReader(stream))
				//{
				//    text = reader.ReadToEnd();
				//}
				//string html = text;
				//MailMessage.Body = html;
				MailMessage.Body = @"<html>
                      <body> 
                      <p>Chào " + name_cs + @",</p> 
                      <p>Bạn đã hủy lịch hẹn tại phong khám " + name_cli_p + @"</p> 
                      <p>Chuyên khoa " + name_fal_p + @"</p> 
                      <p>Vào lúc " + time + @"</p> 
                      <p>Ngày " + day + @"</p> 
                      <p>Trân Trọng</p> 
                      </body> 
                      </html> 
                     ";
				System.Net.Mail.SmtpClient SmtpClient = new System.Net.Mail.SmtpClient("smtp.gmail.com");

				SmtpClient.EnableSsl = true;
				SmtpClient.Credentials = nc;
				SmtpClient.Port = 587;
				SmtpClient.Send(MailMessage);
			}
			catch (Exception ex)
			{
				Console.WriteLine(ex.ToString());
			}
		}
		//lay thong tin book
		public async void getInfoCs(string id_cli, string id_fal)
		{
			string sql = @"SELECT Faculty.Name, Clinic.Name, Clinic.Adr
                                    FROM Faculty,Clinic
                                    WHERE Faculty.FalNo_='" + id_fal + @"' and Clinic.CliNo_='" + id_cli + @"'";
			try
			{
				var json = await ex.getDataBFO(sql);
				foreach (var item in json)
				{
					name_fal_p = item["Name"].ToString();
					name_cli_p = item["Name1"].ToString();
					address_cli_p = item["Adr"].ToString();

				}
			}
			catch (Exception ex)
			{
				Console.WriteLine("eamilservice error" + ex.ToString());
			}
		}
	}
}
