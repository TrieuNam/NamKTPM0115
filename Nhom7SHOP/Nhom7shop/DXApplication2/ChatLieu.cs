using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient; //Sử dụng thư viện để làm việc SQL server
using DXApplication2.Class; //Sử dụng class kn.cs


namespace DXApplication2
{
    public partial class ChatLieu : Form
    {
        public ChatLieu()
        {
            InitializeComponent();
        }
        DataTable tblCL; //Chứa dữ liệu bảng Chất liệu
        private void ChatLieu_Load(object sender, EventArgs e)
        {
           
            txtMaChatLieu.Enabled = false;
            bttLuu.Enabled = false;
            bttHuy.Enabled = false;
            LoadDataGridView(); //Hiển thị bảng tblChatlieu
        }
   
//Trong đó, phương thức LoadDataGridView có tác dụng lấy dữ liệu từ bảng tblChatlieu đổ vào DataGridView
        private void LoadDataGridView()
        {
            string sql;
            sql = "SELECT MaChatLieu, Size FROM ChatLieu";
            tblCL = Class.ClassKN.GetDataToTable(sql); //Đọc dữ liệu từ bảng
            DataGridView.DataSource = tblCL; //Nguồn dữ liệu            
            DataGridView.Columns[0].HeaderText = "Mã chất liệu";
            DataGridView.Columns[1].HeaderText = "Size";
            DataGridView.Columns[0].Width = 100;
            DataGridView.Columns[1].Width = 300;
            DataGridView.AllowUserToAddRows = false; //Không cho người dùng thêm dữ liệu trực tiếp
            DataGridView.EditMode = DataGridViewEditMode.EditProgrammatically; //Không cho sửa dữ liệu trực tiếp
        }
       

        private void bttThem_Click(object sender, EventArgs e)
        {
            bttSua.Enabled = false;
            bttXoa.Enabled = false;
            bttHuy.Enabled = true;
            bttLuu.Enabled = true;
            bttThem.Enabled = true;
            bttThem.Enabled = false;
            ResetValue(); //Xoá trắng các textbox
            txtMaChatLieu.Enabled = true; //cho phép nhập mới
            txtMaChatLieu.Focus();
        }
        private void ResetValue()
        {
            txtMaChatLieu.Text = "";
            txtSize.Text = "";
        }
        private void bttXoa_Click(object sender, EventArgs e)
        {
            string sql;
            if (tblCL.Rows.Count == 0)
            {
                MessageBox.Show("Không còn dữ liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtMaChatLieu.Text == "") //nếu chưa chọn bản ghi nào
            {
                MessageBox.Show("Bạn chưa chọn bản ghi nào", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (MessageBox.Show("Bạn có muốn xoá chất liệu không?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
            {
                sql = "DELETE ChatLieu WHERE MaChatLieu=N'" + txtMaChatLieu.Text + "'";
               Class.ClassKN.RunSqlDel(sql);
                LoadDataGridView();
                ResetValue();
            }

        }

        private void bttSua_Click(object sender, EventArgs e)
        {
            string sql; //Lưu câu lệnh sql
            if (tblCL.Rows.Count == 0)
            {
                MessageBox.Show("Không còn dữ liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtMaChatLieu.Text == "") //nếu chưa chọn bản ghi nào
            {
                MessageBox.Show("Bạn chưa chọn bản ghi nào", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (txtSize.Text.Trim().Length == 0) //nếu chưa nhập tên chất liệu
            {
                MessageBox.Show("Bạn chưa nhập tên chất liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            sql = "UPDATE ChatLieu SET Size=N'" +
                txtSize.Text.ToString() +
                "' WHERE MaChatLieu=N'" + txtMaChatLieu.Text + "'";
            Class.ClassKN.RunSQL(sql);
            LoadDataGridView();
            ResetValue();

            bttHuy.Enabled = false;
        }

        private void bttLuu_Click(object sender, EventArgs e)
        {
            string sql; //Lưu lệnh sql
            if (txtMaChatLieu.Text.Trim().Length == 0) //Nếu chưa nhập mã chất liệu
            {
                MessageBox.Show("Bạn phải nhập mã chất liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtMaChatLieu.Focus();
                return;
            }
            if (txtSize.Text.Trim().Length == 0) //Nếu chưa nhập tên chất liệu
            {
                MessageBox.Show("Bạn phải nhập tên chất liệu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtSize.Focus();
                return;
            }
            sql = "Select MaChatLieu From ChatLieu where MaChatLieu=N'" + txtMaChatLieu.Text.Trim() + "'";
            if (Class.ClassKN.CheckKey(sql))
            {
                MessageBox.Show("Mã chất liệu này đã có, bạn phải nhập mã khác", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                txtMaChatLieu.Focus();
                return;
            }

            sql = "INSERT INTO ChatLieu VALUES(N'" +
                txtMaChatLieu.Text + "','" + txtSize.Text + "')";
            Class.ClassKN.RunSQL(sql); //Thực hiện câu lệnh sql
            LoadDataGridView(); //Nạp lại DataGridView
            ResetValue();
            bttXoa.Enabled = true;
            bttThem.Enabled = true;
            bttSua.Enabled = true;
            bttHuy.Enabled = false;
            bttLuu.Enabled = false;
            txtMaChatLieu.Enabled = false;
        }

        private void bttHuy_Click(object sender, EventArgs e)
        {

            ResetValue();
            bttHuy.Enabled = false;
            bttThem.Enabled = true;
            bttXoa.Enabled = true;
            bttSua.Enabled = true;
            bttLuu.Enabled = false;
            txtMaChatLieu.Enabled = false;
        }

        private void DataGridView_Click(object sender, EventArgs e)
        {
            if (bttThem.Enabled == false)
            {
                MessageBox.Show("Đang ở chế độ thêm mới!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtMaChatLieu.Focus();
                return;
            }
            if (tblCL.Rows.Count == 0) //Nếu không có dữ liệu
            {
                MessageBox.Show("Không có dữ liệu!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            txtMaChatLieu.Text = DataGridView.CurrentRow.Cells["MaChatLieu"].Value.ToString();
            txtSize.Text = DataGridView.CurrentRow.Cells["Size"].Value.ToString();
            bttSua.Enabled = true;
            bttXoa.Enabled = true;
            bttHuy.Enabled = true;
        }

        private void txtMaChatLieu_KeyUp(object sender, KeyEventArgs e)
        {
             if (e.KeyCode == Keys.Enter)
                SendKeys.Send("{TAB}");
        }
  

    }
}
