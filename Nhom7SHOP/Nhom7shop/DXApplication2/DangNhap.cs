using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Data;

namespace DXApplication2
{
    public partial class DangNhap : Form
    {
        public DangNhap()
        {
           
            InitializeComponent();
            
        }
        public SqlConnection LayDB()
        {
            return new SqlConnection(@"Data Source=DESKTOP-TIG4IS3\SQLEXPRESS;Initial Catalog=QuanLyShop;Integrated Security=True");       
        }
        public DataTable KTDB(string name, string pass)
        {

            string sql = "select * from DangNhap where TaiKhoan='" + name + "' and MatKhau='" + pass + "'";
            SqlConnection con = LayDB();
            SqlDataAdapter ad = new SqlDataAdapter(sql, con);
            DataTable dt = new DataTable();
           
            ad.Fill(dt);
            return dt;// ket qua cau truy van se tra ve bang , neu khop se co 1 dong du lieu, khong co eo hien
        }

        private void DangNhap_Load(object sender, EventArgs e)
        {
            try
            {             
                SqlConnection con = LayDB();
                this.Status.Text = "ket noi thanh cong";
            }
            catch
            {
                this.Status.Text = "ket noi that bai";
            }
        }

        private void bttThoat_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void bttDangNhap_Click(object sender, EventArgs e)
        {
            
            if (this.txtTaiKhoan.Text == "" || this.txtMatKhau.Text == "")
            {
                this.lbThongBao.ForeColor = Color.Red;
                this.lbThongBao.Text = "Vui lòng nhập tài khoản và mật khẩu";
                return;
            }
           
            DataTable dt = new DataTable();
            dt = KTDB(this.txtTaiKhoan.Text, this.txtMatKhau.Text);
            
            if (dt.Rows.Count > 0)
            {
                this.Hide();
                QuanLygiaydep QLShop = new QuanLygiaydep();
                QLShop.Show();
            }
            else
            {
                this.lbThongBao.ForeColor = Color.Red;
                this.lbThongBao.Text = "Tài khoản hoặc mật khẩu không đúng";
                this.txtTaiKhoan.Text=" ";
                this.txtMatKhau.Text="";  
            }

        }

        private void chkHienMatKha_CheckedChanged(object sender, EventArgs e)
        {
            if (chkHienMatKhau.Checked)
                txtMatKhau.UseSystemPasswordChar = false;
            else
                txtMatKhau.UseSystemPasswordChar = true;

        }

        
    }
}
