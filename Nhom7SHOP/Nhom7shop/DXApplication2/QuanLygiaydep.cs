using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace DXApplication2
{
    public partial class QuanLygiaydep : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public QuanLygiaydep()
        {
            InitializeComponent();
            this.Size = new Size(1081, 720);
        }


        private void barbttThoat_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            Class.ClassKN.Disconnect(); //Đóng kết nối
            Application.Exit(); //Thoát
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            Class.ClassKN.Connect(); //Mở kết nối

        }

        private void bttHang_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)// chất liệu nhằm ^^
        {   // bỏ
        }
        private Form KiemTraTonTai(Type ftype)
        {
            foreach (Form f in this.MdiChildren)
            {
                if (f.GetType() == ftype)
                {
                    return f;
                }
            }
            return null;
        }

        private void barbttChatLieu_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            Form frm = this.KiemTraTonTai(typeof(ChatLieu));
            if (frm != null)
            {
                frm.Activate();
            }
            else
            {
                ChatLieu frmChatLieu = new ChatLieu();
                frmChatLieu.MdiParent = this;
                frmChatLieu.Show();
            }

        }

        private void barbttNhanVien_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            Form frm = this.KiemTraTonTai(typeof(NhanVien));
            if (frm != null)
            {
                frm.Activate();
            }
            else
            {
                NhanVien frmNhanVien = new NhanVien();
                frmNhanVien.MdiParent = this;
                frmNhanVien.Show();
            }
        }

        private void barbttKhachHang_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            Form frm = this.KiemTraTonTai(typeof(KhachHang));
            if (frm != null)
            {
                frm.Activate();
            }
            else
            {
                KhachHang frmKhachHang = new KhachHang();
                frmKhachHang.MdiParent = this;
                frmKhachHang.Show();
            }
        }

        private void barbttHang_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            Form frm = this.KiemTraTonTai(typeof(Hang));
            if (frm != null)
            {
                frm.Activate();
            }
            else
            {
                Hang frmHang = new Hang();
                frmHang.MdiParent = this;
                frmHang.Show();
            }
        }

        private void barbttHoaDonBan_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            Form frm = this.KiemTraTonTai(typeof(HoaDon));
            if (frm != null)
            {
                frm.Activate();
            }
            else
            {
                HoaDon frmHoaDon = new HoaDon();
                frmHoaDon.MdiParent = this;
                frmHoaDon.Show();
            }
        }

        private void barbttTimHoaDon_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            Form frm = this.KiemTraTonTai(typeof(TimHoaDon));
            if (frm != null)
            {
                frm.Activate();
            }
            else
            {
                TimHoaDon frmTimHoaDon = new TimHoaDon();
                frmTimHoaDon.MdiParent = this;
                frmTimHoaDon.Show();
            }
        }

        private void barbttDangXuat_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            this.Hide();
            DangNhap frmDangNhap = new DangNhap();
            frmDangNhap.ShowDialog();

        }    
    }
}
