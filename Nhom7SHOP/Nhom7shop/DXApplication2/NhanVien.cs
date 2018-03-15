using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data;
using System.Data.SqlClient;
using DXApplication2.Class;

namespace DXApplication2
{
    public partial class NhanVien : Form
    {
        public NhanVien()
        {
            InitializeComponent();
        }
        DataTable tblNV;//Lưu dữ liệu bảng nhân viên

        private void NhanVien_Load(object sender, EventArgs e)
        {
            txtMaNhanVien.Enabled = false;
            bttLuu.Enabled = false;
            bttHuy.Enabled = false;
            LoadDataGridView();
        }

        public void LoadDataGridView()
        {
            string sql;
            sql = "SELECT Manhanvien,Tennhanvien,Gioitinh,Diachi,Dienthoai,Ngaysinh FROm NhanVien";
            tblNV = ClassKN.GetDataToTable(sql); //lấy dữ liệu
            DataGridView.DataSource = tblNV;
            DataGridView.Columns[0].HeaderText = "Mã nhân viên";
            DataGridView.Columns[1].HeaderText = "Tên nhân viên";
            DataGridView.Columns[2].HeaderText = "Giới tính";
            DataGridView.Columns[3].HeaderText = "Địa chỉ";
            DataGridView.Columns[4].HeaderText = "Điện thoại";
            DataGridView.Columns[5].HeaderText = "Ngày sinh";
            DataGridView.Columns[0].Width = 190;
            DataGridView.Columns[1].Width = 190;
            DataGridView.Columns[2].Width = 190;
            DataGridView.Columns[3].Width = 310;
            DataGridView.Columns[4].Width = 190;
            DataGridView.Columns[5].Width = 190;
            DataGridView.AllowUserToAddRows = false;
            DataGridView.EditMode = DataGridViewEditMode.EditProgrammatically;
        }

