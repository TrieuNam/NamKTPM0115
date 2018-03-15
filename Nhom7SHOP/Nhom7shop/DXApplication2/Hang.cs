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
    public partial class Hang : Form
    {
        public Hang()
        {
            InitializeComponent();
        }

        DataTable tblH;//Bảng hàng
        private void LoadDataGridView()
        {
            string sql;
            sql = "SELECT * from Hang";
            tblH = ClassKN.GetDataToTable(sql);
            DataGridView.DataSource = tblH;
            DataGridView.Columns[0].HeaderText = "Mã hàng";
            DataGridView.Columns[1].HeaderText = "Tên hàng";
            DataGridView.Columns[2].HeaderText = "Chất liệu";
            DataGridView.Columns[3].HeaderText = "Số lượng";
            DataGridView.Columns[4].HeaderText = "Đơn giá nhập";
            DataGridView.Columns[5].HeaderText = "Đơn giá bán";
            DataGridView.Columns[6].HeaderText = "Ảnh";
            DataGridView.Columns[7].HeaderText = "Ghi chú";
            DataGridView.Columns[0].Width = 80;
            DataGridView.Columns[1].Width = 140;
            DataGridView.Columns[2].Width = 80;
            DataGridView.Columns[3].Width = 80;
            DataGridView.Columns[4].Width = 100;
            DataGridView.Columns[5].Width = 100;
            DataGridView.Columns[6].Width = 200;
            DataGridView.Columns[7].Width = 300;
            DataGridView.AllowUserToAddRows = false;
            DataGridView.EditMode = DataGridViewEditMode.EditProgrammatically;
        }

        private void Hang_Load(object sender, EventArgs e)
        {
            string sql;
            sql = "SELECT * from ChatLieu";
            txtMaHang.Enabled = false;

            bttLuu.Enabled = false;
            // btnBoqua.Enabled = false;
            bttHuy.Enabled = false;

            LoadDataGridView();
            ClassKN.FillCombo(sql, cboMaChatLieu, "MaChatLieu", "Size");
            cboMaChatLieu.SelectedIndex = -1;
            ResetValues();

        }
        private void ResetValues()
        {
            txtMaHang.Text = "";
            txtTenHang.Text = "";
            cboMaChatLieu.Text = "";
            txtSoLuong.Text = "0";
            txtDonGiaNhap.Text = "0";
            txtDonGiaBan.Text = "0";
            txtSoLuong.Enabled = true;
            txtDonGiaNhap.Enabled = false;
            txtDonGiaBan.Enabled = false;
            txtAnh.Text = "";
            picAnh.Image = null;
            txtGhiChu.Text = "";
        }
        private void bttThem_Click(object sender, EventArgs e)
        {
            bttSua.Enabled = false;
            bttXoa.Enabled = false;
            bttHuy.Enabled = true;
            bttLuu.Enabled = true;
            bttThem.Enabled = false;
            ResetValues();
            txtMaHang.Enabled = true;
            txtMaHang.Focus();
            txtSoLuong.Enabled = true;
            txtDonGiaNhap.Enabled = true;
            txtDonGiaBan.Enabled = true;
        }

        private void bttLuu_Click(object sender, EventArgs e)
        {
            string sql;//Lưu lệnh sql
            if (txtMaHang.Text.Trim().Length == 0)//Nếu chưa nhập mã hang
            {
                MessageBox.Show("Bạn phải nhập mã hàng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtMaHang.Focus();
                return;
            }
            if (txtTenHang.Text.Trim().Length == 0)//nếu chưa nhập tên hàng
            {
                MessageBox.Show("Bạn phải nhập tên hàng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtTenHang.Focus();
                return;
            }
            if (cboMaChatLieu.Text.Trim().Length == 0)// nếu chưa chọn mã chất liệu
            {
                MessageBox.Show("Bạn phải nhập chất liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                cboMaChatLieu.Focus();
                return;
            }
            if (txtAnh.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải chọn ảnh minh hoạ cho hàng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                bttOpen.Focus();
                return;
            }
            if (txtSoLuong.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập số lượng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtSoLuong.Focus();
                return;
            }
            if(txtDonGiaNhap.Text.Trim().Length ==0)
            {
                MessageBox.Show("Bạn phải nhập đơn giá nhập", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtDonGiaNhap.Focus();
                return;
            }
            if (txtDonGiaBan.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập đơn giá bán", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtDonGiaBan.Focus();
                return;
            }
            sql = "SELECT MaHang FROM Hang WHERE MaHang=N'" + txtMaHang.Text.Trim() + "'";
            if (ClassKN.CheckKey(sql))
            {
                MessageBox.Show("Mã hàng này đã tồn tại, bạn phải chọn mã hàng khác", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtMaHang.Focus();
                return;
            }
            sql = "INSERT INTO Hang(MaHang,TenHang,MaChatLieu,SoLuong,DonGiaNhap, DonGiaBan,Anh,GhiChu) VALUES(N'"
                + txtMaHang.Text.Trim() + "',N'" + txtTenHang.Text.Trim() +
                "',N'" + cboMaChatLieu.SelectedValue.ToString() +
                "'," + txtSoLuong.Text.Trim() + "," + txtDonGiaNhap.Text +
                "," + txtDonGiaBan.Text + ",'" + txtAnh.Text + "',N'" + txtGhiChu.Text.Trim() + "')";

            ClassKN.RunSQL(sql);
            LoadDataGridView();
            //ResetValues();
            bttXoa.Enabled = true;
            bttThem.Enabled = true;
            bttSua.Enabled = true;
            bttHuy.Enabled = false;
            bttLuu.Enabled = false;
            txtMaHang.Enabled = false;
        }

        private void bttSua_Click(object sender, EventArgs e)
        {
            
                string sql; //Lưu câu lệnh sql
                if (tblH.Rows.Count == 0)
                {
                    MessageBox.Show("Không còn dữ liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    return;
                }
                if (txtMaHang.Text == "")//nếu chưa chọn bản ghi nào
                {
                    MessageBox.Show("Bạn chưa chọn mã hàng nào", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    txtMaHang.Focus();
                    return;
                }
                if (txtTenHang.Text.Trim().Length == 0)//nếu chưa chọn bản ghi nào
                {
                    MessageBox.Show("Bạn phải nhập tên hàng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    txtTenHang.Focus();
                    return;
                }
                if (cboMaChatLieu.Text.Trim().Length == 0)//nếu chưa chọn bản ghi nào
                {
                    MessageBox.Show("Bạn phải nhập chất liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    cboMaChatLieu.Focus();
                    return;
                }

                if (txtAnh.Text.Trim().Length == 0)//nếu chưa chọn bản ghi nào
                {
                    MessageBox.Show("Bạn phải ảnh minh hoạ cho hàng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    txtAnh.Focus();
                    return;
                }
            if(txtSoLuong.Text.Trim().Length==0)
            {
                MessageBox.Show("Bạn phải nhập số lượng cho hàng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtAnh.Focus();
                return;
            }

                sql = "UPDATE Hang SET TenHang=N'" + txtTenHang.Text.Trim().ToString() +
             "',MaChatLieu=N'" + cboMaChatLieu.SelectedValue.ToString() +
             "',SoLuong=" + txtSoLuong.Text +
             ",Anh='" + txtAnh.Text + "',GhiChu=N'" + txtGhiChu.Text + "' WHERE MaHang=N'" + txtMaHang.Text + "'";
                ClassKN.RunSQL(sql);
                LoadDataGridView();
                ResetValues();
                bttHuy.Enabled = false;

        }

        private void bttXoa_Click(object sender, EventArgs e)
        {
            string sql;
            if (tblH.Rows.Count == 0)
            {
                MessageBox.Show("Không còn dữ liệu!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtMaHang.Text == "")
            {
                MessageBox.Show("Bạn chưa chọn Hàng nào", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (MessageBox.Show("Bạn có muốn xoá Hàng này không?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
            {
                sql = "DELETE Hang WHERE MaHang=N'" + txtMaHang.Text + "'";
                ClassKN.RunSqlDel(sql);
                LoadDataGridView();
                ResetValues();

            }
        }
        private void bttHuy_Click(object sender, EventArgs e)
        {
            ResetValues();
            bttXoa.Enabled = true;
            bttSua.Enabled = true;
            bttThem.Enabled = true;
            bttHuy.Enabled = false;
            bttLuu.Enabled = false;
            txtMaHang.Enabled = false;
        }

        private void bttOpen_Click(object sender, EventArgs e)
        {
            OpenFileDialog dlgOpen = new OpenFileDialog();
            dlgOpen.Filter = "Bitmap(*.bmp)|*.bmp|JPEG(*.jpg)|*.jpg|GIF(*.gif)|*.gif|All files(*.*)|*.*";
            dlgOpen.FilterIndex = 2;
            dlgOpen.Title = "Chọn ảnh minh hoạ cho sản phẩm";
            if (dlgOpen.ShowDialog() == DialogResult.OK)
            {
                picAnh.Image = Image.FromFile(dlgOpen.FileName);
                txtAnh.Text = dlgOpen.FileName;
            }
        }

        private void bttTimKiem_Click(object sender, EventArgs e)
        {
            string sql;
            if ((txtMaHang.Text == "") && (txtTenHang.Text == "") && (cboMaChatLieu.Text == ""))
            {
                MessageBox.Show("Bạn hãy nhập điều kiện tìm kiếm", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }
            sql = "SELECT * from Hang WHERE 1=1";
            if (txtMaHang.Text != "")
                sql += " AND MaHang LIKE N'%" + txtMaHang.Text + "%'";
            if (txtTenHang.Text != "")
                sql += " AND TenHang LIKE N'%" + txtTenHang.Text + "%'";
            if (cboMaChatLieu.Text != "")
                sql += " AND MaChatLieu LIKE N'%" + cboMaChatLieu.SelectedValue + "%'";
            tblH = ClassKN.GetDataToTable(sql);
            if (tblH.Rows.Count == 0)
                MessageBox.Show("Không có Hàng thoả mãn điều kiện tìm kiếm!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
            else MessageBox.Show("Có " + tblH.Rows.Count + "  bản ghi thoả mãn điều kiện!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
            DataGridView.DataSource = tblH;
            ResetValues();
        }

        private void bttHienThi_Click(object sender, EventArgs e)
        {
            string sql;
            sql = "SELECT MaHang, TenHang,MaChatLieu,SoLuong,DonGiaNhap,DonGiaBan,Anh,GhiChu FROM Hang";
            tblH = ClassKN.GetDataToTable(sql);
            DataGridView.DataSource = tblH;
        }

        private void bttThoat_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void DataGridView_Click(object sender, EventArgs e)
        {
            string machatlieu;
            string sql;
            if (bttThem.Enabled == false)
            {
                MessageBox.Show("Đang ở chế độ thêm mới!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtMaHang.Focus();
                return;
            }
            if (tblH.Rows.Count == 0)
            {
                MessageBox.Show("Không có dữ liệu!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            txtMaHang.Text = DataGridView.CurrentRow.Cells["MaHang"].Value.ToString();
            txtTenHang.Text = DataGridView.CurrentRow.Cells["TenHang"].Value.ToString();
            machatlieu = DataGridView.CurrentRow.Cells["MaChatLieu"].Value.ToString();
            sql = "SELECT Size FROM ChatLieu WHERE Machatlieu=N'" + machatlieu + "'";
            cboMaChatLieu.Text = ClassKN.GetFieldValues(sql);
            txtSoLuong.Text = DataGridView.CurrentRow.Cells["SoLuong"].Value.ToString();
            txtDonGiaNhap.Text = DataGridView.CurrentRow.Cells["DonGiaNhap"].Value.ToString();
            txtDonGiaBan.Text = DataGridView.CurrentRow.Cells["DonGiaBan"].Value.ToString();
            sql = "SELECT Anh FROM Hang WHERE MaHang=N'" + txtMaHang.Text + "'";
            txtAnh.Text = ClassKN.GetFieldValues(sql);
            picAnh.Image = Image.FromFile(txtAnh.Text);
            sql = "SELECT Ghichu FROM Hang WHERE MaHang = N'" + txtMaHang.Text + "'";
            txtGhiChu.Text = ClassKN.GetFieldValues(sql);
            bttSua.Enabled = true;
            bttXoa.Enabled = true;
            bttHuy.Enabled = true;
        }
    }


}

