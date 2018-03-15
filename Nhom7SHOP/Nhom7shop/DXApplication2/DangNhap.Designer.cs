namespace DXApplication2
{
    partial class DangNhap
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lbTaiKhoan = new System.Windows.Forms.Label();
            this.lbMatKhau = new System.Windows.Forms.Label();
            this.txtTaiKhoan = new System.Windows.Forms.TextBox();
            this.txtMatKhau = new System.Windows.Forms.TextBox();
            this.grbThongTinDangNhap = new System.Windows.Forms.GroupBox();
            this.chkHienMatKhau = new System.Windows.Forms.CheckBox();
            this.lbThongBao = new System.Windows.Forms.Label();
            this.bttDangNhap = new System.Windows.Forms.Button();
            this.bttThoat = new System.Windows.Forms.Button();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.Status = new System.Windows.Forms.ToolStripStatusLabel();
            this.grbThongTinDangNhap.SuspendLayout();
            this.statusStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // lbTaiKhoan
            // 
            this.lbTaiKhoan.AutoSize = true;
            this.lbTaiKhoan.Location = new System.Drawing.Point(25, 26);
            this.lbTaiKhoan.Name = "lbTaiKhoan";
            this.lbTaiKhoan.Size = new System.Drawing.Size(56, 13);
            this.lbTaiKhoan.TabIndex = 0;
            this.lbTaiKhoan.Text = "Tài Khoản";
            // 
            // lbMatKhau
            // 
            this.lbMatKhau.AutoSize = true;
            this.lbMatKhau.Location = new System.Drawing.Point(25, 64);
            this.lbMatKhau.Name = "lbMatKhau";
            this.lbMatKhau.Size = new System.Drawing.Size(53, 13);
            this.lbMatKhau.TabIndex = 2;
            this.lbMatKhau.Text = "Mật Khẩu";
            // 
            // txtTaiKhoan
            // 
            this.txtTaiKhoan.Location = new System.Drawing.Point(87, 23);
            this.txtTaiKhoan.Name = "txtTaiKhoan";
            this.txtTaiKhoan.Size = new System.Drawing.Size(140, 20);
            this.txtTaiKhoan.TabIndex = 1;
            // 
            // txtMatKhau
            // 
            this.txtMatKhau.Location = new System.Drawing.Point(87, 57);
            this.txtMatKhau.Name = "txtMatKhau";
            this.txtMatKhau.Size = new System.Drawing.Size(140, 20);
            this.txtMatKhau.TabIndex = 3;
            this.txtMatKhau.UseSystemPasswordChar = true;
            // 
            // grbThongTinDangNhap
            // 
            this.grbThongTinDangNhap.Controls.Add(this.chkHienMatKhau);
            this.grbThongTinDangNhap.Controls.Add(this.txtMatKhau);
            this.grbThongTinDangNhap.Controls.Add(this.txtTaiKhoan);
            this.grbThongTinDangNhap.Controls.Add(this.lbThongBao);
            this.grbThongTinDangNhap.Controls.Add(this.lbMatKhau);
            this.grbThongTinDangNhap.Controls.Add(this.lbTaiKhoan);
            this.grbThongTinDangNhap.Location = new System.Drawing.Point(16, 12);
            this.grbThongTinDangNhap.Name = "grbThongTinDangNhap";
            this.grbThongTinDangNhap.Size = new System.Drawing.Size(245, 129);
            this.grbThongTinDangNhap.TabIndex = 0;
            this.grbThongTinDangNhap.TabStop = false;
            this.grbThongTinDangNhap.Text = "Thông Tin Đăng Nhập";
            // 
            // chkHienMatKhau
            // 
            this.chkHienMatKhau.AutoSize = true;
            this.chkHienMatKhau.Location = new System.Drawing.Point(87, 83);
            this.chkHienMatKhau.Name = "chkHienMatKhau";
            this.chkHienMatKhau.Size = new System.Drawing.Size(95, 17);
            this.chkHienMatKhau.TabIndex = 4;
            this.chkHienMatKhau.Text = "Hiện mật khẩu";
            this.chkHienMatKhau.UseVisualStyleBackColor = true;
            this.chkHienMatKhau.CheckedChanged += new System.EventHandler(this.chkHienMatKha_CheckedChanged);
            // 
            // lbThongBao
            // 
            this.lbThongBao.AutoSize = true;
            this.lbThongBao.Location = new System.Drawing.Point(15, 100);
            this.lbThongBao.Name = "lbThongBao";
            this.lbThongBao.Size = new System.Drawing.Size(0, 13);
            this.lbThongBao.TabIndex = 1;
            // 
            // bttDangNhap
            // 
            this.bttDangNhap.Location = new System.Drawing.Point(44, 147);
            this.bttDangNhap.Name = "bttDangNhap";
            this.bttDangNhap.Size = new System.Drawing.Size(75, 23);
            this.bttDangNhap.TabIndex = 1;
            this.bttDangNhap.Text = "Đăng Nhập";
            this.bttDangNhap.UseVisualStyleBackColor = true;
            this.bttDangNhap.Click += new System.EventHandler(this.bttDangNhap_Click);
            // 
            // bttThoat
            // 
            this.bttThoat.Location = new System.Drawing.Point(168, 147);
            this.bttThoat.Name = "bttThoat";
            this.bttThoat.Size = new System.Drawing.Size(75, 23);
            this.bttThoat.TabIndex = 2;
            this.bttThoat.Text = "Thoát";
            this.bttThoat.UseVisualStyleBackColor = true;
            this.bttThoat.Click += new System.EventHandler(this.bttThoat_Click);
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.Status});
            this.statusStrip1.Location = new System.Drawing.Point(0, 185);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(305, 22);
            this.statusStrip1.TabIndex = 5;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // Status
            // 
            this.Status.Name = "Status";
            this.Status.Size = new System.Drawing.Size(0, 17);
            // 
            // DangNhap
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(305, 207);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.bttThoat);
            this.Controls.Add(this.bttDangNhap);
            this.Controls.Add(this.grbThongTinDangNhap);
            this.Name = "DangNhap";
            this.Text = "DangNhap";
            this.Load += new System.EventHandler(this.DangNhap_Load);
            this.grbThongTinDangNhap.ResumeLayout(false);
            this.grbThongTinDangNhap.PerformLayout();
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lbTaiKhoan;
        private System.Windows.Forms.Label lbMatKhau;
        private System.Windows.Forms.TextBox txtTaiKhoan;
        private System.Windows.Forms.TextBox txtMatKhau;
        private System.Windows.Forms.GroupBox grbThongTinDangNhap;
        private System.Windows.Forms.Label lbThongBao;
        private System.Windows.Forms.Button bttDangNhap;
        private System.Windows.Forms.Button bttThoat;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel Status;
        private System.Windows.Forms.CheckBox chkHienMatKhau;
    }
}