        private void DataGridView_Click(object sender, EventArgs e)
        {
            if (bttThem.Enabled == false)
            {
                MessageBox.Show("Đang ở chế độ thêm mới!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtMaNhanVien.Focus();
                return;
            }
            if (tblNV.Rows.Count == 0)
            {
                MessageBox.Show("Không có dữ liệu!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            txtMaNhanVien.Text = DataGridView.CurrentRow.Cells["MaNhanVien"].Value.ToString();
            txtTenNhanVien.Text = DataGridView.CurrentRow.Cells["TenNhanVien"].Value.ToString();
            if (DataGridView.CurrentRow.Cells["GioiTinh"].Value.ToString() == "Nam") chkGioiTinh.Checked = true;
            else chkGioiTinh.Checked = false;
            txtDiaChi.Text = DataGridView.CurrentRow.Cells["DiaChi"].Value.ToString();
            mskDienThoai.Text = DataGridView.CurrentRow.Cells["DienThoai"].Value.ToString();
            mskNgaySinh.Text = DataGridView.CurrentRow.Cells["NgaySinh"].Value.ToString();
            bttSua.Enabled = true;
            bttXoa.Enabled = true;
            bttXoa.Enabled = true;

        }

        private void bttThem_Click(object sender, EventArgs e)
        {
            bttSua.Enabled = false;
            bttXoa.Enabled = false;
            bttHuy.Enabled = true;
            bttLuu.Enabled = true;
            bttThem.Enabled = false;
            ResetValues();
            txtMaNhanVien.Enabled = true;
            txtMaNhanVien.Focus();
        }
        private void ResetValues()
        {
            txtMaNhanVien.Text = "";
            txtTenNhanVien.Text = "";
            chkGioiTinh.Checked = false;
            txtDiaChi.Text = "";
            mskNgaySinh.Text = "";
            mskDienThoai.Text = "";
        }

        private void bttLuu_Click(object sender, EventArgs e)
        {
            string sql, gt;
            if (txtMaNhanVien.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập mã nhân viên", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                txtMaNhanVien.Focus();
                return;
            }
            if (txtTenNhanVien.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập tên nhân viên", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                txtTenNhanVien.Focus();
                return;
            }
            if (txtDiaChi.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập địa chỉ", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                txtDiaChi.Focus();
                return;
            }
            if (mskDienThoai.Text == "(   )     -")
            {
                MessageBox.Show("Bạn phải nhập điện thoại", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                mskDienThoai.Focus();
                return;
            }
            if (mskNgaySinh.Text == "  /  /")
            {
                MessageBox.Show("Bạn phải nhập ngày sinh", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                mskNgaySinh.Focus();
                return;
            }
          /*  if (!ClassKN.IsDate(mskNgaysinh.Text))
            {
                MessageBox.Show("Bạn phải nhập lại ngày sinh", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                // mskNgaysinh.Text = "";
                mskNgaysinh.Focus();
                return;
            }  */
            if (chkGioiTinh.Checked == true)
                gt = "Nam";
            else
                gt = "Nữ";
            sql = "SELECT MaNhanVien FROM NhanVien WHERE MaNhanVien=N'" + txtMaNhanVien.Text.Trim() + "'";
            if (ClassKN.CheckKey(sql))
            {
                MessageBox.Show("Mã nhân viên này đã có, bạn phải nhập mã khác", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                txtMaNhanVien.Focus();
                txtMaNhanVien.Text = "";
                return;
            }
            sql = "INSERT INTO NhanVien(MaNhanVien,TenNhanVien,GioiTinh, DiaChi,DienThoai, NgaySinh) VALUES (N'" + txtMaNhanVien.Text.Trim() + "',N'" + txtTenNhanVien.Text.Trim() + "',N'" + gt + "',N'" + txtDiaChi.Text.Trim() + "','" + mskDienThoai.Text + "','" + Convert.ToDateTime(mskNgaySinh.Text) + "')";
            ClassKN.RunSQL(sql);
            LoadDataGridView();
            ResetValues();
            bttXoa.Enabled = true;
            bttThem.Enabled = true;
            bttSua.Enabled = true;
            bttHuy.Enabled = false;
            bttLuu.Enabled = false;
            txtMaNhanVien.Enabled = false;

        }

        private void bttSua_Click(object sender, EventArgs e)
        {
            string sql, gt;
            if (tblNV.Rows.Count == 0)
            {
                MessageBox.Show("Không còn dữ liệu!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtMaNhanVien.Text == "")
            {
                MessageBox.Show("Bạn chưa chọn bản ghi nào", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtTenNhanVien.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập tên nhân viên", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                txtTenNhanVien.Focus();
                return;
            }
            if (txtDiaChi.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập địa chỉ", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                txtDiaChi.Focus();
                return;
            }
            if (mskDienThoai.Text == "(   )     -")
            {
                MessageBox.Show("Bạn phải nhập điện thoại", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                mskDienThoai.Focus();
                return;
            }
            if (mskNgaySinh.Text == "  /  /")
            {
                MessageBox.Show("Bạn phải nhập ngày sinh", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                mskNgaySinh.Focus();
                return;
            }
      /*      if (!ClassKN.IsDate(mskNgaysinh.Text))
            {
                MessageBox.Show("Bạn phải nhập lại ngày sinh", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                mskNgaysinh.Text = "";
                mskNgaysinh.Focus();
                return;
            }   */
            if (chkGioiTinh.Checked == true)
                gt = "Nam";
            else
                gt = "Nữ";
            sql = "UPDATE NhanVien SET  TenNhanVien=N'" + txtTenNhanVien.Text.Trim().ToString() +
                    "',DiaChi=N'" + txtDiaChi.Text.Trim().ToString() +
                    "',DienThoai='" + mskDienThoai.Text.ToString() + "',Gioitinh=N'" + gt +
                    "',NgaySinh='" + Convert.ToDateTime(mskNgaySinh.Text) +
                    "' WHERE MaNhanVien=N'" + txtMaNhanVien.Text + "'";
            ClassKN.RunSQL(sql);
            LoadDataGridView();
            ResetValues();
        }

        private void bttXoa_Click(object sender, EventArgs e)
        {
            string sql;
            if (tblNV.Rows.Count == 0)
            {
                MessageBox.Show("Không còn dữ liệu!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtMaNhanVien.Text == "")
            {
                MessageBox.Show("Bạn chưa chọn nhân viên nào", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (MessageBox.Show("Bạn có muốn xóa nhân viên không?", "Thông báo", MessageBoxButtons.OKCancel, MessageBoxIcon.Question) == DialogResult.OK)
            {
                sql = "DELETE NhanVien WHERE MaNhanVien=N'" + txtMaNhanVien.Text + "'";
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
            txtMaNhanVien.Enabled = false;

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void txtMaNhanVien_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
                SendKeys.Send("{TAB}");
        }
    }
}
