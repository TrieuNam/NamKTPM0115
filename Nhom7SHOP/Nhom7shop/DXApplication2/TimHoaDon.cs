using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using DXApplication2.Class;
namespace DXApplication2
{
    public partial class TimHoaDon : Form
    {
        public TimHoaDon()
        {
            InitializeComponent();
        }
        DataTable tblHDB;//Hoá đơn bán
        

        private void TimHoaDon_Load(object sender, EventArgs e)
        {
            ResetValues();
            DataGridView.DataSource = null;
        }

        private void ResetValues()
        {
            foreach (Control Ctl in this.Controls)
                if (Ctl is TextBox)
                    Ctl.Text = "";
            txtMaHoaDonBan.Focus();
        }

        private void bttTimKiem_Click(object sender, EventArgs e)
        {
            string sql;
            if ((txtMaHoaDonBan.Text == "") && (txtThang.Text == "") && (txtNam.Text == "") &&
               (txtMaNhanVien.Text == "") && (txtMaKhach.Text == "") &&
               (txtTongTien.Text == ""))
            {
                MessageBox.Show("Hãy nhập một điều kiện tìm kiếm!!!", "Yêu cầu ...", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }
            sql = "SELECT * FROM HoaDonBan WHERE 1=1";
            if (txtMaHoaDonBan.Text != "")
                sql = sql + " AND MaHDBan Like N'%" + txtMaHoaDonBan.Text + "%'";
            if (txtThang.Text != "")
                sql = sql + " AND MONTH(NgayBan) =" + txtThang.Text;
            if (txtNam.Text != "")
                sql = sql + " AND YEAR(NgayBan) =" + txtNam.Text;
            if (txtMaNhanVien.Text != "")
                sql = sql + " AND MaNhanVien Like N'%" + txtMaNhanVien.Text + "%'";
            if (txtMaKhach.Text != "")
                sql = sql + " AND MaKhach Like N'%" + txtMaKhach.Text + "%'";
            if (txtTongTien.Text != "")
                sql = sql + " AND TongTien <=" + txtTongTien.Text;
            tblHDB = ClassKN.GetDataToTable(sql);
            if (tblHDB.Rows.Count == 0)
            {
                MessageBox.Show("Không có bản ghi thỏa mãn điều kiện!!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            else
                MessageBox.Show("Có " + tblHDB.Rows.Count + " bản ghi thỏa mãn điều kiện!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
            DataGridView.DataSource = tblHDB;
            LoadDataGridView();

        }
        private void LoadDataGridView()
        {
            DataGridView.Columns[0].HeaderText = "Mã HĐB";
            DataGridView.Columns[1].HeaderText = "Mã nhân viên";
            DataGridView.Columns[2].HeaderText = "Ngày bán";
            DataGridView.Columns[3].HeaderText = "Mã khách";
            DataGridView.Columns[4].HeaderText = "Tổng tiền";
            DataGridView.Columns[0].Width = 150;
            DataGridView.Columns[1].Width = 100;
            DataGridView.Columns[2].Width = 80;
            DataGridView.Columns[3].Width = 80;
            DataGridView.Columns[4].Width = 80;
            DataGridView.AllowUserToAddRows = false;
            DataGridView.EditMode = DataGridViewEditMode.EditProgrammatically;
        }

        private void bttTimLai_Click(object sender, EventArgs e)
        {
            ResetValues();
            DataGridView.DataSource = null;
        }

        private void DataGridView_DoubleClick(object sender, EventArgs e)
        {
            string mahd;
            if (MessageBox.Show("Bạn có muốn hiển thị thông tin chi tiết?", "Xác nhận", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
            {
                mahd = DataGridView.CurrentRow.Cells["MaHDBan"].Value.ToString();
                HoaDon frm = new HoaDon();
                frm.txtMaHoaDonBan.Text = mahd;
                frm.StartPosition = FormStartPosition.CenterParent;
                frm.ShowDialog();
            }

        }

        private void bttDong_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void txtTongTien_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (((e.KeyChar >= '0') && (e.KeyChar <= '9')) || (Convert.ToInt32(e.KeyChar) == 8))
                e.Handled = false;
            else
                e.Handled = true;
        }

    }
}
