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
using COMExcel = Microsoft.Office.Interop.Excel;
namespace DXApplication2
{
    public partial class HoaDon : Form
    {
        public HoaDon()
        {
            InitializeComponent();
        }

        DataTable tblCTHDB;//Bảng chi tiết hoá đơn bán


        private void groupBox1_Enter(object sender, EventArgs e)
        {
           
        }
        private void LoadDataGridView()
        {
            string sql;
            sql = "SELECT a.MaHang, b.TenHang, a.SoLuong, b.DonGiaBan, a.GiamGia,a.ThanhTien FROM CTHD AS a, Hang AS b WHERE a.MaHDBan = N'" + txtMaHoaDonBan.Text + "' AND a.MaHang=b.MaHang";
            tblCTHDB = ClassKN.GetDataToTable(sql);
            DataGridView.DataSource = tblCTHDB;
            DataGridView.Columns[0].HeaderText = "Mã hàng";
            DataGridView.Columns[1].HeaderText = "Tên hàng";
            DataGridView.Columns[2].HeaderText = "Số lượng";
            DataGridView.Columns[3].HeaderText = "Đơn giá";
            DataGridView.Columns[4].HeaderText = "Giảm giá %";
            DataGridView.Columns[5].HeaderText = "Thành tiền";
            DataGridView.Columns[0].Width = 170;
            DataGridView.Columns[1].Width = 100;
            DataGridView.Columns[2].Width = 170;
            DataGridView.Columns[3].Width = 170;
            DataGridView.Columns[4].Width = 170;
            DataGridView.Columns[5].Width = 170;
            DataGridView.AllowUserToAddRows = false;
            DataGridView.EditMode = DataGridViewEditMode.EditProgrammatically;
        }

        private void LoadInForHoadon()
        {
            string str;
            str = "SELECT NgayBan FROM HoaDonBan WHERE MaHDBan = N'" + txtMaHoaDonBan.Text + "'";
            txtNgayBan.Text = ClassKN.ConvertDateTime(ClassKN.GetFieldValues(str));
            str = "SELECT MaNhanVien FROM HoaDonBan WHERE MaHDBan = N'" + txtMaHoaDonBan.Text + "'";
            cboMaNhanVien.Text = ClassKN.GetFieldValues(str);
            str = "SELECT MaKhach FROM HoaDonBan WHERE MaHDBan = N'" + txtMaHoaDonBan.Text + "'";
            cboMaKhach.Text = ClassKN.GetFieldValues(str);
            str = "SELECT TongTien FROM HoaDonBan WHERE MaHDBan = N'" + txtMaHoaDonBan.Text + "'";
            txtTongTien.Text = ClassKN.GetFieldValues(str);
            lbBangChu.Text = "Bằng chữ: " + ClassKN.ChuyenSoSangChu(txtTongTien.Text);
        }

        private void bttThemHoaDon_Click(object sender, EventArgs e)
        {
            bttHuyHoaDon.Enabled = false;
            bttLuuHoaDon.Enabled = true;
            bttInHoaDon.Enabled = false;
            bttThemHoaDon.Enabled = false;
            ResetValues();
            txtMaHoaDonBan.Text = ClassKN.CreateKey("HDB");
            LoadDataGridView();
        }
        private void ResetValues()
        {
            txtMaHoaDonBan.Text = "";
            txtNgayBan.Text = DateTime.Now.ToShortDateString();
            cboMaNhanVien.Text = "";
            cboMaKhach.Text = "";
            txtTongTien.Text = "0";
            lbBangChu.Text = "Bằng chữ: ";
            cboMaHang.Text = "";
            txtSoLuong.Text = "";
            txtGiamGia.Text = "0";
            txtThanhTien.Text = "0";
        }

