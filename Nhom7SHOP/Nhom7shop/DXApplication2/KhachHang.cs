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
    public partial class KhachHang : Form
    {
        public KhachHang()
        {
            InitializeComponent();
        }
        DataTable tblKH; //Bảng khách hàng
        private void KhachHang_Load(object sender, EventArgs e)
        {
            txtMaKhach.Enabled = false;
            bttLuu.Enabled = false;
            bttHuy.Enabled = false;
            LoadDataGridView();
        }
        private void LoadDataGridView()
        {
            string sql;
            sql = "SELECT * from Khach";
            tblKH = ClassKN.GetDataToTable(sql); //Lấy dữ liệu từ bảng
            DataGridView.DataSource = tblKH; //Hiển thị vào dataGridView
            DataGridView.Columns[0].HeaderText = "Mã khách";
            DataGridView.Columns[1].HeaderText = "Tên khách";
            DataGridView.Columns[2].HeaderText = "Địa chỉ";
            DataGridView.Columns[3].HeaderText = "Điện thoại";
            DataGridView.Columns[0].Width = 100;
            DataGridView.Columns[1].Width = 150;
            DataGridView.Columns[2].Width = 150;
            DataGridView.Columns[3].Width = 150;
            DataGridView.AllowUserToAddRows = false;
            DataGridView.EditMode = DataGridViewEditMode.EditProgrammatically;
        }

        private void DataGridView_Click(object sender, EventArgs e)
        {
            if (bttThem.Enabled == false)
            {
                MessageBox.Show("Đang ở chế độ thêm mới!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtMaKhach.Focus();
                return;
            }
            if (tblKH.Rows.Count == 0)
            {
                MessageBox.Show("Không có dữ liệu!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            txtMaKhach.Text = DataGridView.CurrentRow.Cells["Makhach"].Value.ToString();
            txtTenKhach.Text = DataGridView.CurrentRow.Cells["Tenkhach"].Value.ToString();
            txtDiaChi.Text = DataGridView.CurrentRow.Cells["Diachi"].Value.ToString();
            mskDienThoai.Text = DataGridView.CurrentRow.Cells["Dienthoai"].Value.ToString();
            bttSua.Enabled = true;
            bttXoa.Enabled = true;
            bttHuy.Enabled = true;
        }

        private void bttThem_Click(object sender, EventArgs e)
        {
            bttSua.Enabled = false;
            bttXoa.Enabled = false;
            bttHuy.Enabled = true;
            bttLuu.Enabled = true;
            bttThem.Enabled = false;
            ResetValues();
            txtMaKhach.Enabled = true;
            txtMaKhach.Focus();
        }
        private void ResetValues()
        {
            txtMaKhach.Text = "";
            txtTenKhach.Text = "";
            txtDiaChi.Text = "";
            mskDienThoai.Text = "";
        }

        private void bttLuu_Click(object sender, EventArgs e)
        {
            string sql;
            if (txtMaKhach.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập mã khách", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtMaKhach.Focus();
                return;
            }
            if (txtTenKhach.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập tên khách", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtTenKhach.Focus();
                return;
            }
            if (txtDiaChi.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập địa chỉ", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtDiaChi.Focus();
                return;
            }
            if (mskDienThoai.Text == "(  )    -")
            {
                MessageBox.Show("Bạn phải nhập điện thoại", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                mskDienThoai.Focus();
                return;
            }
            //Kiểm tra đã tồn tại mã khách chưa
            sql = "SELECT MaKhach FROM Khach WHERE MaKhach=N'" + txtMaKhach.Text.Trim() + "'";
            if (ClassKN.CheckKey(sql))
            {
                MessageBox.Show("Mã khách này đã tồn tại", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtMaKhach.Focus();
                return;
            }
            //Chèn thêm
            sql = "INSERT INTO Khach VALUES (N'" + txtMaKhach.Text.Trim() +
                "',N'" + txtTenKhach.Text.Trim() + "',N'" + txtDiaChi.Text.Trim() + "','" + mskDienThoai.Text + "')";
            ClassKN.RunSQL(sql);
            LoadDataGridView();
            ResetValues();

            bttXoa.Enabled = true;
            bttThem.Enabled = true;
            bttSua.Enabled = true;
            bttHuy.Enabled = false;
            bttLuu.Enabled = false;
            txtMaKhach.Enabled = false;
        }

        private void bttSua_Click(object sender, EventArgs e)
        {
            string sql;
            if (tblKH.Rows.Count == 0)
            {
                MessageBox.Show("Không còn dữ liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtMaKhach.Text == "")
            {
                MessageBox.Show("Bạn phải chọn mã khách hàng cần sửa", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtTenKhach.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập tên khách", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtTenKhach.Focus();
                return;
            }
            if (txtDiaChi.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập địa chỉ", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtDiaChi.Focus();
                return;
            }
            if (mskDienThoai.Text == "(  )    -")
            {
                MessageBox.Show("Bạn phải nhập điện thoại", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                mskDienThoai.Focus();
                return;
            }
            sql = "UPDATE Khach SET TenKhach=N'" + txtTenKhach.Text.Trim().ToString() + "',DiaChi=N'" +
                txtDiaChi.Text.Trim().ToString() + "',DienThoai='" + mskDienThoai.Text.ToString() +
                "' WHERE MaKhach=N'" + txtMaKhach.Text + "'";
            ClassKN.RunSQL(sql);
            LoadDataGridView();
            ResetValues();
            bttHuy.Enabled = false;
        }

        private void bttXoa_Click(object sender, EventArgs e)
        {
            string sql;
            if (tblKH.Rows.Count == 0)
            {
                MessageBox.Show("Không còn dữ liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtMaKhach.Text.Trim() == "")
            {
                MessageBox.Show("Bạn chưa chọn khách hàng nào", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (MessageBox.Show("Bạn có muốn xoá khách hàng này không?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
            {
                sql = "DELETE Khach WHERE MaKhach=N'" + txtMaKhach.Text + "'";
                ClassKN.RunSqlDel(sql);
                LoadDataGridView();
                ResetValues();
            }

        }

        private void bttHuy_Click(object sender, EventArgs e)
        {
            ResetValues();
            bttHuy.Enabled = false;
            bttThem.Enabled = true;
            bttXoa.Enabled = true;
            bttSua.Enabled = true;
            bttLuu.Enabled = false;
            txtMaKhach.Enabled = false;
        }

      

        private void txtMaKhach_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
                SendKeys.Send("{TAB}");
        }

    }
}