        private void bttLuuHoaDon_Click(object sender, EventArgs e)
        {
            string sql;
            double sl, SLcon, tong, Tongmoi;
            sql = "SELECT MaHDBan FROM HoaDonBan WHERE MaHDBan=N'" + txtMaHoaDonBan.Text + "'";
            if (!ClassKN.CheckKey(sql))
            {
                // Mã hóa đơn chưa có, tiến hành lưu các thông tin chung
                // Mã HDBan được sinh tự động do đó không có trường hợp trùng khóa
                if (txtNgayBan.Text.Length == 0)
                {
                    MessageBox.Show("Bạn phải nhập ngày bán", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    txtNgayBan.Focus();
                    return;
                }
                if (cboMaNhanVien.Text.Length == 0)
                {
                    MessageBox.Show("Bạn phải nhập nhân viên", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    cboMaNhanVien.Focus();
                    return;
                }
                if (cboMaKhach.Text.Length == 0)
                {
                    MessageBox.Show("Bạn phải nhập khách hàng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    cboMaKhach.Focus();
                    return;
                }
                sql = "INSERT INTO HoaDonBan(MaHDBan,NgayBan,MaNhanVien,MaKhach,TongTien) VALUES (N'" + txtMaHoaDonBan.Text.Trim() + "','" +
                        ClassKN.ConvertDateTime(txtNgayBan.Text.Trim()) + "',N'" + cboMaNhanVien.SelectedValue + "',N'" +
                        cboMaKhach.SelectedValue + "'," + txtTongTien.Text + ")";
                ClassKN.RunSQL(sql);
            }
            // Lưu thông tin của các mặt hàng
            if (cboMaHang.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập mã hàng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                cboMaHang.Focus();
                return;
            }
            if ((txtSoLuong.Text.Trim().Length == 0) || (txtSoLuong.Text == "0"))
            {
                MessageBox.Show("Bạn phải nhập số lượng", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtSoLuong.Text = "";
                txtSoLuong.Focus();
                return;
            }
            if (txtGiamGia.Text.Trim().Length == 0)
            {
                MessageBox.Show("Bạn phải nhập giảm giá", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtGiamGia.Focus();
                return;
            }
            sql = "SELECT MaHang FROM CTHD WHERE MaHang=N'" + cboMaHang.SelectedValue + "' AND MaHDBan = N'" + txtMaHoaDonBan.Text.Trim() + "'";
            if (ClassKN.CheckKey(sql))
            {
                MessageBox.Show("Mã hàng này đã có, bạn phải nhập mã khác", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                ResetValuesHang();
                cboMaHang.Focus();
                return;
            }
            // Kiểm tra xem số lượng hàng trong kho còn đủ để cung cấp không?
            sl = Convert.ToDouble(ClassKN.GetFieldValues("SELECT SoLuong FROM Hang WHERE MaHang = N'" + cboMaHang.SelectedValue + "'"));
            if (Convert.ToDouble(txtSoLuong.Text) > sl)
            {
                MessageBox.Show("Số lượng mặt hàng này chỉ còn " + sl, "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                txtSoLuong.Text = "";
                txtSoLuong.Focus();
                return;
            }
            sql = "INSERT INTO CTHD(MaHDBan,MaHang,SoLuong,DonGia, GiamGia,ThanhTien) VALUES(N'" + txtMaHoaDonBan.Text.Trim() + "',N'" + cboMaHang.SelectedValue + "'," + txtSoLuong.Text + "," + txtDonGiaBan.Text + "," + txtGiamGia.Text + "," + txtThanhTien.Text + ")";
            ClassKN.RunSQL(sql);
            LoadDataGridView();
            // Cập nhật lại số lượng của mặt hàng vào bảng tblHang
            SLcon = sl - Convert.ToDouble(txtSoLuong.Text);
            sql = "UPDATE Hang SET Soluong =" + SLcon + " WHERE MaHang= N'" + cboMaHang.SelectedValue + "'";
            ClassKN.RunSQL(sql);
            // Cập nhật lại tổng tiền cho hóa đơn bán
            tong = Convert.ToDouble(ClassKN.GetFieldValues("SELECT TongTien FROM HoaDonBan WHERE MaHDBan = N'" + txtMaHoaDonBan.Text + "'"));
            Tongmoi = tong + Convert.ToDouble(txtThanhTien.Text);
            sql = "UPDATE HoaDonBan SET TongTien =" + Tongmoi + " WHERE MaHDBan = N'" + txtMaHoaDonBan.Text + "'";
            ClassKN.RunSQL(sql);
            txtTongTien.Text = Tongmoi.ToString();
            lbBangChu.Text = "Bằng chữ: " + ClassKN.ChuyenSoSangChu(Tongmoi.ToString());
            ResetValuesHang();
            bttHuyHoaDon.Enabled = true;
            bttThemHoaDon.Enabled = true;
            bttInHoaDon.Enabled = true;

        }
        private void ResetValuesHang()
        {
            cboMaHang.Text = "";
            txtSoLuong.Text = "";
            txtGiamGia.Text = "0";
            txtThanhTien.Text = "0";
        }

        private void bttHuyHoaDon_Click(object sender, EventArgs e)
        {
            double sl, slcon, slxoa;
            if (MessageBox.Show("Bạn có chắc chắn muốn hủy hóa đơn không?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
            {
                string sql = "SELECT MaHang,SoLuong FROM CTHD WHERE MaHDBan = N'" + txtMaHoaDonBan.Text + "'";
                DataTable tblHang = ClassKN.GetDataToTable(sql);
                for (int hang = 0; hang <= tblHang.Rows.Count - 1; hang++)
                {
                    // Cập nhật lại số lượng cho các mặt hàng
                    sl = Convert.ToDouble(ClassKN.GetFieldValues("SELECT SoLuong FROM Hang WHERE MaHang = N'" + tblHang.Rows[hang][0].ToString() + "'"));
                    slxoa = Convert.ToDouble(tblHang.Rows[hang][1].ToString());
                    slcon = sl + slxoa;
                    sql = "UPDATE Hang SET SoLuong =" + slcon + " WHERE MaHang= N'" + tblHang.Rows[hang][0].ToString() + "'";
                    ClassKN.RunSQL(sql);
                }

                //Xóa chi tiết hóa đơn
                sql = "DELETE CTHD WHERE MaHDBan=N'" + txtMaHoaDonBan.Text + "'";
                ClassKN.RunSqlDel(sql);

                //Xóa hóa đơn
                sql = "DELETE HoaDonBan WHERE MaHDBan=N'" + txtMaHoaDonBan.Text + "'";
                ClassKN.RunSqlDel(sql);
                ResetValues();
                LoadDataGridView();
                bttHuyHoaDon.Enabled = false;
                bttInHoaDon.Enabled = false;
            }
        }     

        private void bttInHoaDon_Click(object sender, EventArgs e)
        {
             // Khởi động chương trình Excel
            COMExcel.Application exApp = new COMExcel.Application();
            COMExcel.Workbook exBook; //Trong 1 chương trình Excel có nhiều Workbook
            COMExcel.Worksheet exSheet; //Trong 1 Workbook có nhiều Worksheet
            COMExcel.Range exRange; 
            string sql;
            int hang=0, cot=0;
            DataTable  tblThongtinHD, tblThongtinHang;
            exBook = exApp.Workbooks.Add(COMExcel.XlWBATemplate.xlWBATWorksheet);
            exSheet = exBook.Worksheets[1];
            // Định dạng chung
            exRange = exSheet.Cells[1, 1];
            exRange.Range["A1:Z300"].Font.Name = "Times new roman"; //Font chữ
            exRange.Range["A1:B3"].Font.Size = 10;            
            exRange.Range["A1:B3"].Font.Bold = true;
            exRange.Range["A1:B3"].Font.ColorIndex = 5; //Màu xanh da trời
            exRange.Range["A1:A1"].ColumnWidth = 7;
            exRange.Range["B1:B1"].ColumnWidth = 15;
            exRange.Range["A1:B1"].MergeCells = true;
            exRange.Range["A1:B1"].HorizontalAlignment = COMExcel.XlHAlign.xlHAlignCenter;
            exRange.Range["A1:B1"].Value = "Shop Triệu Nam.";
            exRange.Range["A2:B2"].MergeCells = true;
            exRange.Range["A2:B2"].HorizontalAlignment = COMExcel.XlHAlign.xlHAlignCenter;
            exRange.Range["A2:B2"].Value = "Ctuet";
            exRange.Range["A3:B3"].MergeCells = true;
            exRange.Range["A3:B3"].HorizontalAlignment = COMExcel.XlHAlign.xlHAlignCenter;
            exRange.Range["A3:B3"].Value = "Điện thoại: 0987121831";
            exRange.Range["C2:E2"].Font.Size = 16;
            exRange.Range["C2:E2"].Font.Bold = true;
            exRange.Range["C2:E2"].Font.ColorIndex = 3; //Màu đỏ
            exRange.Range["C2:E2"].MergeCells = true;
            exRange.Range["C2:E2"].HorizontalAlignment = COMExcel.XlHAlign.xlHAlignCenter;
            exRange.Range["C2:E2"].Value = "HÓA ĐƠN BÁN";
            // Biểu diễn thông tin chung của hóa đơn bán
            sql = "SELECT a.MaHDBan, a.NgayBan, a.TongTien, b.TenKhach, b.DiaChi, b.DienThoai, c.TenNhanVien FROM HoaDonBan AS a, Khach AS b, NhanVien AS c WHERE a.MaHDBan = N'" + txtMaHoaDonBan.Text + "' AND a.MaKhach = b.MaKhach AND a.MaNhanVien = c.MaNhanVien";
            tblThongtinHD = ClassKN.GetDataToTable(sql);
            exRange.Range["B6:C9"].Font.Size = 12;
            exRange.Range["B6:B6"].Value = "Mã hóa đơn:";
            exRange.Range["C6:E6"].MergeCells = true;
            exRange.Range["C6:E6"].Value = tblThongtinHD.Rows[0][0].ToString();
            exRange.Range["B7:B7"].Value = "Khách hàng:";
            exRange.Range["C7:E7"].MergeCells = true;
            exRange.Range["C7:E7"].Value = tblThongtinHD.Rows[0][3].ToString();
            exRange.Range["B8:B8"].Value = "Địa chỉ:";
            exRange.Range["C8:E8"].MergeCells = true;
            exRange.Range["C8:E8"].Value = tblThongtinHD.Rows[0][4].ToString();
            exRange.Range["B9:B9"].Value = "Điện thoại:";
            exRange.Range["C9:E9"].MergeCells = true;
            exRange.Range["C9:E9"].Value = tblThongtinHD.Rows[0][5].ToString();
            //Lấy thông tin các mặt hàng
            sql = "SELECT b.TenHang, a.SoLuong, b.DonGiaBan, a.GiamGia, a.ThanhTien " +
                  "FROM CTHD AS a , Hang AS b WHERE a.MaHDBan = N'" + 
                  txtMaHoaDonBan.Text + "' AND a.Mahang = b.Mahang";
            tblThongtinHang = ClassKN.GetDataToTable(sql);
            //Tạo dòng tiêu đề bảng
            exRange.Range["A11:F11"].Font.Bold = true;
            exRange.Range["A11:F11"].HorizontalAlignment = COMExcel.XlHAlign.xlHAlignCenter;
            exRange.Range["C11:F11"].ColumnWidth = 12;
            exRange.Range["A11:A11"].Value = "STT";
            exRange.Range["B11:B11"].Value = "Tên hàng";
            exRange.Range["C11:C11"].Value = "Số lượng";
            exRange.Range["D11:D11"].Value = "Đơn giá";
            exRange.Range["E11:E11"].Value = "Giảm giá";
            exRange.Range["F11:F11"].Value = "Thành tiền";
            for (hang = 0 ; hang < tblThongtinHang.Rows.Count; hang ++)
            {
                //Điền số thứ tự vào cột 1 từ dòng 12
                exSheet.Cells[1][hang + 12] = hang + 1;
                for (cot = 0; cot < tblThongtinHang.Columns.Count; cot++)
                //Điền thông tin hàng từ cột thứ 2, dòng 12
                {                    
                    exSheet.Cells[cot + 2][hang + 12] = tblThongtinHang.Rows[hang][cot].ToString();                    
                    if (cot == 3) exSheet.Cells[cot + 2][hang + 12] = tblThongtinHang.Rows[hang][cot].ToString() + "%";
                }
            }
            exRange = exSheet.Cells[cot][hang + 14];
            exRange.Font.Bold = true;
            exRange.Value2 = "Tổng tiền:";
            exRange = exSheet.Cells[cot + 1][hang + 14];
            exRange.Font.Bold = true;
            exRange.Value2 = tblThongtinHD.Rows[0][2].ToString();
            exRange = exSheet.Cells[1][hang + 15]; //Ô A1 
            exRange.Range["A1:F1"].MergeCells = true;
            exRange.Range["A1:F1"].Font.Bold = true;
            exRange.Range["A1:F1"].Font.Italic = true;
            exRange.Range["A1:F1"].HorizontalAlignment = COMExcel.XlHAlign.xlHAlignRight;
            exRange.Range["A1:F1"].Value = "Bằng chữ: " + ClassKN.ChuyenSoSangChu(tblThongtinHD.Rows[0][2].ToString ());
            exRange = exSheet.Cells[4][hang + 17]; //Ô A1 
            exRange.Range["A1:C1"].MergeCells = true;
            exRange.Range["A1:C1"].Font.Italic = true;
            exRange.Range["A1:C1"].HorizontalAlignment = COMExcel.XlHAlign.xlHAlignCenter;
            DateTime d = Convert.ToDateTime(tblThongtinHD.Rows[0][1]);
            exRange.Range["A1:C1"].Value = "Hà Nội, ngày " + d.Day + " tháng " + d.Month + " năm " + d.Year;
            exRange.Range["A2:C2"].MergeCells = true;
            exRange.Range["A2:C2"].Font.Italic = true;
            exRange.Range["A2:C2"].HorizontalAlignment = COMExcel.XlHAlign.xlHAlignCenter;
            exRange.Range["A2:C2"].Value = "Nhân viên bán hàng";
            exRange.Range["A6:C6"].MergeCells = true;
            exRange.Range["A6:C6"].Font.Italic = true;
            exRange.Range["A6:C6"].HorizontalAlignment = COMExcel.XlHAlign.xlHAlignCenter;
            exRange.Range["A6:C6"].Value = tblThongtinHD.Rows[0][6];
            exSheet.Name = "Hóa đơn nhập";
            exApp.Visible = true;
        }

        private void bttTimKiem_Click(object sender, EventArgs e)
        {
             if (cboMaHoaDonBan.Text == "")
            {
                MessageBox.Show("Bạn phải chọn một mã hóa đơn để tìm", "Thông báo",MessageBoxButtons.OK, MessageBoxIcon.Information);
                cboMaHoaDonBan.Focus();
                return;
            }
            txtMaHoaDonBan.Text = cboMaHoaDonBan.Text;
            LoadInForHoadon();
            LoadDataGridView();
            bttHuyHoaDon.Enabled = true;
            bttLuuHoaDon.Enabled = true;
            bttInHoaDon.Enabled = true;
            cboMaHoaDonBan.SelectedIndex = -1;

        }

        private void HoaDon_FormClosing(object sender, FormClosingEventArgs e)
        {
            //Xóa dữ liệu trong các điều khiển trước khi đóng Form
            ResetValues();
        }

        private void bttDong_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void DataGridView_DoubleClick(object sender, EventArgs e)
        {
            string mahangxoa, sql;
            Double thanhtienxoa, soluongxoa, sl, slcon, tong, tongmoi;
            if (tblCTHDB.Rows.Count == 0)
            {
                MessageBox.Show("Không có dữ liệu!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if ((MessageBox.Show("Bạn có chắc chắn muốn xóa không?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes))
            {
                //Xóa hàng và cập nhật lại số lượng hàng 
                mahangxoa = DataGridView.CurrentRow.Cells["MaHang"].Value.ToString();
                soluongxoa = Convert.ToDouble(DataGridView.CurrentRow.Cells["SoLuong"].Value.ToString());
                thanhtienxoa = Convert.ToDouble(DataGridView.CurrentRow.Cells["ThanhTien"].Value.ToString());
                sql = "DELETE CTHD WHERE MaHDBan=N'" + txtMaHoaDonBan.Text + "' AND MaHang = N'" + mahangxoa + "'";
                ClassKN.RunSQL(sql);
                // Cập nhật lại số lượng cho các mặt hàng
                sl = Convert.ToDouble(ClassKN.GetFieldValues("SELECT SoLuong FROM Hang WHERE MaHang = N'" + mahangxoa + "'"));
                slcon = sl + soluongxoa;
                sql = "UPDATE Hang SET SoLuong =" + slcon + " WHERE MaHang= N'" + mahangxoa + "'";
                ClassKN.RunSQL(sql);
                // Cập nhật lại tổng tiền cho hóa đơn bán
                tong = Convert.ToDouble(ClassKN.GetFieldValues("SELECT TongTien FROM HoaDonBan WHERE MaHDBan = N'" + txtMaHoaDonBan.Text + "'"));
                tongmoi = tong - thanhtienxoa;
                sql = "UPDATE HoaDonBan SET TongTien =" + tongmoi + " WHERE MaHDBan = N'" + txtMaHoaDonBan.Text + "'";
                ClassKN.RunSQL(sql);
                txtTongTien.Text = tongmoi.ToString();
                lbBangChu.Text = "Bằng chữ: " + ClassKN.ChuyenSoSangChu(tongmoi.ToString());
                LoadDataGridView();
            }
        }

        private void HoaDon_Load(object sender, EventArgs e)
        {
            
            bttThemHoaDon.Enabled = true;
            bttLuuHoaDon.Enabled = false;
            bttHuyHoaDon.Enabled = false;
            bttInHoaDon.Enabled = false;
            txtMaHoaDonBan.ReadOnly = true;
            txtTenNhanVien.ReadOnly = true;
            txtTenKhach.ReadOnly = true;
            txtDiaChi.ReadOnly = true;
            txtDienThoai.ReadOnly = true;
            txtTenHang.ReadOnly = true;
            txtDonGiaBan.ReadOnly = true;
            txtThanhTien.ReadOnly = true;
            txtTongTien.ReadOnly = true;
            txtGiamGia.Text = "0";
            txtTongTien.Text = "0";
            ClassKN.FillCombo("SELECT MaKhach, TenKhach FROM Khach", cboMaKhach, "MaKhach", "TenKhach");
            cboMaKhach.SelectedIndex = -1;
            ClassKN.FillCombo("SELECT MaNhanVien, TenNhanVien FROM NhanVien", cboMaNhanVien, "MaNhanVien", "TenNhanVien");
            cboMaNhanVien.SelectedIndex = -1;
            ClassKN.FillCombo("SELECT MaHang, TenHang FROM Hang", cboMaHang, "MaHang", "TenHang");
            cboMaHang.SelectedIndex = -1;
            //Hiển thị thông tin của một hóa đơn được gọi từ form tìm kiếm
            if (txtMaHoaDonBan.Text != "")
            {
                LoadInForHoadon();
                bttHuyHoaDon.Enabled = true;
                bttInHoaDon.Enabled = true;
            }
            LoadDataGridView();
        }
       
        private void cboMaNhanVien_SelectedIndexChanged(object sender, EventArgs e)
        {
            string str;
            if (cboMaNhanVien.Text == "")
                txtTenNhanVien.Text = "";
            // Khi chọn Mã nhân viên thì tên nhân viên tự động hiện ra
            str = "Select TenNhanVien from NhanVien where MaNhanVien =N'" + cboMaNhanVien.SelectedValue + "'";
            txtTenNhanVien.Text = ClassKN.GetFieldValues(str);
        }

        private void cboMaHoaDonBan_DropDown(object sender, EventArgs e)
        {
            ClassKN.FillCombo("SELECT MaHDBan FROM HoaDonBan", cboMaHoaDonBan, "MaHDBan", "MaHDBan");
            cboMaHoaDonBan.SelectedIndex = -1;
        }

        private void txtSoLuong_KeyPress_1(object sender, KeyPressEventArgs e)
        {
            if (((e.KeyChar >= '0') && (e.KeyChar <= '9')) || (Convert.ToInt32(e.KeyChar) == 8))
                e.Handled = false;
            else e.Handled = true;
        }

        private void txtSoLuong_TextChanged_1(object sender, EventArgs e)
        {
            //Khi thay đổi giảm giá thì tính lại thành tiền
            double tt, sl, dg, gg;
            if (txtSoLuong.Text == "")
                sl = 0;
            else
                sl = Convert.ToDouble(txtSoLuong.Text);
            if (txtGiamGia.Text == "")
                gg = 0;
            else
                gg = Convert.ToDouble(txtGiamGia.Text);
            if (txtDonGiaBan.Text == "")
                dg = 0;
            else
                dg = Convert.ToDouble(txtDonGiaBan.Text);
            tt = sl * dg - sl * dg * gg / 100;
            txtThanhTien.Text = tt.ToString();
        }

        private void cboMaHang_TextChanged_1(object sender, EventArgs e)
        {
            string str;
            if (cboMaHang.Text == "")
            {
                txtTenHang.Text = "";
                txtDonGiaBan.Text = "";
            }
            // Khi chọn mã hàng thì các thông tin về hàng hiện ra
            str = "SELECT TenHang FROM Hang WHERE MaHang =N'" + cboMaHang.SelectedValue + "'";
            txtTenHang.Text = ClassKN.GetFieldValues(str);
            str = "SELECT DonGiaBan FROM Hang WHERE MaHang =N'" + cboMaHang.SelectedValue + "'";
            txtDonGiaBan.Text = ClassKN.GetFieldValues(str);
        }

        private void cboMaKhach_TextChanged_1(object sender, EventArgs e)
        {
            string str;
            if (cboMaKhach.Text == "")
            {
                txtTenKhach.Text = "";
                txtDiaChi.Text = "";
                txtDienThoai.Text = "";
            }
            //Khi chọn Mã khách hàng thì các thông tin của khách hàng sẽ hiện ra
            str = "Select tenKhach from Khach where MaKhach = N'" + cboMaKhach.SelectedValue + "'";
            txtTenKhach.Text = ClassKN.GetFieldValues(str);
            str = "Select DiaChi from Khach where MaKhach = N'" + cboMaKhach.SelectedValue + "'";
            txtDiaChi.Text = ClassKN.GetFieldValues(str);
            str = "Select DienThoai from Khach where MaKhach= N'" + cboMaKhach.SelectedValue + "'";
            txtDienThoai.Text = ClassKN.GetFieldValues(str);
        }
    }
}
